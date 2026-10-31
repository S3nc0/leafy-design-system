// Unwraps WOFF 1.0 files back to the TrueType/OpenType font they wrap. Table data is inflated, not changed.
// Usage: node woff-to-sfnt.cjs <out dir> <file.woff>...
const fs = require('fs'), path = require('path'), zlib = require('zlib');
const [out, ...files] = process.argv.slice(2);
const pad4 = (n) => (n + 3) & ~3;
const checksum = (buf) => { let s = 0; for (let i = 0; i < buf.length; i += 4) s = (s + buf.readUInt32BE(i)) >>> 0; return s; };
for (const file of files) {
  const w = fs.readFileSync(file);
  if (w.toString('ascii', 0, 4) !== 'wOFF') throw new Error(file + ' is not WOFF 1.0');
  const flavor = w.readUInt32BE(4), numTables = w.readUInt16BE(12);
  const tables = [];
  for (let i = 0; i < numTables; i++) {
    const o = 44 + i * 20;
    const t = { tag: w.toString('ascii', o, o + 4), offset: w.readUInt32BE(o + 4), compLength: w.readUInt32BE(o + 8), origLength: w.readUInt32BE(o + 12), origChecksum: w.readUInt32BE(o + 16) };
    const raw = w.subarray(t.offset, t.offset + t.compLength);
    t.data = t.compLength < t.origLength ? zlib.inflateSync(raw) : Buffer.from(raw);
    if (t.data.length !== t.origLength) throw new Error(`${file}: ${t.tag} inflated to ${t.data.length}, expected ${t.origLength}`);
    tables.push(t);
  }
  tables.sort((a, b) => (a.tag < b.tag ? -1 : 1));
  let es = 0; while (1 << (es + 1) <= numTables) es++;
  const searchRange = (1 << es) * 16;
  const header = Buffer.alloc(12 + 16 * numTables);
  header.writeUInt32BE(flavor, 0); header.writeUInt16BE(numTables, 4); header.writeUInt16BE(searchRange, 6);
  header.writeUInt16BE(es, 8); header.writeUInt16BE(numTables * 16 - searchRange, 10);
  let offset = header.length;
  const chunks = [header];
  tables.forEach((t, i) => {
    const r = 12 + i * 16;
    header.write(t.tag, r, 'ascii'); header.writeUInt32BE(t.origChecksum, r + 4); header.writeUInt32BE(offset, r + 8); header.writeUInt32BE(t.origLength, r + 12);
    const padded = Buffer.alloc(pad4(t.origLength)); t.data.copy(padded);
    chunks.push(padded); offset += padded.length;
  });
  const font = Buffer.concat(chunks);
  // Every table's checksum, and the whole-font checksum the head table promises, must match the original font.
  tables.forEach((t) => {
    const b = Buffer.alloc(pad4(t.origLength)); t.data.copy(b);
    if (t.tag === 'head') b.writeUInt32BE(0, 8);
    if (checksum(b) !== t.origChecksum) throw new Error(`${file}: ${t.tag} checksum mismatch`);
  });
  // head.checkSumAdjustment describes the whole file, so it is recomputed for this file's layout (as fontTools does).
  const headIndex = tables.findIndex((t) => t.tag === 'head');
  const headAt = header.readUInt32BE(12 + headIndex * 16 + 8);
  font.writeUInt32BE(0, headAt + 8);
  font.writeUInt32BE((0xb1b0afba - checksum(font)) >>> 0, headAt + 8);
  if (checksum(font) !== 0xb1b0afba) throw new Error(file + ': whole-font checksum still wrong');
  const name = path.basename(file).replace(/\.woff$/i, flavor === 0x4f54544f ? '.otf' : '.ttf');
  fs.writeFileSync(path.join(out, name), font);
  console.log(name.padEnd(28), String(font.length).padStart(7), 'bytes, tables identical, checksum OK');
}

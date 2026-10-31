#!/usr/bin/env node
// Builds the release downloads into dist/, one per platform, each with LICENSE and LICENSES.md:
//   leafy-web-<version>.tgz        the web package, for `npm install <url>`
//   leafy-android-<version>.zip    the Compose library module
//   LeafyDesign-ios-<version>.zip  the Swift package
//
//   node tools/release.mjs
//
// Files come from the last commit, so commit first. The version is leafy-web/package.json's.
// Needs Node 18+, git, npm and zip.
import { execFileSync } from 'node:child_process';
import crypto from 'node:crypto';
import fs from 'node:fs';
import os from 'node:os';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const ROOT = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const git = (...args) => execFileSync('git', args, { cwd: ROOT, maxBuffer: 1 << 28 });
const version = JSON.parse(git('show', 'HEAD:leafy-web/package.json')).version;
if (git('status', '--porcelain', '--untracked-files=no').length) {
  console.warn('Note: there are uncommitted changes; the downloads use the last commit only.');
}

const dist = path.join(ROOT, 'dist');
fs.rmSync(dist, { recursive: true, force: true });
fs.mkdirSync(dist);
const stage = fs.mkdtempSync(path.join(os.tmpdir(), 'leafy-release-'));

// Copies a folder from the last commit into the staging area, with the licence files added.
function extract(treePath, folder) {
  const dest = path.join(stage, folder);
  fs.mkdirSync(dest, { recursive: true });
  execFileSync('tar', ['-x', '-C', dest], { input: git('archive', `HEAD:${treePath}`) });
  for (const file of ['LICENSE', 'LICENSES.md']) fs.writeFileSync(path.join(dest, file), git('show', `HEAD:${file}`));
  return dest;
}

const web = extract('leafy-web', 'leafy-web');
execFileSync('npm', ['pack', '--pack-destination', dist, '--silent'], { cwd: web });

extract('leafy-native/android/leafy', 'leafy');
execFileSync('zip', ['-qrX', path.join(dist, `leafy-android-${version}.zip`), 'leafy'], { cwd: stage });

extract('leafy-native/ios/LeafyDesign', 'LeafyDesign');
execFileSync('zip', ['-qrX', path.join(dist, `LeafyDesign-ios-${version}.zip`), 'LeafyDesign'], { cwd: stage });

fs.rmSync(stage, { recursive: true, force: true });
console.log(`Leafy ${version} (${git('rev-parse', '--short', 'HEAD').toString().trim()})`);
for (const file of fs.readdirSync(dist).sort()) {
  const data = fs.readFileSync(path.join(dist, file));
  console.log(`  dist/${file}  ${(data.length / 1024).toFixed(0)} KB  sha256 ${crypto.createHash('sha256').update(data).digest('hex')}`);
}

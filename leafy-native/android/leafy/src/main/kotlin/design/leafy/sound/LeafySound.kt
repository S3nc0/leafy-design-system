package design.leafy.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

/** A sound that is playing. Call [stop] to end a loop when the state it stands for ends. */
fun interface LeafyPlayingSound {
    fun stop()
}

/**
 * Plays Leafy's Zen sounds and keeps the person's sound preference (on/off and volume, saved in SharedPreferences).
 *
 * Sound repeats what is on screen and never replaces it. Components play their own events;
 * product code plays outcomes, for example `Leafy.sound.play(LeafyCue.Success)` after a save.
 */
class LeafySound private constructor(context: Context?) {
    private val prefs = context?.getSharedPreferences("leafy_sound", Context.MODE_PRIVATE)
    private val pool: SoundPool? = context?.let {
        SoundPool.Builder()
            .setMaxStreams(LeafySoundDefaults.MAX_VOICES)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build(),
            )
            .build()
    }
    private val samples = HashMap<LeafyCue, Int>()
    private val loaded = HashSet<Int>()
    private val lastStream = HashMap<LeafyCue, Int>()
    private val streams = ArrayDeque<Int>()

    /** The person's on/off choice. Defaults to on. */
    var isEnabled by mutableStateOf(prefs?.getBoolean(KEY_ENABLED, true) ?: false)
        private set

    /** Master volume from 0 to 1. Defaults to the `sound-volume` token. */
    var volume by mutableFloatStateOf(prefs?.getFloat(KEY_VOLUME, LeafySoundDefaults.VOLUME) ?: 0f)
        private set

    init {
        if (pool != null && context != null) {
            pool.setOnLoadCompleteListener { _, sampleId, status ->
                if (status == 0) synchronized(loaded) { loaded += sampleId }
            }
            // The Zen files are a few kilobytes each, so they all load at start-up.
            LeafyCue.entries.forEach { samples[it] = pool.load(context, it.rawRes, 1) }
        }
    }

    fun setEnabled(enabled: Boolean) {
        isEnabled = enabled
        prefs?.edit()?.putBoolean(KEY_ENABLED, enabled)?.apply()
        if (!enabled) stopAll()
    }

    fun setVolume(value: Float) {
        volume = value.coerceIn(0f, 1f)
        prefs?.edit()?.putFloat(KEY_VOLUME, volume)?.apply()
    }

    /**
     * Plays a cue. Returns null while sound is off or the file is still loading.
     * A cue that is already sounding restarts instead of stacking.
     */
    fun play(cue: LeafyCue): LeafyPlayingSound? {
        val pool = pool ?: return null
        if (!isEnabled || volume <= 0f) return null
        val sample = samples[cue] ?: return null
        if (!synchronized(loaded) { sample in loaded }) return null
        lastStream.remove(cue)?.let(pool::stop)
        val stream = pool.play(sample, volume, volume, 1, if (cue.loop) -1 else 0, 1f)
        if (stream == 0) return null
        lastStream[cue] = stream
        streams.addLast(stream)
        while (streams.size > 16) streams.removeFirst()
        return LeafyPlayingSound { pool.stop(stream) }
    }

    /** Plays the cue a component event maps to. Events mapped to `none` stay silent. */
    fun event(event: LeafySoundEvent): LeafyPlayingSound? = event.cue?.let(::play)

    fun stopAll() {
        pool?.let { p -> streams.forEach(p::stop) }
        streams.clear()
        lastStream.clear()
    }

    companion object {
        private const val KEY_ENABLED = "enabled"
        private const val KEY_VOLUME = "volume"

        @Volatile
        private var instance: LeafySound? = null

        /** The app-wide player. [design.leafy.theme.LeafyTheme] provides it as `Leafy.sound`. */
        fun get(context: Context): LeafySound =
            instance ?: synchronized(this) {
                instance ?: LeafySound(context.applicationContext).also { instance = it }
            }

        /** A player that never makes a sound, for previews and tests. */
        val Silent = LeafySound(null)
    }
}

val LocalLeafySound = staticCompositionLocalOf { LeafySound.Silent }

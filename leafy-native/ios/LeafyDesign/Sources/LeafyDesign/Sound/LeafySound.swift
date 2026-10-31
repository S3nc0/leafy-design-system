import AVFoundation
import SwiftUI

/// A sound that is playing. Call `stop()` to end a loop when the state it stands for ends.
public struct LeafyPlayingSound {
    private let stopAction: () -> Void

    init(stop: @escaping () -> Void) {
        stopAction = stop
    }

    public func stop() {
        stopAction()
    }
}

/// Plays Leafy's Zen sounds and keeps the person's sound preference.
///
/// Sound repeats what is on screen and never replaces it. Components play their own events;
/// product code plays outcomes, for example `LeafySound.shared.play(.success)` after a save.
@MainActor
public final class LeafySound: ObservableObject {
    public static let shared = LeafySound()

    /// The person's on/off choice, saved in UserDefaults. Defaults to on.
    @Published public private(set) var isEnabled: Bool
    /// Master volume from 0 to 1, saved in UserDefaults. Defaults to the `sound-volume` token.
    @Published public private(set) var volume: Float

    private var players: [LeafyCue: AVAudioPlayer] = [:]
    /// Players that may be sounding, oldest first, for the voice limit.
    private var voices: [AVAudioPlayer] = []
    private let defaults = UserDefaults.standard
    private enum Key {
        static let enabled = "leafy.sound.enabled"
        static let volume = "leafy.sound.volume"
    }

    private init() {
        isEnabled = defaults.object(forKey: Key.enabled) as? Bool ?? true
        volume = defaults.object(forKey: Key.volume) as? Float ?? LeafySoundDefaults.volume
        #if os(iOS)
        // Interface sounds should respect the silent switch and mix with the person's music.
        // Only applied when the app has not chosen a category of its own.
        let session = AVAudioSession.sharedInstance()
        if session.category == .soloAmbient {
            try? session.setCategory(.ambient, options: .mixWithOthers)
        }
        #endif
    }

    public func setEnabled(_ enabled: Bool) {
        isEnabled = enabled
        defaults.set(enabled, forKey: Key.enabled)
        if !enabled { stopAll() }
    }

    public func setVolume(_ value: Float) {
        volume = min(max(value, 0), 1)
        defaults.set(volume, forKey: Key.volume)
        voices.forEach { $0.volume = volume }
    }

    /// Plays a cue. Returns nil while sound is off. A cue that is already sounding restarts instead of stacking.
    @discardableResult
    public func play(_ cue: LeafyCue) -> LeafyPlayingSound? {
        guard isEnabled, volume > 0, let player = player(for: cue) else { return nil }
        voices.removeAll { !$0.isPlaying || $0 === player }
        if player.isPlaying { player.stop() }
        if voices.count >= LeafySoundDefaults.maxVoices { voices.removeFirst().stop() }
        player.currentTime = 0
        player.volume = volume
        player.numberOfLoops = cue.isLoop ? -1 : 0
        guard player.play() else { return nil }
        voices.append(player)
        return LeafyPlayingSound { [weak player] in player?.stop() }
    }

    /// Plays the cue a component event maps to. Events mapped to `none` stay silent.
    @discardableResult
    public func event(_ event: LeafySoundEvent) -> LeafyPlayingSound? {
        guard let cue = event.cue else { return nil }
        return play(cue)
    }

    public func stopAll() {
        voices.forEach { $0.stop() }
        voices.removeAll()
    }

    /// Loads cues ahead of time so the first play has no delay. Call it at launch for the cues you use most.
    public func preload(_ cues: [LeafyCue] = LeafyCue.allCases) {
        cues.forEach { _ = player(for: $0) }
    }

    private func player(for cue: LeafyCue) -> AVAudioPlayer? {
        if let cached = players[cue] { return cached }
        guard let url = Bundle.module.url(forResource: cue.fileName, withExtension: "mp3", subdirectory: "Sounds"),
              let player = try? AVAudioPlayer(contentsOf: url) else { return nil }
        player.prepareToPlay()
        players[cue] = player
        return player
    }
}

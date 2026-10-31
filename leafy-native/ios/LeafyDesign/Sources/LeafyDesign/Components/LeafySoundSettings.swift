import SwiftUI

/// The sound preference panel: a switch for interface sounds, a volume slider and a sample button.
/// Every app that uses Leafy's sounds puts this in its settings.
public struct LeafySoundSettings: View {
    private let title: String
    private let description: String?
    private let showVolume: Bool
    private let sampleCue: LeafyCue
    @ObservedObject private var sound = LeafySound.shared
    @Environment(\.leafyColors) private var c

    public init(title: String = "Interface sounds",
                description: String? = "Short Zen tones confirm the changes you make. Everything you hear is also shown on screen.",
                showVolume: Bool = true, sampleCue: LeafyCue = .success) {
        self.title = title
        self.description = description
        self.showVolume = showVolume
        self.sampleCue = sampleCue
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: LeafySpacing.space5) {
            HStack(alignment: .top, spacing: LeafySpacing.space3) {
                Image(systemName: sound.isEnabled ? "speaker.wave.2.fill" : "speaker.slash.fill")
                    .font(.system(size: 16))
                    .foregroundStyle(c.primary)
                    .frame(width: 20)
                    .accessibilityHidden(true)
                VStack(alignment: .leading, spacing: LeafySpacing.space1) {
                    Text(title).leafyFont(.titleSmall)
                    if let description {
                        Text(description)
                            .leafyFont(.bodySmall)
                            .foregroundStyle(c.onSurfaceVariant)
                            .fixedSize(horizontal: false, vertical: true)
                    }
                }
                Spacer(minLength: 0)
                Toggle(isOn: Binding(
                    get: { sound.isEnabled },
                    set: { on in
                        sound.setEnabled(on)
                        if on { sound.play(.toggleOn) }
                    }
                )) { EmptyView() }
                    .toggleStyle(LeafySwitchToggleStyle(sound: false))
                    .accessibilityLabel(title)
            }
            if showVolume {
                HStack(alignment: .bottom, spacing: LeafySpacing.space4) {
                    VStack(alignment: .leading, spacing: LeafySpacing.space2) {
                        Text("Volume")
                            .leafyFont(.bodySmall)
                            .foregroundStyle(c.onSurfaceVariant)
                        HStack(spacing: LeafySpacing.space3) {
                            // Releasing the slider plays a tick at the new level.
                            Slider(value: Binding(get: { Double(sound.volume) }, set: { sound.setVolume(Float($0)) }),
                                   in: 0...1, step: 0.05) {
                                Text("Volume")
                            } onEditingChanged: { editing in
                                if !editing { sound.play(.volumeChange) }
                            }
                            Text("\(Int((sound.volume * 100).rounded()))")
                                .leafyFont(.bodyMedium)
                                .monospacedDigit()
                                .frame(width: 32, alignment: .trailing)
                                .accessibilityHidden(true)
                        }
                    }
                    LeafyButton("Play sample", systemImage: "play.fill", iconPosition: .leading, variant: .text, size: .sm) {
                        sound.play(sampleCue)
                    }
                }
                .padding(.leading, 20 + LeafySpacing.space3)
                .disabled(!sound.isEnabled)
            }
        }
        .padding(LeafySpacing.space5)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(c.layer01)
    }
}

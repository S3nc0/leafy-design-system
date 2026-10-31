import SwiftUI

/// Leafy's switch: a 48×24 track in `primary` when on, playing `toggle-on` and `toggle-off`.
/// Use it with any `Toggle`: `.toggleStyle(.leafySwitch)`. The system switch with `.leafyTheme()`'s tint also works.
public struct LeafySwitchToggleStyle: ToggleStyle {
    var sound: Bool

    public init(sound: Bool = true) {
        self.sound = sound
    }

    public func makeBody(configuration: Configuration) -> some View {
        LeafyToggleBody(configuration: configuration, sound: sound, kind: .switch)
    }
}

/// Leafy's checkbox: an 18×18 box with 2pt corners, filled `primary` when checked, playing `check` and `uncheck`.
/// iOS has no checkbox of its own; use it for lists of independent options and keep switches for settings.
public struct LeafyCheckboxToggleStyle: ToggleStyle {
    var sound: Bool

    public init(sound: Bool = true) {
        self.sound = sound
    }

    public func makeBody(configuration: Configuration) -> some View {
        LeafyToggleBody(configuration: configuration, sound: sound, kind: .checkbox)
    }
}

public extension ToggleStyle where Self == LeafySwitchToggleStyle {
    static var leafySwitch: LeafySwitchToggleStyle { LeafySwitchToggleStyle() }
}

public extension ToggleStyle where Self == LeafyCheckboxToggleStyle {
    static var leafyCheckbox: LeafyCheckboxToggleStyle { LeafyCheckboxToggleStyle() }
}

private struct LeafyToggleBody: View {
    enum Kind { case `switch`, checkbox }

    let configuration: ToggleStyleConfiguration
    let sound: Bool
    let kind: Kind
    @Environment(\.leafyColors) private var c
    @Environment(\.isEnabled) private var isEnabled
    @Environment(\.accessibilityReduceMotion) private var reduceMotion

    var body: some View {
        Button {
            let next = !configuration.isOn
            if sound {
                switch kind {
                case .switch: LeafySound.shared.event(next ? .toggleOn : .toggleOff)
                case .checkbox: LeafySound.shared.event(next ? .check : .uncheck)
                }
            }
            withAnimation(reduceMotion ? nil : LeafyMotion.productive(.fast02)) {
                configuration.isOn = next
            }
        } label: {
            HStack(spacing: LeafySpacing.space3) {
                control
                configuration.label
                    .leafyFont(.bodyMedium)
                    .foregroundStyle(c.onSurface)
            }
            .opacity(isEnabled ? 1 : LeafyOpacity.disabledContent)
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
        .accessibilityElement(children: .combine)
        .accessibilityValue(configuration.isOn ? (kind == .switch ? "On" : "Checked") : (kind == .switch ? "Off" : "Not checked"))
    }

    @ViewBuilder private var control: some View {
        switch kind {
        case .switch:
            ZStack(alignment: configuration.isOn ? .trailing : .leading) {
                Capsule()
                    .fill(configuration.isOn ? c.primary : c.outline)
                    .frame(width: 48, height: 24)
                Circle()
                    .fill(configuration.isOn ? c.onPrimary : c.surfaceContainerLowest)
                    .frame(width: 18, height: 18)
                    .overlay {
                        if configuration.isOn {
                            Image(systemName: "checkmark")
                                .font(.system(size: 9, weight: .bold))
                                .foregroundStyle(c.primary)
                        }
                    }
                    .padding(3)
            }
        case .checkbox:
            let shape = RoundedRectangle(cornerRadius: LeafyRadius.shapeCornerExtraSmall)
            ZStack {
                shape.fill(configuration.isOn ? c.primary : .clear)
                shape.strokeBorder(configuration.isOn ? c.primary : c.onSurface, lineWidth: 1)
                if configuration.isOn {
                    Image(systemName: "checkmark")
                        .font(.system(size: 11, weight: .bold))
                        .foregroundStyle(c.onPrimary)
                }
            }
            .frame(width: 18, height: 18)
            .padding(3)
        }
    }
}

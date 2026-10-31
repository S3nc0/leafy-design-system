import SwiftUI

/// Button variants by emphasis. Use one `.filled` button per view.
public enum LeafyButtonVariant: Sendable, CaseIterable {
    /// The one main action (Carbon primary).
    case filled
    /// The partner of a filled button, such as "Save draft" beside "Publish" (Carbon secondary).
    case inverse
    /// Important but quieter than filled.
    case tonal
    /// Medium emphasis (Carbon tertiary).
    case outlined
    /// Lowest emphasis: Cancel, inline actions (Carbon ghost).
    case text
    /// Like tonal, over images or busy backgrounds.
    case elevated
}

/// Carbon control heights: small 32, medium 40 (default), large 48.
public enum LeafyControlSize: Sendable, CaseIterable {
    case sm, md, lg

    public var height: CGFloat {
        switch self {
        case .sm: return LeafySize.sizeSm
        case .md: return LeafySize.sizeMd
        case .lg: return LeafySize.sizeLg
        }
    }

    var horizontalPadding: CGFloat { self == .sm ? LeafySpacing.space3 : LeafySpacing.space4 }
}

/// The Leafy button look for any `Button`: `.buttonStyle(.leafy(.outlined, size: .sm))`.
public struct LeafyButtonStyle: ButtonStyle {
    var variant: LeafyButtonVariant
    var size: LeafyControlSize
    var danger: Bool

    public init(_ variant: LeafyButtonVariant = .filled, size: LeafyControlSize = .md, danger: Bool = false) {
        self.variant = variant
        self.size = size
        self.danger = danger
    }

    public func makeBody(configuration: Configuration) -> some View {
        LeafyButtonBody(configuration: configuration, variant: variant, size: size, danger: danger)
    }
}

public extension ButtonStyle where Self == LeafyButtonStyle {
    static func leafy(_ variant: LeafyButtonVariant = .filled, size: LeafyControlSize = .md, danger: Bool = false) -> LeafyButtonStyle {
        LeafyButtonStyle(variant, size: size, danger: danger)
    }
}

private struct LeafyButtonBody: View {
    let configuration: ButtonStyleConfiguration
    let variant: LeafyButtonVariant
    let size: LeafyControlSize
    let danger: Bool
    @Environment(\.leafyColors) private var c
    @Environment(\.isEnabled) private var isEnabled

    private var colors: (container: Color, content: Color) {
        switch variant {
        case .filled: return danger ? (c.error, c.onError) : (c.primary, c.onPrimary)
        case .inverse: return (c.inverseSurface, c.inverseOnSurface)
        case .tonal: return (c.secondaryContainer, c.onSecondaryContainer)
        case .elevated: return (c.surfaceContainerLow, c.primary)
        case .outlined, .text: return (.clear, danger ? c.error : c.primary)
        }
    }

    var body: some View {
        let shape = RoundedRectangle(cornerRadius: LeafyRadius.shapeCornerExtraSmall)
        let filledKind = variant != .outlined && variant != .text
        let content = isEnabled ? colors.content : c.onSurface.opacity(LeafyOpacity.disabledContent)
        let container = isEnabled ? colors.container : (filledKind ? c.onSurface.opacity(LeafyOpacity.disabledContainer) : .clear)
        configuration.label
            .leafyFont(.labelLarge)
            .lineLimit(1)
            .padding(.horizontal, size.horizontalPadding)
            .frame(minWidth: LeafySize.sizeLg, minHeight: size.height, maxHeight: size.height)
            .foregroundStyle(content)
            // The shadow belongs to the container only, so it never blurs the label.
            .background(shape.fill(container).leafyElevation(variant == .elevated && isEnabled ? LeafyElevation.elevation1 : LeafyElevation.elevation0))
            .overlay {
                if variant == .outlined {
                    shape.strokeBorder(isEnabled ? content : c.onSurface.opacity(LeafyOpacity.disabledContainer), lineWidth: 1)
                }
            }
            // The M3 state layer: the content colour at the pressed opacity.
            .overlay(shape.fill(content.opacity(configuration.isPressed ? LeafyOpacity.statePressed : 0)))
            .contentShape(shape)
    }
}

/// Where a button's icon sits. Leafy follows Carbon: the icon trails the label.
public enum LeafyIconPosition: Sendable {
    case leading, trailing
}

/// A Leafy button with a text label and an optional SF Symbol.
public struct LeafyButton: View {
    private let title: String
    private let systemImage: String?
    private let iconPosition: LeafyIconPosition
    private let style: LeafyButtonStyle
    private let sound: Bool
    private let action: () -> Void

    /// - Parameters:
    ///   - title: Verb first, sentence case, one to three words.
    ///   - sound: Plays the `press` event, which is silent by default; `false` keeps it silent even if a product turns press sounds on.
    public init(_ title: String, systemImage: String? = nil, iconPosition: LeafyIconPosition = .trailing,
                variant: LeafyButtonVariant = .filled, size: LeafyControlSize = .md, danger: Bool = false,
                sound: Bool = true, action: @escaping () -> Void) {
        self.title = title
        self.systemImage = systemImage
        self.iconPosition = iconPosition
        self.style = LeafyButtonStyle(variant, size: size, danger: danger)
        self.sound = sound
        self.action = action
    }

    public var body: some View {
        let trailing = systemImage != nil && iconPosition == .trailing
        Button {
            if sound { LeafySound.shared.event(.press) }
            action()
        } label: {
            HStack(spacing: trailing ? LeafySpacing.space8 : LeafySpacing.space2) {
                if let systemImage, !trailing { icon(systemImage) }
                Text(title)
                if let systemImage, trailing { icon(systemImage) }
            }
            // Carbon pulls the padding in on the icon's side.
            .padding(trailing ? .trailing : .leading, systemImage == nil ? 0 : -LeafySpacing.space1)
        }
        .buttonStyle(style)
    }

    private func icon(_ name: String) -> some View {
        Image(systemName: name)
            .font(.system(size: 14, weight: .regular))
            .accessibilityHidden(true)
    }
}

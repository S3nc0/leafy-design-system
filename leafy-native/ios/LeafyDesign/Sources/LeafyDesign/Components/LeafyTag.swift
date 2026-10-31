import SwiftUI

public enum LeafyTagType: Sendable, CaseIterable {
    case neutral, primary, secondary, tertiary, success, warning, error, info
}

/// A short label for status, category or a filter in use. Pair status colours with an icon or a word.
public struct LeafyTag: View {
    private let text: String
    private let type: LeafyTagType
    private let small: Bool
    private let systemImage: String?
    private let sound: Bool
    private let onRemove: (() -> Void)?
    @Environment(\.leafyColors) private var c

    /// - Parameter onRemove: Makes the tag dismissible; removing plays the `remove` event.
    public init(_ text: String, type: LeafyTagType = .neutral, small: Bool = false, systemImage: String? = nil,
                sound: Bool = true, onRemove: (() -> Void)? = nil) {
        self.text = text
        self.type = type
        self.small = small
        self.systemImage = systemImage
        self.sound = sound
        self.onRemove = onRemove
    }

    private var colors: (background: Color, foreground: Color) {
        switch type {
        case .neutral: return (c.surfaceContainerHighest, c.onSurface)
        case .primary: return (c.primaryContainer, c.onPrimaryContainer)
        case .secondary: return (c.secondaryContainer, c.onSecondaryContainer)
        case .tertiary: return (c.tertiaryContainer, c.onTertiaryContainer)
        case .success: return (c.successContainer, c.onSuccessContainer)
        case .warning: return (c.warningContainer, c.onWarningContainer)
        case .error: return (c.errorContainer, c.onErrorContainer)
        case .info: return (c.infoContainer, c.onInfoContainer)
        }
    }

    public var body: some View {
        HStack(spacing: LeafySpacing.space1) {
            if let systemImage {
                Image(systemName: systemImage)
                    .font(.system(size: 12))
                    .accessibilityHidden(true)
            }
            Text(text)
                .leafyFont(.labelMedium)
                .lineLimit(1)
                .truncationMode(.tail)
            if let onRemove {
                Button {
                    if sound { LeafySound.shared.event(.remove) }
                    onRemove()
                } label: {
                    Image(systemName: "xmark")
                        .font(.system(size: 10, weight: .semibold))
                        .frame(width: 20, height: 20)
                        .contentShape(Circle())
                }
                .buttonStyle(.plain)
                .accessibilityLabel("Remove \(text)")
            }
        }
        .padding(.leading, LeafySpacing.space2)
        .padding(.trailing, onRemove == nil ? LeafySpacing.space2 : LeafySpacing.spaceHalf)
        .frame(height: small ? 18 : LeafySize.sizeXs)
        .frame(maxWidth: 208, alignment: .leading)
        .fixedSize()
        .foregroundStyle(colors.foreground)
        .background(Capsule().fill(colors.background))
    }
}

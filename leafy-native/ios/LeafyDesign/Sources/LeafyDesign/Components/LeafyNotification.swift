import SwiftUI

public enum LeafyNotificationKind: Sendable, CaseIterable {
    case info, success, warning, error

    var event: LeafySoundEvent {
        switch self {
        case .info: return .notifyInfo
        case .success: return .notifySuccess
        case .warning: return .notifyWarning
        case .error: return .notifyError
        }
    }

    var systemImage: String {
        switch self {
        case .info: return "info.circle.fill"
        case .success: return "checkmark.circle.fill"
        case .warning: return "exclamationmark.triangle.fill"
        case .error: return "exclamationmark.circle.fill"
        }
    }

    var name: String {
        switch self {
        case .info: return "Information"
        case .success: return "Success"
        case .warning: return "Warning"
        case .error: return "Error"
        }
    }
}

public enum LeafyNotificationVariant: Sendable {
    /// A banner in the page, beside what it is about.
    case inline
    /// A floating message that arrives from elsewhere (360 wide, elevated).
    case toast
}

/// Status messages about the system or a task (Carbon's notification, in Leafy's status roles).
public struct LeafyNotification: View {
    private let kind: LeafyNotificationKind
    private let title: String
    private let message: String?
    private let variant: LeafyNotificationVariant
    private let highContrast: Bool
    private let caption: String?
    private let actionLabel: String?
    private let action: (() -> Void)?
    private let onClose: (() -> Void)?
    private let sound: Bool
    @Environment(\.leafyColors) private var c

    /// - Parameter sound: Toasts play their kind as they appear unless this is `false`;
    ///   inline notifications only play when it is `true`, because they often load with the screen.
    public init(_ kind: LeafyNotificationKind, title: String, message: String? = nil, variant: LeafyNotificationVariant = .inline,
                highContrast: Bool = false, caption: String? = nil, actionLabel: String? = nil, action: (() -> Void)? = nil,
                onClose: (() -> Void)? = nil, sound: Bool? = nil) {
        self.kind = kind
        self.title = title
        self.message = message
        self.variant = variant
        self.highContrast = highContrast
        self.caption = caption
        self.actionLabel = actionLabel
        self.action = action
        self.onClose = onClose
        self.sound = sound ?? (variant == .toast)
    }

    /// Accent, container, on-container and inverse colours for the kind.
    private var roles: (accent: Color, container: Color, onContainer: Color, inverse: Color) {
        switch kind {
        case .info: return (c.info, c.infoContainer, c.onInfoContainer, c.inverseInfo)
        case .success: return (c.success, c.successContainer, c.onSuccessContainer, c.inverseSuccess)
        case .warning: return (c.warning, c.warningContainer, c.onWarningContainer, c.inverseWarning)
        case .error: return (c.error, c.errorContainer, c.onErrorContainer, c.inverseError)
        }
    }

    public var body: some View {
        let r = roles
        HStack(alignment: .top, spacing: LeafySpacing.space4) {
            Image(systemName: kind.systemImage)
                .font(.system(size: 18))
                .foregroundStyle(highContrast ? r.inverse : r.onContainer)
                .padding(.top, 14)
                .accessibilityLabel(kind.name)
            text
            if let actionLabel, let action {
                Button(actionLabel, action: action)
                    .buttonStyle(NotificationActionStyle())
                    .padding(.vertical, LeafySpacing.space2)
            }
            if let onClose {
                Button(action: onClose) {
                    Image(systemName: "xmark")
                        .font(.system(size: 14))
                        .frame(width: LeafySize.sizeLg, height: LeafySize.sizeLg)
                        .contentShape(Rectangle())
                }
                .buttonStyle(.plain)
                .accessibilityLabel("Close notification")
            } else {
                Spacer().frame(width: LeafySpacing.space4)
            }
        }
        .padding(.leading, LeafySpacing.space4)
        .frame(maxWidth: variant == .toast ? 360 : .infinity, minHeight: LeafySize.sizeLg, alignment: .topLeading)
        .foregroundStyle(highContrast ? c.inverseOnSurface : r.onContainer)
        .background(
            Rectangle()
                .fill(highContrast ? c.inverseSurface : r.container)
                .leafyElevation(variant == .toast ? LeafyElevation.elevation3 : LeafyElevation.elevation0)
        )
        .overlay {
            if !highContrast { Rectangle().strokeBorder(r.accent.opacity(0.5), lineWidth: 1) }
        }
        .accessibilityElement(children: .contain)
        .onAppear {
            if sound { LeafySound.shared.event(kind.event) }
        }
    }

    @ViewBuilder private var text: some View {
        if variant == .toast {
            VStack(alignment: .leading, spacing: LeafySpacing.spaceHalf) {
                Text(title).fontWeight(.semibold)
                if let message { Text(message) }
                if let caption {
                    Text(caption)
                        .leafyFont(.bodySmall)
                        .opacity(0.86)
                        .padding(.top, LeafySpacing.space4)
                }
            }
            .leafyFont(.bodyMedium)
            .padding(.top, 15)
            .padding(.bottom, LeafySpacing.space5)
            .frame(maxWidth: .infinity, alignment: .leading)
        } else {
            (Text(title).fontWeight(.semibold) + Text(message.map { " " + $0 } ?? ""))
                .leafyFont(.bodyMedium)
                .padding(.vertical, 15)
                .frame(maxWidth: .infinity, alignment: .leading)
        }
    }
}

private struct NotificationActionStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        let shape = RoundedRectangle(cornerRadius: LeafyRadius.shapeCornerExtraSmall)
        configuration.label
            .leafyFont(.labelLarge)
            .lineLimit(1)
            .padding(.horizontal, LeafySpacing.space4)
            .frame(height: LeafySize.sizeSm)
            .overlay(shape.strokeBorder(.foreground, lineWidth: 1))
            .opacity(configuration.isPressed ? 0.7 : 1)
            .contentShape(shape)
    }
}

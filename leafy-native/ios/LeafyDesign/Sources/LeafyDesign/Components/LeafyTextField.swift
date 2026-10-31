import SwiftUI

/// Carbon's stacked text field in Leafy colours: label above, a `field-01` box with a bottom line,
/// and helper or error text below.
public struct LeafyTextField: View {
    private let label: String
    @Binding private var text: String
    private let placeholder: String?
    private let helperText: String?
    private let errorText: String?
    private let size: LeafyControlSize
    private let systemImage: String?
    private let sound: Bool
    @FocusState private var focused: Bool
    @Environment(\.leafyColors) private var c
    @Environment(\.isEnabled) private var isEnabled

    /// - Parameters:
    ///   - errorText: Puts the field in its error state and replaces the helper text. Say what to do: "Enter a value from 0 to 100".
    ///   - sound: Plays the `typing` event per change, which is silent by default.
    public init(_ label: String, text: Binding<String>, placeholder: String? = nil, helperText: String? = nil,
                errorText: String? = nil, size: LeafyControlSize = .md, systemImage: String? = nil, sound: Bool = true) {
        self.label = label
        _text = text
        self.placeholder = placeholder
        self.helperText = helperText
        self.errorText = errorText
        self.size = size
        self.systemImage = systemImage
        self.sound = sound
    }

    public var body: some View {
        let hasError = errorText != nil
        VStack(alignment: .leading, spacing: LeafySpacing.space2) {
            Text(label)
                .leafyFont(.bodySmall)
                .foregroundStyle(c.onSurfaceVariant)
                .accessibilityHidden(true)
            HStack(spacing: LeafySpacing.space2) {
                if let systemImage {
                    Image(systemName: systemImage)
                        .font(.system(size: 14))
                        .foregroundStyle(c.onSurfaceVariant)
                        .accessibilityHidden(true)
                }
                TextField(label, text: $text, prompt: Text(placeholder ?? "").foregroundColor(c.onSurfaceVariant))
                    .textFieldStyle(.plain)
                    .leafyFont(.bodyMedium)
                    .foregroundStyle(c.onSurface)
                    .focused($focused)
                    .accessibilityHint(errorText ?? helperText ?? "")
                if hasError {
                    Image(systemName: "exclamationmark.circle.fill")
                        .font(.system(size: 16))
                        .foregroundStyle(c.error)
                        .accessibilityLabel("Error")
                }
            }
            .padding(.horizontal, LeafySpacing.space4)
            .frame(height: size.height)
            .background(c.field01)
            .overlay(alignment: .bottom) {
                Rectangle().fill(c.borderStrong).frame(height: 1)
            }
            .overlay {
                if hasError {
                    Rectangle().strokeBorder(c.error, lineWidth: 2)
                } else if focused {
                    Rectangle().strokeBorder(c.focus, lineWidth: 2)
                }
            }
            .clipShape(RoundedRectangle(cornerRadius: LeafyRadius.shapeCornerExtraSmall))
            .contentShape(Rectangle())
            .onTapGesture { focused = true }
            if let message = errorText ?? helperText {
                Text(message)
                    .leafyFont(.bodySmall)
                    .foregroundStyle(hasError ? c.error : c.onSurfaceVariant)
            }
        }
        .opacity(isEnabled ? 1 : LeafyOpacity.disabledContent)
        .onChange(of: text) { _ in
            if sound && focused { LeafySound.shared.event(.typing) }
        }
    }
}

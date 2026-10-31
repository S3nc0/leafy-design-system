import SwiftUI

/// A square-cornered block of content on `layer-01` (Carbon's tile).
/// Base tiles show content, clickable tiles navigate, selectable tiles toggle.
public struct LeafyTile<Content: View>: View {
    private enum Kind {
        case base
        case clickable(() -> Void)
        case selectable(Binding<Bool>)
    }

    private let kind: Kind
    private let sound: Bool
    private let content: Content
    @Environment(\.leafyColors) private var c

    /// A base tile.
    public init(@ViewBuilder content: () -> Content) {
        kind = .base
        sound = true
        self.content = content()
    }

    /// A clickable tile with a trailing arrow.
    public init(action: @escaping () -> Void, @ViewBuilder content: () -> Content) {
        kind = .clickable(action)
        sound = true
        self.content = content()
    }

    /// A selectable tile; selecting plays `select` and clearing plays `deselect`.
    public init(isSelected: Binding<Bool>, sound: Bool = true, @ViewBuilder content: () -> Content) {
        kind = .selectable(isSelected)
        self.sound = sound
        self.content = content()
    }

    public var body: some View {
        switch kind {
        case .base:
            frame(content, trailingPadding: LeafySpacing.space4, bottomPadding: LeafySpacing.space4)
        case .clickable(let action):
            Button(action: action) {
                frame(content, trailingPadding: LeafySpacing.space4, bottomPadding: LeafySpacing.space12)
                    .overlay(alignment: .bottomTrailing) {
                        Image(systemName: "arrow.right")
                            .foregroundStyle(c.primary)
                            .padding(LeafySpacing.space4)
                            .accessibilityHidden(true)
                    }
            }
            .buttonStyle(TilePressStyle())
        case .selectable(let selected):
            Button {
                if sound { LeafySound.shared.event(selected.wrappedValue ? .deselect : .select) }
                selected.wrappedValue.toggle()
            } label: {
                frame(content, trailingPadding: LeafySpacing.space12, bottomPadding: LeafySpacing.space4)
                    .overlay(alignment: .topTrailing) {
                        Image(systemName: selected.wrappedValue ? "checkmark.square.fill" : "square")
                            .font(.system(size: 18))
                            .foregroundStyle(selected.wrappedValue ? c.primary : c.onSurfaceVariant)
                            .padding(LeafySpacing.space4)
                            .accessibilityHidden(true)
                    }
                    .overlay {
                        if selected.wrappedValue { Rectangle().strokeBorder(c.onSurface, lineWidth: 1) }
                    }
            }
            .buttonStyle(TilePressStyle())
            .accessibilityAddTraits(selected.wrappedValue ? .isSelected : [])
        }
    }

    private func frame(_ content: Content, trailingPadding: CGFloat, bottomPadding: CGFloat) -> some View {
        VStack(alignment: .leading, spacing: 0) { content }
            .padding(.top, LeafySpacing.space4)
            .padding(.leading, LeafySpacing.space4)
            .padding(.trailing, trailingPadding)
            .padding(.bottom, bottomPadding)
            .frame(maxWidth: .infinity, minHeight: LeafySize.sizeXl, alignment: .topLeading)
            .foregroundStyle(c.onSurface)
            .background(c.layer01)
    }
}

private struct TilePressStyle: ButtonStyle {
    @Environment(\.leafyColors) private var c

    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .overlay(c.onSurface.opacity(configuration.isPressed ? LeafyOpacity.statePressed : 0))
            .contentShape(Rectangle())
    }
}

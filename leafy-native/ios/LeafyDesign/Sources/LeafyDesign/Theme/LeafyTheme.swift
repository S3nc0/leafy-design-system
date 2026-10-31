import SwiftUI
#if canImport(UIKit)
import UIKit
#endif

public extension Color {
    /// A colour from a 0xRRGGBB value in sRGB.
    init(leafyHex hex: UInt32, opacity: Double = 1) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xFF) / 255,
            green: Double((hex >> 8) & 0xFF) / 255,
            blue: Double(hex & 0xFF) / 255,
            opacity: opacity
        )
    }
}

/// Which Leafy theme to use. `.system` follows Dark Mode and Increase Contrast.
public enum LeafyThemeMode: Sendable {
    case system, light, dark, lightHighContrast, darkHighContrast
}

public extension LeafyColors {
    /// The theme for a colour scheme and contrast: Increase Contrast picks the high-contrast themes.
    static func resolve(colorScheme: ColorScheme, contrast: ColorSchemeContrast) -> LeafyColors {
        switch (colorScheme, contrast) {
        case (.dark, .increased): return .darkHighContrast
        case (.dark, _): return .dark
        case (_, .increased): return .lightHighContrast
        default: return .light
        }
    }
}

private struct LeafyColorsKey: EnvironmentKey {
    static let defaultValue = LeafyColors.light
}

public extension EnvironmentValues {
    /// The active Leafy colours, set by `.leafyTheme()`.
    var leafyColors: LeafyColors {
        get { self[LeafyColorsKey.self] }
        set { self[LeafyColorsKey.self] = newValue }
    }
}

private struct LeafyThemeModifier: ViewModifier {
    let mode: LeafyThemeMode
    @Environment(\.colorScheme) private var colorScheme
    @Environment(\.colorSchemeContrast) private var contrast

    func body(content: Content) -> some View {
        let colors: LeafyColors
        let scheme: ColorScheme
        switch mode {
        case .system:
            colors = .resolve(colorScheme: colorScheme, contrast: contrast)
            scheme = colorScheme
        case .light: colors = .light; scheme = .light
        case .dark: colors = .dark; scheme = .dark
        case .lightHighContrast: colors = .lightHighContrast; scheme = .light
        case .darkHighContrast: colors = .darkHighContrast; scheme = .dark
        }
        return content
            .environment(\.leafyColors, colors)
            .environment(\.colorScheme, scheme)
            .tint(colors.primary)
            .foregroundStyle(colors.onSurface)
            .leafyFont(.bodyMedium)
    }
}

public extension View {
    /// Applies Leafy: colours for the current appearance, the brand tint and the body type style.
    /// Put it once at the root of your app (or of a preview).
    func leafyTheme(_ mode: LeafyThemeMode = .system) -> some View {
        modifier(LeafyThemeModifier(mode: mode))
    }

    /// Applies an elevation level's shadow layers, for example `LeafyElevation.elevation3`.
    func leafyElevation(_ layers: [LeafyShadowLayer]) -> some View {
        layers.reduce(AnyView(self)) { view, layer in
            AnyView(view.shadow(color: .black.opacity(layer.opacity), radius: layer.radius, x: layer.x, y: layer.y))
        }
    }
}

/// One layer of an elevation shadow.
public struct LeafyShadowLayer: Sendable, Equatable {
    public let opacity: Double
    public let radius: CGFloat
    public let x: CGFloat
    public let y: CGFloat

    public init(opacity: Double, radius: CGFloat, x: CGFloat, y: CGFloat) {
        self.opacity = opacity
        self.radius = radius
        self.x = x
        self.y = y
    }
}

#if canImport(UIKit)
public extension UIColor {
    /// A dynamic UIKit colour for a Leafy role that follows Dark Mode and Increase Contrast,
    /// for UIKit appearance APIs such as `UINavigationBar.appearance()`.
    static func leafy(_ role: KeyPath<LeafyColors, Color>) -> UIColor {
        UIColor { traits in
            let colors = LeafyColors.resolve(
                colorScheme: traits.userInterfaceStyle == .dark ? .dark : .light,
                contrast: traits.accessibilityContrast == .high ? .increased : .standard
            )
            return UIColor(colors[keyPath: role])
        }
    }
}
#endif

/// Carbon's motion: productive curves for everyday UI, expressive ones for moments that deserve attention.
public enum LeafyMotion {
    /// Toggles, state changes, menus, chevrons.
    public static func productive(_ duration: LeafyDuration = .fast02) -> Animation {
        .timingCurve(0.2, 0, 0.38, 0.9, duration: duration.rawValue)
    }

    /// A dialog entering, a toast arriving, a page transition.
    public static func expressive(_ duration: LeafyDuration = .moderate02) -> Animation {
        .timingCurve(0.4, 0.14, 0.3, 1, duration: duration.rawValue)
    }
}

/// Carbon's durations, in seconds.
public enum LeafyDuration: Double, Sendable {
    case fast01 = 0.07, fast02 = 0.11, moderate01 = 0.15, moderate02 = 0.24, slow01 = 0.4, slow02 = 0.7
}

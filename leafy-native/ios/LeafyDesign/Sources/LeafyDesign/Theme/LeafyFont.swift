import CoreText
import SwiftUI
#if canImport(UIKit)
import UIKit
#elseif canImport(AppKit)
import AppKit
#endif

/// The three font roles in tokens.json: brand and plain are IBM Plex Sans, mono is IBM Plex Mono.
public enum LeafyFontFamily: Sendable {
    case brand, plain, mono
}

/// One Leafy type style. The values come from tokens.json (see `LeafyTypography.generated.swift`).
public struct LeafyTextStyle: Sendable {
    public let family: LeafyFontFamily
    public let size: CGFloat
    public let lineHeight: CGFloat
    public let weight: Font.Weight
    public let tracking: CGFloat
    public let relativeTo: Font.TextStyle

    public init(family: LeafyFontFamily, size: CGFloat, lineHeight: CGFloat, weight: Font.Weight, tracking: CGFloat, relativeTo: Font.TextStyle) {
        self.family = family
        self.size = size
        self.lineHeight = lineHeight
        self.weight = weight
        self.tracking = tracking
        self.relativeTo = relativeTo
    }

    /// The bundled IBM Plex face for this weight, or the system font at the same size and weight
    /// for a weight Leafy does not ship. Prefer `.leafyFont(_:)`, which also scales the fallback with Dynamic Type.
    public var font: Font {
        font(scaledSize: size)
    }

    func font(scaledSize: CGFloat) -> Font {
        if let name = LeafyFontRegistry.availableName(family, weight) {
            return .custom(name, size: size, relativeTo: relativeTo)
        }
        return .system(size: scaledSize, weight: weight, design: family == .mono ? .monospaced : .default)
    }

    /// Extra space between lines so the line height matches the token (fonts set about 1.2× their size).
    func lineSpacing(scaledSize: CGFloat) -> CGFloat {
        max(0, (lineHeight - size * 1.2) * scaledSize / size)
    }
}

private struct LeafyFontModifier: ViewModifier {
    let style: LeafyTextStyle
    @ScaledMetric private var size: CGFloat

    init(style: LeafyTextStyle) {
        self.style = style
        _size = ScaledMetric(wrappedValue: style.size, relativeTo: style.relativeTo)
    }

    func body(content: Content) -> some View {
        content
            .font(style.font(scaledSize: size))
            .tracking(style.tracking)
            .lineSpacing(style.lineSpacing(scaledSize: size))
    }
}

/// IBM Plex ships inside the package under the SIL Open Font License 1.1 (Resources/Fonts/IBM-Plex-OFL.txt):
/// Sans in Light, Regular, Medium, SemiBold and Bold; Mono in Regular and SemiBold.
public enum LeafyFonts {
    /// Registers the bundled fonts with the system. Leafy does this itself the first time it sets a font;
    /// call it at launch only if UIKit code asks for Plex with `UIFont(name:size:)` before any Leafy view appears.
    public static func registerBundledFonts() {
        _ = registration
    }

    private static let registration: Void = {
        for url in Bundle.module.urls(forResourcesWithExtension: "ttf", subdirectory: "Fonts") ?? [] {
            // Fails harmlessly if the app has already registered the same font.
            CTFontManagerRegisterFontsForURL(url as CFURL, .process, nil)
        }
    }()
}

enum LeafyFontRegistry {
    /// The first installed PostScript name for a face: IBM's builds use short style names (Medm, SmBld),
    /// Google Fonts builds the long ones.
    static func availableName(_ family: LeafyFontFamily, _ weight: Font.Weight) -> String? {
        LeafyFonts.registerBundledFonts()
        return postScriptNames(family, weight).first(where: isAvailable)
    }

    static func postScriptNames(_ family: LeafyFontFamily, _ weight: Font.Weight) -> [String] {
        let base = family == .mono ? "IBMPlexMono" : "IBMPlexSans"
        switch weight {
        case .ultraLight, .thin: return [base + "-Thin"]
        case .light: return [base + "-Light"]
        case .medium: return [base + "-Medm", base + "-Medium"]
        case .semibold: return [base + "-SmBld", base + "-SemiBold"]
        case .bold, .heavy, .black: return [base + "-Bold"]
        default: return [base, base + "-Regular"]
        }
    }

    private static let lock = NSLock()
    private static var cache: [String: Bool] = [:]

    static func isAvailable(_ name: String) -> Bool {
        lock.lock()
        defer { lock.unlock() }
        if let known = cache[name] { return known }
        #if canImport(UIKit)
        let found = UIFont(name: name, size: 12) != nil
        #elseif canImport(AppKit)
        let found = NSFont(name: name, size: 12) != nil
        #else
        let found = false
        #endif
        cache[name] = found
        return found
    }
}

public extension View {
    /// Sets a Leafy type style: font, tracking and line height, scaled with Dynamic Type.
    /// For example `.leafyFont(.titleSmall)`.
    func leafyFont(_ style: LeafyTextStyle) -> some View {
        modifier(LeafyFontModifier(style: style))
    }
}

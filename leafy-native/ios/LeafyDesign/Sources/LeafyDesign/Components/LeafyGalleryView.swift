import SwiftUI

/// Every Leafy component with greenhouse sample content. Show it in a debug menu, or open the preview
/// below in Xcode, to check the theme on a device.
public struct LeafyGalleryView: View {
    @State private var frostAlerts = true
    @State private var nightLighting = false
    @State private var zoneB4 = true
    @State private var dripLine = true
    @State private var zoneName = ""
    @State private var moisture = "120"
    @Environment(\.leafyColors) private var c

    public init() {}

    public var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: LeafySpacing.space5) {
                section("Buttons")
                FlowRow {
                    LeafyButton("Run irrigation", systemImage: "arrow.right") {}
                    LeafyButton("Save draft", variant: .inverse) {}
                    LeafyButton("Calibrate", variant: .tonal) {}
                    LeafyButton("Export CSV", variant: .outlined) {}
                    LeafyButton("Cancel", variant: .text) {}
                    LeafyButton("Delete zone", systemImage: "trash", danger: true) {}
                }

                section("Tags")
                FlowRow {
                    LeafyTag("Healthy", type: .success, systemImage: "checkmark.circle.fill")
                    LeafyTag("Dry soil", type: .warning, systemImage: "exclamationmark.triangle.fill")
                    LeafyTag("Sensor offline", type: .error)
                    LeafyTag("Zone B4", small: true, onRemove: {})
                }

                section("Notifications")
                LeafyNotification(.success, title: "Run complete", message: "Zone B4 watered for 12 minutes.", onClose: {})
                LeafyNotification(.warning, title: "Soil is drying", message: "MS-105 reads 21%.", actionLabel: "Run now", action: {}, onClose: {})
                LeafyNotification(.info, title: "Firmware 2.4 available", message: "Install it during the next quiet hour.",
                                  variant: .toast, caption: "Today, 08:40", onClose: {}, sound: false)

                section("Tiles")
                HStack(alignment: .top, spacing: 1) {
                    LeafyTile {
                        Text("Soil moisture").leafyFont(.titleSmall)
                        Text("32%").leafyFont(.displaySmall)
                    }
                    LeafyTile(isSelected: $dripLine) {
                        Text("Drip line A").leafyFont(.titleSmall)
                    }
                }
                .background(c.surface)

                section("Selection")
                Toggle("Frost alerts", isOn: $frostAlerts).toggleStyle(.leafySwitch)
                Toggle("Night lighting", isOn: $nightLighting).toggleStyle(.leafySwitch)
                Toggle("Zone B4", isOn: $zoneB4).toggleStyle(.leafyCheckbox)

                section("Text fields")
                LeafyTextField("Zone name", text: $zoneName, placeholder: "e.g. B4 Tomatoes", helperText: "Shown on the floor plan")
                LeafyTextField("Target moisture (%)", text: $moisture, errorText: "Enter a value from 0 to 100")

                section("Sound")
                LeafySoundSettings(sampleCue: .complete)
            }
            .padding(LeafySpacing.space4)
        }
        .background(c.surface)
    }

    private func section(_ title: String) -> some View {
        Text(title)
            .leafyFont(.titleMedium)
            .padding(.top, LeafySpacing.space2)
    }
}

/// Lays children out in rows, wrapping when a row is full.
private struct FlowRow: Layout {
    var spacing: CGFloat = LeafySpacing.space2

    func sizeThatFits(proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) -> CGSize {
        let width = proposal.width ?? .infinity
        var x: CGFloat = 0, y: CGFloat = 0, rowHeight: CGFloat = 0, widest: CGFloat = 0
        for view in subviews {
            let size = view.sizeThatFits(.unspecified)
            if x > 0 && x + size.width > width {
                y += rowHeight + spacing
                x = 0
                rowHeight = 0
            }
            x += size.width + spacing
            rowHeight = max(rowHeight, size.height)
            widest = max(widest, x - spacing)
        }
        return CGSize(width: proposal.width ?? widest, height: y + rowHeight)
    }

    func placeSubviews(in bounds: CGRect, proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) {
        var x = bounds.minX, y = bounds.minY, rowHeight: CGFloat = 0
        for view in subviews {
            let size = view.sizeThatFits(.unspecified)
            if x > bounds.minX && x + size.width > bounds.maxX {
                y += rowHeight + spacing
                x = bounds.minX
                rowHeight = 0
            }
            view.place(at: CGPoint(x: x, y: y), proposal: ProposedViewSize(size))
            x += size.width + spacing
            rowHeight = max(rowHeight, size.height)
        }
    }
}

struct LeafyGalleryView_Previews: PreviewProvider {
    static var previews: some View {
        LeafyGalleryView().leafyTheme(.light).previewDisplayName("Light")
        LeafyGalleryView().leafyTheme(.dark).previewDisplayName("Dark")
        LeafyGalleryView().leafyTheme(.darkHighContrast).previewDisplayName("Dark high contrast")
    }
}

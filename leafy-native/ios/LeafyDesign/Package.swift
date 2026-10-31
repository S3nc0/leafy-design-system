// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "LeafyDesign",
    platforms: [.iOS(.v16), .macOS(.v13)],
    products: [
        .library(name: "LeafyDesign", targets: ["LeafyDesign"]),
    ],
    targets: [
        .target(
            name: "LeafyDesign",
            resources: [.copy("Resources/Sounds"), .copy("Resources/Fonts")]
        ),
    ]
)

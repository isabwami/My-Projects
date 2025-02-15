//
//  ContentView.swift
//  Devices
//
//  Created by Immanuel Sabwami on 6/14/24.
//

import SwiftUI

struct ContentView: View {
    
    var model: Devices
    var body: some View {
        List {
            Section("My Devices") {
                ForEach(model.myDevices) { device in
                    row(device: device)
                }
            }
            Section("Other Devices") {
                ForEach(model.otherDevices) { device in
                    row(device: device)
                }
            }
        }
    }
    
    private func row(device: DeviceModel) -> some View {
        return deviceRowView(device: device)
            .swipeActions(edge: .leading) {
                Button("Favorite", systemImage: "star.fill") {
                    device.markFavorite()
                }
                .tint(.yellow)
            }
    }
    
}

struct deviceRowView: View {
    var device: DeviceModel
    var body: some View {
        HStack {
            deviceIcon
            VStack(alignment: .leading) {
                HStack(alignment: .firstTextBaseline) {
                    Text(device.name)
                            .fontWeight(.semibold)
                    
                    if device.isFavorite {
                        Image(systemName: "star.fill")
                            .foregroundStyle(.orange)
                    }
                }
                
                Text(device.model)
                    .font(.subheadline)
                    .foregroundStyle(.secondary)
            }
            Spacer()
        }
    }
    
    private var deviceIcon: some View {
        Image(device.image)
            .resizable()
            .scaledToFit()
            .frame(width: 40, height: 40)
            .clipShape(.circle)
            .shadow(radius: 5)
            .overlay {
                Circle().stroke(.blue, lineWidth: 1.5)
            }
    }
}

@Observable
class DeviceModel: Identifiable {
    
    enum deviceType {
        case iPhone
        case iPad
        case appleWatch
        case mac
    }
    
    var id = UUID()
    var name: String
    var type: deviceType
    var model: String
    var image: String
    var isFavorite: Bool = false
    
    init(name: String, model: String, type: deviceType, image: String) {
        self.name = name
        self.model = model
        self.type = type
        self.image = image
    }
    
    func markFavorite() {
        isFavorite = true
    }
    
}

@Observable
class Devices {
    var myDevices: [DeviceModel] = [
        DeviceModel(name: "Immanuel's iPhone", model: "iPhone 14 Pro", type: .iPhone, image: "iPhone14Pro"),
        DeviceModel(name: "Immanuel's iPad", model: "iPad Pro", type: .iPad, image: "iPadPro"),
        DeviceModel(name: "Immanuel's MacBook Pro", model: "MacBook Pro 16\"", type: .mac, image: "MacBookPro"),
        DeviceModel(name: "Immanuel's Apple Watch", model: "Apple Watch Ultra", type: .appleWatch, image: "AppleWatchUltra")
    ]
    
    var otherDevices: [DeviceModel] = [
        DeviceModel(name: "Bob's iPhone", model: "iPhone 15 Plus", type: .iPhone, image: "iPhone14Pro"),
        DeviceModel(name: "Joe's iPhone", model: "iPhone 15 Pro", type: .iPhone, image: "iPhone14Pro"),
        DeviceModel(name: "Joe's Apple Watch", model: "Apple Watch Ultra", type: .appleWatch, image: "AppleWatchUltra"),
        DeviceModel(name: "Emily's MacBook Air", model: "MacBook Air 15\"", type: .mac, image: "MacBookPro"),
        DeviceModel(name: "Jan's iPad", model: "iPad Air 13\"", type: .iPad, image: "iPadPro"),
        DeviceModel(name: "Richard's iPhone", model: "iPhone 14 Pro", type: .iPhone, image: "iPhone14Pro")
    ]
}

var deviceCollection: Devices = Devices()

#Preview {
    ContentView(model: deviceCollection)
}

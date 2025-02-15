//
//  Landmark.swift
//  Landmarks
//
//  Created by Immanuel Sabwami on 6/15/24.
//

import Foundation
import SwiftUI
import CoreLocation

struct Landmark: Hashable, Codable, Identifiable {

    var id: Int
    var name: String
    var park: String
    var state: String
    var description: String
    
    private var imageName: String
    
    var image: Image {
        return Image(imageName)
    }
    
    private var coordinates: Coordinates
    var locationCoordinate: CLLocationCoordinate2D {
        return CLLocationCoordinate2D(latitude: coordinates.latitude,longitude: coordinates.longitude)
    }
    
    struct Coordinates: Hashable, Codable {
        var longitude: Double
        var latitude: Double
    }
    
    
    
}

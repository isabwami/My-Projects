//
//  MapView.swift
//  Landmarks
//
//  Created by Immanuel Sabwami on 6/15/24.
//

import SwiftUI
import MapKit

// Map View
struct MapView: View {
    var body: some View {
        Map(initialPosition: .region(region)) // this view only contains a map that takes in an initial position
    }
    
    // specify a region
    private var region: MKCoordinateRegion {
        MKCoordinateRegion (
            center: CLLocationCoordinate2D(latitude: 34.011_286, longitude: -116.166_868),
            span: MKCoordinateSpan(latitudeDelta: 0.2, longitudeDelta: 0.2)
        ) // function of MapKit that specifies the center of a region and how far it spans
    }
}

#Preview {
    MapView()
}

//
//  CircleImage.swift
//  Landmarks
//
//  Created by Immanuel Sabwami on 6/15/24.
//

import SwiftUI

// Circle Image
struct CircleImage: View {
    var body: some View {
        Image("turtlerock") // image in the assets file
            .clipShape(Circle()) // mask the shape
            .overlay {
                Circle()
                    .stroke(.white, lineWidth: 4) // overlay a circle with a white stroke
            }
            .shadow(radius: 7) // add a drop shadow
    }
}

#Preview {
    CircleImage()
}

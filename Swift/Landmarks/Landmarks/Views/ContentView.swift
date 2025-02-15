//
//  ContentView.swift
//  Landmarks
//
//  Created by Immanuel Sabwami on 6/14/24.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        // vertical stack containing all views in this screen
        VStack {
            MapView()
                .frame(height: 300) // shrink the map to a height of 300 pixels and automatic width
            
            CircleImage()
                .offset(y: -130) // move circle image up 130 pixels
                .padding(.bottom, -130) // pad the bottom of the image by 130 pixels
            
            // text stack
            VStack(alignment: .leading) {
                Text("Turtle Rock")
                    .font(.title)
                    .fontWeight(.bold)
                HStack {
                    Text("Joshua Tree National Park")
                    
                    Spacer() // make stack extend its full width
                    
                    Text("California") // from the right
                }
                .font(.subheadline)
                .foregroundStyle(.secondary)
                
                Divider()
                    .padding(.bottom, 10)
                
                Text("About Turtle Rock")
                    .font(.title2)
                    .fontWeight(.semibold)
                    .padding(.bottom, 5)
                
                Text("Descriptive text goes here.")
                
            }
            .padding()
            
            Spacer()
        }
    }
}

#Preview {
    ContentView()
}

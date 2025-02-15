//
//  ColorsBootcamp.swift
//  Swift UI Training
//
//  Created by Immanuel Sabwami on 6/14/24.
//

import SwiftUI

struct ColorsBootcamp: View {
    var body: some View {
        RoundedRectangle(cornerRadius: 25)
            //.fill(Color.primary) // Color.primary is black for light mode and white for darkmode and changes automatically
            .fill(
                //Color.primary
                //Color(UIColor.secondarySystemBackground) // UIkit colors can be used using UIColor. which are useful for system colors
                Color("CustomColor")
            )
        
            .frame(width: 300, height: 200)
            //.shadow(radius: /*@START_MENU_TOKEN@*/10/*@END_MENU_TOKEN@*/) // adds a shadow
            .shadow(color: Color("CustomColor").opacity(0.3),
                    radius: 10,
                    x: 0.0,
                    y: 0.0
            )
        
    }
}

#Preview {
    ColorsBootcamp()
}

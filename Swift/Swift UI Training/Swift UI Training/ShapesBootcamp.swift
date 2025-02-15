//
//  ShapesBootcamp.swift
//  Swift UI Training
//
//  Created by Immanuel Sabwami on 6/14/24.
//

import SwiftUI

struct ShapesBootcamp: View {
    var body: some View {
        //Circle()
            //.fill(Color.blue) //Color
            //.foregroundColor(.pink)
            
            //.stroke() // outline
            //.stroke(Color.red)
            //.stroke(Color.red, lineWidth: 30)
            //.stroke(Color.orange, style: StrokeStyle(lineWidth: 20, lineCap: .butt, dash: [10]))
            
            //.trim(from: 0.0, to: 0.5) // visibility
          
        //Ellipse()
            //.frame(width: 200, height: 100)
        
        //Capsule(style: .circular)
            //.frame(width: 200, height: 100)
        
        //Rectangle()
            //.frame(width: 200, height: 100)
        
        RoundedRectangle(cornerRadius: 10)
            .frame(width: 300, height: 200)
        
    }
}

#Preview {
    ShapesBootcamp()
}

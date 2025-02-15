//
//  TextBootcamp.swift
//  Swift UI Training
//
//  Created by Immanuel Sabwami on 6/14/24.
//

import SwiftUI

struct TextBootcamp: View {
    var body: some View {
        Text("Hello, World! This is the Swiftful Thinking Bootcamp. I am really enjoying this course and learning alot.")
            //.font(.body)
            //.fontWeight(.medium)
            //.bold()
            //.underline()
            //.underline(true, color: Color.red)
            //.italic()
            //.strikethrough(true, color: Color.green)
        
            // note: this below is acceptable, but font does not scale with system setting
            //.font(.system(size: 24, weight: .semibold, design: .default))
            
            // if baseline offset or kerning is used, they must be before multiline text alignment
            //.baselineOffset(50) // space between lines
            //.kerning(1) // space between characters
            .multilineTextAlignment(.leading)
        
            .foregroundColor(.red) // text color
        
            .frame(width: 200, height: 100, alignment: .center) // text frame size
            .minimumScaleFactor(0.1) // the font can scale down to 10% of the font size to fit in the box
        
    }
}

#Preview {
    TextBootcamp()
}

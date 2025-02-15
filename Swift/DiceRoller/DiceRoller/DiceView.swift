//
//  DiceView.swift
//  DiceRoller
//
//  Created by Immanuel Sabwami on 6/27/24.
//

import SwiftUI

struct DiceView: View {
    
    @State private var numberOfPips: Int = 1 // state properties should always be private
    
    public func setNumPips(numPips: Int) {
        numberOfPips = 1
    }
    
    var body: some View {
        VStack {
            Image(systemName: "die.face.\(numberOfPips).fill")
                .resizable() // typically SFSymbols are resized with .font() to match surrounding content
                .frame(maxWidth: 100, maxHeight: 100)// here it is just the image so .resizable() and .frame() are ok
                .aspectRatio(1, contentMode: .fit)
                .foregroundStyle(.black, .white) // sets primary color to black and secondary to white
            
            Button("Roll") {
                withAnimation {
                    numberOfPips = Int.random(in: 1...6)
                }
            }
            .buttonStyle(.bordered)
            
        }
    }
}

#Preview {
    DiceView()
}

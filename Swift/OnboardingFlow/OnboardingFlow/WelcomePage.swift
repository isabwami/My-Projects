//
//  WelcomePage.swift
//  OnboardingFlow
//
//  Created by Immanuel Sabwami on 6/20/24.
//

import SwiftUI

struct WelcomePage: View {
    var body: some View {
        VStack() {
            
            ZStack {
                RoundedRectangle(cornerRadius: 30)
                    .frame(width: 150, height: 150)
                    .foregroundStyle(.tint)
             
                Image(systemName: "macbook.and.ipad")
                    .font(.system(size: 70))
                    .foregroundStyle(.white)
                
            }
            
            Text("Welcome to MyApp")
                .font(.title)
                .fontWeight(.semibold)
                .padding(.top)
                //.border(.black, width: 1.5)
            
            Text("Description text")
                .font(.title2)
                .multilineTextAlignment(.center)
                //.border(.black, width: 1.5)
        }
        //.border(.orange, width: 1.5)
        .padding()
        //.border(.purple, width: 1.5)
    }
        
}

#Preview {
    WelcomePage()
}

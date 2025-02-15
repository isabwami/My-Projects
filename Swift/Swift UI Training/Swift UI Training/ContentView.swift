//
//  ContentView.swift
//  Swift UI Training
//
//  Created by Immanuel Sabwami on 6/13/24.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        
        // Vertical Stack
        VStack {
            Image(systemName: "airpodspro")
                .imageScale(.large)
                .foregroundStyle(.tint)
            Text("AirPods Pro")
                .bold()
                .foregroundStyle(Color("CustomColor"))
        }
        .padding()
    }
}

#Preview {
    ContentView()
}

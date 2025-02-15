//
//  ContentView.swift
//  Weather
//
//  Created by Immanuel Sabwami on 6/19/24.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        HStack {
            DayForecast(day: "Mon", high: 70, low: 50, isRainy: false)
            
            DayForecast(day: "Mon", high: 74, low: 55, isRainy: true)
        }
    }
}

struct DayForecast: View {
    
    var day: String
    var high: Int
    var low: Int
    var isRainy: Bool
    
    var body: some View {
        
        var systemIcon: String {
            if isRainy {
                return "cloud.rain.fill"
            } else {
                return "sun.max.fill"
            }
        }
        
        var iconColor: Color {
            if isRainy {
                return Color.blue
            } else {
                return Color.yellow
            }
        }
        
        VStack {
            
            Text(day)
                .font(Font.headline)
            
            Image(systemName: systemIcon)
                .foregroundStyle(iconColor)
                .font(Font.largeTitle)
                .padding(5)
            
            Text("High: \(high)")
                .fontWeight(.semibold)
            
            Text("Low: \(low)")
                .fontWeight(.medium)
                .foregroundStyle(.secondary)
            
        }
        .padding()
    }
}

#Preview {
    ContentView()
}

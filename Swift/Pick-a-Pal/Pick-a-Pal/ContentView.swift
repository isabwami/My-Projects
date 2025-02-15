//
//  ContentView.swift
//  Pick-a-Pal
//
//  Created by Immanuel Sabwami on 6/27/24.
//

import SwiftUI

struct ContentView: View {
    
    @State private var names: [String] = ["Elisha", "Andre", "Jasmine", "Po-Chun"]
    @State private var nameToAdd: String = ""
    @State private var pickedName: String = ""
    @State private var shouldRemovePickedName: Bool = false
    
    var body: some View {
        VStack {
            Text(pickedName.isEmpty ? " " : pickedName)
            
            List {
                ForEach(names, id: \.self) { name in
                    Text(name)
                }
            }
            
            TextField("Add Name", text: $nameToAdd) // $ binds the nameToAdd variable to this text field
                .autocorrectionDisabled()
                .onSubmit {
                    if !nameToAdd.isEmpty {
                        names.append(nameToAdd)
                        nameToAdd = ""
                    }
                }
            
            Divider()
            
            Toggle("Remove when picked? ", isOn: $shouldRemovePickedName)
            
            Button("Pick Random Name") {
                if let randomName = names.randomElement() {
                    pickedName = randomName
                    
                    if shouldRemovePickedName {
                        names.removeAll { name in
                            return (name == randomName)
                        }
                    }
                    
                } else {
                    pickedName = ""
                }
            }
        }
        .padding()
    }
}

#Preview {
    ContentView()
}

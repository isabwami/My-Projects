//
//  LandmarkList.swift
//  Landmarks
//
//  Created by Immanuel Sabwami on 6/15/24.
//

import SwiftUI

struct LandmarkList: View {
    var body: some View {
        List(landmarks) { landmark in
            LandmarkRow(landmark: landmark)
        }
    }
}

#Preview {
    LandmarkList()
}

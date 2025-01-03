//
//  FindMeeting.swift
//  iosApp
//
//  Created by 王世安 on 2025/1/3.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct FindMeeting: View {
    @EnvironmentObject private var timeZoneItems: TimeZoneItems
    
    var body: some View {
        NavigationView {
            
        }
    }
}

#Preview {
    FindMeeting().environmentObject(TimeZoneItems())
}

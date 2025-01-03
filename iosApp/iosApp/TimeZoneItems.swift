//
//  Untitled.swift
//  iosApp
//
//  Created by 王世安 on 2025/1/3.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

class TimeZoneItems: ObservableObject {
    @Published var timezones: [String] = []
    @Published var selectedTimeZones = Set<String>()
    
    init() {
        self.timezones = TimeZoneHelperImpl().getTimeZoneStrings()
    }
}

//
//  TimezoneDialog.swift
//  iosApp
//
//  Created by 王世安 on 2025/1/3.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

extension String: Identifiable {
    public var id: String { return self }
}

struct TimezoneDialog: View {
    @State private var searchText: String = ""
    @EnvironmentObject var timeZoneItems: TimeZoneItems
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        VStack {
            Searchbar(text: $searchText)
            List(selection: $timeZoneItems.selectedTimeZones) {
                ForEach(
                    timeZoneItems.timezones.filter {
                        searchText.isEmpty ? true : $0.lowercased().contains(searchText.lowercased())
                    },
                    id: \.self
                ) {
                    timeZone in
                    HStack {
                        Image(systemName: timeZoneItems.selectedTimeZones.contains(timeZone) ? "checkmark.circle" : "circle")
                            .onTapGesture {
                                selectTimeZone(timeZone: timeZone)
                            }
                        Text(timeZone)
                            .onTapGesture {
                                selectTimeZone(timeZone: timeZone)
                            }
                    }
                }
            }
        }
        Button("Dismiss") {
            presentationMode.wrappedValue.dismiss()
        }
    }
    
    func selectTimeZone(timeZone: String) {
        if timeZoneItems.selectedTimeZones.contains(timeZone) {
            timeZoneItems.selectedTimeZones.remove(timeZone)
        } else {
            timeZoneItems.selectedTimeZones.insert(timeZone)
        }
    }
}

#Preview {
    TimezoneDialog().environmentObject(TimeZoneItems())
}

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
    private var timeZoneHelper = TimeZoneHelperImpl()
    
    @State private var meetingHours: [Int] = []
    @State private var hoursDialogVisible = false
    @State private var startDate = Calendar.current.date(
        bySettingHour: 8,
        minute: 0,
        second: 0,
        of: Date()
    )!
    @State private var endDate = Calendar.current.date(
        bySettingHour: 17,
        minute: 0,
        second: 0,
        of: Date()
    )!
    
    var body: some View {
        NavigationView {
          VStack {
            Spacer()
              .frame(height: 8)
              Form {
                Section(header: Text("Time Range")) {
                    DatePicker("Start Time", selection: $startDate, displayedComponents: .hourAndMinute)
                    DatePicker("End Time", selection: $endDate, displayedComponents: .hourAndMinute)
                }
                Section(header: Text("Time Zones")) {
                  ForEach(
                    Array(timeZoneItems.selectedTimeZones),
                    id: \.self
                  ) {  timezone in
                    HStack {
                      Text(timezone)
                      Spacer()
                    }
                  }
                }
              } // Form
              Spacer()
              Button(action: {
                  meetingHours.removeAll()
                  let startHour = Calendar.current.component(.hour, from: startDate)
                  let endHour = Calendar.current.component(.hour, from: endDate)
                  let hours = timeZoneHelper.search(
                    startHour: Int32(startHour),
                    endHour: Int32(endHour),
                    timeZoneStringList: Array(timeZoneItems.selectedTimeZones)
                  )
                  let hourInts = hours.map { kotinHour in
                      Int(truncating: kotinHour)
                  }
                  meetingHours += hourInts
                  hoursDialogVisible = true
              }, label: {
                Text("Search")
                  .foregroundColor(Color.black)
              })
              Spacer()
                .frame(height: 8)
          } // VStack
          .navigationTitle("Find Meeting Time")
          .sheet(isPresented: $hoursDialogVisible) {
            HourSheet(hours: $meetingHours)
          }
        }
    }
}

#Preview {
    FindMeeting().environmentObject(TimeZoneItems())
}

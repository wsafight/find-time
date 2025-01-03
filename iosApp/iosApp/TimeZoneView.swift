//
//  TimezoneView.swift
//  iosApp
//
//

import SwiftUI
import shared

struct TimeZoneView: View {
    @EnvironmentObject private var timeZoneItems: TimeZoneItems
    private var timezoneHelper = TimeZoneHelperImpl()
    @State private var currentDate = Date()
    let timer = Timer.publish(every: 1000, on: .main, in: .common).autoconnect()
    @State private var showTimezoneDialog = false
    
    var body: some View {
        NavigationView {
          VStack {
            TimeCard(
                timeZone: timezoneHelper.currentTimeZone(),
                time: DateFormatter.short.string(from: currentDate),
                date: DateFormatter.long.string(from: currentDate)
            )
            Spacer()
              List {
                ForEach(Array(
                    timeZoneItems.selectedTimeZones),
                    id: \.self
                ) { timeZone in
                  NumberTimeCard(
                    timeZone: timeZone,
                    time: timezoneHelper.getTime(timeZoneId: timeZone),
                    hours: "\(timezoneHelper.hoursFromTimeZone(otherTimeZoneId: timeZone)) hours from local",
                    date: timezoneHelper.getDate(timeZoneId: timeZone)
                  ).withListModifier()
                }
                .onDelete(perform: deleteItems)
              }
              .listStyle(.plain)
              Spacer()

          }
          .onReceive(timer) { input in
            currentDate = input
          }
          .navigationTitle("World Clocks")
          .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
              Button(action: {
                  showTimezoneDialog = true
              }) {
                  Image(systemName: "plus")
                      .frame(alignment: .trailing)
                      .foregroundColor(.black)
              }
            }
          }
        }
        .fullScreenCover(isPresented: $showTimezoneDialog) {
          TimezoneDialog()
            .environmentObject(timeZoneItems)
        }
    }
    func deleteItems(at offsets: IndexSet) {
        let timezoneArray = Array(timeZoneItems.selectedTimeZones)
        for index in offsets {
            let element = timezoneArray[index]
            timeZoneItems.selectedTimeZones.remove(element)
        }
    }
}

#Preview {
    TimeZoneView().environmentObject(TimeZoneItems())
}

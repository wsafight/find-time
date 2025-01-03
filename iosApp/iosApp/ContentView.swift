import SwiftUI
import shared

struct ContentView: View {
    @StateObject private var timezoneItems = TimeZoneItems();
    
    var body: some View {
        TabView {
            TimeZoneView()
                .tabItem {
                    Label("Time Zones", systemImage: "network")
                }
            FindMeeting()
                .tabItem {
                    Label("Find Meeting", systemImage: "clock")
                }
        }
        .accentColor(Color.white)
        .environmentObject(timezoneItems)
    }
}

#Preview {
    ContentView()
}

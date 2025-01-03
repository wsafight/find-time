//
//  HourSheet.swift
//  iosApp
//
//  Created by 王世安 on 2025/1/3.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct HourSheet: View {
    @Binding var hours: [Int]
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        NavigationView {
            VStack {
                List {
                    ForEach(
                        hours,
                        id: \.self
                    ) { hour in
                        Text("\(hour)")
                    }
                }
            }
            .navigationTitle("Found Meeting Hours")
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(
                        action: {
                            presentationMode.wrappedValue.dismiss()
                        }
                    ) {
                        Text("Dismiss")
                            .frame(alignment: .trailing)
                            .foregroundColor(.black)
                    }
                }
            }
        }
    }
}

#Preview {
    HourSheet(hours: .constant([8, 9, 10]))
}

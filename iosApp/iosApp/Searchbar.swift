//
//  Searchbar.swift
//  iosApp
//
//  Created by 王世安 on 2025/1/3.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct Searchbar: View {
    @Binding var text: String
    @State private var isEditing = false
    
    var body: some View {
      VStack {
        Spacer()
          .frame(height: 8)
        HStack {
          TextField("Search ...", text: $text)
            .padding(7)
            .padding(.horizontal, 25)
            .background(Color(.systemGray6))
            .cornerRadius(8)
            .overlay(
              HStack {
                Image(systemName: "magnifyingglass")
                  .foregroundColor(.gray)
                  .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                  .padding(.leading, 8)

                if isEditing {
                  Button(
                    action: { self.text = "" },
                    label: {
                      Image(systemName: "multiply.circle.fill")
                        .foregroundColor(.gray)
                        .padding(.trailing, 8)
                    })
                }
              }
            )
            .padding(.horizontal, 10)
            .onTapGesture {
              self.isEditing = true
            }

          if isEditing {
            Button(
              action: {
                self.isEditing = false
                self.text = ""
              },
              label: {
                Text("Cancel")
              })
              .padding(.trailing, 10)
              .transition(.move(edge: .trailing))
              .animation(.default)
          }
        }
      }
    }
}

#Preview {
    Searchbar(text: .constant("Test"))
}

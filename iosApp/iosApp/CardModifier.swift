//
//  SwiftUIView.swift
//  iosApp
//
//  Created by 王世安 on 2025/1/3.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct CardModifier: ViewModifier {
    func body(content: Content) -> some View {
        content.cornerRadius(16)
            .shadow(color: Color.black.opacity(0.2), radius: 16, x: 0, y: 0)
    }
}


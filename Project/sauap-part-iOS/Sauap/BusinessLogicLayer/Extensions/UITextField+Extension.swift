//
//  UITextField+Extension.swift
//  Sauap
//
//  Created by Aidana on 28.03.2022.
//

import UIKit

extension UITextField {
    func togglePasswordVisibility() {
        let existingTintColor = tintColor
        tintColor = .clear
        DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(200)) {
            self.tintColor = existingTintColor }
        isSecureTextEntry = !isSecureTextEntry

        if let existingText = text, isSecureTextEntry {
            /* When toggling to secure text, all text will be purged if the user
             continues typing unless we intervene. This is prevented by first
             deleting the existing text and then recovering the original text. */
            deleteBackward()

            if let textRange = textRange(from: beginningOfDocument, to: endOfDocument) {
                replace(textRange, withText: existingText)
            }
        }

        /* Reset the selected text range since the cursor can end up in the wrong
         position after a toggle because the text might vary in width */
        if let existingSelectedTextRange = selectedTextRange {
            selectedTextRange = nil
            selectedTextRange = existingSelectedTextRange
        }
    }
}

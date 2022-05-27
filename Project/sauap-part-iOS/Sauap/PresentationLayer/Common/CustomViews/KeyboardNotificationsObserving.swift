//
//  KeyboardNotificationsObserving.swift
//  Sauap
//
//  Created by Aidana on 29.04.2022.
//

import Foundation
import UIKit

/// Отслеживает показ и скрытие клавиатуры
@objc public protocol KeyboardNotificationsObserving {
    func keyboardWillShow(notification: Notification)
    func keyboardWillHide(notification: Notification)
}

public extension KeyboardNotificationsObserving {
    func startKeyboardNotificationsObserving() {
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(keyboardWillShow),
                                               name: UIResponder.keyboardWillShowNotification,
                                               object: nil)
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(keyboardWillHide),
                                               name: UIResponder.keyboardWillHideNotification,
                                               object: nil)
    }
}


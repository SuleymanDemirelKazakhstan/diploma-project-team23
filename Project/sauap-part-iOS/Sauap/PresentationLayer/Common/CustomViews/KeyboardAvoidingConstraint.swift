//
//  KeyboardAvoidingConstraint.swift
//  Sauap
//
//  Created by Aidana on 29.04.2022.
//

import UIKit

/// Автоматически увеличивается и уменьшается на высоту клавиатуры при её показе или скрытии.
/// Используется, если нужно двигать кнопку, например при показе и скрытии клавиатуры.
///
/// - Attention: Необходимо засетить свойство parentViewController для правильной работы.
public final class KeyboardAvoidingConstraint: NSLayoutConstraint {
    @objc public weak var parentViewController: UIViewController?
    private var initialConstant: CGFloat = 0
    
    override public func awakeFromNib() {
        super.awakeFromNib()
        
        initialConstant = constant
        startKeyboardNotificationsObserving()
    }
}

extension KeyboardAvoidingConstraint: KeyboardNotificationsObserving {
    public func keyboardWillShow(notification: Notification) {
        guard let keyboardSize =
            (notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue.size else {
                return
        }
        
        var newConstant = keyboardSize.height + initialConstant
        
        // Учитываем либо высоту таббара, либо высоту safeArea, так как высота клавиатуры считается от края экрана.
        // tabBar включает в себя высоту safeArea, поэтому проверяем через else.
//        if let tabbar = parentViewController?.tabBarController?.tabBar, !tabbar.isHidden {
//            newConstant -= tabbar.height
//        } else if let safeAreaBottomInset = UIApplication.shared.keyWindow?.safeAreaBottomInset() {
//            newConstant -= safeAreaBottomInset
//        }
        
        moveContinueButtonByKeyboardNotification(notification,
                                                 constant: newConstant)
    }
    
    public func keyboardWillHide(notification: Notification) {
        moveContinueButtonByKeyboardNotification(notification,
                                                 constant: initialConstant)
    }
    
    private func moveContinueButtonByKeyboardNotification(_ notification: Notification, constant: CGFloat) {
        guard let duration = notification.userInfo?[UIResponder.keyboardAnimationDurationUserInfoKey] as? NSNumber,
              let curve = notification.userInfo?[UIResponder.keyboardAnimationCurveUserInfoKey] as? NSNumber else {
            return
        }

        self.constant = constant

        UIView.animate(withDuration: duration.doubleValue,
                       delay: 0.0,
                       options: UIView.AnimationOptions(rawValue: UIView.AnimationOptions.RawValue((curve.intValue))),
                       animations: {
                        self.parentViewController?.view.layoutIfNeeded()
                       },
                       completion: nil)
    }
}

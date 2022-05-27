//
//  NSObject+ClassName.swift
//  Sauap
//
//  Created by Aidana on 23.04.2022.
//

import Foundation

extension NSObject {
    /// Название класса в виде строки
    @objc public static var typeName: String {
        return String(describing: self)
    }
}

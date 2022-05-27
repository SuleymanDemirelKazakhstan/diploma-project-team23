//
//  UIView+Nib.swift
//  Sauap
//
//  Created by Aidana on 23.04.2022.
//

import UIKit

public extension UIView {
    @objc func loadFromNib() {
        loadFromNib(.main)
    }
    
    /// Загружает XIB-файл с таким же названием как у view и добавляет первый объект из файла как subview
    @objc func loadFromNib(_ bundle: Bundle?) {
        loadFromNib(bundle, name: String(describing: type(of: self)))
    }
    
    /// Загружает XIB-файл c задаваемым названием
    /// - Parameters:
    ///   - bundle: bundle проекта
    ///   - name: название XIB-файла
    @objc func loadFromNib(_ bundle: Bundle?, name: String) {
        guard let view = bundle?.loadNibNamed(name,
                                              owner: self,
                                              options: nil)?.first as? UIView else {
            fatalError("Не найдено xib-файла для объекта UIView: \(self)")
        }
        
        view.frame = bounds
        addSubview(view)
    }
    
    static func instantiateFromNib() -> Self {
        let nib = UINib(nibName: Self.typeName, bundle: nil)
        return nib.instantiate(withOwner: nil, options: nil).first as! Self
    }
}

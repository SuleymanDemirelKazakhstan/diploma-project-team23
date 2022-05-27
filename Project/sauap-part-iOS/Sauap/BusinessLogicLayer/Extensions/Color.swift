//
//  Color.swift
//  Sauap
//
//  Created by Aidana on 24.04.2022.
//

import UIKit

public class Color {
    public static let mainColor = UIColor(red: 90.0/255.0, green: 164.0/255.0, blue: 129.0/255.0, alpha: 1)
    public static let disabledButtonBgColor = UIColor(red: 248.0/255.0, green: 251.0/255.0, blue: 249.0/255.0, alpha: 1)
    public static let disabledButtonTintColor = UIColor(red: 141.0/255.0, green: 191.0/255.0, blue: 167.0/255.0, alpha: 1)
}

extension UIColor {
    func image(_ size: CGSize = CGSize(width: 1, height: 1)) -> UIImage {
        return UIGraphicsImageRenderer(size: size).image { rendererContext in
            self.setFill()
            rendererContext.fill(CGRect(x: 0, y: 0, width: size.width, height: size.height))
        }
    }
}

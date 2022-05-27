//
//  NoFundsPlaceholder.swift
//  Sauap
//
//  Created by Aidana on 23.04.2022.
//

import UIKit

public final class NoFundsPlaceholder: UIView {
    public var registerButtonDidPress: (() -> Void)?
    
    @IBOutlet weak var registerButton: UIButton!
    override init(frame: CGRect) {
        super.init(frame: frame)

        setup()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        
        setup()
    }
    
    public func setup() {
        loadFromNib()
        registerButton.tintColor = Color.mainColor
    }
    
    @IBAction func registerButtonDidPress(_ sender: UIButton) {
        registerButtonDidPress?()
    }
}
//FavoritesPlaceholderView - Kolesa

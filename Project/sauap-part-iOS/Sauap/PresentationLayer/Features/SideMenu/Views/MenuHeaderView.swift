//
//  MenuHeaderView.swift
//  Sauap
//
//  Created by Aidana on 24.04.2022.
//

import UIKit

public final class MenuHeaderView: UIView {
 
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var settingsLabel: UILabel!
    @IBOutlet weak var imageView: UIImageView!
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
        imageView.makeRounded()
    }
}


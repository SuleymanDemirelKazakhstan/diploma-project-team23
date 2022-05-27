//
//  ProfileHeaderView.swift
//  Sauap
//
//  Created by Aidana on 25.04.2022.
//

import UIKit

public final class ProfileHeaderView: UIView {
 
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var donatedButton: UIButton!
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
        donatedButton.tintColor = Color.mainColor
        imageView.makeRounded()
    }
}



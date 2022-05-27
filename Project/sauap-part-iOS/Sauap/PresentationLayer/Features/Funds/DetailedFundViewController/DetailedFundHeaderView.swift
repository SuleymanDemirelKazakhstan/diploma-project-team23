//
//  DetailedFundHeaderView.swift
//  Sauap
//
//  Created by Aidana on 24.05.2022.
//

import UIKit

public final class DetailedFundHeaderView: UIView {
 

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
        imageView.image = UIImage(named: "giveChildrenLife")
    }
}



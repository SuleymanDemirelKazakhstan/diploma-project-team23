//
//  SuccessView.swift
//  Sauap
//
//  Created by Aidana on 25.05.2022.
//

import UIKit

protocol SuccessViewDelegate: AnyObject {
    func excellentButtonDidPress()
}

public final class SuccessView: UIView {
    
    weak var delegate: SuccessViewDelegate?
    
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
        
    }
    @IBAction func excellentButtonDidPress(_ sender: UIButton) {
        delegate?.excellentButtonDidPress()
    }
}

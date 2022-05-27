//
//  DonateLoadingView.swift
//  Sauap
//
//  Created by Aidana on 25.05.2022.
//

import UIKit

public final class DonateLoadingView: UIView {
    
    @IBOutlet weak var indicatorView: UIActivityIndicatorView!
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
        
        indicatorView.startAnimating()
    }
}

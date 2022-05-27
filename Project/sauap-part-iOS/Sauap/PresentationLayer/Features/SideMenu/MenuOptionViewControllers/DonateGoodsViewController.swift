//
//  DonateGoodsViewController.swift
//  Sauap
//
//  Created by Aidana on 24.04.2022.
//

import UIKit

class DonateGoodsViewController: UIViewController {
    
    let imageView: UIImageView = {
        let imageView = UIImageView()
        imageView.image = UIImage(named: "donateScreen")
        return imageView
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .white
        title = "Donate goods"
        view.addSubview(imageView)
        imageView.frame = view.frame
    }
}

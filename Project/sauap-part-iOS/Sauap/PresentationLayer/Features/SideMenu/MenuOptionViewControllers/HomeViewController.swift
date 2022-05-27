//
//  HomeViewController.swift
//  Sauap
//
//  Created by Aidana on 21.04.2022.
//

import UIKit

protocol HomeViewControllerDelegate: AnyObject {
    func didTapButtonMenu()
}

class HomeViewController: UIViewController {
    
    weak var delegate: HomeViewControllerDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = .white
        title = "Home"
        
        navigationItem.leftBarButtonItem = UIBarButtonItem(image: UIImage(systemName: "list.dash"),
                                                           style: .done,
                                                           target: self,
                                                           action: #selector(barbuttonTapped))
    }
    
    @objc func barbuttonTapped() {
        delegate?.didTapButtonMenu()
    }
}

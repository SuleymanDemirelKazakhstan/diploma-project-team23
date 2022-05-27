//
//  FirstViewController.swift
//  Sauap
//
//  Created by Aidana on 25.03.2022.
//

import UIKit
import FirebaseAuth

class ChoosingViewController: UIViewController {
    
    private var currentUser: User?
    
    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var registrationButton: UIButton!
    
    override func viewDidAppear(_ animated: Bool) {
        if (Auth.auth().currentUser != nil && Auth.auth().currentUser!.isEmailVerified) {
            goToMainPage()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        configureUI()
    }
    
    private func configureUI() {
        registrationButton.cornerRadius = 8
        loginButton.cornerRadius = 8
    }
    
    @IBAction func registrationButtonDidPress(_ sender: UIButton) {
        let registrationViewController = RegistrationViewController()
        self.navigationController?.pushViewController(registrationViewController, animated: true)
    }
    
    @IBAction func loginButtonDidPress(_ sender: UIButton) {
        let loginViewController = LoginViewController()
        self.navigationController?.pushViewController(loginViewController, animated: true)
    }
    
    func goToMainPage() {
        let containerViewController = ContainerViewController()
        UIApplication.shared.keyWindow?.rootViewController = containerViewController
    }
    
}

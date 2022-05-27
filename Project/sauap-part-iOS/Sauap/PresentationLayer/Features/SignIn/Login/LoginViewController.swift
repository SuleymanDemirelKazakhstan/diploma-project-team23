//
//  LoginViewController.swift
//  Sauap
//
//  Created by Aidana on 11.03.2022.
//

import UIKit
import FirebaseAuth

class LoginViewController: UIViewController {
    
    @IBOutlet weak var emailField: UITextField!
    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var passwordField: UITextField!
    @IBOutlet weak var forgetPasswordButton: UIButton!
    
    let eyeButton = UIButton(type: .custom)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        configureUI()
    }
    
    private func configureUI() {
        loginButton.cornerRadius = 8
        //        navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Регистрация", style: .plain, target: self, action: #selector(registrationButtonDidPress))
        
        configureEyeButton()
    }
    
    private func configureEyeButton() {
        passwordField.rightViewMode = .always
        eyeButton.setImage(UIImage(named: "hide"), for: .normal)
        eyeButton.imageEdgeInsets = UIEdgeInsets(top: 2, left: -24, bottom: 2, right: 15)
        eyeButton.frame = CGRect(x: CGFloat(passwordField.frame.size.width - 25),
                                 y: CGFloat(5),
                                 width: CGFloat(15),
                                 height: CGFloat(25))
        eyeButton.addTarget(self, action: #selector(eyeButtonDidPress), for: .touchUpInside)
        passwordField.rightView = eyeButton
    }
    
    @objc private func eyeButtonDidPress(_ sender: Any) {
        if passwordField.isSecureTextEntry {
            eyeButton.setImage(UIImage(named: "show"), for: .normal)
        } else {
            eyeButton.setImage(UIImage(named: "hide"), for: .normal)
        }
        passwordField.togglePasswordVisibility()
    }
    
    @objc private func registrationButtonDidPress() {
        let registrationViewController = RegistrationViewController()
        self.navigationController?.pushViewController(registrationViewController, animated: true)
    }
    
    @IBAction func loginButtonDidPress(_ sender: UIButton) {
        let email = emailField.text
        let password = passwordField.text
        if email != "" && password != "" {
            Auth.auth().signIn(withEmail: email!, password: password!) { [weak self] result, error in
                if error == nil {
                    if Auth.auth().currentUser!.isEmailVerified {
                        self?.goToMainPage()
                    }
                } else {
                    self?.showMessage(title: "Warning", message: "No no no")
                }
            }
        }
    }
    
    private func showMessage(title: String, message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let ok = UIAlertAction(title: "OK", style: .default) { (UIAlertAction) in
            if title != "Error" {
                self.dismiss(animated: true, completion: nil)
            }
        }
        alert.addAction(ok)
        self.present(alert, animated: true, completion: nil)
    }
    
    private func goToMainPage() {
        let containerViewController = ContainerViewController()
        UIApplication.shared.keyWindow?.rootViewController = containerViewController
    }
    
}


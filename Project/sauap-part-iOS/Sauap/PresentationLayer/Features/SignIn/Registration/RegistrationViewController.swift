//
//  RegistrationViewController.swift
//  Sauap
//
//  Created by Aidana on 11.03.2022.
//

import UIKit
import FirebaseAuth

class RegistrationViewController: UIViewController {
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var registrationButton: UIButton!
    @IBOutlet weak var agreementLabel: UILabel!
    
    private let eyeButton = UIButton(type: .custom)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        configureUI()
    }
    
    private func configureUI() {
        registrationButton.cornerRadius = 8
        //        navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Войти", style: .plain, target: self, action: #selector(rightBarButtonDidPress))
        
        configureEyeButton()
        configureAgreementLabel()
    }
    
    private func configureEyeButton() {
        passwordTextField.rightViewMode = .always
        eyeButton.setImage(UIImage(named: "hide"), for: .normal)
        eyeButton.imageEdgeInsets = UIEdgeInsets(top: 2, left: -24, bottom: 2, right: 15)
        eyeButton.frame = CGRect(x: CGFloat(passwordTextField.frame.size.width - 25),
                                 y: CGFloat(5),
                                 width: CGFloat(15),
                                 height: CGFloat(25))
        eyeButton.addTarget(self, action: #selector(eyeButtonDidPress), for: .touchUpInside)
        passwordTextField.rightView = eyeButton
    }
    
    private func configureAgreementLabel() {
        let strSignup = "Нажимая кнопку “ Зарегистрироваться”, вы\nсоглашаетесь с нашими Положениями и Условиями\nи Политикой конфиденциальности"
        let rangeSignUp1 = NSString(string: strSignup).range(of: "Положениями и Условиями", options: String.CompareOptions.caseInsensitive)
        let rangeSignUp2 = NSString(string: strSignup).range(of: "Политикой конфиденциальности", options: String.CompareOptions.caseInsensitive)
        let attrStr = NSMutableAttributedString.init(string:strSignup)
        attrStr.addAttributes([NSAttributedString.Key.underlineStyle: NSUnderlineStyle.thick.rawValue as Any],range: rangeSignUp1)
        attrStr.addAttributes([NSAttributedString.Key.underlineStyle: NSUnderlineStyle.thick.rawValue as Any],range: rangeSignUp2)
        agreementLabel.attributedText = attrStr
    }
    
    @objc private func rightBarButtonDidPress() {
        let registrationViewController = RegistrationViewController()
        self.navigationController?.pushViewController(registrationViewController, animated: true)
    }
    
    @objc private func eyeButtonDidPress(_ sender: Any) {
        if passwordTextField.isSecureTextEntry {
            eyeButton.setImage(UIImage(named: "show"), for: .normal)
        } else {
            eyeButton.setImage(UIImage(named: "hide"), for: .normal)
        }
        passwordTextField.togglePasswordVisibility()
    }
    
    @IBAction func registrationButtonDidPress(_ sender: UIButton) {
        let email = emailTextField.text
        let password = passwordTextField.text
        if email != "" && password != "" {
            Auth.auth().createUser(withEmail: email!, password: password!) { [weak self] result, error in
                Auth.auth().currentUser?.sendEmailVerification(completion: nil)
                if error == nil {
                    self?.showMessage(title: "Success", message: "Please, verify your email")
                } else {
                    self?.showMessage(title: "Error", message: "Some error")
                }
            }
        }
    }
    
    func showMessage(title: String, message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let ok = UIAlertAction(title: "OK", style: .default) { (UIAlertAction) in
            if title != "Error" {
                self.dismiss(animated: true, completion: nil)
            }
        }
        alert.addAction(ok)
        self.present(alert, animated: true, completion: nil)
    }
    
}

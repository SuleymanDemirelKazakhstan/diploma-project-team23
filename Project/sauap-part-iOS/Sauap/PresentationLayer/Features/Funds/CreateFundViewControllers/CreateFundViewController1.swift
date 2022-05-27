//
//  CreateFundViewController1Temp.swift
//  Sauap
//
//  Created by Aidana on 21.05.2022.
//

import UIKit
import Alamofire

class CreateFundViewController1: UIViewController {
    
    @IBOutlet weak var cityTextField: UITextField!
    
    @IBOutlet weak var binTextField: UITextField!
    
    @IBOutlet weak var contactNameTextField: UITextField!
    
    @IBOutlet weak var nameTextField: UITextField!
    
    @IBOutlet weak var phoneNumberTextField: UITextField!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        hideKeyboardWhenTappedAround()
    }
    
    @IBAction func continueButtonDidPress(_ sender: UIButton) {
        guard let foundationName = nameTextField.text,
            let phoneNumber = phoneNumberTextField.text,
            let contactName = contactNameTextField.text,
            let bin = binTextField.text,
        let city = cityTextField.text else {
            return
        }
                      
        let fundStep1 = FundStep1(foundationName: foundationName,
                                  phoneNumber: phoneNumber,
                                  contactName: contactName,
                                  bin: bin,
                                  city: city)
        
        let createFundViewController2 = CreateFundViewController2()
        createFundViewController2.fundStep1 = fundStep1
        
        navigationController?.pushViewController(createFundViewController2, animated: true)
    }
    
    
}


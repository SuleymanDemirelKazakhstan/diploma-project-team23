//
//  AddCardViewController.swift
//  Sauap
//
//  Created by Aidana on 25.05.2022.
//

import UIKit
import Alamofire

protocol AddCardViewControllerDelegate: AnyObject {
    func addCardButtonDidPress(card: Card)
}

class AddCardViewController: UIViewController {
    
    weak var delegate: AddCardViewControllerDelegate?

    @IBOutlet weak var addCardButton: UIButton!
    @IBOutlet weak var leftBarButtonItem: UIBarButtonItem!
    @IBOutlet weak var navigatonItem: UINavigationItem!
    @IBOutlet weak var backButton: UIBarButtonItem!
    @IBOutlet weak var CVVTextField: UITextField!
    @IBOutlet weak var dateTextField: UITextField!
    @IBOutlet weak var cardTextField: DesignableUITextField!
    override func viewDidLoad() {
        super.viewDidLoad()
      
        leftBarButtonItem.tintColor = Color.mainColor
        leftBarButtonItem.target = self
        leftBarButtonItem.action = #selector(leftBarButtonDidPress)
    }
    
    @objc private func leftBarButtonDidPress() {
        self.dismiss(animated: true)
    }
    
    @IBAction func textFieldDidChange(_ sender: UITextField) {
        
        if let dateText = dateTextField.text,
           let cardText = cardTextField.text,
           let CVVText = CVVTextField.text,
           !dateText.isEmpty && !cardText.isEmpty && !CVVText.isEmpty {
            addCardButton.isEnabled = true
            addCardButton.tintColor = UIColor.white
            addCardButton.backgroundColor = Color.mainColor
        } else {
            addCardButton.isEnabled = false
            addCardButton.tintColor = Color.disabledButtonTintColor
            addCardButton.backgroundColor = Color.disabledButtonBgColor
        }
    }
    
    @IBAction func addCardButtonDidPress(_ sender: UIButton) {
        guard let number = cardTextField.text, let date = dateTextField.text, let cvvString = CVVTextField.text, let cvv = Int(cvvString) else {
                  return
              }
        
        let card = Card(number: number,
                        date: date,
                        CVV: cvv)
        delegate?.addCardButtonDidPress(card: card)
        dismiss(animated: true)
    }
    
    @IBAction func cardTextField(_ sender: UITextField) {
        if let text = cardTextField.text {
            cardTextField.text = text.insert(separator: " ", every: 4)
        }
       
    }
}

//
//  DonateViewController.swift
//  Sauap
//
//  Created by Aidana on 24.05.2022.
//

import UIKit
import Alamofire

class DonateViewController: UIViewController, AddCardViewControllerDelegate, SuccessViewDelegate {
    
    @IBOutlet private var loadingView: DonateLoadingView!
    @IBOutlet private var successView: SuccessView!
    @IBOutlet private var cardNumberLabel: UILabel!
    @IBOutlet private var descriptionLabel: UILabel!
    @IBOutlet private var fundNameLabel: UILabel!
    @IBOutlet private var textField: UITextField!
    @IBOutlet private var zhuzButton: UIButton!
    @IBOutlet private var UshZhuzButton: UIButton!
    @IBOutlet private var besZhuzButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        successView.delegate = self
        setupUI()
    }
    
    private func setupUI() {
        zhuzButton.layer.borderWidth = 1
        zhuzButton.layer.borderColor = Color.mainColor.cgColor
        UshZhuzButton.layer.borderWidth = 1
        UshZhuzButton.layer.borderColor = Color.mainColor.cgColor
        besZhuzButton.layer.borderWidth = 1
        besZhuzButton.layer.borderColor = Color.mainColor.cgColor
        
        textField.becomeFirstResponder()
    }
    
    func excellentButtonDidPress() {
        dismiss(animated: true)
    }
    
    func addCardButtonDidPress(card: Card) {
        cardNumber = card.number
    }
    
    
    
    
    @IBOutlet weak var donateButton: UIButton!
    var cardNumber: String?
    
    @IBAction func newCardButtonDidTap(_ sender: UITapGestureRecognizer) {
        let donateViewController = AddCardViewController()
        donateViewController.delegate = self
        donateViewController.modalPresentationStyle = .fullScreen
        present(donateViewController, animated: true)
    }
    @IBOutlet var newCardButtonDidTap: UITapGestureRecognizer!
    
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        if let cardNumber = cardNumber {
            donateButton.isEnabled = true
            donateButton.tintColor = UIColor.white
            donateButton.backgroundColor = Color.mainColor
            cardNumberLabel.text = String(cardNumber.suffix(4))
        } else {
            donateButton.isEnabled = false
            donateButton.tintColor = Color.disabledButtonTintColor
            donateButton.backgroundColor = Color.disabledButtonBgColor
            cardNumberLabel.text = "New card"
        }
    }
    
    
    
    @IBAction func zhuzButtonDidPress(_ sender: UIButton) {
        textField.text = "100"
    }
    
    @IBAction func UshZhuzButtonDidPress(_ sender: UIButton) {
        textField.text = "300"
    }
    
    @IBAction func besZhuzButtonDidPress(_ sender: UIButton) {
        textField.text = "500"
    }
    
    @IBAction func donateButtonDidPress(_ sender: UIButton) {
        self.successView.isHidden = false
        loadingView.isHidden = false
        UIView.animate(withDuration: 0.5, delay: 1, options: UIView.AnimationOptions.transitionFlipFromTop, animations: {
            self.loadingView.alpha = 0
        }, completion: { finished in
            self.loadingView.isHidden = true
        })
    }
    
}



//
//  EventTableViewCell.swift
//  Sauap
//
//  Created by Aidana on 25.05.2022.
//

import UIKit

class EventTableViewCell: UITableViewCell {

    @IBOutlet weak var dateLabel: UILabel!
    
    @IBOutlet weak var cityLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    static let identifier = "EventTableViewCell"
    
    override func layoutSubviews() {
        super.layoutSubviews()

    }

    
    func configure(with event: Event) {
        setupUI(with: event)
    }
    
    func setupUI(with event: Event) {
        dateLabel.text = event.date
        descriptionLabel.text = event.description
        cityLabel.text = event.city
    }
    
    @IBAction func donateButtonDidPress(_ sender: UIButton) {
    }
}


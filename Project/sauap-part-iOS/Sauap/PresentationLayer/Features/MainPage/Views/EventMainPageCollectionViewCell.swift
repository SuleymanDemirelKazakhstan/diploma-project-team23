//
//  EventMainPageCollectionViewCell.swift
//  Sauap
//
//  Created by Aidana on 08.04.2022.
//

import UIKit

class EventMainPageCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    @IBOutlet weak var cityLabel: UILabel!
    
    static let identifier = "EventMainPageCollectionViewCell"
    
    func setup(_ event: Event) {
        dateLabel.text = event.date
        descriptionLabel.text = event.description
        cityLabel.text = event.city
    }
}

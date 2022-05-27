//
//  FundraisingTableViewCell.swift
//  Sauap
//
//  Created by Aidana on 25.04.2022.
//

import UIKit

class FundraisingTableViewCell: UITableViewCell {
    weak var delegate: FundraisingMainPageViewDelegate?
    
    @IBOutlet weak var fundIconImageView: UIImageView!
    @IBOutlet weak var fundNameLabel: UILabel!
    
    @IBOutlet weak var progressView: UIProgressView!
    @IBOutlet weak var percentLabel: UILabel!
    @IBOutlet weak var goalLabel: UILabel!
    @IBOutlet weak var raisedLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    @IBOutlet weak var photoImageView: UIImageView!
    @IBOutlet weak var donateButton: UIButton!
    
    
    static let identifier = "FundraisingTableViewCell"
    
    override func layoutSubviews() {
        super.layoutSubviews()

        contentView.frame = contentView.frame.inset(by: UIEdgeInsets(top: 5, left: 16, bottom: 5, right: 16))
//        donateButton.tag = indexPath.row
        isUserInteractionEnabled = true
    }

    
    func configure(with fundraising: Fundraising) {
        setupUI(with: fundraising)
    }
    
    private func setupUI(with fundraising: Fundraising) {
        fundIconImageView.makeRounded()
        if let photoLink = fundraising.fund.photoLink {
            fundIconImageView.image = UIImage(named: photoLink)
        }
        fundNameLabel.text = fundraising.fund.foundationName
        raisedLabel.text = fundraising.raisedAmout.formattedWithSeparator.addTenge()
        goalLabel.text = fundraising.goalAmout.formattedWithSeparator.addTenge()
        photoImageView.image = UIImage(named: fundraising.image)
        descriptionLabel.text = fundraising.description
        percentLabel.text = String(fundraising.completedPercent).addPercent()
        progressView.progress = Float(fundraising.raisedAmout) / Float(fundraising.goalAmout)
    }
    
    @IBAction func donateButtonDidPress(_ sender: UIButton) {
        delegate?.donateButtonDidTouch()
    }
}

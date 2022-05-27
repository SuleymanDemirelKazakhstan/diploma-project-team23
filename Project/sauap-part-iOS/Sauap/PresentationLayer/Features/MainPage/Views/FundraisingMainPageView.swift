//
//  FundraisingMainPageView.swift
//  Sauap
//
//  Created by Aidana on 02.04.2022.
//

import UIKit

protocol FundraisingMainPageViewDelegate: AnyObject {
    func donateButtonDidTouch()
}

public final class FundraisingMainPageView: UIView {
    
    weak var delegate: FundraisingMainPageViewDelegate?
    
    @IBOutlet weak var fundIconImageView: UIImageView!
    @IBOutlet weak var funcNameLabel: UILabel!
    
    @IBOutlet weak var progressView: UIProgressView!
    @IBOutlet weak var percentLabel: UILabel!
    @IBOutlet weak var goalLabel: UILabel!
    @IBOutlet weak var raisedLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet var contentView: UIView!
    @IBOutlet weak var donateButton: UIButton!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        loadFromNib()
        dropShadow()
        contentView.fixInView(self)
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        loadFromNib()
        dropShadow()
        contentView.fixInView(self)
    }

    override public func layoutSubviews() {
        super.layoutSubviews()
    }
    
    public func setup(with fundraising: Fundraising) {
        setupUI(with: fundraising)
    }
    
    private func setupUI(with fundraising: Fundraising) {
        fundIconImageView.makeRounded()
        if let photoLink = fundraising.fund.photoLink {
            fundIconImageView.image = UIImage(named: photoLink)
        }
        funcNameLabel.text = fundraising.fund.foundationName
        raisedLabel.text = fundraising.raisedAmout.formattedWithSeparator.addTenge()
        goalLabel.text = fundraising.goalAmout.formattedWithSeparator.addTenge()
        imageView.image = UIImage(named: fundraising.image)
        descriptionLabel.text = fundraising.description
        percentLabel.text = String(fundraising.completedPercent).addPercent()
        progressView.progress = Float(fundraising.raisedAmout) / Float(fundraising.goalAmout)
    }
    @IBAction func donateButtonDidPress(_ sender: UIButton) {
        delegate?.donateButtonDidTouch()
    }
}

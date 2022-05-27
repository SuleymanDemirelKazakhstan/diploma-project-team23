//
//  DetailedFundViewController.swift
//  Sauap
//
//  Created by Aidana on 24.05.2022.
//

import UIKit
import Alamofire

protocol DetailedFundViewControllerDelegate: AnyObject {
    func didSelect(menuItem: Fundraising)
}

class DetailedFundViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, FundraisingMainPageViewDelegate {
    
    func donateButtonDidTouch() {
        let donateViewController = DonateViewController()
//        donateViewController.modalPresentationStyle = .overCurrentContext
        present(donateViewController, animated: true)
    }
    
    private var fundraisings: [Fundraising] = [Fundraising(id: "1",
                                                           organisation: "Give children life",
                                                           description: "Give 10 special children a chance for a high-quality education",
                                                           raisedAmout: 20550,
                                                           goalAmout: 200550,
                                                           completedPercent: 10,
                                                           image: "fundraising3", fund: Fund(foundationId: 1, foundationName: "Give children life", photoLink: "giveChildrenLife", city: "Almaty")),
                                               Fundraising(id: "1", organisation: "SSSSSS", description: "Alice needs a rehabilitation course!", raisedAmout: 20550, goalAmout: 200550, completedPercent: 10, image: "", fund: Fund(foundationId: 1, foundationName: "Bolashak", photoLink: "giveChildrenLife", city: "Almaty")),
                                               Fundraising(id: "2", organisation: "VVVVVV", description: "Alice needs a rehabilitation course!", raisedAmout: 20550, goalAmout: 200550, completedPercent: 10, image: "", fund: Fund(foundationId: 1, foundationName: "Bolashak", photoLink: "bolashak", city: "Almaty")),
        Fundraising(id: "3", organisation: "BBBBBB", description: "Alice needs a rehabilitation course!", raisedAmout: 20550, goalAmout: 200550, completedPercent: 10, image: "", fund: Fund(foundationId: 1, foundationName: "Bolashak", photoLink: "bolashak", city: "Almaty"))
    ]
    @IBOutlet weak var tableView: UITableView!
    weak var delegate: DetailedFundViewControllerDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.rowHeight = 450
        self.navigationController?.addCustomBottomLine(color: UIColor.lightGray, height: 0.2)
        let leftBarButton = UIBarButtonItem(image: UIImage(systemName: "chevron.backward"), style: .plain, target: self, action: #selector(leftBarButtonDidPress))
        leftBarButton.tintColor = Color.mainColor
        let rightBarButton = UIBarButtonItem(image: UIImage(systemName: "gearshape"), style: .plain, target: self, action: #selector(rightBarButtonDidPress))
        rightBarButton.tintColor = Color.mainColor
        
//        navigationItem.rightBarButtonItem = rightBarButton
        navigationItem.leftBarButtonItem = leftBarButton
        tableView.register(UINib(nibName: "FundraisingTableViewCell",
                                 bundle: nil),
                           forCellReuseIdentifier: "FundraisingTableViewCell")
    }
    
    @objc private func rightBarButtonDidPress() {
        navigationController?.pushViewController(TempViewController(), animated: true)
    }
    
    @objc private func leftBarButtonDidPress() {
        self.navigationController?.popViewController(animated: true)
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
//        let item = filteredData[indexPath.row]
        let item = fundraisings[indexPath.row]
        delegate?.didSelect(menuItem: item )
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return fundraisings.count
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 560
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = DetailedFundHeaderView.init(frame: CGRect.init(x: 0, y: 0, width: tableView.frame.width, height: 560))
        return headerView
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FundraisingTableViewCell", for: indexPath) as! FundraisingTableViewCell
        cell.delegate = self
        cell.configure(with: fundraisings[indexPath.row])
        cell.backgroundColor = .clear
        cell.contentView.backgroundColor = .clear
        cell.contentView.layer.masksToBounds = true
        cell.contentView.layer.cornerRadius = 15
        cell.contentView.layer.borderWidth = 0.75
        cell.contentView.layer.shadowOffset = CGSize.zero
        cell.contentView.layer.borderColor = UIColor.lightGray.cgColor
     
        return cell
    }
}


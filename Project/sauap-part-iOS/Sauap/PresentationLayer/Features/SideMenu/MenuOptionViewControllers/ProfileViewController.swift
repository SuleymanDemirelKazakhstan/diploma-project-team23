//
//  ProfileViewController.swift
//  Sauap
//
//  Created by Aidana on 25.04.2022.
//

import UIKit

class ProfileViewController: UIViewController, UITableViewDataSource, UITableViewDelegate  {
    
    private enum MenuOption: String, CaseIterable {
        case personalInformation
        case monthlyDonations
        case bankCards
        case follows
        case requestForHelp
        case aboutSauap
        case contactUs
        case termsOfService
        case privacyPolice
        
        var description: String {
            switch self {
            case .personalInformation:
                return "Personal information"
            case .monthlyDonations:
                return "Monthly donations"
            case .bankCards:
                return "Bank cards"
            case .follows:
                return "My funds"
            case .requestForHelp:
                return "Request for help"
            case .aboutSauap:
                return "About Sauap"
            case .contactUs:
                return "Contact us"
            case .termsOfService:
                return "Terms of service"
            case .privacyPolice:
                return "Privacy police"
            }
        }
    }
    
    private let tableView: UITableView = {
        let tableView = UITableView()
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: "cell")
        tableView.backgroundColor = .clear
        tableView.translatesAutoresizingMaskIntoConstraints = false
        tableView.separatorStyle = .none
        tableView.rowHeight = 48
        return tableView
    }()
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        tableView.frame = CGRect(x: 0,
                                 y: view.safeAreaInsets.top,
                                 width: view.bounds.size.width,
                                 height: view.bounds.size.height)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .purple
        title = "My profile"
        
        view.addSubview(tableView)
        view.backgroundColor = .white
        tableView.delegate = self
        tableView.dataSource = self
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = ProfileHeaderView.init(frame: CGRect.init(x: 0, y: 0, width: tableView.frame.width, height: 244))
        headerView.nameLabel.text = "Aidana"
        return headerView
    }

    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 244
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return MenuOption.allCases.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        cell.textLabel?.text = MenuOption.allCases[indexPath.row].description
        cell.imageView?.image = UIImage(named: MenuOption.allCases[indexPath.row].rawValue)
        cell.backgroundColor = .clear
        cell.contentView.backgroundColor = .clear
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let item = MenuOption.allCases[indexPath.row]
//        delegate?.didSelect(menuItem: item )
    }
}


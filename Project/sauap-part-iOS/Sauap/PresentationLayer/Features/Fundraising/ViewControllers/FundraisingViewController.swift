//
//  FundraisingViewController.swift
//  Sauap
//
//  Created by Aidana on 24.04.2022.
//

import UIKit
import CoreMedia

protocol FundraisingViewControllerDelegate: AnyObject {
    func didSelect(menuItem: Fundraising)
}

class FundraisingViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, UISearchResultsUpdating, UITabBarDelegate, FundraisingMainPageViewDelegate {
    func donateButtonDidTouch() {
        let donateViewController = DonateViewController()
//        donateViewController.modalPresentationStyle = .overCurrentContext
        present(donateViewController, animated: true)
    }
    
    
    @IBOutlet weak var separatorView: UIView!
    @IBOutlet weak var fundraisingTabButton: UIButton!
    
    @IBOutlet weak var completedTabButton: UIButton!
    
    @IBOutlet weak var placeholderView: UIView!
    enum Mode {
        case fundraising
        case completed
    }
    
    private var mode = Mode.fundraising {
        didSet {
            switch mode {
            case .fundraising:
                completedTabButton.setImage(UIImage(named: "fundIcon"), for: .normal)
                fundraisingTabButton.tintColor = Color.mainColor
                completedTabButton.tintColor = .darkGray
                title = "Funds"
            case .completed:
                completedTabButton.setImage(UIImage(named: "fundIconSelected"), for: .normal)
                completedTabButton.tintColor = Color.mainColor
                fundraisingTabButton.tintColor = .darkGray
                title = "Managed Funds"
            }
            filteredData = mode == .fundraising ? allFunds : userFunds
            tableView.reloadData()
            configurePlaceholderView()
        }
    }
    
    private func configurePlaceholderView () {
        if mode == .completed && filteredData.count == 0 {
            tableView.isHidden = true
            placeholderView.isHidden = false
        } else {
            tableView.isHidden = false
            placeholderView.isHidden = true
        }
    }
    
    weak var delegate: FundraisingViewControllerDelegate?
    
    private var allFunds: [Fundraising] = [
        Fundraising(id: "1",
                    organisation: "Give children life",
                    description: "Give 10 special children a chance for a high-quality education",
                    raisedAmout: 20550,
                    goalAmout: 200550,
                    completedPercent: 10,
                    image: "fundraising3", fund: Fund(foundationId: 1, foundationName: "Give children life", photoLink: "giveChildrenLife", city: "Almaty")),
        Fundraising(id: "2",
                    organisation: "Dara",
                    description: "An operation will get Nikita back on his feet!",
                    raisedAmout: 507350,
                    goalAmout: 1055000,
                    completedPercent: 52,
                    image: "birBala", fund: Fund(foundationId: 1, foundationName: "Bolashak", photoLink: "dara", city: "Almaty")),
        Fundraising(id: "3",
                    organisation: "Bolashak",
                    description: "Alice needs a rehabilitation course!",
                    raisedAmout: 20550,
                    goalAmout: 200550,
                    completedPercent: 10,
                    image: "childFundraising",
                    fund: Fund(foundationId: 1, foundationName: "Bolashak", photoLink: "bolashak", city: "Almaty"))
    ]
    
    private var userFunds: [Fundraising] = [
    ]
    
    var filteredData: [Fundraising]!
    
    var searchController: UISearchController!
    

    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var tabbar: UITabBar!
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
//        tableView.frame = CGRect(x: 0,
//                                 y: view.safeAreaInsets.top,
//                                 width: view.bounds.size.width,
//                                 height: view.bounds.size.height - 100)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        configurePlaceholderView()
        tableView.reloadData()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.register(UINib(nibName: "FundraisingTableViewCell",
                                 bundle: nil),
                           forCellReuseIdentifier: "FundraisingTableViewCell")
//        tableView.register(FundraisingTableViewCell.self, forCellReuseIdentifier: "FundraisingTableViewCell")
                tableView.backgroundColor = .clear
        view.backgroundColor = .white
        title = "Fundraising"

        tableView.delegate = self
        tableView.dataSource = self
        tableView.rowHeight = 450
        
        fundraisingTabButton.isUserInteractionEnabled = true
        completedTabButton.isUserInteractionEnabled = true
        
        filteredData = mode == .fundraising ? allFunds : userFunds
        
        // Initializing with searchResultsController set to nil means that
        // searchController will use this view controller to display the search results
        searchController = UISearchController(searchResultsController: nil)
        searchController.searchResultsUpdater = self
        
        // If we are using this same view controller to present the results
        // dimming it out wouldn't make sense. Should probably only set
        // this to yes if using another controller to display the search results.
        searchController.obscuresBackgroundDuringPresentation = false
//        searchController.dimsBackgroundDuringPresentation = false
        
        searchController.searchBar.sizeToFit()
        tableView.tableHeaderView = searchController.searchBar
        
        // Sets this view controller as presenting view controller for the search interface
        definesPresentationContext = true
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return filteredData.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FundraisingTableViewCell", for: indexPath) as! FundraisingTableViewCell
        cell.delegate = self
        cell.configure(with: filteredData[indexPath.row])
        cell.backgroundColor = .clear
        cell.contentView.backgroundColor = .clear
        cell.contentView.layer.masksToBounds = true
        cell.contentView.layer.cornerRadius = 15
        cell.contentView.layer.borderWidth = 0.75
        cell.contentView.layer.shadowOffset = CGSize.zero
        cell.contentView.layer.borderColor = UIColor.lightGray.cgColor
     
        return cell
    }
    
    func imageWithImage(image: UIImage, scaledToSize newSize: CGSize) -> UIImage {
        
        UIGraphicsBeginImageContext(newSize)
        image.draw(in: CGRect(x: 0 ,y: 0 ,width: newSize.width ,height: newSize.height))
        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return newImage!.withRenderingMode(.alwaysOriginal)
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let item = filteredData[indexPath.row]
        delegate?.didSelect(menuItem: item )
    }
    
    func updateSearchResults(for searchController: UISearchController) {
        if let searchText = searchController.searchBar.text {
            let funds = mode == .fundraising ? allFunds : userFunds
            filteredData = searchText.isEmpty ? funds : funds.filter({ fund in
                return fund.description.contains(searchText)
            })

            tableView.reloadData()
        }
    }
    
    @IBAction func SearchButtonDidPress(_ sender: UIButton) {
        mode = .fundraising
    }
    
    @IBAction func fundButtonDidPress(_ sender: UIButton) {
        mode = .completed
    }
    
}


//
//  FundsViewController.swift
//  Sauap
//
//  Created by Aidana on 21.04.2022.
//

import UIKit
import CoreMedia
import SDWebImage

protocol FundsViewControllerDelegate: AnyObject {
    func didSelect(menuItem: Fund)
}

class FundsViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, UISearchResultsUpdating, UITabBarDelegate {
    
    @IBOutlet weak var separatorView: UIView!
    @IBOutlet weak var searchTabButton: UIButton!
    
    @IBOutlet weak var fundTabButton: UIButton!
    
    @IBOutlet weak var placeholderView: NoFundsPlaceholder!
    enum Mode {
        case search
        case fund
    }
    
    private var mode = Mode.search {
        didSet {
            switch mode {
            case .search:
                fundTabButton.setImage(UIImage(named: "fundIcon"), for: .normal)
                searchTabButton.tintColor = Color.mainColor
                fundTabButton.tintColor = .darkGray
                title = "Funds"
            case .fund:
                fundTabButton.setImage(UIImage(named: "fundIconSelected"), for: .normal)
                fundTabButton.tintColor = Color.mainColor
                searchTabButton.tintColor = .darkGray
                title = "Managed Funds"
            }
            filteredData = mode == .search ? allFunds : userFunds
            tableView.reloadData()
            configurePlaceholderView()
        }
    }
    
    private func configurePlaceholderView () {
        placeholderView.registerButtonDidPress = { [weak self] in
            let createFundViewController1 = CreateFundViewController1()
//            createFundViewController1.modalPresentationStyle = .fullScreen
            
            let navigationController = UINavigationController(rootViewController: createFundViewController1)
            navigationController.modalPresentationStyle = .overFullScreen
            self?.present(navigationController, animated: true)
        }
        
        if mode == .fund && filteredData.count == 0 {
            tableView.isHidden = true
            placeholderView.isHidden = false
        } else {
            tableView.isHidden = false
            placeholderView.isHidden = true
        }
    }
    
    weak var delegate: FundsViewControllerDelegate?
    
    private var allFunds: [Fund] = []
    
    private var userFunds: [Fund] = [
    ]
    
    var filteredData: [Fund]!
    
    var searchController: UISearchController!
    
//    private let tableView: UITableView = {
//        let tableView = UITableView()
//        tableView.register(SubtitleTableViewCell.self, forCellReuseIdentifier: "SubtitleTableViewCell")
//        tableView.backgroundColor = .clear
//        return tableView
//    }()
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var tabbar: UITabBar!
    
    private func fetchData() {
        let group = DispatchGroup()
        group.enter()
        
        APICaller.shared.getFunds { [weak self] result in
            guard let self = self else {
                return
            }
            
            defer {
                group.leave()
            }
            switch result {
            case .success(let model):
                self.allFunds = model
                self.filteredData = self.mode == .search ? self.allFunds : self.userFunds
                self.tableView.reloadData()
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }
    
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
        fetchData()
        tableView.register(SubtitleTableViewCell.self, forCellReuseIdentifier: "SubtitleTableViewCell")
                tableView.backgroundColor = .clear
        title = "Funds"
        
        tableView.reloadData()
//        view.addSubview(tableView)
//        tableView.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 0).isActive = true
//        tableView.topAnchor.constraint(equalTo: view.topAnchor, constant: 0).isActive = true
//        tableView.rightAnchor.constraint(equalTo: view.rightAnchor, constant: 0).isActive = true
//        tableView.bottomAnchor.constraint(equalTo: separatorView.topAnchor, constant: 0).isActive = true
        tableView.delegate = self
        tableView.dataSource = self
        tableView.rowHeight = 60
        
        searchTabButton.isUserInteractionEnabled = true
        fundTabButton.isUserInteractionEnabled = true
        
        filteredData = mode == .search ? allFunds : userFunds
        
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
    
    let funds = ["bolashak", "dara", "giveChildrenLife", "bolashak", "dara", "giveChildrenLife"]
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "SubtitleTableViewCell", for: indexPath)
        cell.textLabel?.text = filteredData[indexPath.row].foundationName
//        cell.detailTextLabel?.text = filteredData[indexPath.row].city
////        if let photoLink = filteredData[indexPath.row].photoLink, let url = URL(string: "https://sauap-bucket.s3.ap-southeast-1.amazonaws.com" + photoLink) {
////            cell.imageView?.sd_setImage(with: url)
////        }
//        if let photoLink = filteredData[indexPath.row].photoLink {
//            cell.imageView?.image = UIImage(named: photoLink)
//        }
        cell.imageView?.image = UIImage(named: funds[indexPath.row])
        cell.backgroundColor = .clear
        cell.contentView.backgroundColor = .clear
        return cell
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
//        let item = filteredData[indexPath.row]
        navigationController?.pushViewController(DetailedFundViewController(), animated: true)
//        delegate?.didSelect(menuItem: item )
    }
    
    func updateSearchResults(for searchController: UISearchController) {
        if let searchText = searchController.searchBar.text {
            let funds = mode == .search ? allFunds : userFunds
            filteredData = searchText.isEmpty ? funds : funds.filter({ fund in
                return fund.foundationName.contains(searchText)
            })

            tableView.reloadData()
        }
    }
    
    @IBAction func SearchButtonDidPress(_ sender: UIButton) {
        mode = .search
    }
    
    @IBAction func fundButtonDidPress(_ sender: UIButton) {
        mode = .fund
    }
    
}

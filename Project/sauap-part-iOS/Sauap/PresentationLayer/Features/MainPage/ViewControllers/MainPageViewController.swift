//
//  MainPageViewController.swift
//  Sauap
//
//  Created by Aidana on 11.03.2022.
//

import UIKit
import FirebaseAuth

class MainPageViewController: UIViewController, FundraisingMainPageViewDelegate {
    weak var delegate: HomeViewControllerDelegate?
    
    @IBOutlet private var fundraisingView: FundraisingMainPageView!
    @IBOutlet private var fundraisingView2: FundraisingMainPageView!
    @IBOutlet private var collectionView: UICollectionView!
    @IBOutlet private var eventsButton: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupUI()
        collectionView.register(UINib(nibName: "EventMainPageCollectionViewCell", bundle: nil),
                                forCellWithReuseIdentifier: "EventMainPageCollectionViewCell")
    }
    
    private func setupUI() {
        navigationItem.title = "Home"
        
        navigationItem.rightBarButtonItem = UIBarButtonItem(image: UIImage(systemName: "bell"), style: .plain, target: self, action: #selector(rightBarButtonDidPress))
        navigationItem.leftBarButtonItem = UIBarButtonItem(image: UIImage(named: "menu"), style: .plain, target: self, action: #selector(leftBarButtonDidPress))
        
        
        fundraisingView.delegate = self
        fundraisingView2.delegate = self
        fundraisingView.setup(with: Fundraising(id: "1",
                                                organisation: "Bolashak",
                                                description: "Alice needs a rehabilitation course!",
                                                raisedAmout: 20550,
                                                goalAmout: 200550,
                                                completedPercent: 10,
                                                image: "childFundraising", fund: Fund(foundationId: 1, foundationName: "Bolashak", photoLink: "bolashak", city: "Almaty")))
        
        fundraisingView2.setup(with: Fundraising(id: "2",
                                                 organisation: "Dara",
                                                 description: "An operation will get Nikita back on his feet!",
                                                 raisedAmout: 507350,
                                                 goalAmout: 1055000,
                                                 completedPercent: 52,
                                                 image: "birBala", fund: Fund(foundationId: 1, foundationName: "Dara", photoLink: "dara", city: "Almaty")))
    }
    

    
    @objc func leftBarButtonDidPress() {
        delegate?.didTapButtonMenu()
    }
    
    @IBAction func eventsButtonDidTap(_ sender: UITapGestureRecognizer) {
        navigationController?.pushViewController(EventsViewController(), animated: true)
    }
    
    @objc private func rightBarButtonDidPress() {
        navigationController?.pushViewController(DetailedFundViewController(), animated: true)
    }
    private var events = [Event(city: "Almaty",
                                description: "Сharity fair in the shopping center \"Mega Park\"",
                                date: "СБ, 26 марта, 15:00"),
                          Event(city: "Almaty",
                                description: "How to help a \"special baby\" in development",
                                date: "ВС, 27 марта, 12:00"),
                          Event(city: "Almaty",
                                description: "Сharity fair in the shopping center \"Mega Park\"",
                                date: "ВС, 27 марта, 12:00")]
    
    func donateButtonDidTouch() {
        let donateViewController = DonateViewController()
        present(donateViewController, animated: true)
    }
    
    @IBAction func logoutButtonDidPress(_ sender: UIButton) {
        do {
            try Auth.auth().signOut()
        }
        catch {
            print("Error message")
        }
    }
    
    @IBAction func openTempVC(_ sender: UIButton) {
        navigationController?.pushViewController(TempViewController(), animated: true)
    }
}

extension MainPageViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return events.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: EventMainPageCollectionViewCell.identifier, for: indexPath) as! EventMainPageCollectionViewCell
        cell.setup(events[indexPath.row])
        cell.shadowDecorate()
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 240, height: 135)
    }
    
    
}


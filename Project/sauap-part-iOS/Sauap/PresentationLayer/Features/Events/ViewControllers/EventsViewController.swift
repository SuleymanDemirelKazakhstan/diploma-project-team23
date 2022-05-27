//
//  EventsViewController.swift
//  Sauap
//
//  Created by Aidana on 24.04.2022.
//

import UIKit

class EventsViewController: UIViewController {
//    let cellSpacingHeight: CGFloat = 1
    
    @IBOutlet weak var collectionView: UICollectionView!
    
    private var events = [Event(city: "Almaty",
                                description: "Сharity fair in the shopping center \"Mega Park\"",
                                date: "СБ, 26 марта, 15:00"),
                          Event(city: "Almaty",
                                description: "How to help a \"special baby\" in development",
                                date: "ВС, 27 марта, 12:00"),
                          Event(city: "Almaty",
                                description: "Сharity fair in the shopping center \"Mega Park\"",
                                date: "ВС, 27 марта, 12:00"),
                          Event(city: "Almaty",
                                description: "Сharity fair in the shopping center \"Mega Park\"",
                                date: "СБ, 26 марта, 15:00"),
                          Event(city: "Almaty",
                                description: "How to help a \"special baby\" in development",
                                date: "ВС, 27 марта, 12:00"),
                          Event(city: "Almaty",
                                description: "Сharity fair in the shopping center \"Mega Park\"",
                                date: "ВС, 27 марта, 12:00"),
                          Event(city: "Almaty",
                                description: "Сharity fair in the shopping center \"Mega Park\"",
                                date: "СБ, 26 марта, 15:00"),
                          Event(city: "Almaty",
                                description: "How to help a \"special baby\" in development",
                                date: "ВС, 27 марта, 12:00"),
                          Event(city: "Almaty",
                                description: "Сharity fair in the shopping center \"Mega Park\"",
                                date: "ВС, 27 марта, 12:00")]
    
//    @IBOutlet weak var tableView: UITableView!
    override func viewDidLoad() {
        super.viewDidLoad()
        title = "Events"
        collectionView.register(UINib(nibName: "EventMainPageCollectionViewCell",
                                      bundle: nil),
                                forCellWithReuseIdentifier: "EventMainPageCollectionViewCell")
    }
    
}

extension EventsViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
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
        return CGSize(width: UIScreen.main.bounds.width - 32, height: 115)
    }
}

//
//  ContainerViewController.swift
//  Sauap
//
//  Created by Aidana on 21.04.2022.
//

import UIKit

class ContainerViewController: UIViewController {
    
    enum MenuState {
        case opened
        case closed
    }
    
    private var currentVC: UIViewController?
    private var menuState: MenuState = .closed
    
    let menuVC = MenuViewController()
    let homeVC = MainPageViewController()
    var navVC: UINavigationController?
    lazy var notificationsViewController = NotificationsViewController()
    lazy var fundraisingViewController = FundraisingViewController()
    lazy var donateGoodsViewController = DonateMapViewController()
    lazy var eventsViewController = EventsViewController()
    lazy var fundsViewController = FundsViewController()
    lazy var profileViewController = ProfileViewController()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        addChildsVC()
    }
    
    private func addChildsVC() {
        menuVC.delegate = self
        addChild(menuVC)
        view.addSubview(menuVC.view)
        menuVC.didMove(toParent: self)
        
        homeVC.delegate = self
        let navVC = UINavigationController(rootViewController: homeVC)
        addChild(navVC)
        view.addSubview(navVC.view)
        navVC.didMove(toParent: self)
        self.navVC = navVC
    }
}

extension ContainerViewController: HomeViewControllerDelegate {
    func didTapButtonMenu() {
        toggleMenu(completion: nil)
    }
    
    func toggleMenu(completion: (() -> Void)?) {
        switch menuState {
        case .closed:
            UIView.animate(withDuration: 0.5,
                           delay: 0,
                           usingSpringWithDamping: 0.8,
                           initialSpringVelocity: 0,
                           options: .curveEaseOut) {
                self.navVC?.view.frame.origin.x = self.homeVC.view.frame.size.width - 100
            } completion: { [weak self] (done) in
                if done {
                    self?.menuState = .opened
                }
            }
        case .opened:
            UIView.animate(withDuration: 0.5,
                           delay: 0,
                           usingSpringWithDamping: 0.8,
                           initialSpringVelocity: 0,
                           options: .curveEaseOut) {
                self.navVC?.view.frame.origin.x = 0
            } completion: { [weak self] (done) in
                if done {
                    self?.menuState = .closed
                    DispatchQueue.main.async {
                        completion?()
                    }
                }
            }
        }
    }
}

extension ContainerViewController: MenuViewControllerDelegate {
    func openProfile() {
        toggleMenu(completion: nil)
        if let vc = currentVC {
            vc.view.removeFromSuperview()
            vc.didMove(toParent: nil)
        }
        
        let vc = profileViewController
        homeVC.addChild(vc)
        homeVC.view.addSubview(vc.view)
        vc.view.frame = view.frame
        vc.didMove(toParent: homeVC)
        homeVC.title = vc.title
        currentVC = vc
    }
    
    func didSelect(menuItem: MenuViewController.MenuOption) {
        toggleMenu(completion: nil)
        if let vc = currentVC {
            vc.view.removeFromSuperview()
            vc.didMove(toParent: nil)
        }
        switch menuItem {
        case .home:
            resetToHome()
//        case .notifications:
//            addInfo()
        case .funds:
            openFundsViewController()
        case .fundraising:
            openFundraisingViewController()
        case .donateGoods:
            openDonateGoodsViewController()
        case .events:
            openEventsViewController()
        }
    }
    @objc func rightBarButtonDidPress() {
//        delegate?.didTapButtonMenu()
    }
    func addInfo() {
        let vc = notificationsViewController
        homeVC.addChild(vc)
        homeVC.view.addSubview(vc.view)
        vc.view.frame = view.frame
        vc.didMove(toParent: homeVC)
        homeVC.title = vc.title
        currentVC = vc
    }
    
    func resetToHome() {
        homeVC.title = "Home"
    }
    
    func openFundsViewController() {
        let vc = fundsViewController
        homeVC.addChild(vc)
        homeVC.view.addSubview(vc.view)
        vc.view.frame = view.frame
        vc.didMove(toParent: homeVC)
        homeVC.title = vc.title
        currentVC = vc
    }
    
    func openFundraisingViewController() {
        let vc = fundraisingViewController
        homeVC.addChild(vc)
        homeVC.view.addSubview(vc.view)
        vc.view.frame = view.frame
        vc.didMove(toParent: homeVC)
        homeVC.title = vc.title
        currentVC = vc
    }
    
    func openDonateGoodsViewController() {
        let vc = donateGoodsViewController
        homeVC.addChild(vc)
        homeVC.view.addSubview(vc.view)
        vc.view.frame = view.frame
        vc.didMove(toParent: homeVC)
        homeVC.title = vc.title
        currentVC = vc
    }
    
    func openEventsViewController() {
        let vc = eventsViewController
        homeVC.addChild(vc)
        homeVC.view.addSubview(vc.view)
        vc.view.frame = view.frame
        vc.didMove(toParent: homeVC)
        homeVC.title = vc.title
        currentVC = vc
    }
}

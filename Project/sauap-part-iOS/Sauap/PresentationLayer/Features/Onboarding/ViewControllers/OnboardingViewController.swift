//
//  OnboardingViewController.swift
//  Sauap
//
//  Created by Aidana on 25.03.2022.
//

import UIKit

class OnboardingViewController: UIViewController {
    
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var nextButton: UIButton!
    @IBOutlet weak var pageControl: UIPageControl!
    
    private var slides: [OnboardingSlide] = []
    
    private var currentPage = 0 {
        didSet {
            pageControl.currentPage = currentPage
            if currentPage == slides.count - 1 {
                nextButton.setTitle("Начать", for: .normal)
            } else {
                nextButton.setTitle("Далее", for: .normal)
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        configureUI()
        collectionView.register(UINib(nibName: "OnboardingCollectionViewCell",
                                      bundle: nil),
                                forCellWithReuseIdentifier: "OnboardingCollectionViewCell")
    }
    
    private func configureUI() {
        configureSlide()
    }
    
    private func configureSlide() {
        slides = [
            OnboardingSlide(title: "OnboardingSlide.title.welcome".localized(), description: "OnboardingSlide.description.welcome".localized(), image: UIImage(named: "nophoto")!),
            OnboardingSlide(title: "OnboardingSlide.title.help".localized(), description: "OnboardingSlide.description.help".localized(), image: UIImage(named: "nophoto")!),
            OnboardingSlide(title: "OnboardingSlide.title.share".localized(), description: "OnboardingSlide.description.share".localized(), image: UIImage(named: "nophoto")!),
            OnboardingSlide(title: "OnboardingSlide.title.safe".localized(), description: "OnboardingSlide.description.safe".localized(), image: UIImage(named: "nophoto")!)
        ]
        
        pageControl.numberOfPages = slides.count
    }
    
    @IBAction func nextBtnClicked(_ sender: UIButton) {
        if currentPage == slides.count - 1 {
            let controller = ChoosingViewController()
            controller.modalPresentationStyle = .fullScreen
            controller.modalTransitionStyle = .flipHorizontal
            UserDefaults.standard.hasOnboarded = true
            self.navigationController?.pushViewController(controller, animated: true)
        } else {
            currentPage += 1
            let indexPath = IndexPath(item: currentPage, section: 0)
            collectionView.scrollToItem(at: indexPath, at: .centeredHorizontally, animated: true)
        }
    }
    
}

extension OnboardingViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return slides.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: OnboardingCollectionViewCell.identifier, for: indexPath) as! OnboardingCollectionViewCell
        cell.setup(slides[indexPath.row])
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.frame.width, height: collectionView.frame.height)
    }
    
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let width = scrollView.frame.width
        currentPage = Int(scrollView.contentOffset.x / width)
    }
}

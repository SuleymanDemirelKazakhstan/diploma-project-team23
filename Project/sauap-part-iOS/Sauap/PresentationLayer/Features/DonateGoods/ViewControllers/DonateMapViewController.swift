//
//  DonateMapViewController.swift
//  Sauap
//
//  Created by Aidana on 25.05.2022.
//

import UIKit
import Alamofire
import MapKit

public struct Place {
    let latitude: Float
    let longitude: Float
    let title: String
    let description: String
    let distance: String
}

class DonateMapViewController: UIViewController, CLLocationManagerDelegate, MKMapViewDelegate {
    private var events = [Event(city: "Almaty",
                                description: "Сharity fair in the shopping center \"Mega Park\"",
                                date: "СБ, 26 марта, 15:00"),
                          Event(city: "Almaty",
                                description: "How to help a \"special baby\" in development",
                                date: "ВС, 27 марта, 12:00"),
                          Event(city: "Almaty",
                                description: "Сharity fair in the shopping center \"Mega Park\"",
                                date: "ВС, 27 марта, 12:00")]
   
    @IBOutlet weak var mapView: MKMapView!
    let manager = CLLocationManager()
    
    @IBOutlet weak var clothesButton: UIButton!
    @IBOutlet weak var annotationView: UIView!
    let places = [Place(latitude: 43.20246, longitude: 76.8762, title: "AAA", description: "BBB", distance: "4.7 km"),
                  Place(latitude: 43.20450, longitude: 76.8870, title: "AAA", description: "BBB", distance: "4.7 km"),
                  Place(latitude: 43.20846, longitude: 76.8662, title: "AAA", description: "BBB", distance: "4.7 km"),
                  Place(latitude: 43.19350, longitude: 76.8702, title: "AAA", description: "BBB", distance: "4.7 km")]
    
  
    @IBAction func clothesButtonDidPress(_ sender: Any) {
        clothesButton.backgroundColor = Color.mainColor
        clothesButton.tintColor = .white
        showData()
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.mapView.delegate = self
        annotationView.dropShadow()
        self.navigationController?.addCustomBottomLine(color: UIColor.lightGray, height: 0.2)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        title = "Donate goods"
        
        manager.desiredAccuracy = kCLLocationAccuracyBest
        manager.delegate = self
        manager.requestWhenInUseAuthorization()
        manager.startUpdatingLocation()
        
    }
    
    func mapView(_ mapView: MKMapView, didSelect view: MKAnnotationView) {
        annotationView.isHidden = false
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        if let location = locations.first {
            manager.stopUpdatingLocation()

//            render(location)
            let coordinate = CLLocationCoordinate2D(latitude: 43.20246,
                                                    longitude: 76.8762)
            let span = MKCoordinateSpan(latitudeDelta: 0.03,
                                        longitudeDelta: 0.03)
            let region = MKCoordinateRegion(center: coordinate,
                                            span: span)
            mapView.setRegion(region,
                              animated: true)
        }
    }
//
//    func render(_ location: CLLocation) {
//        let coordinate = CLLocationCoordinate2D(latitude: 43.20246,
//                                                longitude: 76.8762)
//        let span = MKCoordinateSpan(latitudeDelta: 0.05,
//                                    longitudeDelta: 0.05)
//        let region = MKCoordinateRegion(center: coordinate,
//                                        span: span)
//        mapView.setRegion(region,
//                          animated: true)
//
//        let pin = MKPointAnnotation()
//        pin.coordinate = coordinate
//        mapView.addAnnotation(pin)
//
//    }
    
    func showData() {
        mapView.removeAnnotations(mapView.annotations)
        for place in places {
            
            let coordinate = CLLocationCoordinate2D(latitude: CLLocationDegrees(place.latitude),
                                                    longitude: CLLocationDegrees(place.longitude))
//            let span = MKCoordinateSpan(latitudeDelta: 0.05,
//                                        longitudeDelta: 0.05)
//            let region = MKCoordinateRegion(center: coordinate,
//                                            span: span)
//            mapView.setRegion(region,
//                              animated: true)
            
            let pin = MKPointAnnotation()
            pin.coordinate = coordinate
            mapView.addAnnotation(pin)
            
        }
        
    }
}

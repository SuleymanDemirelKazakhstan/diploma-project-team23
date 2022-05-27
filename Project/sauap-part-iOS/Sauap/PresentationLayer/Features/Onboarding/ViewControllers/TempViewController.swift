//
//  TempViewController.swift
//  Sauap
//
//  Created by Aidana on 09.04.2022.
//

import UIKit
import Alamofire

class TempViewController: UIViewController {
    let a = 56
    func fetchFilms() {
        let request = AF.request("http://86.107.198.108:8080/fundraise/?is-completed=false")
        request.responseJSON { (data) in
            print(data)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        printMyStarredGistsWithBasicAuth()
    }
    
    func printMyStarredGistsWithBasicAuth() -> Void {
        let user = "ios-app"
        let password = "12345"
        let credentialData = "\(user):\(password)".data(using: String.Encoding.utf8)!
        let base64Credentials = credentialData.base64EncodedString(options: [])
        
        let headers : HTTPHeaders = [
            "Authorization": "Basic \(base64Credentials)"
        ]
        AF.request("http://86.107.198.108:8080/foundations/?is-completed=false",
                   method: .get,
                   parameters: nil,
                   encoding: URLEncoding.default,
                   headers:headers)
        .validate()
        .responseJSON { response in
            switch response.result {
            case .success(let value):
                print()
//                completion(try? SomeRequest(protobuf: value))
            case .failure(let error):
                print(error)
//                completion(nil)
            }
        }
    }
    
}


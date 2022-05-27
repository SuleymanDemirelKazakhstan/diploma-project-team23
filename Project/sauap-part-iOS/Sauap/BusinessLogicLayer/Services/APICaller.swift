//
//  APICaller.swift
//  Sauap
//
//  Created by Aidana on 21.05.2022.
//

import Foundation
import Alamofire

final class APICaller: Service {
    static let shared = APICaller()
        
    public func getFunds(completion: @escaping (Result<[Fund], NetworkError>) -> Void) {
        let credentialData = "\(user):\(password)".data(using: String.Encoding.utf8)!
        let base64Credentials = credentialData.base64EncodedString(options: [])
        
        let headers : HTTPHeaders = [
            "Authorization": "Basic \(base64Credentials)"
        ]
        AF.request(baseURL + "/foundations/?is-completed=false",
                   method: .get,
                   parameters: nil,
                   encoding: URLEncoding.default,
                   headers:headers)
        .responseJSON { response in
            guard let itemsData = response.data else {
                completion(.failure(.badResponse))
                return
            }
            do {
                let decoder = JSONDecoder()
                let items = try decoder.decode([Fund].self, from: itemsData)
                DispatchQueue.main.async {
                    completion(.success(items))
                }
            } catch {
                completion(.failure(.noData))
            }
        }
        
    }
    
    private let baseURL = "http://86.107.198.108:8080"
    private let user = "ios-app"
    private let password = "12345"
}

enum NetworkError: Error {
    case badResponse
    case noData
}

class Service {
    
}

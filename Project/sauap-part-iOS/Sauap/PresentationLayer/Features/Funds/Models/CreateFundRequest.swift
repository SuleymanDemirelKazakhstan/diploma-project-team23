//
//  CreateFundRequest.swift
//  Sauap
//
//  Created by Aidana on 21.05.2022.
//

import Foundation
import Alamofire

public struct CreateFundRequest: Codable {
    let foundationName: String
    let phoneNumber: String
    let credit: Credit
    let contactName: String
    let bin: String
    let fileName: String
}

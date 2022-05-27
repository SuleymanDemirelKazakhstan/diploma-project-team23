//
//  CreateFundViewController3.swift
//  Sauap
//
//  Created by Aidana on 21.05.2022.
//

import UIKit
import Alamofire
import AudioToolbox

class CreateFundViewController3: UIViewController {
    public var fundStep1: FundStep1?
    public var fundStep2: FundStep2?
    
    
    @IBOutlet weak var binTextField: UITextField!
    
    @IBOutlet weak var kBETextField: UITextField!
    
    @IBOutlet weak var kNPTextField: UITextField!
    
    
    @IBOutlet weak var bICTextField: UITextField!
    
    
    @IBOutlet weak var rAccountTextField: UITextField!
    
    
    @IBOutlet weak var legalAddressTextField: UITextField!
    
    
    @IBOutlet weak var button: UIButton!
    @IBAction func button(_ sender: UIButton) {
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        hideKeyboardWhenTappedAround()
    }
    
    @IBAction func create(_ sender: UIButton) {
        printMyStarredGistsWithBasicAuth()
    }
    
    func printMyStarredGistsWithBasicAuth() -> Void {
        let user = "ios-app"
        let password = "12345"
        guard let credentialData = "\(user):\(password)".data(using: String.Encoding.utf8) else {
            return
        }
        let base64Credentials = credentialData.base64EncodedString(options: [])
        let boundary = UUID().uuidString
        let headers : HTTPHeaders = [
            "Authorization": "Basic \(base64Credentials)",
            "Content-Type" : "multipart/form-data; boundary=\(boundary)"
        ]
        //        AF.request("http://86.107.198.108:8080/foundations/create/?userId=8",
        //                   method: .post,
        //                   parameters: nil,
        //                   encoding: URLEncoding.default,
        //                   headers:headers)
        //        .validate()
        //        .responseJSON { response in
        //            switch response.result {
        //            case .success(let value):
        //                print()
        ////                completion(try? SomeRequest(protobuf: value))
        //            case .failure(let error):
        //                print(error)
        ////                completion(nil)
        //            }
        //        }
        
        guard let legalAddress = legalAddressTextField.text,
              let knp = kNPTextField.text,
              let kbe = kBETextField.text,
              let bin = binTextField.text,
              let bic = bICTextField.text,
              let account = rAccountTextField.text else {
            return
        }
        
        let credit = Credit(legalAddress: legalAddress,
                            knp: knp,
                            kbe: kbe,
                            bin: bin,
                            bic: bic,
                            account: account)
        let encoder = JSONEncoder()
        let json = try? encoder.encode(credit)
        
        var parameters = [String: String]()
        parameters["foundationName"] = fundStep1?.foundationName
        parameters["phoneNumber"] = fundStep1?.phoneNumber
        parameters["contactName"] = fundStep1?.contactName
        parameters["bin"] = fundStep1?.bin
        parameters["city"] = fundStep1?.city
        
        
        guard let url = URL(string: "http://86.107.198.108:8080/foundations/create/?userId=8"), let json = json  else {
            return
        }
        var request = URLRequest(url: url)
        request.headers = headers
        request.httpMethod = "POST"
                AF.upload(multipartFormData: { [weak self] multipartFormData in
                    for (key, value) in parameters {
                        guard let temp = value.data(using: .utf8) else {
                            return
                        }
                        multipartFormData.append (temp, withName: key)
                    }
                    multipartFormData.append(json, withName: "credits")
                    if let data = self?.fundStep2?.file {
                        multipartFormData.append(data, withName: "fileName", fileName: "screen.png", mimeType: "image/png")
                    }
                }, with: request)
                .response { response in
                    switch response.result {
                    case .success(let value):
                        print(value)
                        break
                    case .failure(let error):
                        print(error.errorDescription)
                        break
                    }
                }
    }
}


final class ImageUploader {

    let uploadImage: UIImage
    let number: Int
    let boundary = "example.boundary.\(ProcessInfo.processInfo.globallyUniqueString)"
    let fieldName = "upload_image"
    let endpointURI: URL = .init(string: "https://example.com/uploadImage")!

    var parameters: Parameters? {
        return [
            "number": number
        ]
    }
    var headers: HTTPHeaders {
        return [
            "Content-Type": "multipart/form-data; boundary=\(boundary)",
            "Accept": "application/json"
        ]
    }

    init(uploadImage: UIImage, number: Int) {
        self.uploadImage = uploadImage
        self.number = number
    }
    
    typealias ImageUploadResult = Result<Response, ResponseError>
    
    func uploadImage(completionHandler: @escaping (ImageUploadResult) -> Void) {
        let imageData = uploadImage.jpegData(compressionQuality: 1)!
        let mimeType = imageData.mimeType!

        var request = try! URLRequest(url: endpointURI, method: .post, headers: headers)
        request.httpBody = createHttpBody(binaryData: imageData, mimeType: mimeType)
        
        let session = URLSession(configuration: .default)
        let task = session.dataTask(with: request) { (data, urlResponse, error) in
            let statusCode = (urlResponse as? HTTPURLResponse)?.statusCode ?? 0
            if let data = data, case (200..<300) = statusCode {
                do {
                    let value = try Response(from: data, statusCode: statusCode)
                    completionHandler(.success(value))
                } catch {
                    let _error = ResponseError(statusCode: statusCode, error: AnyError(error))
                    completionHandler(.failure(_error))
                }
            }
            let tmpError = error ?? NSError(domain: "Unknown", code: 499, userInfo: nil)
            let _error = ResponseError(statusCode: statusCode, error: AnyError(error!))
            completionHandler(.failure(_error))
        }
        task.resume()
    }
    
    private func createHttpBody(binaryData: Data, mimeType: String) -> Data {
        var postContent = "--\(boundary)\r\n"
        let fileName = "\(UUID().uuidString).jpeg"
        postContent += "Content-Disposition: form-data; name=\"\(fieldName)\"; filename=\"\(fileName)\"\r\n"
        postContent += "Content-Type: \(mimeType)\r\n\r\n"

        var data = Data()
        guard let postData = postContent.data(using: .utf8) else { return data }
        data.append(postData)
        data.append(binaryData)

        // その他パラメータがあれば追加
        if let parameters = parameters {
            var content = ""
            parameters.forEach {
                content += "\r\n--\(boundary)\r\n"
                content += "Content-Disposition: form-data; name=\"\($0.key)\"\r\n\r\n"
                content += "\($0.value)"
            }
            if let postData = content.data(using: .utf8) { data.append(postData) }
        }

        // HTTPBodyの終了を設定
        guard let endData = "\r\n--\(boundary)--\r\n".data(using: .utf8) else { return data }
        data.append(endData)
        return data
    }
}



struct AnyError: Error {

    let error: Error

    init(_ error: Error) {
        self.error = error
    }
}

extension Data {

    var mimeType: String? {
        var values = [UInt8](repeating: 0, count: 1)
        copyBytes(to: &values, count: 1)

        switch values[0] {
        case 0xFF:
            return "image/jpeg"
        case 0x89:
            return "image/png"
        case 0x47:
            return "image/gif"
        case 0x49, 0x4D:
            return "image/tiff"
        default:
            return nil
        }
    }
}

struct Response {

    let statusCode: Int
    let body: Parameters?

    init(from data: Data, statusCode: Int) throws {
        self.statusCode = statusCode
        let jsonObject = try JSONSerialization.jsonObject(with: data, options: []) as? Parameters
        self.body = jsonObject
    }
}

struct ResponseError: Error {

    let statusCode: Int
    let error: AnyError
}

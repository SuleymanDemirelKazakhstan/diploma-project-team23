//
//  CreateFundViewController2.swift
//  Sauap
//
//  Created by Aidana on 22.05.2022.
//

import UIKit
import Alamofire

class CreateFundViewController2: UIViewController, UIDocumentPickerDelegate {
    
    public var fundStep1: FundStep1?
    
    var data = Data()
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func uploadFileButtonDidPress(_ sender: UIButton) {
        let importMenu = UIDocumentPickerViewController(documentTypes: ["public.text", "com.apple.iwork.pages.pages", "public.data"], in: .import)
        importMenu.delegate = self
        importMenu.modalPresentationStyle = .formSheet
        self.present(importMenu, animated: true, completion: nil)
    }
    
    fileprivate func getDocumentFromURL(URL: URL,handler : @escaping (Data) -> Void) {
        let sessionConfig = URLSessionConfiguration.default
        let session = URLSession(configuration: sessionConfig, delegate: nil, delegateQueue: nil)
        var request = URLRequest(url: URL as URL)
        request.httpMethod = "GET"
        let task = session.dataTask(with: request as URLRequest, completionHandler: { (data, response, error) -> Void in
            if (error == nil) {
                // Success
                DispatchQueue.main.async(execute: {
                    //this is an async operation, use handler to handle the result
                    handler(data!)
                })

            }
            else {
                // Failure
                print("Failure: %@", error!.localizedDescription)
            }
        })
        task.resume()
    }
    
    public func documentPicker(_ controller: UIDocumentPickerViewController, didPickDocumentsAt urls: [URL]) {
        guard let documentURL = urls.first else {
            return
        }
        
        getDocumentFromURL(URL: documentURL) { data in
            self.data = data
        }
        
    }
    
    @IBAction func continueButtonDidPress(_ sender: UIButton) {
        let fundStep2 = FundStep2(file: data)
        
        let createFundViewController3 = CreateFundViewController3()
        createFundViewController3.fundStep2 = fundStep2
        createFundViewController3.fundStep1 = fundStep1
        
        navigationController?.pushViewController(createFundViewController3, animated: true)
    }
}


//
//  String+Extension.swift
//  Sauap
//
//  Created by Aidana on 26.03.2022.
//

import Foundation

public extension String {
    func localized() -> String {
        return Bundle.main.localizedString(forKey: self, value: nil, table: nil)
    }
    
    func addTenge() -> String {
        return self + " ₸"
    }
    
    func addPercent() -> String {
        return self + "%"
    }
}

extension Formatter {
    static let withSeparator: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        formatter.groupingSeparator = " "
        return formatter
    }()
}

extension Numeric {
    var formattedWithSeparator: String { Formatter.withSeparator.string(for: self) ?? "" }
}


extension Collection {

    func unfoldSubSequences(limitedTo maxLength: Int) -> UnfoldSequence<SubSequence,Index> {
        sequence(state: startIndex) { start in
            guard start < endIndex else { return nil }
            let end = index(start, offsetBy: maxLength, limitedBy: endIndex) ?? endIndex
            defer { start = end }
            return self[start..<end]
        }
    }

    func every(n: Int) -> UnfoldSequence<Element,Index> {
        sequence(state: startIndex) { index in
            guard index < endIndex else { return nil }
            defer { formIndex(&index, offsetBy: n, limitedBy: endIndex) }
            return self[index]
        }
    }

    var pairs: [SubSequence] { .init(unfoldSubSequences(limitedTo: 2)) }
}

extension StringProtocol where Self: RangeReplaceableCollection {

    func insert<S: StringProtocol>(separator: S, every n: Int) -> String {
        var str = self
        for index in indices.every(n: n).dropFirst().reversed() {
            str.insert(contentsOf: separator, at: index)
        }
        return str as! String
    }

    func inserting<S: StringProtocol>(separator: S, every n: Int) -> Self {
        .init(unfoldSubSequences(limitedTo: n).joined(separator: separator))
    }
}

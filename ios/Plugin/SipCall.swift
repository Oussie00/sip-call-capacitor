import Foundation

@objc public class SipCall: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}

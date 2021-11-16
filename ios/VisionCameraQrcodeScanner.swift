import MLKitBarcodeScanning
import MLKitVision

@objc(VisionCameraQrcodeScanner)
class VisionCameraQrcodeScanner: NSObject, FrameProcessorPluginBase {
    
    static var format: BarcodeFormat = .qrCode
    static var barcodeOptions = BarcodeScannerOptions(formats: format)
    static var barcodeScanner = BarcodeScanner.barcodeScanner(options: barcodeOptions)
    
    @objc
    public static func callback(_ frame: Frame!, withArgs _: [Any]!) -> Any! {
        let image = VisionImage(buffer: frame.buffer)
        image.orientation = .up
        var barCodeAttributes: [Any] = []
        
        do {
            let barcodes: [Barcode] =  try barcodeScanner.results(in: image)
            if (!barcodes.isEmpty){
                for barcode in barcodes {
                    barCodeAttributes.append(self.convertBarcode(barcode: barcode))
                }
            }
        } catch _ {
            return nil
        }
        
        return barCodeAttributes
    }
    
    static func convertContent(barcode: Barcode) -> Any {
        var map: [String: Any] = [:]
        
        map["type"] = barcode.valueType
        
        switch barcode.valueType {
        case .unknown, .ISBN, .text:
            map["content"] = barcode.rawValue
        case .contactInfo:
            map["content"] = BarcodeConverter.convertToMap(contactInfo: barcode.contactInfo)
        case .email:
            map["content"] = BarcodeConverter.convertToMap(email: barcode.email)
        case .phone:
            map["content"] = BarcodeConverter.convertToMap(phone: barcode.phone)
        case .SMS:
            map["content"] = BarcodeConverter.convertToMap(sms: barcode.sms)
        case .URL:
            map["content"] = BarcodeConverter.convertToMap(url: barcode.url)
        case .wiFi:
            map["content"] = BarcodeConverter.convertToMap(wifi: barcode.wifi)
        case .geographicCoordinates:
            map["content"] = BarcodeConverter.convertToMap(geoPoint: barcode.geoPoint)
        case .calendarEvent:
            map["content"] = BarcodeConverter.convertToMap(calendarEvent: barcode.calendarEvent)
        case .driversLicense:
            map["content"] = BarcodeConverter.convertToMap(driverLicense: barcode.driverLicense)
        default:
            map = [:]
        }
        
        return map
    }
    
    static func convertBarcode(barcode: Barcode) -> Any {
        var map: [String: Any] = [:]
        
        map["cornerPoints"] = BarcodeConverter.convertToArray(barcode.cornerPoints)
        map["displayValue"] = barcode.displayValue
        map["rawValue"] = barcode.rawValue
        map["content"] = self.convertContent(barcode: barcode)
        
        return map
    }
}

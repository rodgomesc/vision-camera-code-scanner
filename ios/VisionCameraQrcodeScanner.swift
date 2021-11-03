import MLKitBarcodeScanning
import MLKitVision

@objc(VisionCameraQrcodeScanner)
class VisionCameraQrcodeScanner: NSObject, FrameProcessorPluginBase {
    
    static var barcodeScanner: BarcodeScanner?
    static var barcodeRawFormats: [Int]?
    
    @objc
    public static func callback(_ frame: Frame!, withArgs args: [Any]!) -> Any! {
        let image = VisionImage(buffer: frame.buffer)
        image.orientation = .up
        var barCodeAttributes: [Any] = []
        do {
            try self.createScanner(args)
            let barcodes: [Barcode] = try barcodeScanner!.results(in: image)
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
    
    static func createScanner(_ args: [Any]!) throws {
        guard let rawFormats = args[0] as? [Int] else {
            throw BarcodeError.noBarcodeFormatProvided
        }
        if (barcodeScanner == nil || barcodeRawFormats != rawFormats) {
            var formats: [BarcodeFormat] = []
            rawFormats.forEach { rawFormat in
                formats.append(BarcodeFormat(rawValue: rawFormat))
            }
            let barcodeOptions = BarcodeScannerOptions(formats: BarcodeFormat(formats))
            barcodeScanner = BarcodeScanner.barcodeScanner(options: barcodeOptions)
            barcodeRawFormats = rawFormats
        }
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
        
        map["cornerPoints"] = barcode.cornerPoints
        map["displayValue"] = barcode.displayValue
        map["rawValue"] = barcode.rawValue
        map["content"] = self.convertContent(barcode: barcode)
        
        return map
    }
}

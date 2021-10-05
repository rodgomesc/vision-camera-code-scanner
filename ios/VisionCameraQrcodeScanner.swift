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
                    barCodeAttributes.append(convertBarcode(barcode))
                }
            }
        } catch _ {
            return nil
        }
        
        return barCodeAttributes
    }

    func convertContent(barcode: Barcode) -> Any! {
        var map: [String: Any] = [:]

        map["type"] = barcode.valueType

        switch barcode.valueType {
            case .unknown, .ISBN, .text:
                map["content"] = barcode.rawValue
            case .contactInfo:
                map["content"] = BarcodeConverter.convertToMap(barcode.contactInfo)
            case .email:
                map["content"] = BarcodeConverter.convertToMap(barcode.email)
            case .phone:
                map["content"] = BarcodeConverter.convertToMap(barcode.phone)
            case .SMS:
                map["content"] = BarcodeConverter.convertToMap(barcode.sms)
            case .URL:
                map["content"] = BarcodeConverter.convertToMap(barcode.url)
            case .wiFi:
                map["content"] = BarcodeConverter.convertToMap(barcode.wifi)
            case .geographicCoordinates:
                map["content"] = BarcodeConverter.convertToMap(barcode.geoPoint)
            case .calendarEvent:
                map["content"] = BarcodeConverter.convertToMap(barcode.calendarEvent)
            case .driversLicense:
                map["content"] = BarcodeConverter.convertToMap(barcode.driverLicense)
        }

        return map
    }

    func convertBarcode(barcode: Barcode) -> Any! {
        var map: [String: Any] = [:]

        map["cornerPoints"] = barcode.cornerPoints
        map["displayValue"] = barcode.displayValue
        map["rawValue"] = barcode.rawValue
        map["content"] = convertContent(barcode)

        return map
    }
}

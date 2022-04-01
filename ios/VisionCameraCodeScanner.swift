import MLKitBarcodeScanning
import MLKitVision

@objc(VisionCameraCodeScanner)
class VisionCameraCodeScanner: NSObject, FrameProcessorPluginBase {
    
    static var barcodeScanner: BarcodeScanner?
    static var barcodeFormatOptionSet: BarcodeFormat = []
    
    @objc
    public static func callback(_ frame: Frame!, withArgs args: [Any]!) -> Any! {
        let image = VisionImage(buffer: frame.buffer)
        image.orientation = .up
        
        var barCodeAttributes: [Any] = []
        
        do {
            try self.createScanner(args)
            var barcodes: [Barcode] = []
            barcodes.append(contentsOf: try barcodeScanner!.results(in: image))
            
            if let options = args[1] as? [String: Any] {
                let checkInverted = options["checkInverted"] as? Bool ?? false
                if (checkInverted) {
                    guard let buffer = CMSampleBufferGetImageBuffer(frame.buffer) else {
                        return nil
                    }
                    let ciImage = CIImage(cvPixelBuffer: buffer)
                    guard let invertedImage = invert(src: ciImage) else {
                        return nil
                    }
                    barcodes.append(contentsOf: try barcodeScanner!.results(in: VisionImage.init(image: invertedImage)))
                }
            }
            
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
        var formatOptionSet: BarcodeFormat = []
        rawFormats.forEach { rawFormat in
            if (rawFormat == 0) {
                // ALL is a special case, since the Android and iOS option raw values don't match
                formatOptionSet.insert(.all)
            } else {
                formatOptionSet.insert(BarcodeFormat(rawValue: rawFormat))
            }
        }
        if (barcodeScanner == nil || barcodeFormatOptionSet != formatOptionSet) {
            let barcodeOptions = BarcodeScannerOptions(formats: formatOptionSet)
            barcodeScanner = BarcodeScanner.barcodeScanner(options: barcodeOptions)
            barcodeFormatOptionSet = formatOptionSet
        }
    }
    
    static func convertContent(barcode: Barcode) -> Any {
        var map: [String: Any] = [:]
        
        map["type"] = barcode.valueType
        
        switch barcode.valueType {
        case .unknown, .ISBN, .text:
            map["data"] = barcode.rawValue
        case .contactInfo:
            map["data"] = BarcodeConverter.convertToMap(contactInfo: barcode.contactInfo)
        case .email:
            map["data"] = BarcodeConverter.convertToMap(email: barcode.email)
        case .phone:
            map["data"] = BarcodeConverter.convertToMap(phone: barcode.phone)
        case .SMS:
            map["data"] = BarcodeConverter.convertToMap(sms: barcode.sms)
        case .URL:
            map["data"] = BarcodeConverter.convertToMap(url: barcode.url)
        case .wiFi:
            map["data"] = BarcodeConverter.convertToMap(wifi: barcode.wifi)
        case .geographicCoordinates:
            map["data"] = BarcodeConverter.convertToMap(geoPoint: barcode.geoPoint)
        case .calendarEvent:
            map["data"] = BarcodeConverter.convertToMap(calendarEvent: barcode.calendarEvent)
        case .driversLicense:
            map["data"] = BarcodeConverter.convertToMap(driverLicense: barcode.driverLicense)
        default:
            map = [:]
        }
        
        return map
    }
    
    static func convertBarcode(barcode: Barcode) -> Any {
        var map: [String: Any] = [:]
        
        map["cornerPoints"] = BarcodeConverter.convertToArray(points: barcode.cornerPoints as? [CGPoint])
        map["displayValue"] = barcode.displayValue
        map["rawValue"] = barcode.rawValue
        map["content"] = self.convertContent(barcode: barcode)
        map["format"] = barcode.format.rawValue
        
        return map
    }
    
    // CIImage Inversion Filter https://stackoverflow.com/a/42987565
    static func invert(src: CIImage) -> UIImage? {
        guard let filter = CIFilter(name: "CIColorInvert") else { return nil }
        filter.setDefaults()
        filter.setValue(src, forKey: kCIInputImageKey)
        let context = CIContext(options: nil)
        guard let outputImage = filter.outputImage else { return nil }
        guard let outputImageCopy = context.createCGImage(outputImage, from: outputImage.extent) else { return nil }
        return UIImage(cgImage: outputImageCopy, scale: 1, orientation: .up)
    }
}

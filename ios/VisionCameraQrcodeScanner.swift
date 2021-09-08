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
                   var map: [String: Any] = [:]
                   switch barcode.valueType {
                   case .URL:
                       map["title"] = barcode.url?.title
                       map["url"] = barcode.url?.url
                   case .wiFi:
                       map["password"] = barcode.wifi?.password
                       map["encryptionType"] = barcode.wifi?.type
                       map["ssid"] = barcode.wifi?.ssid
                   default:
                       // TODO: implement all valueTypes https://developers.google.com/ml-kit/reference/swift/mlkitbarcodescanning/api/reference/Classes/Barcode
                       map = [:]
                   }
                   barCodeAttributes.append(map)
                 }
           }
         } catch _ {
               return nil
         }
       
       return barCodeAttributes
        
    }
}

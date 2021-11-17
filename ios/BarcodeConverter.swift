import MLKitBarcodeScanning

class BarcodeConverter {
    public static func convertToArray(points: [CGPoint]?) -> Any! {
        return points?.map(convertToMap(point:))
    }

    public static func convertToMap(point: CGPoint?) -> Any! {
        var map: [String: Any] = [:]

        map["x"] = point?.x
        map["y"] = point?.y

        return map;
    }

    public static func convertToMap(url: BarcodeURLBookmark?) -> Any! {
        var map: [String: Any] = [:]

        map["title"] = url?.title
        map["url"] = url?.url

        return map
    }

    public static func convertToMap(wifi: BarcodeWifi?) -> Any! {
        var map: [String: Any] = [:]

        map["encryptionType"] = wifi?.type
        map["password"] = wifi?.password
        map["ssid"] = wifi?.ssid

        return map
    }

    public static func convertToMap(sms: BarcodeSMS?) -> Any! {
        var map: [String: Any] = [:]

        map["message"] = sms?.message
        map["phoneNumber"] = sms?.phoneNumber

        return map
    }

    public static func convertToMap(phone: BarcodePhone?) -> Any! {
        var map: [String: Any] = [:]

        map["number"] = phone?.number
        map["type"] = phone?.type

        return map
    }

    public static func convertToMap(geoPoint: BarcodeGeoPoint?) -> Any! {
        var map: [String: Any] = [:]

        map["lat"] = geoPoint?.latitude
        map["lng"] = geoPoint?.longitude

        return map
    }

    public static func convertToMap(email: BarcodeEmail?) -> Any! {
        var map: [String: Any] = [:]

        map["address"] = email?.address
        map["body"] = email?.body
        map["subject"] = email?.subject
        map["type"] = email?.type

        return map
    }

    public static func convertToMap(name: BarcodePersonName?) -> Any! {
        var map: [String: Any] = [:]

        map["first"] = name?.first
        map["formattedName"] = name?.formattedName
        map["last"] = name?.last
        map["middle"] = name?.middle
        map["prefix"] = name?.prefix
        map["pronunciation"] = name?.pronunciation
        map["suffix"] = name?.suffix

        return map
    }

    public static func convertToMap(driverLicense: BarcodeDriverLicense?) -> Any! {
        var map: [String: Any] = [:]

        map["addressCity"] = driverLicense?.addressCity
        map["addressState"] = driverLicense?.addressState
        map["addressStreet"] = driverLicense?.addressStreet
        map["addressZip"] = driverLicense?.addressZip
        map["birthDate"] = driverLicense?.birthDate
        map["documentType"] = driverLicense?.documentType
        map["expiryDate"] = driverLicense?.expiryDate
        map["firstName"] = driverLicense?.firstName
        map["gender"] = driverLicense?.gender
        map["issueDate"] = driverLicense?.issuingDate
        map["issuingCountry"] = driverLicense?.issuingCountry
        map["lastName"] = driverLicense?.lastName
        map["licenseNumber"] = driverLicense?.licenseNumber
        map["middleName"] = driverLicense?.middleName

        return map
    }

    public static func convertToMap(address: BarcodeAddress?) -> Any! {
        var map: [String: Any] = [:]

        map["addressLines"] = address?.addressLines
        map["type"] = address?.type

        return map
    }

    public static func convertToMap(calendarEvent: BarcodeCalendarEvent?) -> Any! {
        var map: [String: Any] = [:]

        map["description"] = calendarEvent?.eventDescription
        map["end"] = calendarEvent?.end
        map["location"] = calendarEvent?.location
        map["organizer"] = calendarEvent?.organizer
        map["start"] = calendarEvent?.start
        map["status"] = calendarEvent?.status
        map["summary"] = calendarEvent?.summary

        return map
    }

    public static func convertToMap(contactInfo: BarcodeContactInfo?) -> Any! {
        var map: [String: Any] = [:]

        map["addresses"] = contactInfo?.addresses
        map["emails"] = contactInfo?.emails
        map["name"] = BarcodeConverter.convertToMap(name: contactInfo?.name)
        map["organization"] = contactInfo?.organization
        map["phones"] = contactInfo?.phones
        map["title"] = contactInfo?.jobTitle
        map["urls"] = contactInfo?.urls

        return map
    }
}

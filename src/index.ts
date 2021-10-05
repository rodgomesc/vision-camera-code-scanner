import type { Frame } from 'react-native-vision-camera';

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.BarcodeValueType
 */
export enum QrCodeType {
  UNKNOWN = 0,
  CONTACT_INFO = 1,
  EMAIL = 2,
  ISBN = 3,
  PHONE = 4,
  PRODUCT = 5,
  SMS = 6,
  TEXT = 7,
  URL = 8,
  WIFI = 9,
  GEO = 10,
  CALENDAR_EVENT = 11,
  DRIVER_LICENSE = 12,
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Address.AddressType
 */
export enum QrCodeAddressType {
  UNKNOWN = 0,
  WORK = 1,
  HOME = 2,
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Address
 */
export interface QrCodeAddress {
  addressLines?: string[];
  type?: QrCodeAddressType;
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.PersonName
 */
export interface QrCodePersonName {
  first?: string;
  formattedName?: string;
  last?: string;
  middle?: string;
  prefix?: string;
  pronunciation?: string;
  suffix?: string;
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.ContactInfo
 */
export interface QrCodeContactInfo {
  addresses?: QrCodeAddress[];
  emails?: QrCodeEmail[];
  name?: QrCodePersonName[];
  organization?: string;
  phones?: QrCodePhone[];
  title?: string;
  urls?: string[];
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Email.FormatType
 */
export enum QrCodeEmailType {
  UNKNOWN = 0,
  WORK = 1,
  HOME = 2,
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Email
 */
export interface QrCodeEmail {
  address?: string;
  body?: string;
  subject?: string;
  type?: QrCodeEmailType;
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Phone.FormatType
 */
export enum QrCodePhoneType {
  UNKNOWN = 0,
  WORK = 1,
  HOME = 2,
  FAX = 3,
  MOBILE = 4,
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Phone
 */
export interface QrCodePhone {
  number?: string;
  type?: QrCodePhoneType;
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Sms
 */
export interface QrCodeSms {
  message?: string;
  phoneNumber?: string;
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.UrlBookmark
 */
export interface QrCodeUrl {
  title?: string;
  url?: string;
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.WiFi.EncryptionType
 */
export enum QrCodeWifiType {
  OPEN = 1,
  WPA = 2,
  WEP = 3,
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.WiFi
 */
export interface QrCodeWifi {
  encryptionType?: QrCodeWifiType;
  password?: string;
  ssid?: string;
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.GeoPoint
 */
export interface QrCodeGeoPoint {
  lat?: number;
  lng?: number;
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.CalendarDateTime
 */
export interface QrCodeDate {
  seconds: number;
  rawValue: string;
  isUtc: boolean;
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.CalendarEvent
 */
export interface QrCodeCalendarEvent {
  description?: string;
  end?: QrCodeDate;
  location?: string;
  organizer?: string;
  start?: QrCodeDate;
  status?: string;
  summary?: string;
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.DriverLicense
 */
export interface QrCodeDriverLicense {
  addressCity?: string;
  addressState?: string;
  addressStreet?: string;
  addressZip?: string;
  birthDate?: string;
  documentType?: string;
  expiryDate?: string;
  firstName?: string;
  gender?: string;
  issueDate?: string;
  issuingCountry?: string;
  lastName?: string;
  licenseNumber?: string;
  middleName?: string;
}

/**
 * @see https://developer.android.com/reference/android/graphics/Rect.html
 */
export interface Rect {
  bottom: number;
  left: number;
  right: number;
  top: number;
}

/**
 * @see https://developer.android.com/reference/android/graphics/Point.html
 */
export interface Point {
  x: number;
  y: number;
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode
 */
export type QrCode = {
  boundingBox?: Rect;
  cornerPoints?: Point[];
  displayValue?: string;
  content:
    | {
        type: QrCodeType.UNKNOWN;
        data: string;
      }
    | {
        type: QrCodeType.CONTACT_INFO;
        data: QrCodeContactInfo;
      }
    | {
        type: QrCodeType.EMAIL;
        data: QrCodeEmail;
      }
    | {
        type: QrCodeType.ISBN;
        data: string;
      }
    | {
        type: QrCodeType.PHONE;
        data: QrCodePhone;
      }
    | {
        type: QrCodeType.SMS;
        data: QrCodeSms;
      }
    | {
        type: QrCodeType.TEXT;
        data: string;
      }
    | {
        type: QrCodeType.URL;
        data: QrCodeUrl;
      }
    | {
        type: QrCodeType.WIFI;
        data: QrCodeWifi;
      }
    | {
        type: QrCodeType.GEO;
        data: QrCodeGeoPoint;
      }
    | {
        type: QrCodeType.CALENDAR_EVENT;
        data: QrCodeCalendarEvent;
      }
    | {
        type: QrCodeType.DRIVER_LICENSE;
        data: QrCodeDriverLicense;
      };
};

/**
 * Scans QR codes.
 */
export function scanQRCodes(frame: Frame): QrCode[] {
  'worklet';
  // @ts-ignore
  // eslint-disable-next-line no-undef
  return __scanQRCodes(frame);
}

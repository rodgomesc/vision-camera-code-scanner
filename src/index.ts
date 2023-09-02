import { VisionCameraProxy, Frame } from 'react-native-vision-camera';

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.BarcodeFormat
 */
export enum BarcodeFormat {
  UNKNOWN = -1,
  ALL_FORMATS = 0,
  CODE_128 = 1,
  CODE_39 = 2,
  CODE_93 = 4,
  CODABAR = 8,
  DATA_MATRIX = 16,
  EAN_13 = 32,
  EAN_8 = 64,
  ITF = 128,
  QR_CODE = 256,
  UPC_A = 512,
  UPC_E = 1024,
  PDF417 = 2048,
  AZTEC = 4096,
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.BarcodeValueType
 */
export enum BarcodeValueType {
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
export enum AddressType {
  UNKNOWN = 0,
  WORK = 1,
  HOME = 2,
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Address
 */
export type Address = {
  addressLines?: string[];
  type?: AddressType;
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.PersonName
 */
export type PersonName = {
  first?: string;
  formattedName?: string;
  last?: string;
  middle?: string;
  prefix?: string;
  pronunciation?: string;
  suffix?: string;
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.ContactInfo
 */
export type ContactInfo = {
  addresses?: Address[];
  emails?: Email[];
  name?: PersonName;
  organization?: string;
  phones?: Phone[];
  title?: string;
  urls?: string[];
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Email.FormatType
 */
export enum EmailType {
  UNKNOWN = 0,
  WORK = 1,
  HOME = 2,
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Email
 */
export type Email = {
  address?: string;
  body?: string;
  subject?: string;
  type?: EmailType;
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Phone.FormatType
 */
export enum PhoneType {
  UNKNOWN = 0,
  WORK = 1,
  HOME = 2,
  FAX = 3,
  MOBILE = 4,
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Phone
 */
export type Phone = {
  number?: string;
  type?: PhoneType;
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.Sms
 */
export type Sms = {
  message?: string;
  phoneNumber?: string;
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.UrlBookmark
 */
export type UrlBookmark = {
  title?: string;
  url?: string;
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.WiFi.EncryptionType
 */
export enum EncryptionType {
  OPEN = 1,
  WPA = 2,
  WEP = 3,
}

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.WiFi
 */
export type Wifi = {
  encryptionType?: EncryptionType;
  password?: string;
  ssid?: string;
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.GeoPoint
 */
export type GeoPoint = {
  lat?: number;
  lng?: number;
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.CalendarDateTime
 */
export type Date = {
  day: number;
  hours: number;
  minutes: number;
  month: number;
  rawValue: string;
  seconds: number;
  year: number;
  isUtc: boolean;
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.CalendarEvent
 */
export type CalendarEvent = {
  description?: string;
  end?: Date;
  location?: string;
  organizer?: string;
  start?: Date;
  status?: string;
  summary?: string;
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode.DriverLicense
 */
export type DriverLicense = {
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
};

/**
 * @see https://developer.android.com/reference/android/graphics/Rect.html
 */
export type Rect = {
  bottom: number;
  left: number;
  right: number;
  top: number;
};

/**
 * @see https://developer.android.com/reference/android/graphics/Point.html
 */
export type Point = {
  x: number;
  y: number;
};

/**
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/barcode/Barcode
 */
export type Barcode = {
  boundingBox?: Rect;
  cornerPoints?: Point[];
  displayValue?: string;
  rawValue?: string;
  format: BarcodeFormat;
  content:
    | {
        type:
          | BarcodeValueType.UNKNOWN
          | BarcodeValueType.ISBN
          | BarcodeValueType.TEXT;
        data: string;
      }
    | {
        type: BarcodeValueType.CONTACT_INFO;
        data: ContactInfo;
      }
    | {
        type: BarcodeValueType.EMAIL;
        data: Email;
      }
    | {
        type: BarcodeValueType.PHONE;
        data: Phone;
      }
    | {
        type: BarcodeValueType.SMS;
        data: Sms;
      }
    | {
        type: BarcodeValueType.URL;
        data: UrlBookmark;
      }
    | {
        type: BarcodeValueType.WIFI;
        data: Wifi;
      }
    | {
        type: BarcodeValueType.GEO;
        data: GeoPoint;
      }
    | {
        type: BarcodeValueType.CALENDAR_EVENT;
        data: CalendarEvent;
      }
    | {
        type: BarcodeValueType.DRIVER_LICENSE;
        data: DriverLicense;
      };
};

export type CodeScannerOptions = {
  /**
   * checkInverted: `Allows you to also scan white barcode with black backgrounds`
   */
  checkInverted: boolean;
};

export const DefaultCodeScannerOptions = {
  checkInverted: false,
};

const plugin =
  VisionCameraProxy.getFrameProcessorPlugin<Barcode[]>('scanCodes');

/**
 * Scans barcodes in the passed frame with MLKit
 *
 * @param frame Camera frame
 * @param types Array of barcode types to detect (for optimal performance, use less types)
 * @returns Detected barcodes from MLKit
 */
export function scanBarcodes(
  frame: Frame,
  types: BarcodeFormat[],
  options: CodeScannerOptions = DefaultCodeScannerOptions
): Barcode[] {
  'worklet';

  if (plugin == null)
    throw new Error('Failed to load Frame Processor Plugin "scanCodes"!');

  return plugin.call(frame, {
    ...options,
    types,
  });
}

export * from './hook';

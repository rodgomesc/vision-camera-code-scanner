import type { Frame } from 'react-native-vision-camera';

/**
 * Scans QR codes.
 */

export interface QrCode {
  title?: string;
  url?: string;
  password?: string;
  encryptionType?: string;
  ssid?: string;
}

export function scanQRCodes(frame: Frame): QrCode[] {
  'worklet';
  // @ts-ignore
  // eslint-disable-next-line no-undef
  return __scanQRCodes(frame);
}

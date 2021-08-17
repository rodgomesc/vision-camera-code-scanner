import type { Frame } from 'react-native-vision-camera';

/**
 * Scans QR codes.
 */

export interface QrCode {
  title: string;
  url: string;
}

export function scanQRCodes(frame: Frame): QrCode[] {
  'worklet';
  return __scanQRCodes(frame);
}

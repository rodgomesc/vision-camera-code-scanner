import { NativeModules } from 'react-native';

type VisionCameraQrcodeScannerType = {
  multiply(a: number, b: number): Promise<number>;
};

const { VisionCameraQrcodeScanner } = NativeModules;

export default VisionCameraQrcodeScanner as VisionCameraQrcodeScannerType;

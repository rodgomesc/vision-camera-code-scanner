import { Frame, useFrameProcessor } from 'react-native-vision-camera';
import { useState } from 'react';

import {
  Barcode,
  BarcodeFormat,
  CodeScannerOptions,
  scanBarcodes,
} from './index';

export function useScanBarcodes(
  types: BarcodeFormat[],
  options?: CodeScannerOptions
): [(frame: Frame) => void, Barcode[]] {
  const [barcodes, setBarcodes] = useState<Barcode[]>([]);
  const setBarcodesJS = Worklets.createRunInJsFn(setBarcodes);

  const frameProcessor = useFrameProcessor((frame) => {
    'worklet';
    const opts = Object.keys(options || {}).reduce((acc, key) => {
      acc[key as keyof CodeScannerOptions] = (options || {})[
        key as keyof CodeScannerOptions
      ];
      return acc;
    }, {} as CodeScannerOptions);
    const detectedBarcodes = scanBarcodes(frame, Array.from(types), opts);
    setBarcodesJS(detectedBarcodes);
  }, []);

  return [frameProcessor, barcodes];
}

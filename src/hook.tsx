import { Frame, useFrameProcessor } from 'react-native-vision-camera';
import { useState } from 'react';
import { runOnJS } from 'react-native-reanimated';

import { Barcode, BarcodeFormat, CodeScannerOptions, scanBarcodes } from '.';

export function useScanBarcodes(
  types: BarcodeFormat[],
  options?: CodeScannerOptions
): [(frame: Frame) => void, Barcode[]] {
  const [barcodes, setBarcodes] = useState<Barcode[]>([]);

  const frameProcessor = useFrameProcessor((frame) => {
    'worklet';
    const detectedBarcodes = scanBarcodes(frame, types, options);
    runOnJS(setBarcodes)(detectedBarcodes);
  }, []);

  return [frameProcessor, barcodes];
}

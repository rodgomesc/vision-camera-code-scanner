import { Frame, useFrameProcessor } from 'react-native-vision-camera';
import { useState } from 'react';
import { runOnJS } from 'react-native-reanimated';

import { Barcode, BarcodeFormat, scanBarcodes } from '.';

export function useScanBarcodes(
  types: BarcodeFormat[]
): [(frame: Frame) => void, Barcode[]] {
  const [barcodes, setBarcodes] = useState<Barcode[]>([]);

  const frameProcessor = useFrameProcessor((frame) => {
    'worklet';
    const detectedBarcodes = scanBarcodes(frame, types);
    runOnJS(setBarcodes)(detectedBarcodes);
  }, []);

  return [frameProcessor, barcodes];
}

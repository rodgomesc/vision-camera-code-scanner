import { FrameProcessor, useFrameProcessor } from 'react-native-vision-camera';
import { useState } from 'react';

import {
  Barcode,
  BarcodeFormat,
  CodeScannerOptions,
  DefaultCodeScannerOptions,
  scanBarcodes,
} from './index';

export function useScanBarcodes(
  types: BarcodeFormat[],
  options: CodeScannerOptions = DefaultCodeScannerOptions
): [FrameProcessor, Barcode[]] {
  const [barcodes, setBarcodes] = useState<Barcode[]>([]);
  const setBarcodesJS = Worklets.createRunInJsFn(setBarcodes);

  const frameProcessor = useFrameProcessor((frame) => {
    'worklet';
    // For some reason, arguments to hooks accessed from worklets don't have the same runtime type they
    // are declared as. This leads to tricky errors, hence the Array.from and reduce on the options' keys.
    //
    // Example error:
    // Frame Processor threw an error: Exception in HostFunction: Received an unknown HostObject! Cannot convert to a JNI value.
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

# vision-camera-qrcode-scanner

VisionCamera Frame Processor Plugin to read barcodes using MLKit Vision Barcode Scanning

## Installation

```sh
yarn add vision-camera-qrcode-scanner
```
Add this to your `babel.config.js`
```
[
  'react-native-reanimated/plugin',
  {
    globals: ['__scanQRCodes'],
  },
]
```

## Usage

Simply call the `useScanBarcodes()` hook or call `scanBarcodes()` inside of the `useFrameProcessor()` hook. In both cases you will need to pass an array of `BarcodeFormat` to specify the kind of barcode you want to detect. 

```js
import * as React from 'react';
import { runOnJS } from 'react-native-reanimated';

import { StyleSheet, Text } from 'react-native';
import {
  useCameraDevices,
  useFrameProcessor,
} from 'react-native-vision-camera';
import { Camera } from 'react-native-vision-camera';
import { scanQRCodes, BarcodeFormat } from 'vision-camera-qrcode-scanner';

export default function App() {
   const [hasPermission, setHasPermission] = React.useState(false);
  const devices = useCameraDevices();
  const device = devices.back;
  
  const barcodes = useScanBarcodes([BarcodeFormat.QR_CODE]);

  // Alternatively you can use the underlying function:
  // 
  // const frameProcessor = useFrameProcessor((frame) => {
  //   'worklet';
  //   const detectedBarcodes = scanBarcodes(frame, [BarcodeFormat.QR_CODE]);
  //   runOnJS(setBarcodes)(detectedBarcodes);
  // }, []);

  React.useEffect(() => {
    (async () => {
      const status = await Camera.requestCameraPermission();
      setHasPermission(status === 'authorized');
    })();
  }, []);

  return (
    device != null &&
    hasPermission && (
      <>
        <Camera
          style={StyleSheet.absoluteFill}
          device={device}
          isActive={true}
          frameProcessor={frameProcessor}
          frameProcessorFps={5}
        />
        {barcodes.map((barcode, idx) => (
          <Text key={idx} style={styles.barcodeTextURL}>
            {barcode.displayValue}
          </Text>
        ))}
      <p/>
    )
  );
}

const styles = StyleSheet.create({
  barcodeTextURL: {
    fontSize: 20,
    color: 'white',
    fontWeight: 'bold',
  },
});

```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

# vision-camera-code-scanner

VisionCamera Frame Processor Plugin to read barcodes using MLKit Vision Barcode Scanning

## Installation

```sh
yarn add vision-camera-code-scanner
```

make sure you correctly [setup](https://github.com/chrfalch/react-native-worklets) react-native-worklets and insert as a first line of your [`index.tsx`](https://github.com/rodgomesc/vision-camera-code-scanner/blob/1409a8afd02328a26e336036493b2d6ef8441359/example/index.tsx#L1)

```sh
import "react-native-worklets/src";
```

Add this to your `babel.config.js`

```
["react-native-worklets/plugin"],
```

## Usage

Simply call the `useScanBarcodes()` hook or call `scanBarcodes()` inside of the `useFrameProcessor()` hook. In both cases you will need to pass an array of `BarcodeFormat` to specify the kind of barcode you want to detect.

> Note: The underlying MLKit barcode reader is only created once meaning that changes to the array will not be reflected in the app.

```js
import * as React from 'react';

import { StyleSheet, Text } from 'react-native';
import { useCameraDevices } from 'react-native-vision-camera';
import { Camera } from 'react-native-vision-camera';
import { useScanBarcodes, BarcodeFormat } from 'vision-camera-code-scanner';

export default function App() {
  const [hasPermission, setHasPermission] = React.useState(false);
  const devices = useCameraDevices();
  const device = devices.back;

  const [frameProcessor, barcodes] = useScanBarcodes([BarcodeFormat.QR_CODE], {
    checkInverted: true,
  });

  // Alternatively you can use the underlying function:
  //
  // const [barcodes, setBarcodes] = React.useState<Barcode[]>([]);
  // const setBarcodesJS = Worklets.createRunInJsFn(setBarcodes);
  //
  // const frameProcessor = useFrameProcessor((frame) => {
  //   'worklet';
  //   const detectedBarcodes = scanBarcodes(frame, [BarcodeFormat.ALL_FORMATS], {
  //     checkInverted: true,
  //   });
  //   setBarcodesJS(detectedBarcodes);
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
      </>
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

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

```js
import * as React from 'react';
import { runOnJS } from 'react-native-reanimated';

import { StyleSheet, Text } from 'react-native';
import {
  useCameraDevices,
  useFrameProcessor,
} from 'react-native-vision-camera';
import { Camera } from 'react-native-vision-camera';
import { scanQRCodes, QrCode } from 'vision-camera-qrcode-scanner';

export default function App() {
  const [hasPermission, setHasPermission] = React.useState(false);
  const [qrCodes, setQrCodes] = React.useState<QrCode[]>([]);
  const devices = useCameraDevices();
  const device = devices.back;

  React.useEffect(() => {
    (async () => {
      const status = await Camera.requestCameraPermission();
      setHasPermission(status === 'authorized');
    })();
  }, []);

  const frameProcessor = useFrameProcessor((frame) => {
    'worklet';
    const qrcode = scanQRCodes(frame);
    runOnJS(setQrCodes)(qrcode);
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
        {qrCodes.map((qrcode, idx) => (
          <Text key={idx} style={styles.qrCodeTextURL}>
            {qrcode.url}
          </Text>
        ))}
      </>
    )
  );
}

const styles = StyleSheet.create({
  qrCodeTextURL: {
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

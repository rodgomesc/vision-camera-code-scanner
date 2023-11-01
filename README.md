
## 🔔 Update: Vision Camera v3

**Good news!** Vision Camera v3 now includes native barcode scanning!

🛑 As a result, maintenance and support for the library with Vision Camera v2 will be discontinued. Please consider upgrading to v3 for the best experience.

Thank you for your support and understanding.



# vision-camera-code-scanner

VisionCamera Frame Processor Plugin to read barcodes using MLKit Vision Barcode Scanning



## Installation

```sh
yarn add vision-camera-code-scanner
```

make sure you correctly [setup](https://docs.swmansion.com/react-native-reanimated/docs/fundamentals/installation/) react-native-reanimated and insert as a first line of your [`index.tsx`](https://github.com/rodgomesc/vision-camera-code-scanner/blob/1409a8afd02328a26e336036493b2d6ef8441359/example/index.tsx#L1)

```sh
import 'react-native-reanimated'
```

Add this to your `babel.config.js`

```js
[
  'react-native-reanimated/plugin',
  {
    globals: ['__scanCodes'],
  },
]
```

## Ios podfile workaround

On ios if you have `use_frameworks` in your podfile, you must use this workaround to compile your project:
```rb
pre_install do |installer|
  installer.pod_targets.each do |pod|
    if pod.name.eql?('vision-camera-code-scanner') || pod.name.eql?('VisionCamera')
      def pod.build_type
        Pod::BuildType.static_library
      end
    end
  end
end
```
Make sure to put this just above `use_react_native!` as advised in https://github.com/rodgomesc/vision-camera-code-scanner/issues/62#issuecomment-1272598027

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
  // const frameProcessor = useFrameProcessor((frame) => {
  //   'worklet';
  //   const detectedBarcodes = scanBarcodes(frame, [BarcodeFormat.QR_CODE], { checkInverted: true });
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

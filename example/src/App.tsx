import * as React from 'react';

import { runOnJS } from 'react-native-reanimated';
import { Button, StyleSheet, Text } from 'react-native';
import {
  useCameraDevices,
  useFrameProcessor,
} from 'react-native-vision-camera';
import { Camera } from 'react-native-vision-camera';
import {
  scanBarcodes,
  BarcodeFormat,
  Barcode,
} from 'vision-camera-code-scanner';
import {
  ImagePickerResponse,
  launchImageLibrary,
} from 'react-native-image-picker';
import MlkitOcr from 'react-native-mlkit-ocr';

function launchGallery() {
  launchImageLibrary(
    {
      mediaType: 'photo',
    },
    async (response: ImagePickerResponse) => {
      console.log(response);
      const uri = response?.assets?.[0].uri;
      if (!uri) {
        throw new Error('oh!');
      }
      try {
        console.log(uri);
        const result = await MlkitOcr.detectFromUri(uri);
        console.log(result);
      } catch (e) {
        console.error(e);
      }
    }
  );
}

export default function App() {
  const [hasPermission, setHasPermission] = React.useState(false);
  const [barcodes, setBarcodes] = React.useState<Barcode[]>([]);
  const devices = useCameraDevices();
  const device = devices.back;

  const frameProcessor = useFrameProcessor((frame) => {
    'worklet';
    const data = scanBarcodes(frame, [BarcodeFormat.ALL_FORMATS], {
      checkInverted: true,
    });
    runOnJS(setBarcodes)(data);
  }, []);

  React.useEffect(() => {
    (async () => {
      const status = await Camera.requestCameraPermission();
      setHasPermission(status === 'authorized');
    })();
  }, []);

  React.useEffect(() => {
    // console.log(barcodes);
  }, [barcodes]);

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
        <Button onPress={launchGallery} title="Start" />
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

package com.visioncameraqrcodescanner;

import static com.visioncameraqrcodescanner.BarcodeConverter.convertToArray;
import static com.visioncameraqrcodescanner.BarcodeConverter.convertToMap;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;

import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageProxy;

import com.google.android.gms.tasks.Tasks;
import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.common.InputImage;


import java.util.List;

public class VisionCameraQrcodeScannerPlugin extends FrameProcessorPlugin {
  // Note that if you know which format of barcode your app is dealing with, detection will be
  // faster than specify the supported barcode formats one by one, e.g.
  private final BarcodeScanner barcodeScanner =
    BarcodeScanning.getClient(
      new BarcodeScannerOptions.Builder()
      .setBarcodeFormats(
        Barcode.FORMAT_QR_CODE,
        Barcode.FORMAT_EAN_13
      )
      .build());

  @Override
  public Object callback(ImageProxy frame, Object[] params) {
    @SuppressLint("UnsafeOptInUsageError")
    Image mediaImage = frame.getImage();
    if (mediaImage != null) {
      InputImage image = InputImage.fromMediaImage(mediaImage, frame.getImageInfo().getRotationDegrees());
      Task<List<Barcode>> task = barcodeScanner.process(image);

      try {
        List<Barcode> barcodes = Tasks.await(task);

        WritableNativeArray array = new WritableNativeArray();
        for (Barcode barcode: barcodes) {
          array.pushMap(convertBarcode(barcode));
        }
        return array;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  private WritableNativeMap convertContent(@NonNull Barcode barcode) {
    WritableNativeMap map = new WritableNativeMap();

    int type = barcode.getValueType();
    map.putInt("type", type);

    switch (type) {
      case Barcode.TYPE_UNKNOWN:
      case Barcode.TYPE_ISBN:
        map.putString("content", barcode.getRawValue());
        break;
      case Barcode.TYPE_TEXT:
        map.putString("content", barcode.getRawValue());
        break;
      case Barcode.TYPE_CONTACT_INFO:
        map.putMap("content", convertToMap(barcode.getContactInfo()));
        break;
      case Barcode.TYPE_EMAIL:
        map.putMap("content", convertToMap(barcode.getEmail()));
        break;
      case Barcode.TYPE_PHONE:
        map.putMap("content", convertToMap(barcode.getPhone()));
        break;
      case Barcode.TYPE_SMS:
        map.putMap("content", convertToMap(barcode.getSms()));
        break;
      case Barcode.TYPE_URL:
        map.putMap("content", convertToMap(barcode.getUrl()));
        break;
      case Barcode.TYPE_WIFI:
        map.putMap("content", convertToMap(barcode.getWifi()));
        break;
      case Barcode.TYPE_GEO:
        map.putMap("content", convertToMap(barcode.getGeoPoint()));
        break;
      case Barcode.TYPE_CALENDAR_EVENT:
        map.putMap("content", convertToMap(barcode.getCalendarEvent()));
        break;
      case Barcode.TYPE_DRIVER_LICENSE:
        map.putMap("content", convertToMap(barcode.getDriverLicense()));
        break;
    }

    return map;
  }

  private WritableNativeMap convertBarcode(@NonNull Barcode barcode) {
    WritableNativeMap map = new WritableNativeMap();

    Rect boundingBox = barcode.getBoundingBox();
    if (boundingBox != null) {
      map.putMap("boundingBox", convertToMap(boundingBox));
    }

    Point[] cornerPoints = barcode.getCornerPoints();
    if (cornerPoints != null) {
      map.putArray("cornerPoints", convertToArray(cornerPoints));
    }

    String displayValue = barcode.getDisplayValue();
    if (displayValue != null) {
      map.putString("displayValue", displayValue);
    }

    String rawValue = barcode.getRawValue();
    if (rawValue != null) {
      map.putString("rawValue", rawValue);
    }

    map.putMap("content", convertContent(barcode));

    return map;
  }

  VisionCameraQrcodeScannerPlugin() {
    super("scanQRCodes");
  }
}

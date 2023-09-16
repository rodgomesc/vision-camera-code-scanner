package com.visioncameracodescanner;

import static com.visioncameracodescanner.BarcodeConverter.convertToArray;
import static com.visioncameracodescanner.BarcodeConverter.convertToMap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;

import com.mrousavy.camera.frameprocessor.Frame;
import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin;
import com.mrousavy.camera.parsers.Orientation;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.Nullable;

import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.ImageConvertUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class VisionCameraCodeScannerPlugin extends FrameProcessorPlugin {
  private BarcodeScanner barcodeScanner = null;
  private int barcodeScannerFormatsBitmap = -1;

  private static final Set<Integer> barcodeFormats = new HashSet<>(Arrays.asList(
    Barcode.FORMAT_UNKNOWN,
    Barcode.FORMAT_ALL_FORMATS,
    Barcode.FORMAT_CODE_128,
    Barcode.FORMAT_CODE_39,
    Barcode.FORMAT_CODE_93,
    Barcode.FORMAT_CODABAR,
    Barcode.FORMAT_DATA_MATRIX,
    Barcode.FORMAT_EAN_13,
    Barcode.FORMAT_EAN_8,
    Barcode.FORMAT_ITF,
    Barcode.FORMAT_QR_CODE,
    Barcode.FORMAT_UPC_A,
    Barcode.FORMAT_UPC_E,
    Barcode.FORMAT_PDF417,
    Barcode.FORMAT_AZTEC
  ));

  @Override
  public Object callback(@NonNull Frame frame, @Nullable Map<String, Object> params) {
    createBarcodeInstance(params);

    @SuppressLint("UnsafeOptInUsageError")
    Image mediaImage = frame.getImage();
    if (mediaImage != null) {
      ArrayList<Task<List<Barcode>>> tasks = new ArrayList<Task<List<Barcode>>>();
      InputImage image = InputImage.fromMediaImage(mediaImage, Orientation.Companion.fromUnionValue(frame.getOrientation()).toDegrees());

      if (params != null && params.containsKey("checkInverted")) {
        boolean checkInverted = (Boolean) params.get("checkInverted");

        if (checkInverted) {
          Bitmap bitmap = null;
          try {
            bitmap = ImageConvertUtils.getInstance().getUpRightBitmap(image);
            Bitmap invertedBitmap = this.invert(bitmap);
            InputImage invertedImage = InputImage.fromBitmap(invertedBitmap, 0);
            tasks.add(barcodeScanner.process(invertedImage));
          } catch (Exception e) {
            e.printStackTrace();
            return null;
          }
        }
      }

      tasks.add(barcodeScanner.process(image));

      try {
        ArrayList<Barcode> barcodes = new ArrayList<Barcode>();
        for (Task<List<Barcode>> task : tasks) {
          barcodes.addAll(Tasks.await(task));
        }

        List<Object> array = new ArrayList<>();
        for (Barcode barcode : barcodes) {
          array.add(convertBarcode(barcode));
        }
        return array;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  private void createBarcodeInstance(@Nullable Map<String, Object> params) {
    if (params != null && params.containsKey("types") && params.get("types") instanceof ArrayList) {
      ArrayList<Double> rawFormats = (ArrayList<Double>) params.get("types");

      int formatsBitmap = 0;
      int formatsIndex = 0;
      int formatsSize = rawFormats.size();
      int[] formats = new int[formatsSize];

      for (int i = 0; i < formatsSize; i++) {
        int format = rawFormats.get(i).intValue();
        if (barcodeFormats.contains(format)){
          formats[formatsIndex] = format;
          formatsIndex++;
          formatsBitmap |= format;
        }
      }

      if (formatsIndex == 0) {
        throw new ArrayIndexOutOfBoundsException("Need to provide at least one valid Barcode format");
      }

      if (barcodeScanner == null || formatsBitmap != barcodeScannerFormatsBitmap) {
        barcodeScanner = BarcodeScanning.getClient(
          new BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
              formats[0],
              Arrays.copyOfRange(formats, 1, formatsIndex)
            )
            .build());
        barcodeScannerFormatsBitmap = formatsBitmap;
      }
    } else {
      throw new IllegalArgumentException("Second parameter must be an Array");
    }
  }

  private Map<String, Object> convertContent(@NonNull Barcode barcode) {
    Map<String, Object> map = new HashMap<>();

    int type = barcode.getValueType();
    map.put("type", type);

    switch (type) {
      case Barcode.TYPE_UNKNOWN:
      case Barcode.TYPE_ISBN:
      case Barcode.TYPE_TEXT:
        map.put("data", barcode.getRawValue());
        break;
      case Barcode.TYPE_CONTACT_INFO:
        map.put("data", convertToMap(barcode.getContactInfo()));
        break;
      case Barcode.TYPE_EMAIL:
        map.put("data", convertToMap(barcode.getEmail()));
        break;
      case Barcode.TYPE_PHONE:
        map.put("data", convertToMap(barcode.getPhone()));
        break;
      case Barcode.TYPE_SMS:
        map.put("data", convertToMap(barcode.getSms()));
        break;
      case Barcode.TYPE_URL:
        map.put("data", convertToMap(barcode.getUrl()));
        break;
      case Barcode.TYPE_WIFI:
        map.put("data", convertToMap(barcode.getWifi()));
        break;
      case Barcode.TYPE_GEO:
        map.put("data", convertToMap(barcode.getGeoPoint()));
        break;
      case Barcode.TYPE_CALENDAR_EVENT:
        map.put("data", convertToMap(barcode.getCalendarEvent()));
        break;
      case Barcode.TYPE_DRIVER_LICENSE:
        map.put("data", convertToMap(barcode.getDriverLicense()));
        break;
    }

    return map;
  }

  private Map<String, Object> convertBarcode(@NonNull Barcode barcode) {
    Map<String, Object> map = new HashMap<>();

    Rect boundingBox = barcode.getBoundingBox();
    if (boundingBox != null) {
      map.put("boundingBox", convertToMap(boundingBox));
    }

    Point[] cornerPoints = barcode.getCornerPoints();
    if (cornerPoints != null) {
      map.put("cornerPoints", convertToArray(cornerPoints));
    }

    String displayValue = barcode.getDisplayValue();
    if (displayValue != null) {
      map.put("displayValue", displayValue);
    }

    String rawValue = barcode.getRawValue();
    if (rawValue != null) {
      map.put("rawValue", rawValue);
    }

    map.put("content", convertContent(barcode));
    map.put("format", barcode.getFormat());

    return map;
  }

  // Bitmap Inversion https://gist.github.com/moneytoo/87e3772c821cb1e86415
  private Bitmap invert(Bitmap src)
	{ 
		int height = src.getHeight();
		int width = src.getWidth();    

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		
		ColorMatrix matrixGrayscale = new ColorMatrix();
		matrixGrayscale.setSaturation(0);
		
		ColorMatrix matrixInvert = new ColorMatrix();
		matrixInvert.set(new float[]
		{
			-1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
			0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
			0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
			0.0f, 0.0f, 0.0f, 1.0f, 0.0f
		});
		matrixInvert.preConcat(matrixGrayscale);
		
		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrixInvert);
		paint.setColorFilter(filter);
		
		canvas.drawBitmap(src, 0, 0, paint);
		return bitmap;
	}

  VisionCameraCodeScannerPlugin() {

  }
}

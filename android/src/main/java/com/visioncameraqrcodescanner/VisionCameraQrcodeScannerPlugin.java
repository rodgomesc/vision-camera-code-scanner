package com.visioncameraqrcodescanner;

import androidx.camera.core.ImageProxy;
import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin;

public class QRCodeFrameProcessorPlugin extends FrameProcessorPlugin {

  @Override
  public Object callback(ImageProxy image, Object[] params) {
    // code goes here
    return null;
  }

  QRCodeFrameProcessorPlugin() {
    super("scanQRCodes");
  }
}

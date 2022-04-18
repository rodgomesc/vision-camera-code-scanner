package com.visioncameracodescanner;

import android.graphics.Point;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.mlkit.vision.barcode.common.Barcode;

import java.util.List;

/**
 * Converter util class used to convert Barcode related variables to either WritableNativeArray or
 * WritableNativeMap
 */
public class BarcodeConverter {
  public static WritableNativeArray convertToArray(@NonNull Point[] points) {
    WritableNativeArray array = new WritableNativeArray();

    for (Point point: points) {
      array.pushMap(convertToMap(point));
    }

    return array;
  }

  public static WritableNativeArray convertToArray(@NonNull String[] elements) {
    WritableNativeArray array = new WritableNativeArray();

    for (String elem: elements) {
      array.pushString(elem);
    }

    return array;
  }

  public static WritableNativeArray convertStringList(@NonNull List<String> elements) {
    WritableNativeArray array = new WritableNativeArray();

    for (String elem: elements) {
      array.pushString(elem);
    }

    return array;
  }

  public static WritableNativeArray convertAddressList(@NonNull List<Barcode.Address> addresses) {
    WritableNativeArray array = new WritableNativeArray();

    for (Barcode.Address address: addresses) {
      array.pushMap(convertToMap(address));
    }

    return array;
  }

  public static WritableNativeArray convertPhoneList(@NonNull List<Barcode.Phone> phones) {
    WritableNativeArray array = new WritableNativeArray();

    for (Barcode.Phone phone: phones) {
      array.pushMap(convertToMap(phone));
    }

    return array;
  }

  public static WritableNativeArray convertEmailList(@NonNull List<Barcode.Email> emails) {
    WritableNativeArray array = new WritableNativeArray();

    for (Barcode.Email email: emails) {
      array.pushMap(convertToMap(email));
    }

    return array;
  }

  public static WritableNativeMap convertToMap(@NonNull Point point) {
    WritableNativeMap map = new WritableNativeMap();

    map.putInt("x", point.x);
    map.putInt("y", point.y);

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.Address address) {
    WritableNativeMap map = new WritableNativeMap();

    map.putArray("addressLines", convertToArray(address.getAddressLines()));
    map.putInt("type", address.getType());

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Rect rect) {
    WritableNativeMap map = new WritableNativeMap();

    map.putInt("bottom", rect.bottom);
    map.putInt("left", rect.left);
    map.putInt("right", rect.right);
    map.putInt("top", rect.top);

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.ContactInfo contactInfo) {
    WritableNativeMap map = new WritableNativeMap();

    map.putArray("addresses", convertAddressList(contactInfo.getAddresses()));
    map.putArray("emails", convertEmailList(contactInfo.getEmails()));
    map.putMap("name", convertToMap(contactInfo.getName()));
    map.putString("organization", contactInfo.getOrganization());
    map.putArray("phones", convertPhoneList(contactInfo.getPhones()));
    map.putString("title", contactInfo.getTitle());
    map.putArray("urls", convertStringList(contactInfo.getUrls()));

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.PersonName name) {
    WritableNativeMap map = new WritableNativeMap();

    map.putString("first", name.getFirst());
    map.putString("formattedName", name.getFormattedName());
    map.putString("last", name.getLast());
    map.putString("middle", name.getMiddle());
    map.putString("prefix", name.getPrefix());
    map.putString("pronunciation", name.getPronunciation());
    map.putString("suffix", name.getSuffix());

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.UrlBookmark url) {
    WritableNativeMap map = new WritableNativeMap();

    map.putString("title", url.getTitle());
    map.putString("url", url.getUrl());

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.Email email) {
    WritableNativeMap map = new WritableNativeMap();

    map.putString("address", email.getAddress());
    map.putString("body", email.getBody());
    map.putString("subject", email.getSubject());
    map.putInt("type", email.getType());

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.Phone phone) {
    WritableNativeMap map = new WritableNativeMap();

    map.putString("number", phone.getNumber());
    map.putInt("type", phone.getType());

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.Sms sms) {
    WritableNativeMap map = new WritableNativeMap();

    map.putString("message", sms.getMessage());
    map.putString("phoneNumber", sms.getPhoneNumber());

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.WiFi wifi) {
    WritableNativeMap map = new WritableNativeMap();

    map.putInt("encryptionType", wifi.getEncryptionType());
    map.putString("password", wifi.getPassword());
    map.putString("ssid", wifi.getSsid());

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.GeoPoint geoPoint) {
    WritableNativeMap map = new WritableNativeMap();

    map.putDouble("lat", geoPoint.getLat());
    map.putDouble("lng", geoPoint.getLng());

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.CalendarDateTime calendarDateTime) {
    WritableNativeMap map = new WritableNativeMap();

    map.putInt("day", calendarDateTime.getDay());
    map.putInt("hours", calendarDateTime.getHours());
    map.putInt("minutes", calendarDateTime.getMinutes());
    map.putInt("month", calendarDateTime.getMonth());
    map.putString("rawValue", calendarDateTime.getRawValue());
    map.putInt("year", calendarDateTime.getYear());
    map.putInt("seconds", calendarDateTime.getSeconds());
    map.putBoolean("isUtc", calendarDateTime.isUtc());

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.CalendarEvent calendarEvent) {
    WritableNativeMap map = new WritableNativeMap();

    map.putString("description", calendarEvent.getDescription());
    map.putMap("end", convertToMap(calendarEvent.getEnd()));
    map.putString("location", calendarEvent.getLocation());
    map.putString("organizer", calendarEvent.getOrganizer());
    map.putMap("start", convertToMap(calendarEvent.getStart()));
    map.putString("status", calendarEvent.getStatus());
    map.putString("summary", calendarEvent.getSummary());

    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Barcode.DriverLicense driverLicense) {
    WritableNativeMap map = new WritableNativeMap();

    map.putString("addressCity", driverLicense.getAddressCity());
    map.putString("addressState", driverLicense.getAddressState());
    map.putString("addressStreet", driverLicense.getAddressStreet());
    map.putString("addressZip", driverLicense.getAddressZip());
    map.putString("birthDate", driverLicense.getBirthDate());
    map.putString("documentType", driverLicense.getDocumentType());
    map.putString("expiryDate", driverLicense.getExpiryDate());
    map.putString("firstName", driverLicense.getFirstName());
    map.putString("gender", driverLicense.getGender());
    map.putString("issueDate", driverLicense.getIssueDate());
    map.putString("issuingCountry", driverLicense.getIssuingCountry());
    map.putString("lastName", driverLicense.getLastName());
    map.putString("licenseNumber", driverLicense.getLicenseNumber());
    map.putString("middleName", driverLicense.getMiddleName());

    return map;
  }
}

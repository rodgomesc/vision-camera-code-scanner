package com.visioncameracodescanner;

import android.graphics.Point;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.google.mlkit.vision.barcode.common.Barcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converter util class used to convert Barcode related variables to either WritableNativeArray or
 * WritableNativeMap
 */
public class BarcodeConverter {
  public static List<Object> convertToArray(@NonNull Point[] points) {
    List<Object> array = new ArrayList<>();

    for (Point point: points) {
      array.add(convertToMap(point));
    }

    return array;
  }

  public static List<Object> convertToArray(@NonNull String[] elements) {
    List<Object> array = new ArrayList<>();

    for (String elem: elements) {
      array.add(elem);
    }

    return array;
  }

  public static List<Object> convertStringList(@NonNull List<String> elements) {
    List<Object> array = new ArrayList<>();

    for (String elem: elements) {
      array.add(elem);
    }

    return array;
  }

  public static List<Object> convertAddressList(@NonNull List<Barcode.Address> addresses) {
    List<Object> array = new ArrayList<>();

    for (Barcode.Address address: addresses) {
      array.add(convertToMap(address));
    }

    return array;
  }

  public static List<Object> convertPhoneList(@NonNull List<Barcode.Phone> phones) {
    List<Object> array = new ArrayList<>();

    for (Barcode.Phone phone: phones) {
      array.add(convertToMap(phone));
    }

    return array;
  }

  public static List<Object> convertEmailList(@NonNull List<Barcode.Email> emails) {
    List<Object> array = new ArrayList<>();

    for (Barcode.Email email: emails) {
      array.add(convertToMap(email));
    }

    return array;
  }

  public static Map<String, Object> convertToMap(@NonNull Point point) {
    Map<String, Object> map = new HashMap<>();

    map.put("x", point.x);
    map.put("y", point.y);

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.Address address) {
    Map<String, Object> map = new HashMap<>();

    map.put("addressLines", convertToArray(address.getAddressLines()));
    map.put("type", address.getType());

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Rect rect) {
    Map<String, Object> map = new HashMap<>();

    map.put("bottom", rect.bottom);
    map.put("left", rect.left);
    map.put("right", rect.right);
    map.put("top", rect.top);

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.ContactInfo contactInfo) {
    Map<String, Object> map = new HashMap<>();

    map.put("addresses", convertAddressList(contactInfo.getAddresses()));
    map.put("emails", convertEmailList(contactInfo.getEmails()));
    map.put("name", convertToMap(contactInfo.getName()));
    map.put("organization", contactInfo.getOrganization());
    map.put("phones", convertPhoneList(contactInfo.getPhones()));
    map.put("title", contactInfo.getTitle());
    map.put("urls", convertStringList(contactInfo.getUrls()));

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.PersonName name) {
    Map<String, Object> map = new HashMap<>();

    map.put("first", name.getFirst());
    map.put("formattedName", name.getFormattedName());
    map.put("last", name.getLast());
    map.put("middle", name.getMiddle());
    map.put("prefix", name.getPrefix());
    map.put("pronunciation", name.getPronunciation());
    map.put("suffix", name.getSuffix());

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.UrlBookmark url) {
    Map<String, Object> map = new HashMap<>();

    map.put("title", url.getTitle());
    map.put("url", url.getUrl());

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.Email email) {
    Map<String, Object> map = new HashMap<>();

    map.put("address", email.getAddress());
    map.put("body", email.getBody());
    map.put("subject", email.getSubject());
    map.put("type", email.getType());

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.Phone phone) {
    Map<String, Object> map = new HashMap<>();

    map.put("number", phone.getNumber());
    map.put("type", phone.getType());

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.Sms sms) {
    Map<String, Object> map = new HashMap<>();

    map.put("message", sms.getMessage());
    map.put("phoneNumber", sms.getPhoneNumber());

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.WiFi wifi) {
    Map<String, Object> map = new HashMap<>();

    map.put("encryptionType", wifi.getEncryptionType());
    map.put("password", wifi.getPassword());
    map.put("ssid", wifi.getSsid());

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.GeoPoint geoPoint) {
    Map<String, Object> map = new HashMap<>();

    map.put("lat", geoPoint.getLat());
    map.put("lng", geoPoint.getLng());

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.CalendarDateTime calendarDateTime) {
    Map<String, Object> map = new HashMap<>();

    map.put("day", calendarDateTime.getDay());
    map.put("hours", calendarDateTime.getHours());
    map.put("minutes", calendarDateTime.getMinutes());
    map.put("month", calendarDateTime.getMonth());
    map.put("rawValue", calendarDateTime.getRawValue());
    map.put("year", calendarDateTime.getYear());
    map.put("seconds", calendarDateTime.getSeconds());
    map.put("isUtc", calendarDateTime.isUtc());

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.CalendarEvent calendarEvent) {
    Map<String, Object> map = new HashMap<>();

    map.put("description", calendarEvent.getDescription());
    map.put("end", convertToMap(calendarEvent.getEnd()));
    map.put("location", calendarEvent.getLocation());
    map.put("organizer", calendarEvent.getOrganizer());
    map.put("start", convertToMap(calendarEvent.getStart()));
    map.put("status", calendarEvent.getStatus());
    map.put("summary", calendarEvent.getSummary());

    return map;
  }

  public static Map<String, Object> convertToMap(@NonNull Barcode.DriverLicense driverLicense) {
    Map<String, Object> map = new HashMap<>();

    map.put("addressCity", driverLicense.getAddressCity());
    map.put("addressState", driverLicense.getAddressState());
    map.put("addressStreet", driverLicense.getAddressStreet());
    map.put("addressZip", driverLicense.getAddressZip());
    map.put("birthDate", driverLicense.getBirthDate());
    map.put("documentType", driverLicense.getDocumentType());
    map.put("expiryDate", driverLicense.getExpiryDate());
    map.put("firstName", driverLicense.getFirstName());
    map.put("gender", driverLicense.getGender());
    map.put("issueDate", driverLicense.getIssueDate());
    map.put("issuingCountry", driverLicense.getIssuingCountry());
    map.put("lastName", driverLicense.getLastName());
    map.put("licenseNumber", driverLicense.getLicenseNumber());
    map.put("middleName", driverLicense.getMiddleName());

    return map;
  }
}

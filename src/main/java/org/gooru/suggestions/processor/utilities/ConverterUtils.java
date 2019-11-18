package org.gooru.suggestions.processor.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.gooru.suggestions.processor.data.TxCodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public final class ConverterUtils {
  private final static Logger LOGGER = LoggerFactory.getLogger(ConverterUtils.class);

  public static UUID convertToUuid(JsonObject input, String key) {
    String value = input.getString(key);
    if (value == null || value.isEmpty()) {
      return null;
    }
    return UUID.fromString(value);
  }
  
  public static Long convertToLong(JsonObject input, String key) {
    try {
      Long value = input.getLong(key);
      if (value == null) {
        return null;
      }
      return value;
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid value for long");
    }
  }
  
  public static Boolean convertToBoolean(JsonObject input, String key) {
    try {
      Boolean value = input.getBoolean(key);
      if (value == null) {
        return false;
      }
      return value;
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid value for boolean. true/false expected.");
    }
  }

  public static List<Long> convertFromJsonArrayToListOfLong(JsonObject input, String key) {
    try {
      JsonArray value = input.getJsonArray(key);
      if (value == null) {
        return null;
      }
      List<Long> listOfLong = new ArrayList<>(value.size());
      value.forEach(v -> {
        listOfLong.add(Long.valueOf(v.toString()));
      });
      return listOfLong;
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid numeric type");
    }
  }
  
  public static TxCodeType toTxCodeType(String txCodeType) {
    if (txCodeType != null) {
      return TxCodeType.builder(txCodeType);
    }
    return null;
  }
  
  private ConverterUtils() {
    throw new AssertionError();
  }
}

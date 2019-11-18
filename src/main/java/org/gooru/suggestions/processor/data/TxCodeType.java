package org.gooru.suggestions.processor.data;

import java.util.HashMap;
import java.util.Map;

public enum TxCodeType {
  Competency("competency"),
  MicroCompetency("micro-competency"),
  AltConcept("alt-concept");

  private final String name;

  TxCodeType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  private static final Map<String, TxCodeType> LOOKUP = new HashMap<>(values().length);

  static {
    for (TxCodeType txCodeType : values()) {
      LOOKUP.put(txCodeType.name, txCodeType);
    }
  }

  public static TxCodeType builder(String type) {
    TxCodeType result = LOOKUP.get(type);
    if (result == null) {
      throw new IllegalArgumentException("Invalid taxonomy code type");
    }
    return result;
  }
}

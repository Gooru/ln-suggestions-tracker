package org.gooru.suggestions.processor.utilities;

import java.util.Iterator;
import java.util.List;

public class PGUtils {
  
  public static String listToPostgresArrayLong(List<Long> input) {
    Iterator<Long> it = input.iterator();
    if (!it.hasNext()) {
      return "{}";
    }
    
    StringBuilder sb = new StringBuilder();
    sb.append('{');
    for (;;) {
      Long s = (Long) it.next();
      sb.append(s);
      if (!it.hasNext()) {
        return sb.append('}').toString();
      }
      sb.append(',');
    }
  }

  private PGUtils() {
    throw new AssertionError();
  }
}

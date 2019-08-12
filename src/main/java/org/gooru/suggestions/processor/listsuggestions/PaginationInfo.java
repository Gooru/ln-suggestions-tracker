package org.gooru.suggestions.processor.listsuggestions;

import io.vertx.core.json.JsonObject;
import org.gooru.suggestions.app.components.AppConfiguration;

/**
 * @author ashish.
 */

class PaginationInfo {

  private final int offset;
  private final int max;

  private static final String MAX = "max";
  private static final String OFFSET = "offset";

  private PaginationInfo(int offset, int max) {
    this.offset = offset;
    this.max = max;
  }

  public int getOffset() {
    return offset;
  }

  public int getMax() {
    return max;
  }

  static PaginationInfo buildFromRequest(JsonObject request) {
    String maxString = request.getString(MAX);
    String offsetString = request.getString(OFFSET);

    int max = validatedMax(maxString);
    int offset = validatedOffset(offsetString);

    return new PaginationInfo(offset, max);
  }

  private static int validatedOffset(String offsetString) {
    if (offsetString == null) {
      return 0;
    }
    try {
      return Integer.parseInt(offsetString);
    } catch (NumberFormatException nfe) {
      return 0;
    }
  }

  private static int validatedMax(String maxString) {
    Integer maxAllowed = AppConfiguration.getInstance().maxAllowed();
    if (maxString == null) {
      return maxAllowed;
    }
    try {
      int max = Integer.parseInt(maxString);
      if (max <= maxAllowed) {
        return max;
      } else {
        return maxAllowed;
      }
    } catch (NumberFormatException nfe) {
      return maxAllowed;
    }
  }
}

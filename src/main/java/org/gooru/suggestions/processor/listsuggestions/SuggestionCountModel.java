package org.gooru.suggestions.processor.listsuggestions;

/**
 * @author renuka.
 */

public class SuggestionCountModel {

  private Long caContentId;
  private int total;

  public Long getCaContentId() {
    return caContentId;
  }
  public void setCaContentId(Long caContentId) {
    this.caContentId = caContentId;
  }
  public int getTotal() {
    return total;
  }
  public void setTotal(int total) {
    this.total = total;
  }
  
}

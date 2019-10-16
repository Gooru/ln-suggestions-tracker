package org.gooru.suggestions.processor.listsuggestions.common.enricher;

public class CollectionContentCountModel {

  private String id;
  private int contentCount;
  private String contentformat;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public int getContentCount() {
    return contentCount;
  }
  public void setContentCount(int contentCount) {
    this.contentCount = contentCount;
  }
  public String getContentformat() {
    return contentformat;
  }
  public void setContentformat(String contentformat) {
    this.contentformat = contentformat;
  }
}

package org.gooru.suggestions.processor.listsuggestions.common.enricher;

public class ContentModel {

  private String id;
  private String title;
  private String thumbnail;
  private String url;
  private String taxonomy;

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getThumbnail() {
    return thumbnail;
  }
  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getTaxonomy() {
    return taxonomy;
  }
  public void setTaxonomy(String taxonomy) {
    this.taxonomy = taxonomy;
  }
  
}

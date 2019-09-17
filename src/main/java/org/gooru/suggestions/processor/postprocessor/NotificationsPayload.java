package org.gooru.suggestions.processor.postprocessor;

class NotificationsPayload {

  private String userId;
  private String classId;
  private String courseId;
  private String unitId;
  private String lessonId;
  private String collectionId;
  private String currentItemId;
  private String currentItemType;
  private String pathType;
  private Long pathId;
  private String notificationType;
  private String action;
  private String contentSource;
  private Long caId;
  private String txCode;
  private String txCodeType;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getClassId() {
    return classId;
  }

  public void setClassId(String classId) {
    this.classId = classId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getLessonId() {
    return lessonId;
  }

  public void setLessonId(String lessonId) {
    this.lessonId = lessonId;
  }

  public String getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }

  public String getCurrentItemId() {
    return currentItemId;
  }

  public void setCurrentItemId(String currentItemId) {
    this.currentItemId = currentItemId;
  }

  public String getCurrentItemType() {
    return currentItemType;
  }

  public void setCurrentItemType(String currentItemType) {
    this.currentItemType = currentItemType;
  }

  public String getPathType() {
    return pathType;
  }

  public void setPathType(String pathType) {
    this.pathType = pathType;
  }

  public Long getPathId() {
    return pathId;
  }

  public void setPathId(Long pathId) {
    this.pathId = pathId;
  }

  public String getNotificationType() {
    return notificationType;
  }

  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getContentSource() {
    return contentSource;
  }

  public void setContentSource(String contentSource) {
    this.contentSource = contentSource;
  }

  public Long getCaId() {
    return caId;
  }

  public void setCaId(Long caId) {
    this.caId = caId;
  }

  public String getTxCode() {
    return txCode;
  }

  public void setTxCode(String txCode) {
    this.txCode = txCode;
  }

  public String getTxCodeType() {
    return txCodeType;
  }

  public void setTxCodeType(String txCodeType) {
    this.txCodeType = txCodeType;
  }
}

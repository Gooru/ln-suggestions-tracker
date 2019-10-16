package org.gooru.suggestions.processor.listsuggestions.common.enricher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.gooru.suggestions.processor.utilities.jdbi.DBICreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Take the activity list and fetch details for each activity to enrich it and render it
 *
 * @author ashish.
 * @param <T>
 */

class ContentEnricherForAllTypesOfActivities<T> implements ContentEnricher {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ContentEnricherForAllTypesOfActivities.class);
  public static final Pattern ASSESSMENT_TYPES = Pattern.compile("assessment|assessment-external");
  public static final Pattern COLLECTION_TYPES = Pattern.compile("collection|collection-external");
  public static final String OFFLINE_ACTIVITY = "offline-activity";
  public static final String RESOURCE = "resource";
  public static final String QUESTION = "question";
  public static final String SUGGESTED_CONTENT_TYPE = "suggestedContentType";
  public static final String SUGGESTED_CONTENT_ID = "suggestedContentId";
  public static final String COLLECTION_ID = "collectionId";
  public static final String SUGGESTIONS = "suggestions";
  public static final String SUGGESTED_CONTENTS = "suggestedContents";
  public static final String SUGGESTED_FOR_CONTENT = "suggestedForContent";

  private final Object suggestions;
  private final boolean isCaResponse;

  private List<String> contentIds = new ArrayList<>();
  private List<String> collectionIds = new ArrayList<>();

  private JsonObject unenrichedActivities;
  private JsonObject enrichedActivities;
  private CollectionAssessmentEnricherService collectionAssessmentEnricherService;
  private ResourceQuestionEnricherService resourceQuestionEnricherService;

  ContentEnricherForAllTypesOfActivities(Object suggestions, boolean isCaResponse) {
    this.suggestions = suggestions;
    this.isCaResponse = isCaResponse;
  }

  @Override
  public JsonObject enrichContent() {
    initializeUnenrichedActivities();
    enrichedActivities = new JsonObject();

    doEnrichment();
    return enrichedActivities;
  }

  private void doEnrichment() {
    if (unenrichedActivities.size() > 0) {
      if (isCaResponse) {
        initializeIdsForContentTypesFromCAResponse(unenrichedActivities);
      } else {
        initializeIdsForContentTypes(unenrichedActivities);
      }
      if (contentIds.size() > 0) {
        resourceQuestionEnricherService =
            new ResourceQuestionEnricherService(DBICreator.getDbiForDefaultDS(), contentIds);
        resourceQuestionEnricherService.enrichContentInfo();
      }

      if (collectionIds.size() > 0) {
        collectionAssessmentEnricherService =
            new CollectionAssessmentEnricherService(DBICreator.getDbiForDefaultDS(), collectionIds);
        collectionAssessmentEnricherService.enrichCollectionInfo();
      }

      if (isCaResponse) {
        mergeCaResponseWithEnrichedData();
      } else {
        mergeResponseWithEnrichedData();
      }
    }
  }

  private void mergeResponseWithEnrichedData() {
    JsonArray suggestions = unenrichedActivities.getJsonArray(SUGGESTIONS);
    for (Object result : suggestions) {
      enrichSuggestedContentsWithMetadata(result);
    }
    enrichedActivities.put(SUGGESTIONS, suggestions);
  }

  private void mergeCaResponseWithEnrichedData() {
    JsonArray suggestions = unenrichedActivities.getJsonArray(SUGGESTIONS);
    for (Object suggestion : suggestions) {
      JsonArray suggestedContents = ((JsonObject) suggestion).getJsonArray(SUGGESTED_CONTENTS);
      for (Object content : suggestedContents) {
        content = enrichSuggestedContentsWithMetadata(content);
      }
    }
    enrichedActivities.put(SUGGESTIONS, suggestions);
  }

  private JsonObject enrichSuggestedContentsWithMetadata(Object result) {
    JsonObject data = ((JsonObject) result);
    JsonObject enrichmentInfo = fetchEnrichmentInfo(data);
    if (enrichmentInfo != null && !enrichmentInfo.isEmpty()) {
      data.mergeIn(enrichmentInfo);
    }
    return data;
  }

  private JsonObject fetchEnrichmentInfo(JsonObject data) {
    String contentType = data.getString(SUGGESTED_CONTENT_TYPE);
    String contentId = data.getString(SUGGESTED_CONTENT_ID);
    String collectionId = data.getString(COLLECTION_ID);

    JsonObject suggestedData = new JsonObject();
    if (checkContentTypeIsCollection(contentType)) {
      if (collectionAssessmentEnricherService != null) {
        suggestedData = collectionAssessmentEnricherService.getEnrichmentInfo(contentId);
      }
    } else if (checkContentTypeIsContent(contentType)) {
      if (resourceQuestionEnricherService != null) {
        suggestedData = resourceQuestionEnricherService.getEnrichmentInfo(contentId);
      }
    }
    JsonObject suggestions = new JsonObject();
    if (collectionId != null) {
      if (suggestedData != null) {
        suggestions = suggestedData.copy();
      }
      JsonObject suggestedForData =
          collectionAssessmentEnricherService.getEnrichmentInfo(collectionId);
      if (suggestedForData != null && !suggestedForData.isEmpty()) {
        suggestedForData.put(COLLECTION_ID, collectionId);
        suggestions.put(SUGGESTED_FOR_CONTENT, suggestedForData);
      }
    }
    return suggestions;
  }

  private void initializeUnenrichedActivities() {
    String resultString;
    try {
      resultString = new ObjectMapper().writeValueAsString(suggestions);
      unenrichedActivities = new JsonObject(resultString);
    } catch (Exception e) {
      LOGGER.warn("Encountered exception", e);
    }
  }

  private void initializeIdsForContentTypes(JsonObject results) {
    JsonArray suggestions = results.getJsonArray(SUGGESTIONS);
    for (Object content : suggestions) {
      segregateIdsByCollectionTypes(content);
    }
  }

  private void initializeIdsForContentTypesFromCAResponse(JsonObject results) {
    JsonArray suggestions = results.getJsonArray(SUGGESTIONS);
    for (Object suggestion : suggestions) {
      JsonArray suggestedContents = ((JsonObject) suggestion).getJsonArray(SUGGESTED_CONTENTS);
      for (Object content : suggestedContents) {
        segregateIdsByCollectionTypes(content);
      }
    }
  }

  private void segregateIdsByCollectionTypes(Object content) {
    JsonObject suggestedContent = (JsonObject) content;
    String suggestedContentType = suggestedContent.getString(SUGGESTED_CONTENT_TYPE);
    String suggestedContentId = suggestedContent.getString(SUGGESTED_CONTENT_ID);
    String collectionId = suggestedContent.getString(COLLECTION_ID);
    if (collectionId != null) {
      collectionIds.add(collectionId);
    }
    if (checkContentTypeIsCollection(suggestedContentType)) {
      collectionIds.add(suggestedContentId);
    } else if (checkContentTypeIsContent(suggestedContentType)) {
      contentIds.add(suggestedContentId);
    }
  }

  private boolean checkContentTypeIsCollection(String contentType) {
    return (ASSESSMENT_TYPES.matcher(contentType).matches()
        || COLLECTION_TYPES.matcher(contentType).matches()
        || (contentType.equalsIgnoreCase(OFFLINE_ACTIVITY)));
  }

  private boolean checkContentTypeIsContent(String contentType) {
    return (contentType.equalsIgnoreCase(RESOURCE) || contentType.equalsIgnoreCase(QUESTION));
  }

}

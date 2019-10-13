package org.gooru.suggestions.processor.listsuggestions.common.enricher;

import java.util.List;
import org.gooru.suggestions.constants.Constants;
import org.gooru.suggestions.processor.utilities.PGUtils;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.json.JsonObject;

class CollectionAssessmentEnricherService {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(CollectionAssessmentEnricherService.class);
  private JsonObject idToEnrichmentDataMap;
  private List<String> collectionIds;
  private boolean enrichmentDone = false;
  private CollectionDao collectionDao;
  private ContentDao contentDao;
  private static final String TASK_COUNT = "task_count";

  CollectionAssessmentEnricherService(DBI dbi, List<String> collectionIds) {
    this.collectionIds = collectionIds;
    this.collectionDao = dbi.onDemand(CollectionDao.class);
    this.contentDao = dbi.onDemand(ContentDao.class);
  }

  void enrichCollectionInfo() {
    if (!enrichmentDone) {
      idToEnrichmentDataMap = new JsonObject();
      String collectionArrayString = PGUtils.toPostgresArrayString(collectionIds);
      List<ContentModel> collections =
          collectionDao.findCollectionsForSpecifiedIds(collectionArrayString);
      if (collections != null) {
        collections.forEach(content -> {
          JsonObject data = createCollectionDetailJson(content);
          idToEnrichmentDataMap.put(content.getId(), data);
        });
      }

      List<CollectionContentCountModel> collectionContentCount =
          contentDao.fetchCollectionCounts(collectionArrayString);
      if (collectionContentCount != null) {
        collectionContentCount.forEach(data -> {
          final String key = (data.getContentformat().equalsIgnoreCase(ContentDao.QUESTION_FORMAT)
              ? ContentDao.QUESTION_COUNT
              : ContentDao.RESOURCE_COUNT);
          idToEnrichmentDataMap.getJsonObject(data.getId()).put(key, data.getContentCount());
        });
      }

      List<CollectionContentCountModel> oeQuestionCountFromDB =
          contentDao.fetchOEQuestionCount(collectionArrayString);
      if (oeQuestionCountFromDB != null) {
        oeQuestionCountFromDB.forEach(data -> {
          idToEnrichmentDataMap.getJsonObject(data.getId()).put(ContentDao.OE_QUESTION_COUNT,
              data.getContentCount());
        });
      }

      List<CountInfoModel> tasksCountListMap = collectionDao.fetchTaskCount(collectionArrayString);
      if (tasksCountListMap != null && !tasksCountListMap.isEmpty()) {
        tasksCountListMap.forEach(data -> {
          idToEnrichmentDataMap.getJsonObject(data.getId()).put(TASK_COUNT, data.getCount());
        });
      }

      enrichmentDone = true;
    }
  }

  private JsonObject createCollectionDetailJson(ContentModel content) {
    JsonObject data = new JsonObject();
    data.put(Constants.Response.RESP_TITLE, content.getTitle());
    data.put(Constants.Response.RESP_THUMBNAIL, content.getThumbnail());
    data.put(Constants.Response.RESP_URL, content.getUrl());
    String taxonomyString = content.getTaxonomy();
    JsonObject taxonomy = (taxonomyString == null || taxonomyString.isEmpty()) ? new JsonObject()
        : new JsonObject(taxonomyString);
    data.put(Constants.Response.RESP_TAXONOMY, taxonomy);
    return data;
  }

  JsonObject getEnrichmentInfo(String collectionId) {
    if (!enrichmentDone) {
      throw new IllegalStateException("Enrichment not done, while asking for enrichment data");
    }
    return idToEnrichmentDataMap.getJsonObject(collectionId);
  }
}

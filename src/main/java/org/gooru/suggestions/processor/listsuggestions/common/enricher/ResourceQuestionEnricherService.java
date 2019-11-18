package org.gooru.suggestions.processor.listsuggestions.common.enricher;

import java.util.List;
import org.gooru.suggestions.constants.Constants;
import org.gooru.suggestions.processor.utilities.PGUtils;
import org.skife.jdbi.v2.DBI;
import io.vertx.core.json.JsonObject;

/**
 * @author ashish.
 */

class ResourceQuestionEnricherService {

  private List<String> contentIds;
  private JsonObject idToEnrichmentDataMap;
  private boolean enrichmentDone = false;
  private ContentDao contentDao;

  ResourceQuestionEnricherService(DBI dbi, List<String> contentIds) {
    this.contentDao = dbi.onDemand(ContentDao.class);
    this.contentIds = contentIds;
  }

  void enrichContentInfo() {
    if (!enrichmentDone) {
      idToEnrichmentDataMap = new JsonObject();
      String contentArrayString = PGUtils.toPostgresArrayString(contentIds);
      List<ContentModel> contents = contentDao.fetchContentDetails(contentArrayString);

      contents.forEach(content -> {
        JsonObject data = createContentDetailJson(content);
        idToEnrichmentDataMap.put(content.getId(), data);
      });
      enrichmentDone = true;
    }
  }

  private JsonObject createContentDetailJson(ContentModel content) {
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

  JsonObject getEnrichmentInfo(String contentId) {
    if (!enrichmentDone) {
      throw new IllegalStateException("Enrichment not done, while asking for enrichment data");
    }
    return idToEnrichmentDataMap.getJsonObject(contentId);
  }
}

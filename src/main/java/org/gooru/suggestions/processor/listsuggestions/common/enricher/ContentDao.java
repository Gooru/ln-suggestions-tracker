package org.gooru.suggestions.processor.listsuggestions.common.enricher;

import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface ContentDao {

  public static final String RESOURCE_COUNT = "resource_count";
  public static final String QUESTION_COUNT = "question_count";
  public static final String OE_QUESTION_COUNT = "oe_question_count";
  public static final String CONTENT_COUNT = "content_count";
  public static final String RESOURCE_FORMAT = "resource";
  public static final String QUESTION_FORMAT = "question";
  
  @Mapper(CollectionContentCountMapper.class)
  @SqlQuery("SELECT count(id) as content_count, content_format, collection_id FROM content WHERE"
      + " collection_id = ANY(:collectionIds::uuid[])  AND is_deleted = false GROUP BY"
      + " collection_id, content_format")
  List<CollectionContentCountModel> fetchCollectionCounts(@Bind("collectionIds") String collectionIds);
  
  @Mapper(CollectionContentCountMapper.class)
  @SqlQuery("SELECT count(id) as content_count, content_format, collection_id FROM content WHERE collection_id = ANY(:collectionIds::uuid[]) AND"
      + " is_deleted = false AND content_format = 'question' AND"
      + " content_subformat = 'open_ended_question' GROUP BY collection_id, content_format")
  List<CollectionContentCountModel> fetchOEQuestionCount(@Bind("collectionIds") String collectionIds);
  
  @Mapper(ContentMapper.class)
  @SqlQuery("select id, title, thumbnail, taxonomy from content where id = ANY(:contentIds::uuid[])")
  List<ContentModel> fetchContentDetails(@Bind("contentIds") String contentIds);
  
}

package org.gooru.suggestions.processor.listsuggestions.common.enricher;

import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface CollectionDao {

  @Mapper(ContentMapper.class)
  @SqlQuery("SELECT id, title, thumbnail, url, taxonomy from collection where id = ANY(:collectionIds::uuid[])")
  List<ContentModel> findCollectionsForSpecifiedIds(@Bind("collectionIds") String collectionIds);

  @Mapper(CountInfoMapper.class)
  @SqlQuery("SELECT count(id) as count, oa_id as id FROM oa_tasks WHERE oa_id = ANY(:collectionIds::uuid[]) GROUP BY oa_id")
  List<CountInfoModel> fetchTaskCount(@Bind("collectionIds") String collectionIds);

}

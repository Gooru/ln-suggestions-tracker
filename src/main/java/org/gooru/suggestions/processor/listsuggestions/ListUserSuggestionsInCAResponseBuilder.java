package org.gooru.suggestions.processor.listsuggestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gooru.suggestions.processor.listsuggestions.ListSuggestionsInCAResponse.SuggestedContents;
import org.gooru.suggestions.processor.listsuggestions.ListSuggestionsInCAResponse.SuggestedToContext;
import org.gooru.suggestions.processor.listsuggestions.ListSuggestionsInCAResponse.Suggestions;

/**
 * @author Renuka
 *
 */
public class ListUserSuggestionsInCAResponseBuilder {

  public static ListSuggestionsInCAResponse buildDetailedResponse(
      List<SuggestionTrackerModel> suggestionsOfAllCAIds) {
    // Group suggestions by each ca id & suggested content id
    Map<String, SuggestedContents> groupSuggestionsByCaIds =
        groupSuggestionsByCaIdScId(suggestionsOfAllCAIds);

    // Group suggestions by each ca id
    Map<Long, List<SuggestedContents>> suggestionPerCaId =
        groupSuggestedContentsPerCaId(groupSuggestionsByCaIds);

    // Generate response object
    ListSuggestionsInCAResponse listOfSuggestionsForAllCAIds =
        generateResponseForAllCAIds(suggestionPerCaId);
    return listOfSuggestionsForAllCAIds;
  }

  public static ListSuggestionsInCAResponse buildCountResponse(List<CountInfoModel> suggestions) {
    ListSuggestionsInCAResponse listOfCASuggestions = new ListSuggestionsInCAResponse();
    List<Suggestions> listSuggestionInCAResponse = new ArrayList<>();
    suggestions.forEach(suggestion -> {
      Suggestions suggestionInCAResponse =
          generateSuggestionsModelResponse(suggestion.getId(), suggestion.getTotal(), null);
      listSuggestionInCAResponse.add(suggestionInCAResponse);
    });
    listOfCASuggestions.setSuggestions(listSuggestionInCAResponse);
    return listOfCASuggestions;
  }

  private static ListSuggestionsInCAResponse generateResponseForAllCAIds(
      Map<Long, List<SuggestedContents>> suggestionPerCaId) {
    ListSuggestionsInCAResponse listOfSuggestionsForAllCAIds = new ListSuggestionsInCAResponse();
    List<Suggestions> listSuggestionInCAResponse = new ArrayList<>();
    suggestionPerCaId.forEach((k, v) -> {
      Suggestions suggestionInCAResponse = generateSuggestionsModelResponse(k, v.size(), v);
      listSuggestionInCAResponse.add(suggestionInCAResponse);
    });
    listOfSuggestionsForAllCAIds.setSuggestions(listSuggestionInCAResponse);
    return listOfSuggestionsForAllCAIds;
  }

  private static Map<Long, List<SuggestedContents>> groupSuggestedContentsPerCaId(
      Map<String, SuggestedContents> groupUniqueSuggestionsPerCaId) {
    Map<Long, List<SuggestedContents>> suggestionPerCaId = new HashMap<>();
    groupUniqueSuggestionsPerCaId.forEach((k, v) -> {
      Long caId = Long.valueOf(k.split(" ")[0]);
      if (suggestionPerCaId.containsKey(caId)) {
        List<SuggestedContents> tempSuggestions = suggestionPerCaId.get(caId);
        tempSuggestions.add(v);
        suggestionPerCaId.put(caId, tempSuggestions);
      } else {
        List<SuggestedContents> suggestions = new ArrayList<>();
        suggestions.add(v);
        suggestionPerCaId.put(caId, suggestions);
      }
    });
    return suggestionPerCaId;
  }

  private static Map<String, SuggestedContents> groupSuggestionsByCaIdScId(
      List<SuggestionTrackerModel> suggestionsOfAllCAIds) {
    Map<String, SuggestedContents> groupSuggestionsByCaIdScId = new HashMap<>();
    if (suggestionsOfAllCAIds != null && !suggestionsOfAllCAIds.isEmpty()) {
      suggestionsOfAllCAIds.forEach(suggestion -> {
        String caIdSuggContentIdCombination =
            suggestion.getCaId() + " " + suggestion.getSuggestedContentId();
        SuggestedToContext suggestedToContext = generateSuggestedToContextModelResponse(suggestion);
        if (groupSuggestionsByCaIdScId.containsKey(caIdSuggContentIdCombination)) {
          SuggestedContents tempSuggestions =
              (SuggestedContents) groupSuggestionsByCaIdScId.get(caIdSuggContentIdCombination);
          List<SuggestedToContext> users =
              (List<SuggestedToContext>) tempSuggestions.getSuggestedToContext();
          users.add(suggestedToContext);
          tempSuggestions.setSuggestedToContext(users);
          tempSuggestions.setUserCount(tempSuggestions.getUserCount() + 1);
          groupSuggestionsByCaIdScId.put(caIdSuggContentIdCombination, tempSuggestions);
        } else {
          List<SuggestedToContext> suggestedToContextArray = new ArrayList<>();
          suggestedToContextArray.add(suggestedToContext);
          SuggestedContents suggestions =
              generateSuggestedContentsModelResponse(suggestion, suggestedToContextArray);
          groupSuggestionsByCaIdScId.put(caIdSuggContentIdCombination, suggestions);
        }
      });
    }
    return groupSuggestionsByCaIdScId;
  }

  private static SuggestedContents generateSuggestedContentsModelResponse(
      SuggestionTrackerModel suggestion, List<SuggestedToContext> suggestedToContext) {
    SuggestedContents suggestedContent = new SuggestedContents();
    suggestedContent.setClassId(suggestion.getClassId());
    suggestedContent.setCollectionId(suggestion.getCollectionId());
    suggestedContent.setSuggestedContentType(suggestion.getSuggestedContentType());
    suggestedContent.setSuggestedContentId(suggestion.getSuggestedContentId());
    suggestedContent.setSuggestedToContext(suggestedToContext);
    suggestedContent.setUserCount(suggestedToContext.size());
    return suggestedContent;
  }

  private static SuggestedToContext generateSuggestedToContextModelResponse(
      SuggestionTrackerModel suggestion) {
    SuggestedToContext suggestedToContext = new SuggestedToContext();
    suggestedToContext.setId(suggestion.getId());
    suggestedToContext.setUserId(suggestion.getUserId());
    suggestedToContext.setSuggestedTo(suggestion.getSuggestedTo());
    suggestedToContext.setSuggestionArea(suggestion.getSuggestionArea());
    suggestedToContext.setSuggestionCriteria(suggestion.getSuggestionCriteria());
    suggestedToContext.setSuggestionOrigin(suggestion.getSuggestionOrigin());
    suggestedToContext.setSuggestionOriginatorId(suggestion.getSuggestionOriginatorId());
    suggestedToContext.setCreatedAt(suggestion.getCreatedAt());
    suggestedToContext.setUpdatedAt(suggestion.getUpdatedAt());
    return suggestedToContext;
  }

  private static Suggestions generateSuggestionsModelResponse(Long caId, int total,
      List<SuggestedContents> suggestions) {
    Suggestions sugggestions = new Suggestions();
    sugggestions.setCaId(caId);
    sugggestions.setTotal(total);
    sugggestions.setSuggestedContents(suggestions);
    return sugggestions;
  }
}

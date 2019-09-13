package org.gooru.suggestions.processor.listsuggestions;

import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.constants.HttpConstants.HttpStatus;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.gooru.suggestions.processor.data.TxCodeType;
import org.gooru.suggestions.processor.utilities.ConverterUtils;
import io.vertx.core.json.JsonObject;

/**
 * @author renuka
 */
class ListUserSuggestionsForTxCodeCommand {

  private UUID userId;
  private UUID classId;
  private String txCode;
  private String txCodeType;
  private PaginationInfo paginationInfo;

  public UUID getUserId() {
    return userId;
  }

  public String getTxCode() {
    return txCode;
  }

  public String getTxCodeType() {
    return txCodeType;
  }

  public UUID getClassId() {
    return classId;
  }

  public PaginationInfo getPaginationInfo() {
    return paginationInfo;
  }

  private static ListUserSuggestionsForTxCodeCommand buildFromJsonObject(JsonObject input) {
    ListUserSuggestionsForTxCodeCommand command = new ListUserSuggestionsForTxCodeCommand();
    try {
      command.userId = ConverterUtils.convertToUuid(input, CommandAttributes.USER_ID);
      command.txCode = input.getString(CommandAttributes.TX_CODE);          
      TxCodeType txCodeType = ConverterUtils.toTxCodeType(input.getString(CommandAttributes.TX_CODE_TYPE));
      command.txCodeType = txCodeType != null ? txCodeType.getName() : null;
      command.classId = ConverterUtils.convertToUuid(input, CommandAttributes.CLASS_ID);
      command.paginationInfo = PaginationInfo.buildFromRequest(input);
    } catch (IllegalArgumentException e) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, e.getMessage());
    }
    return command;
  }

  public static ListUserSuggestionsForTxCodeCommand builder(JsonObject input) {
    ListUserSuggestionsForTxCodeCommand result =
        ListUserSuggestionsForTxCodeCommand.buildFromJsonObject(input);
    result.validate();
    return result;
  }

  public UserSuggestionsForCompetencyBean asBean() {
    UserSuggestionsForCompetencyBean bean = new UserSuggestionsForCompetencyBean();
    bean.setTxCode(txCode);
    bean.setUserId(userId);
    bean.setClassId(classId);
    bean.setTxCodeType(txCodeType);
    return bean;
  }

  private void validate() {
    if (txCode == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid txCode");
    } else if (userId == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid userId");
    } else if (txCodeType == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid txCodeType");
    }
  }

  static class CommandAttributes {
    private static final String USER_ID = "userId";
    private static final String TX_CODE = "txCode";
    private static final String CLASS_ID = "classId";
    private static final String TX_CODE_TYPE = "txCodeType";
  }

  public static final class UserSuggestionsForCompetencyBean {

    private UUID userId;
    private String txCode;
    private UUID classId;
    private String txCodeType;

    public UUID getUserId() {
      return userId;
    }

    public void setUserId(UUID userId) {
      this.userId = userId;
    }

    public String getTxCode() {
      return txCode;
    }

    public void setTxCode(String txCode) {
      this.txCode = txCode;
    }

    public UUID getClassId() {
      return classId;
    }

    public void setClassId(UUID classId) {
      this.classId = classId;
    }

    public String getTxCodeType() {
      return txCodeType;
    }

    public void setTxCodeType(String txCodeType) {
      this.txCodeType = txCodeType;
    }

  }
}

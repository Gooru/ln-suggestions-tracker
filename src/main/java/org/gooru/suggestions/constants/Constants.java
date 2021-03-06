package org.gooru.suggestions.constants;

/**
 * @author ashish
 */
public final class Constants {

  public static final class EventBus {

    public static final String MBEP_AUTH = "org.gooru.suggestions.eventbus.auth";
    public static final String MBEP_SUGGEST_TRACKER = "org.gooru.suggestions.tracker";
    public static final String MBEP_POST_PROCESS = "org.gooru.suggestions.tracker.eventbus.post.process";

    public static final String MBUS_TIMEOUT = "event.bus.send.timeout.seconds";

    private EventBus() {
      throw new AssertionError();
    }
  }

  public static final class Message {

    public static final String MSG_OP = "mb.op";
    public static final String MSG_OP_AUTH = "auth";
    public static final String MSG_OP_STATUS = "mb.op.status";
    public static final String MSG_OP_STATUS_SUCCESS = "mb.op.status.success";
    public static final String MSG_OP_STATUS_FAIL = "mb.op.status.fail";
    public static final String MSG_OP_SUGGESTIONS_ADD = "mb.op.suggestion.track";
    public static final String MSG_OP_LIST_USER_SUGGESTIONS_IN_CLASS = "mb.op.user.suggestions.class.list";
    public static final String MSG_OP_LIST_USER_SUGGESTIONS_FOR_COURSE = "mb.op.user.suggestions.course.list";
    public static final String MSG_OP_LIST_USER_SUGGESTIONS_FOR_TX_CODE = "mb.op.user.suggestions.txcode.list";
    public static final String MSG_OP_LIST_USER_SUGGESTIONS_IN_CA = "mb.op.user.suggestions.ca.list";
    public static final String MSG_OP_POSTPROCESS_SUGGESTION_ADD = "mb.op.postprocess.suggestions.add";

    public static final String MSG_API_VERSION = "api.version";
    public static final String MSG_SESSION_TOKEN = "session.token";
    public static final String MSG_KEY_SESSION = "session";
    public static final String MSG_USER_ANONYMOUS = "anonymous";
    public static final String MSG_USER_ID = "user_id";
    public static final String MSG_HTTP_STATUS = "http.status";
    public static final String MSG_HTTP_BODY = "http.body";
    public static final String MSG_HTTP_HEADERS = "http.headers";

    public static final String MSG_MESSAGE = "message";
    public static final String PROCESSING_AUTH_TIME = "auth.processing.time";
    public static final String PROCESSING_HANDLER_START_TIME = "handler.start.time";
    public static final String MSG_TEACHER_ID = "teacher_id";
    public static final String MSG_ID = "id";

    private Message() {
      throw new AssertionError();
    }
  }

  public static final class Response {

    public static final String RESP_CONTENT = "content";
    public static final String RESP_SUGGESTIONS = "suggestions";
    public static final String RESP_CONTEXT = "context";
    public static final String RESP_ID = "id";
    public static final String RESP_TITLE = "title";
    public static final String RESP_THUMBNAIL = "thumbnail";
    public static final String RESP_TAXONOMY = "taxonomy";
    public static final String RESP_URL = "url";

    private Response() {
      throw new AssertionError();
    }
  }

  public static final class Params {

    public static final String PARAM_COURSE_ID = "course_id";
    public static final String PARAM_CLASS_ID = "class_id";

    private Params() {
      throw new AssertionError();
    }
  }

  public static final class Route {

    public static final String API_AUTH_ROUTE = "/api/stracker/*";
    private static final String API_BASE_ROUTE = "/api/stracker/:version/";
    public static final String API_SUGGESTIONS_ADD = API_BASE_ROUTE + "track";

    public static final String API_USER_SUGGESTIONS_IN_CLASS =
        API_BASE_ROUTE + "user/:userId/class/:classId";
    public static final String API_USER_SUGGESTIONS_FOR_COURSE =
        API_BASE_ROUTE + "user/:userId/course/:courseId";
    public static final String API_USER_SUGGESTIONS_FOR_TX_CODE =
        API_BASE_ROUTE + "user/:userId/code/:txCode/codetype/:txCodeType";
    public static final String API_USER_SUGGESTIONS_IN_CA =
        API_BASE_ROUTE + "ca/class/:classId";
    private Route() {
      throw new AssertionError();
    }
  }

  private Constants() {
    throw new AssertionError();
  }
}

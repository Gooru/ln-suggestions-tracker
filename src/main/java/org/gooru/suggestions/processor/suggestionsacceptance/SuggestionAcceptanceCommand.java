package org.gooru.suggestions.processor.suggestionsacceptance;

import java.util.UUID;

import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;

import io.vertx.core.json.JsonObject;

/**
 * @author ashish on 17/11/17.
 */
class SuggestionAcceptanceCommand {
    private UUID ctxUserId;
    private UUID ctxClassId;
    private UUID ctxCourseId;
    private UUID ctxUnitId;
    private UUID ctxLessonId;
    private UUID ctxCollectionId;
    private Long pathId;
    private UUID suggestedContentId;

    static SuggestionAcceptanceCommand builder(JsonObject input) {
        SuggestionAcceptanceCommand result = buildFromJsonObject(input);
        result.validate();
        return result;
    }

    public UUID getCtxUserId() {
        return ctxUserId;
    }

    public UUID getCtxClassId() {
        return ctxClassId;
    }

    public UUID getCtxCourseId() {
        return ctxCourseId;
    }

    public UUID getCtxUnitId() {
        return ctxUnitId;
    }

    public UUID getCtxLessonId() {
        return ctxLessonId;
    }

    public UUID getCtxCollectionId() {
        return ctxCollectionId;
    }

    public Long getPathId() {
        return pathId;
    }

    public UUID getSuggestedContentId() {
        return suggestedContentId;
    }

    private void validate() {
        if (ctxUserId == null) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, "Invalid user id");
        } else if (ctxCourseId == null) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, "Course should be provided");
        } else if ((ctxUnitId == null || ctxLessonId == null)) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Invalid unit or lesson for suggestion");
        } else if (suggestedContentId == null) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Invalid content id for suggestion");
        } else if (pathId == null) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Invalid path id for suggestion");
        }
    }

    private static SuggestionAcceptanceCommand buildFromJsonObject(JsonObject input) {
        SuggestionAcceptanceCommand command = new SuggestionAcceptanceCommand();

        try {
            command.ctxUserId = toUuid(input, CommandAttributes.USER_ID);
            command.ctxClassId = toUuid(input, CommandAttributes.CLASS_ID);
            command.ctxCourseId = toUuid(input, CommandAttributes.COURSE_ID);
            command.ctxUnitId = toUuid(input, CommandAttributes.UNIT_ID);
            command.ctxLessonId = toUuid(input, CommandAttributes.LESSON_ID);
            command.ctxCollectionId = toUuid(input, CommandAttributes.COLLECTION_ID);
            command.suggestedContentId = toUuid(input, CommandAttributes.SUGGESTED_CONTENT_ID);
            command.pathId = input.getLong(CommandAttributes.PATH_ID);
        } catch (IllegalArgumentException e) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return command;
    }

    public SuggestionAcceptanceBean getBean() {
        SuggestionAcceptanceBean result = new SuggestionAcceptanceBean();
        result.setCtxUserId(ctxUserId);
        result.setCtxClassId(ctxClassId);
        result.setCtxCourseId(ctxCourseId);
        result.setCtxUnitId(ctxUnitId);
        result.setCtxLessonId(ctxLessonId);
        result.setCtxCollectionId(ctxCollectionId);
        result.setSuggestedContentId(suggestedContentId);
        result.setPathId(pathId);
        return result;
    }

    private static UUID toUuid(JsonObject input, String key) {
        String value = input.getString(key);
        if (value == null || value.isEmpty()) {
            return null;
        }
        return UUID.fromString(value);
    }

    public static final class CommandAttributes {

        public static final String USER_ID = "ctx_user_id";
        public static final String CLASS_ID = "ctx_class_id";
        public static final String COURSE_ID = "ctx_course_id";
        public static final String LESSON_ID = "ctx_lesson_id";
        public static final String UNIT_ID = "ctx_unit_id";
        public static final String COLLECTION_ID = "ctx_collection_id";
        public static final String SUGGESTED_CONTENT_ID = "suggested_content_id";
        public static final String PATH_ID = "path_id";

        private CommandAttributes() {
            throw new AssertionError();
        }
    }

    public static final class SuggestionAcceptanceBean {
        private UUID ctxUserId;
        private UUID ctxClassId;
        private UUID ctxCourseId;
        private UUID ctxUnitId;
        private UUID ctxLessonId;
        private UUID ctxCollectionId;
        private Long pathId;
        private UUID suggestedContentId;

        public UUID getCtxUserId() {
            return ctxUserId;
        }

        public void setCtxUserId(UUID ctxUserId) {
            this.ctxUserId = ctxUserId;
        }

        public UUID getCtxClassId() {
            return ctxClassId;
        }

        public void setCtxClassId(UUID ctxClassId) {
            this.ctxClassId = ctxClassId;
        }

        public UUID getCtxCourseId() {
            return ctxCourseId;
        }

        public void setCtxCourseId(UUID ctxCourseId) {
            this.ctxCourseId = ctxCourseId;
        }

        public UUID getCtxUnitId() {
            return ctxUnitId;
        }

        public void setCtxUnitId(UUID ctxUnitId) {
            this.ctxUnitId = ctxUnitId;
        }

        public UUID getCtxLessonId() {
            return ctxLessonId;
        }

        public void setCtxLessonId(UUID ctxLessonId) {
            this.ctxLessonId = ctxLessonId;
        }

        public UUID getCtxCollectionId() {
            return ctxCollectionId;
        }

        public void setCtxCollectionId(UUID ctxCollectionId) {
            this.ctxCollectionId = ctxCollectionId;
        }

        public Long getPathId() {
            return pathId;
        }

        public void setPathId(Long pathId) {
            this.pathId = pathId;
        }

        public UUID getSuggestedContentId() {
            return suggestedContentId;
        }

        public void setSuggestedContentId(UUID suggestedContentId) {
            this.suggestedContentId = suggestedContentId;
        }
    }
}

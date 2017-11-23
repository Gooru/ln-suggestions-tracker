package org.gooru.suggestions.processor.teachersuggestions;

import java.util.UUID;

import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.gooru.suggestions.processor.data.SuggestedContentSubType;
import org.gooru.suggestions.processor.data.SuggestedContentType;

import io.vertx.core.json.JsonObject;

/**
 * @author ashish on 17/11/17.
 */
public class AddTeacherSuggestionsCommand {
    private UUID ctxUserId;
    private UUID ctxClassId;
    private UUID ctxCourseId;
    private UUID ctxUnitId;
    private UUID ctxLessonId;
    private UUID ctxCollectionId;
    private Long pathId;
    private UUID suggestedContentId;
    private SuggestedContentType suggestedContentType;
    private SuggestedContentSubType suggestedContentSubType;
    private UUID targetCourseId;
    private UUID targetUnitId;
    private UUID targetLessonId;
    private UUID targetCollectionId;

    public static AddTeacherSuggestionsCommand builder(JsonObject input) {
        AddTeacherSuggestionsCommand result = buildFromJsonObject(input);
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

    public SuggestedContentType getSuggestedContentType() {
        return suggestedContentType;
    }

    public SuggestedContentSubType getSuggestedContentSubType() {
        return suggestedContentSubType;
    }

    public UUID getTargetCourseId() {
        return targetCourseId;
    }

    public UUID getTargetUnitId() {
        return targetUnitId;
    }

    public UUID getTargetLessonId() {
        return targetLessonId;
    }

    public UUID getTargetCollectionId() {
        return targetCollectionId;
    }

    private void validate() {
        if (ctxUserId == null) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, "Invalid user id");
        } else if (ctxClassId == null || ctxCourseId == null) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Course and class both should be provided");
        } else if ((ctxUnitId == null || ctxLessonId == null)) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Invalid unit or lesson for suggestion");
        } else if (suggestedContentId == null) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Invalid content id for suggestion");
        } else if (suggestedContentType == null) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
                "Invalid suggested content type");
        }
    }

    private static AddTeacherSuggestionsCommand buildFromJsonObject(JsonObject input) {
        AddTeacherSuggestionsCommand command = new AddTeacherSuggestionsCommand();

        try {
            command.ctxUserId =
                toUuid(input, CommandAttributes.USER_ID);
            command.ctxClassId =
                toUuid(input, CommandAttributes.CLASS_ID);
            command.ctxCourseId =
                toUuid(input, CommandAttributes.COURSE_ID);
            command.ctxUnitId =
                toUuid(input, CommandAttributes.UNIT_ID);
            command.ctxLessonId =
                toUuid(input, CommandAttributes.LESSON_ID);
            command.ctxCollectionId =
                toUuid(input, CommandAttributes.COLLECTION_ID);
            command.suggestedContentId =
                toUuid(input, CommandAttributes.SUGGESTED_CONTENT_ID);
            command.pathId = input.getLong(CommandAttributes.PATH_ID);
            command.targetCourseId =
                toUuid(input, CommandAttributes.TARGET_COURSE_ID);
            command.targetUnitId =
                toUuid(input, CommandAttributes.TARGET_UNIT_ID);
            command.targetLessonId =
                toUuid(input, CommandAttributes.TARGET_LESSON_ID);
            command.targetCollectionId =
                toUuid(input, CommandAttributes.TARGET_COLLECTION_ID);
            String value = input.getString(CommandAttributes.SUGGESTED_CONTENT_TYPE);
            command.suggestedContentType =
                (value != null && !value.isEmpty()) ? SuggestedContentType.builder(value) : null;
            value = input.getString(CommandAttributes.SUGGESTED_CONTENT_SUBTYPE);
            command.suggestedContentSubType =
                (value != null && !value.isEmpty()) ? SuggestedContentSubType.builder(value) : null;
        } catch (IllegalArgumentException e) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return command;
    }

    private static UUID toUuid(JsonObject input, String key) {
        String value = input.getString(key);
        if (value == null || value.isEmpty()) {
            return null;
        }
        return UUID.fromString(value);
    }

    public static final class CommandAttributes {

        public static final String USER_ID = "ctx_class_id";
        public static final String CLASS_ID = "ctx_class_id";
        public static final String COURSE_ID = "ctx_course_id";
        public static final String LESSON_ID = "ctx_lesson_id";
        public static final String UNIT_ID = "ctx_unit_id";
        public static final String COLLECTION_ID = "ctx_collection_id";
        public static final String SUGGESTED_CONTENT_ID = "suggested_content_id";
        public static final String SUGGESTED_CONTENT_TYPE = "suggested_content_type";
        public static final String SUGGESTED_CONTENT_SUBTYPE = "suggested_content_subtype";
        public static final String PATH_ID = "path_id";
        public static final String TARGET_COURSE_ID = "target_course_id";
        public static final String TARGET_UNIT_ID = "target_unit_id";
        public static final String TARGET_LESSON_ID = "target_lesson_id";
        public static final String TARGET_COLLECTION_ID = "target_collection_id";

        private CommandAttributes() {
            throw new AssertionError();
        }
    }
}

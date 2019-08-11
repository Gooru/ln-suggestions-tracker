package org.gooru.suggestions.processor.data;

import java.util.HashMap;
import java.util.Map;

public enum SuggestedTo {
    Student("student"),
    Teacher("teacher");

    private final String name;

    SuggestedTo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private static final Map<String, SuggestedTo> LOOKUP = new HashMap<>(values().length);

    static {
        for (SuggestedTo suggestedTo : values()) {
            LOOKUP.put(suggestedTo.name, suggestedTo);
        }
    }

    public static SuggestedTo builder(String type) {
        SuggestedTo result = LOOKUP.get(type);
        if (result == null) {
            throw new IllegalArgumentException("Invalid suggested to type");
        }
        return result;
    }
}

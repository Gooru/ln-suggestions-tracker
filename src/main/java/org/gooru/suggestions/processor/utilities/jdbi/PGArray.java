package org.gooru.suggestions.processor.utilities.jdbi;

import java.util.Collection;

/**
 * @author ashish
 */
public class PGArray<T> {

  private final Object[] elements;
  private final Class<T> type;

  public PGArray(Class<T> type, Collection<T> elements) {
    this.elements = toArray(elements);
    this.type = type;
  }

  private Object[] toArray(Collection<T> elements) {
    return elements.toArray();
  }

  public static <T> PGArray<T> arrayOf(Class<T> type, Collection<T> elements) {
    return new PGArray<>(type, elements);
  }

  public Object[] getElements() {
    return elements;
  }

  public Class<T> getType() {
    return type;
  }
}


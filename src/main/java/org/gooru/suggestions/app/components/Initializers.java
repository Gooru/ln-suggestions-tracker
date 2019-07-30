package org.gooru.suggestions.app.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ashish on 3/11/17.
 */
public class Initializers implements Iterable<Initializer> {

  private final Iterator<Initializer> internalIterator;

  public Initializers() {
    List<Initializer> initializers = new ArrayList<>();
    initializers.add(DataSourceRegistry.getInstance());
    initializers.add(UtilityManager.getInstance());
    initializers.add(AppConfiguration.getInstance());
    internalIterator = initializers.iterator();
  }

  @Override
  public Iterator<Initializer> iterator() {
    return new Iterator<Initializer>() {

      @Override
      public boolean hasNext() {
        return internalIterator.hasNext();
      }

      @Override
      public Initializer next() {
        return internalIterator.next();
      }

    };
  }

}

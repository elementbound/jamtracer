package com.github.elementbound.jamtracer.event;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Simple event source implementations.
 * <p>The event source can be subscribed to, and on emit, all subscribers will be called with the
 * event data.</p>
 *
 * @param <T> event type
 */
public class EventSource<T> {
  private final List<Consumer<T>> listeners = new LinkedList<>();

  /**
   * Subscribe to event source.
   *
   * @param listener listener
   */
  public void subscribe(Consumer<T> listener) {
    listeners.add(listener);
  }

  /**
   * Unsubscribe from event source.
   *
   * @param listener listener to unsubscribe
   */
  public void unsubscribe(Consumer<T> listener) {
    listeners.remove(listener);
  }

  /**
   * Emit event.
   *
   * @param event event
   */
  public void emit(T event) {
    listeners.forEach(listener -> listener.accept(event));
  }
}

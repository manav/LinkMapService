package com.demo.tweets.client;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * A widget that implements this interface is a public source of
 * {@link CloseEvent} events.
 * 
 * @param <T> the type being closed
 */
public interface HasLogInEventHandlers<T> extends HasHandlers {
  /**
   * Adds a {@link CloseEvent} handler.
   * 
   * @param handler the handler
   * @return the registration for the event
   */
  HandlerRegistration addLogInEventHandler(LogInEventHandler<T> handler);
}

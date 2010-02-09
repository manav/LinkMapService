package com.demo.tweets.client;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link ValueChangeEvent} events.
 * 
 * @param <I> the value about to be changed
 */
public interface LogInEventHandler<I> extends EventHandler {

  /**
   * Called when {@link ValueChangeEvent} is fired.
   * 
   * @param event the {@link ValueChangeEvent} that was fired
   */
  void onLogInSuccess(LogInEvent<I> event);
  
}

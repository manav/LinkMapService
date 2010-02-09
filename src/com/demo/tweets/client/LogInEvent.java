package com.demo.tweets.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a value change event.
 * 
 * @param <I> the value about to be changed
 */
public class LogInEvent<I> extends GwtEvent<LogInEventHandler<I>> {

  /**
   * Handler type.
   */
  private static Type<LogInEventHandler<?>> TYPE;

  /**
   * Fires a value change event on all registered handlers in the handler
   * manager.If no such handlers exist, this method will do nothing.
   * 
   * @param <I> the old value type
   * @param source the source of the handlers
   * @param value the value
   */
  public static <I> void fire(HasLogInEventHandlers<I> source, I value) {
    if (TYPE != null) {
      LogInEvent<I> event = new LogInEvent<I>(value);
      source.fireEvent(event);
    }
  }


  /**
   * Gets the type associated with this event.
   * 
   * @return returns the handler type
   */
  public static Type<LogInEventHandler<?>> getType() {
    if (TYPE == null) {
      TYPE = new Type<LogInEventHandler<?>>();
    }
    return TYPE;
  }


  private final I value;

  /**
   * Creates a value change event.
   * 
   * @param value the value
   */
  protected LogInEvent(I value) {
    this.value = value;
  }

  // The instance knows its BeforeSelectionHandler is of type I, but the TYPE
  // field itself does not, so we have to do an unsafe cast here.
  @SuppressWarnings("unchecked")
  @Override
  public final Type<LogInEventHandler<I>> getAssociatedType() {
    return (Type) TYPE;
  }

  /**
   * Gets the value.
   * 
   * @return the value
   */
  public I getValue() {
    return value;
  }
 
  @Override
  public String toDebugString() {
    return super.toDebugString() + getValue();
  }

  @Override
  protected void dispatch(LogInEventHandler<I> handler) {
    handler.onLogInSuccess(this);
  }
}

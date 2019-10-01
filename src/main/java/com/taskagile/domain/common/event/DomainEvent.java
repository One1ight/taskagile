package com.taskagile.domain.common.event;

import org.springframework.context.ApplicationEvent;

public abstract class DomainEvent extends ApplicationEvent {

  private static final long serialVersionUID = -3406378569334848949L;

  public DomainEvent(Object source) {
    super(source);
  }

  /**
   *Get the timestamp this event occurred
   */
  public long occurredAt() {
    return getTimestamp();
  }
}

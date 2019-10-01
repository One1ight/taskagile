package com.taskagile.domain.common.model;

import java.io.Serializable;

public abstract class AbstractBaseEntity implements Serializable {

  private static final long serialVersionUID = 6270359250783209767L;

  public abstract boolean equals(Object obj);

  public abstract int hashCode();

  public abstract String toString();
}

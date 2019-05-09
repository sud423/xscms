package com.susd.domain;


public interface DomainEvent<T> {

	boolean sameEventAs(T other);
}

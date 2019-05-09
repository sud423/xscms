package com.susd.domain;


/**
 * 实体
 * @author sushu
 *
 * @param <T> 
 */
public interface Entity<T> {
	
	/**
	 * 通过身份进行两实体比较，而不是通过属性
	 * @param other 待比较的实体
	 * @return 无论其它属性如果，如果身份相同则返回true
	 */
	boolean sameIdentityAs(T other);
}

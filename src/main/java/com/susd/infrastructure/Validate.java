package com.susd.infrastructure;

import java.util.Set;

import javax.validation.*;

public class Validate {
	private static Validator validator = Validation.buildDefaultValidatorFactory()  
            .getValidator();  

	public static <T> OptResult verify(T t) {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
		if (constraintViolations==null ||constraintViolations.isEmpty())
			return OptResult.Successed();
		
		return OptResult.Failed(constraintViolations.iterator().next().getMessage());
		
//		List<String> messageList = new ArrayList<>();
//		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
//			messageList.add(constraintViolation.getMessage());
//		}
//		return messageList;
	}

	/**
	 * 判断实体是否通过验证，true表示通过，否则为false
	 * 
	 * @param t 待验证实例
	 * @return 成功：true
	 */
	public static <T> boolean isValid(T t) {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
		return constraintViolations==null || constraintViolations.isEmpty();
	}
}

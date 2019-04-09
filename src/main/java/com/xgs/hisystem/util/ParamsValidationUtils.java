package com.xgs.hisystem.util;


import com.xgs.hisystem.pojo.bo.ValidationResultBO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 
 * @Description: 参数校验类
 * @PackageName com.hxgy.push.utils
 * @ClassName: ParamsValidationUtils 
 * @Author MDH
 * @Date 2019年1月16日 上午11:49:06
 */
public class ParamsValidationUtils {
	
	private ParamsValidationUtils() {}
	
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	
	public static <T> ValidationResultBO validateEntity(T obj) {
		ValidationResultBO result = new ValidationResultBO();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        // if( CollectionUtils.isNotEmpty(set) ){
        if (set != null && set.size() != 0) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());

                if (errorMsg.size() > 0) {
                    break;
                }
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }
 
    public static <T> ValidationResultBO validateProperty(T obj, String propertyName) {
    	ValidationResultBO result = new ValidationResultBO();
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
        if (set != null && set.size() != 0) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(propertyName, cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }
}

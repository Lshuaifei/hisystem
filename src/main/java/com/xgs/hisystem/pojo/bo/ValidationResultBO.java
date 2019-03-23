package com.xgs.hisystem.pojo.bo;

import java.util.Map;

/**
 * 
 * @Description: 验证结果返回类
 * @PackageName com.hxgy.push.umeng.pojo.bo
 * @ClassName: ValidationResultBO 
 * @Author MDH
 * @Date 2019年1月16日 上午11:51:05
 */
public class ValidationResultBO {
	
	
	// 校验结果是否有错
    private boolean             hasErrors;
 
    // 校验错误信息
    private Map<String, String> errorMsg;

	/**
	 * @return the hasErrors
	 */
	public boolean isHasErrors() {
		return hasErrors;
	}

	/**
	 * @param hasErrors the hasErrors to set
	 */
	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	/**
	 * @return the errorMsg
	 */
	public Map<String, String> getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(Map<String, String> errorMsg) {
		this.errorMsg = errorMsg;
	}
    
    
    
}

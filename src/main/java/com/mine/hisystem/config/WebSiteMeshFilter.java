package com.mine.hisystem.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * 配置SiteMesh拦截器FIlter，指定装饰页面和不需要装饰的路径
 *
 *
 */
public class WebSiteMeshFilter extends ConfigurableSiteMeshFilter{  
  
    @Override  
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {  
        builder.addDecoratorPath("/templates/*", "/templates/default.html")  // 配置装饰页面
               .addExcludedPath("/static/*") 	        // 静态资源不装饰
               .addExcludedPath("/templates/login");  	// 登录页面不装饰
    }  

}

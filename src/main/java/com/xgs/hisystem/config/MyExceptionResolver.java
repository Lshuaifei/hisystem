//package com.mine.hisystem.config;
//
///**
// * @author xgs
// * @Description:
// * @date 2019/2/28
// */
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.shiro.authz.UnauthorizedException;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.web.servlet.ModelAndView;
//
//public class MyExceptionResolver implements HandlerExceptionResolver{
//
//    public ModelAndView resolveException(HttpServletRequest request,
//                                         HttpServletResponse response, Object handler, Exception ex) {
//
//        System.out.println("==============异常开始=============");
//        if(ex instanceof UnauthorizedException){
//            ModelAndView mv = new ModelAndView("error");
//            return mv;
//        }
//        ex.printStackTrace();
//        ModelAndView mv = new ModelAndView("error");
//        return mv;
//    }
//
//
//
//}
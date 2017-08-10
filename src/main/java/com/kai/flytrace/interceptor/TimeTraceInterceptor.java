package com.kai.flytrace.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Created on 2017/7/31.
 */
public class TimeTraceInterceptor extends HandlerInterceptorAdapter {

    private String[] packages;

    public void setPackages(String[] packages) {
        this.packages = packages;
    }

    public boolean preHandle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o) throws Exception {

        checkAndDone((HandlerMethod)o);
        return true;
    }

    public void postHandle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        checkAndDone((HandlerMethod)o);
    }

    private void checkAndDone(HandlerMethod method){
        try {
            if(packages!=null&&packages.length>0){
                HandlerMethod handlerMethod = (HandlerMethod) method;
                Class clazz= handlerMethod.getBeanType();
                String clazzName=clazz.getName();
                for(String str:packages){
                    if(str.equals(clazzName)){
                        long methodTime=System.currentTimeMillis();
                        System.out.println(clazzName+" time :"+methodTime);
                    }
                }
            }
        }catch (Exception e){

        }
    }

    public void afterCompletion(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

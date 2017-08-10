package com.kai.flytrace.controller;

import com.kai.flytrace.interceptor.TimeTraceInterceptor;
import com.kai.flytrace.trace.TraceServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created on 2017/7/28.
 */
@RequestMapping
public class TraceControllerBean implements InitializingBean {

    private String tracePackages = null;

    private Properties properties;

    @RequestMapping(value = "/start/{starttoken}")
    @ResponseBody
    public String start(@PathVariable String starttoken, final String packages) throws Exception {
        if(!isValidPath("start",starttoken)){
            return "error";
        }

        return "start success,trace package :" + packages;
    }

    @RequestMapping(value = "/stop/{stoptoken}")
    @ResponseBody
    public String stop(@PathVariable String stoptoken) {
        if(!isValidPath("stop",stoptoken)){
            return "error";
        }
        return "this is stop method";
    }

    @RequestMapping(value = "/show/{showtoken}")
    @ResponseBody
    public String show(@PathVariable String showtoken) {
        if(!isValidPath("show",showtoken)){
            return "error";
        }
        return "this is show method";
    }

    public void afterPropertiesSet() throws Exception {
        String propFileName = "flytrace.properties";
        String defaultPropFileName = "com/kai/flytrace/flytrace.properties";
        Properties props = new Properties();
        InputStream inputStream = null;

        try {
            String propSrc = "default resource file in Quartz package: 'quartz.properties'";

            ClassLoader cl = getClass().getClassLoader();
            if (cl == null) {
                cl = Thread.currentThread().getContextClassLoader();
            }
            if (cl == null) {
                throw new Exception("unable to find a class loader on this current thread or class ");
            }

            inputStream = cl.getResourceAsStream(propFileName);

            if (inputStream == null) {
                inputStream = cl.getResourceAsStream("/" + propFileName);
            }
            if (inputStream == null) {
                inputStream = cl.getResourceAsStream(defaultPropFileName);
            }
            if (inputStream == null) {
                throw new Exception("Default flytrace.properties not found in class path");
            }

            props.load(inputStream);
            properties=props;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
        }
    }

    public Boolean isValidPath(String methodName,String token){
        if("start".equals(methodName)){
            return token.equals(properties.getProperty("com.kai.flytrace.controller.starToken"));
        }
        if ("stop".equals(methodName)){
            return token.equals(properties.getProperty("com.kai.flytrace.controller.stopToken"));
        }
        if("show".equals(methodName)){
            return token.equals(properties.getProperty("com.kai.flytrace.controller.showToken"));
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        TraceControllerBean traceControllerBean=new TraceControllerBean();
        traceControllerBean.afterPropertiesSet();
        traceControllerBean.isValidPath("start","starttrace");

        TraceServer traceServer=new TraceServer();
        traceServer.getStackMethod();

        //Runtime.getRuntime().traceMethodCalls(true);
    }

}

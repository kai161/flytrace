package com.kai.flytrace.executor;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.lang.management.ManagementFactory;

/**
 * Created on 2017/8/8.
 */
public class LoadAgent {

    public void attchAgent(){
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
        String name= ManagementFactory.getRuntimeMXBean().getName();
        String pid=name.split("@")[0];

        try {
            Class<?> vmdclazz=classLoader.loadClass("com.sun.tools.attach.VirtualMachineDescriptor");
            Class<?> vmclazz=classLoader.loadClass("com.sun.tools.attach.VirtualMachine");







        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

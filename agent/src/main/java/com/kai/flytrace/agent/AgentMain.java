package com.kai.flytrace.agent;

import java.lang.instrument.Instrumentation;

/**
 * Created on 2017/8/8.
 */
public class AgentMain {
    public static void agentmain(String agrs, Instrumentation instrumentation){
        System.out.println("string agrs"+agrs);
    }
}

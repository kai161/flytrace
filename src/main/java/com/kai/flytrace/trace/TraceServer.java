package com.kai.flytrace.trace;

/**
 * Created on 2017/7/31.
 */
public class TraceServer {

    public String getStackMethod(){
        String methodName=Throwable.class.getName();

        //Throwable throwable=new Throwable("throw");
        StackTraceElement[] stackTraceElements= Thread.currentThread().getStackTrace();;
        StringBuilder sb=new StringBuilder();
        for (int i=1;i<=10;i++){
            StackTraceElement ste=stackTraceElements[i];
            sb.append(ste.getLineNumber()+" "+ste.getClassName()+" "+ste.getMethodName()).append("\n");
        }

        System.out.println(sb.toString());


        return methodName;
    }
}

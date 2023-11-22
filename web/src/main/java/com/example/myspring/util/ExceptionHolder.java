package com.example.myspring.util;


public class ExceptionHolder implements AutoCloseable {

    private static final ThreadLocal<String> holder = new ThreadLocal();

    public static void setExceptionInfo(Exception e){
        holder.set(e.getMessage());
    }

    public static String getExceptionInfo(){
        return holder.get();
    }

    @Override
    public void close() throws Exception {
        System.out.println("--AutoCloseable--");
        holder.remove();
    }


}

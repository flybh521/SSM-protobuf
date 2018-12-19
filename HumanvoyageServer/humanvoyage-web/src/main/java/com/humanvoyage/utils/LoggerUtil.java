package com.humanvoyage.utils;



import org.apache.log4j.Logger;


public class LoggerUtil {


    

    public enum LogType {
        REQUEST, //客户端请求
        RESPONSE,//服务器响应
 

    }

    public enum GoldGetType {
        BUY;
    }

    public enum GoldUseType {

    }

    public enum GoodsGetType {
        REWARD, BUY;
    }
    private static Logger logger = Logger.getLogger(LoggerUtil.class);


    private static class LazyHolder {
        private static final LoggerUtil INSTANCE = new LoggerUtil();
    }

    public static LoggerUtil getInstance() {
        return LazyHolder.INSTANCE;
    }
}

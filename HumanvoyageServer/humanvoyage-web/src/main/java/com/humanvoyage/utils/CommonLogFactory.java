package com.humanvoyage.utils;



import org.apache.log4j.Logger;

import com.googlecode.protobuf.format.JsonFormat;
import com.humanvoyage.constant.Constants;
import com.humanvoyage.pb.BasePb;

/**
 * 创建日志
 * Created by wangxinqiang on 2017/8/4.
 */
public class CommonLogFactory {


    /**
     * 创建log对象
     *
     * @param clazz
     * @return
     */
    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(String.format("%s", clazz.getName()));
    }

    /**
     * 格式化日志
     *
     * @param type   日志类型
     * @param uid    玩家id
     * @param params 日志参数
     * @return
     */
    public static String formatLog(LoggerUtil.LogType type, long uid, Object... params) {
        StringBuilder log = new StringBuilder();
        log.append(Constants.LOG_SEPARATOR);
        log.append(type.toString());
        log.append(Constants.LOG_SEPARATOR);
        log.append(uid);
        
        
        if (params != null) {
            for (Object obj : params) {
                log.append(Constants.LOG_SEPARATOR);
                if (obj instanceof BasePb.Base) {
                    obj = new JsonFormat().printToString((BasePb.Base) obj);
                }
                log.append(obj);
            }
        }
        return log.toString();
    }  
    
   
}

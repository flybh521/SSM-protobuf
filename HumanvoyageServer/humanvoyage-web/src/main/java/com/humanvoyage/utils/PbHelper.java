/**   
 * @Title: PbHelper.java    
 * @Package com.account.util    
 * @Description: TODO  
 * @author ZhangJun   
 * @date 2015年11月6日 下午6:43:38    
 * @version V1.0   
 */
package com.humanvoyage.utils;

import java.util.List;
import com.google.protobuf.GeneratedMessage;
import com.humanvoyage.pb.BasePb;


public class PbHelper {
	public static byte[] putShort(short s) {
		byte[] b = new byte[2];
		b[0] = ((byte) (s >> 8));
		b[1] = ((byte) (s >> 0));
		return b;
	}

	public static short getShort(byte[] b, int index) {
		return (short) (b[(index + 1)] & 0xFF | b[(index + 0)] << 8);
	}

	public static <T> BasePb.Base createRqBase(int cmd, Long param, GeneratedMessage.GeneratedExtension<BasePb.Base, T> ext, T msg) {
		BasePb.Base.Builder baseBuilder = BasePb.Base.newBuilder();
		baseBuilder.setCommand(cmd);
		if (param != null) {
			baseBuilder.setParam(param.longValue());
		}
		baseBuilder.setExtension(ext, msg);
		return baseBuilder.build();
	}

	
	// 封装成一个可以发送给服务器的包
	public static byte[] wrapMsg(BasePb.Base msg) {
		if (msg == null) {
			throw new NullPointerException("msg is null");
		}

		short len = (short) msg.toByteArray().length;
		byte[] lenArray = PbHelper.putShort(len);
		byte[] content = msg.toByteArray();
		byte[] totalByte = new byte[2 + content.length];
		// src->dst
		System.arraycopy(lenArray, 0, totalByte, 0, 2);
		System.arraycopy(content, 0, totalByte, 2, content.length);
		return totalByte;
	}
}

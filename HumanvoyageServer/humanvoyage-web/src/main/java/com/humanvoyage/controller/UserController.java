package com.humanvoyage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.humanvoyage.constant.GameError;
import com.humanvoyage.handle.MessageHandle;
import com.humanvoyage.pb.BasePb.Base;
import com.humanvoyage.utils.MessageHelper;
@Controller
public class UserController {
	private MessageHandle clientMessageHandle;
	@ResponseBody
    @RequestMapping("account.do")
    public byte[] accountLogic(@RequestBody byte[] msgData, HttpServletRequest request) {
	////权限
    	HttpSession session = request.getSession();
    	System.out.println(session);
        try {
        	//final Base64 base64 = new Base64();
        	//byte[] decode = base64.decode(msgData);
            if (msgData != null && msgData.length != 0) {
            	Base base = Base.parseFrom(msgData, MessageHandle.PB_EXTENDSION_REGISTRY);
            	
            	return clientMessageHandle.handle(base,request);
            } else {
            	return MessageHelper.packErrorPbMsg(100, GameError.NOT_WHITE_NAME);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return MessageHelper.packErrorPbMsg(100, GameError.SERVER_EXCEPTION);
        }
    }
	public MessageHandle getClientMessageHandle() {
		return clientMessageHandle;
	}
	public void setClientMessageHandle(MessageHandle clientMessageHandle) {
		this.clientMessageHandle = clientMessageHandle;
	}
	
}

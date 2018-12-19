package com.humanvoyage.msg;

import javax.servlet.http.HttpServletRequest;

import com.humanvoyage.constant.GameError;
import com.humanvoyage.pb.BasePb.Base;

public interface MessageBase {
	public abstract GameError execute(Object req, Base.Builder builder,HttpServletRequest request);
	
}

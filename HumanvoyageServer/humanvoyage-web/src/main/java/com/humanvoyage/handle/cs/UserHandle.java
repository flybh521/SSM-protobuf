package com.humanvoyage.handle.cs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.humanvoyage.constant.GameError;
import com.humanvoyage.msg.ClientHandler;
import com.humanvoyage.pb.BasePb.Base;
import com.humanvoyage.pb.UseInfoPb.GetUseInfoRq;
import com.humanvoyage.pb.UseInfoPb.GetUseInfoRs;
import com.humanvoyage.service.UserService;

@Component
public class UserHandle extends ClientHandler {
	@Autowired
	private UserService userService;
	@Override
	public GameError execute(Object req, Base.Builder builder,HttpServletRequest request) {
		GetUseInfoRq doLoginRq = (GetUseInfoRq) req;
		GetUseInfoRs.Builder rsBuilder = GetUseInfoRs.newBuilder();
		GameError aa = userService.aa(doLoginRq,rsBuilder,request);
		
		rsBuilder.setKeyId(10000);
		if(aa==GameError.OK){
			builder.setExtension(GetUseInfoRs.ext, rsBuilder.build());
		}
		return aa;
	}
}

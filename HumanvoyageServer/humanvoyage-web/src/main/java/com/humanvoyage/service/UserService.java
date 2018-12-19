package com.humanvoyage.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.humanvoyage.constant.GameError;
import com.humanvoyage.pb.UseInfoPb.GetUseInfoRq;
import com.humanvoyage.pb.UseInfoPb.GetUseInfoRs;

@Service
public class UserService {
	public GameError aa(GetUseInfoRq req,GetUseInfoRs.Builder res,HttpServletRequest request){
		
		return GameError.OK;
	}
}

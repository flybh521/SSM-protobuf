package com.humanvoyage.handle;

import org.springframework.stereotype.Component;

import com.humanvoyage.handle.cs.UserHandle;
import com.humanvoyage.pb.UseInfoPb.GetUseInfoRq;
import com.humanvoyage.pb.UseInfoPb.GetUseInfoRs;

public class ClientMessageHandle extends MessageHandle {
	@Override
	public void addMessage() {
		registerPbMessage(GetUseInfoRq.EXT_FIELD_NUMBER, UserHandle.class, GetUseInfoRq.ext, GetUseInfoRs.ext);
		
	}

}

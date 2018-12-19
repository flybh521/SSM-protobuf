package com.humanvoyage.handle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.humanvoyage.constant.GameError;
import com.humanvoyage.msg.MessageBase;
import com.humanvoyage.pb.BasePb;
import com.humanvoyage.pb.BasePb.Base;
import com.humanvoyage.pb.UseInfoPb;
import com.humanvoyage.utils.*;

abstract public class MessageHandle {
	class PbMsg {
		public MessageBase msg;
		public GeneratedMessage.GeneratedExtension<Base, ?> req;
		public GeneratedMessage.GeneratedExtension<Base, ?> res;
	}

	private Map<String, MessageBase> messageBump;
	private Map<Integer, PbMsg> pbMessageBump;
	private Logger logger =CommonLogFactory.getLogger(MessageHandle.class);;
	public static ExtensionRegistry PB_EXTENDSION_REGISTRY = ExtensionRegistry.newInstance();
	static {
		BasePb.registerAllExtensions(PB_EXTENDSION_REGISTRY);
		UseInfoPb.registerAllExtensions(PB_EXTENDSION_REGISTRY);
	}
	
	public void init() {
		messageBump = new HashMap<String, MessageBase>();
		pbMessageBump = new HashMap<>();
		this.addMessage();
	}

	abstract public void addMessage();
	public byte[] handle(Base base,HttpServletRequest request) throws IOException {
		byte[] back = this.dealMsgs(base,request);
		return back;
	}

	

	public byte[] dealMsgs(Base base,HttpServletRequest request) throws IOException {
		byte[] route = route(base,request);
		return route;
	}
	//寻找相应的
	public byte[] route(Base base,HttpServletRequest request) throws InvalidProtocolBufferException {
		if (!base.hasCommand()) {
			return null;
		}
		//记录请求
		logger.info(CommonLogFactory.formatLog(LoggerUtil.LogType.REQUEST, 123,base));
		int cmd = base.getCommand();
		PbMsg pbMsg = pbMessageBump.get(cmd);
		if (pbMsg == null) {
			return null;
		}
		MessageBase executer = pbMsg.msg;
		if (executer == null) {
			return null;
		}
		Base.Builder builder = Base.newBuilder();
		GameError gameError = executer.execute(base.getExtension(pbMsg.req), builder,request);
		builder.setCommand(cmd + 1);
		builder.setCode(gameError.getCode());
        if (base.hasIndex()) {
            builder.setIndex(base.getIndex());
        }

		Base out = builder.build();
		logger.info(CommonLogFactory.formatLog(LoggerUtil.LogType.RESPONSE, 123,out));
		return out.toByteArray();
	}
	
	
	
	
	protected Object getPbExtension(Base base, GeneratedMessage.GeneratedExtension<Base, ?> ext) {
		return base.getExtension(ext);
	}

	protected void registerMessage(String name, Class<?> c) {
		messageBump.put(name, (MessageBase) ContextLoader.getCurrentWebApplicationContext().getBean(c));
	}

	protected void registerPbMessage(int cmd, Class<?> c, GeneratedMessage.GeneratedExtension<Base, ?> rq, GeneratedMessage.GeneratedExtension<Base, ?> rs) {
		PbMsg pbMsg = new PbMsg();
		pbMsg.msg = (MessageBase) ContextLoader.getCurrentWebApplicationContext().getBean(c);
		pbMsg.req = rq;
		pbMsg.res = rs;
		pbMessageBump.put(cmd, pbMsg);
	}
}

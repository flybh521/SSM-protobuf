package com.humanvoyage.constant;

public enum GameError {
	OK(200, "OK") {
	},
	PARAM_ERROR(1001, "PARAM FORMAT ERROR") {
	},
	INVALID_PARAM(1002, "INVALID PARAM VALUE") {
	},
	MSG_LEN(1003, "MSG LENTH ERROR") {
	},
	DDOS_ERROR(1004, "SEND MSG TOO FAST") {
	},
	PROTOCAL_ERROR(1005, "WRONG ENCODE") {
	},
	SERVER_EXCEPTION(1006, "OCCUR EXCEPTION") {
	},
	CUR_VERSION(1007, "CURRENT VERSION OLD") {// 版本有更新，请退出重进
	},
	SESSION_LOST(1008, "SESSION LOST") {// SESSION失效，请重新登录
	},
	TOKEN_LOST(1009, "TOKEN LOST") {// token丢失，请重新登录
	},
	NO_LORD(1010, "NOT FOUND LORD") {
	},
	SENSITIVE_WORD(1011, "CONTAIN SENSITIVE WORD") {
	},

	EXIST_ACCOUNT(1012, "ACCOUNT ALREADY EXSIT") {
	},
	PWD_ERROR(1013, "PASSWORD ERROR") {
	},
	NOT_EXIST_ACCOUNT(1014, "ACCOUNT NOT EXSIT") {
	},
	NOT_WHITE_NAME(1015, "NOT IN WHITE NAME") {
	},
	FORBID_ACCOUNT(1016, "ACCOUNT IS FORBID") {
	},
	INVALID_TOKEN(1017, "INVALID TOKEN") {
	},
	BASE_VERSION(1018, "VERSION TOO OLD") {
	},
	SDK_LOGIN(1019, "SDK LOGIN FAILED") {
	},
	ACTIVE_AGAIN(1020, "CAN'T ACTIVE AGAIN") {
	},
	NO_ACTIVE_CODE(1021, "THIS CODE NOT EXIST") {
	},
	USED_ACTIVE_CODE(1022, "THIS CODE USED") {
	},
	NO_CHANNEL_CONFIG(1023, "NO_CHANNEL_CONFIG") {
	},
	USER_CHANNEL_CREATED_FAILED(1024, "USER_CHANNEL_CREATED_FAILED") {
	},
	PLAT_TYPE_ERROR(1025, "PLAT_TYPE_ERROR") {
	},
	FORBID_ACCOUNT_NEW(1026, "FORBID_ACCOUNT_NEW") {
	},


	KICK_USER(8888, "KICK_USER") {
	},
	NEW_ACCOUNT(9999, "NEW ACCOUNT") {
	};

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private GameError(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private int code;
	private String msg;
}

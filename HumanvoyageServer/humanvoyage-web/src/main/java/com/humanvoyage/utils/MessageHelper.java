package com.humanvoyage.utils;

import java.io.ByteArrayOutputStream;

import com.humanvoyage.constant.GameError;
import com.humanvoyage.pb.BasePb.Base;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MessageHelper {
	static public String getRequestCmd(JSONObject msg) {
		return msg.getString("request");
	}

	static public int getResultState(JSONObject res) {
		return res.getInt("code");
	}

	static public JSONObject getRequestParam(JSONObject msg) {
		return msg.getJSONObject("param");
	}

	static public JSONObject getPacketData(JSONObject msg) {
		return msg.getJSONObject("response");
	}

	static public JSONObject createPacket(String cmd) {
		JSONObject pack = new JSONObject();
		pack.put("cmd", cmd);
		return pack;
	}

	static public JSONObject createPacket(String cmd, GameError gameError) {
		JSONObject pack = new JSONObject();
		pack.put("cmd", cmd);
		pack.put("code", gameError.getCode());
		pack.put("msg", gameError.getMsg());
		return pack;
	}

	static public JSONObject packError(JSONObject rs, GameError gameError) {
		rs.put("code", gameError.getCode());
		rs.put("msg", gameError.getMsg());
		return rs;
	}

	static public JSONObject packResponse(JSONObject rs, String cmd, JSONObject response) {
		rs.put("cmd", cmd);
		rs.put("response", response);
		return rs;
	}

	static public JSONObject packResponse(JSONObject rs, GameError error, JSONObject response) {
		rs.put("code", error.getCode());
		rs.put("msg", error.getMsg());
		rs.put("response", response);
		return rs;
	}

	static public JSONObject packCmd(JSONObject rs, String cmd) {
		rs.put("cmd", cmd);
		return rs;
	}

	static public JSONObject packParam(JSONObject packet, JSONObject param) {
		packet.put("param", param);
		return packet;
	}

	static public JSONObject packRequest(JSONObject packet, String request) {
		packet.put("request", request);
		return packet;
	}

	static public JSONObject packPacket(JSONObject packet, String request, JSONObject param) {
		packet.put("request", request);
		packet.put("param", param);
		return packet;
	}

	static public JSONObject sendPacket(String url, JSONObject packet) {
		JSONArray packets = new JSONArray();
		packets.add(packet);
		try {
			String response = HttpHelper.doPost(url, packets.toString());
			JSONArray resArray = JSONArray.fromObject(response);
			JSONObject res = JSONObject.fromObject(resArray.get(0));
			if (MessageHelper.getResultState(res) != GameError.OK.getCode()) {
				return null;
			}

			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	static public byte[] packErrorPbMsg(int cmd, GameError gameError) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			Base.Builder builder = Base.newBuilder();
			builder.setCommand(cmd + 1);
			builder.setCode(gameError.getCode());
			Base out = builder.build();
			byte[] packMsg = out.toByteArray();
			output.write(MessageHelper.putShort((short) packMsg.length));
			output.write(packMsg);
			
			return output.toByteArray();
			
		} catch (Exception e) {
			return new byte[1];
		}
	}

	static public String packErrorJsonMsg(String cmd, GameError gameError) {
		JSONArray array = new JSONArray();
		JSONObject pack = new JSONObject();
		pack.put("cmd", cmd);
		pack.put("code", gameError.getCode());
		pack.put("msg", gameError.getMsg());
		array.add(pack);
		return array.toString();
	}

	// public static byte[] putShort(short s) {
	// byte[] b = new byte[2];
	// b[1] = (byte) (s >> 8);
	// b[0] = (byte) (s >> 0);
	// return b;
	// }
	//
	// static public short getShort(byte[] b, int index) {
	// return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
	// }

	public static byte[] putShort(short s) {
		byte[] b = new byte[2];
		b[0] = (byte) (s >> 8);
		b[1] = (byte) (s >> 0);
		return b;
	}

	static public short getShort(byte[] b, int index) {
		return (short) (((b[index + 1] & 0xff) | b[index + 0] << 8));
	}
}

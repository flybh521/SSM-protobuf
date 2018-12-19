package com.humanvoyage.utils;

public class PrintHelper {
	public static final boolean ENABALE = true;

	static public void println(String x) {
		if (ENABALE) {
			System.out.println(x);
		}
	}

	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	static public void printHex(String str) {
		try {
			byte[] b = str.getBytes("utf-8");
			System.out.println(bytesToHexString(b));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}

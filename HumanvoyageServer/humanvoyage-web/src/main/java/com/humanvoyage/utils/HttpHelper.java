package com.humanvoyage.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import com.google.protobuf.InvalidProtocolBufferException;
import com.humanvoyage.handle.MessageHandle;
import com.humanvoyage.pb.BasePb.Base;

import javax.servlet.http.HttpServletRequest;

public class HttpHelper {
	public static final String UTF8_ENCODE = "UTF-8";
	public static final ContentType CONTENT_TYPE = ContentType.create("text/plain", "UTF-8");
	private static final int TIME_OUT = 5;

	static public String doPost(String url, String body) throws Exception {
		String responseBody = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(body, CONTENT_TYPE));
			CloseableHttpResponse response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return responseBody;
			}
			try {
				HttpEntity httpEntity = response.getEntity();
				if (httpEntity != null) {
					responseBody = EntityUtils.toString(response.getEntity());
				}
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}

		return responseBody;
	}

	private static String _gzipStream2Str(InputStream inputStream) throws IOException {
		GZIPInputStream gzipinputStream = new GZIPInputStream(inputStream);
		byte[] buf = new byte[1024];
		int num = -1;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((num = gzipinputStream.read(buf, 0, buf.length)) != -1) {
			baos.write(buf, 0, num);
		}
		return new String(baos.toByteArray(), "utf-8");
	}

	static public String doPost(String url, byte[] body) throws Exception {
		String responseBody = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/msgpack;charset=UTF-8");
			// httpPost.setHeader("Content-Length", "" + body.length);
			httpPost.setEntity(new ByteArrayEntity(body));

			CloseableHttpResponse response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				responseBody = _gzipStream2Str(response.getEntity().getContent());
				return responseBody;
			}
			try {
				HttpEntity httpEntity = response.getEntity();
				if (httpEntity != null) {
					// responseBody =
					// EntityUtils.toString(response.getEntity());
					responseBody = _gzipStream2Str(response.getEntity().getContent());
				}
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}

		return responseBody;
	}

	public static Base sendMsgToGame(String url, Base msg) throws InvalidProtocolBufferException {
		byte[] result = sendPbByte(url, msg.toByteArray());

		short len = PbHelper.getShort(result, 0);
		// System.out.println("back len:" + len);
		byte[] data = new byte[len];
		System.arraycopy(result, 2, data, 0, len);

		Base rs = Base.parseFrom(data, MessageHandle.PB_EXTENDSION_REGISTRY);
		return rs;
	}

	public static byte[] sendPbByte(String httpUrl, byte[] body) {
		HttpURLConnection httpCon = null;
		byte[] responseBody = null;
		URL url = null;
		try {
			url = new URL(httpUrl);
		} catch (MalformedURLException e1) {
			System.out.println("URL null");
			e1.printStackTrace();
			return null;
		}

		try {
			httpCon = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("openConnection exception");
			return null;
		}

		if (httpCon == null) {
			System.out.println("openConnection null");
			return null;
		}

		httpCon.setDoOutput(true);
		httpCon.setConnectTimeout(TIME_OUT * 1000);
		httpCon.setReadTimeout(TIME_OUT * 1000);
		httpCon.setDoOutput(true);
		httpCon.setUseCaches(false);

		try {
			httpCon.setRequestMethod("POST");
		} catch (ProtocolException e1) {
			e1.printStackTrace();
			return null;
		}

		OutputStream output;
		try {
			output = httpCon.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}

		try {
			if (body != null) {
				// System.out.println("send byte lenth:" + body.length);
				output.write(PbHelper.putShort((short) body.length));

				// System.out.println("head:" +
				// Arrays.toString(PbHelper.putShort((short) body.length)));
				output.write(body);
				// System.out.println("body:" + Arrays.toString(body));
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}

		try {
			output.flush();
			output.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}

		// 开始读取返回的内容
		InputStream in;
		try {
			in = httpCon.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		/**
		 * 这个方法可以在读写操作前先得知数据流里有多少个字节可以读取。 需要注意的是，如果这个方法用在从本地文件读取数据时，一般不会遇到问题，
		 * 但如果是用于网络操作，就经常会遇到一些麻烦。
		 * 比如，Socket通讯时，对方明明发来了1000个字节，但是自己的程序调用available()方法却只得到900，或者100，甚至是0，
		 * 感觉有点莫名其妙，怎么也找不到原因。 其实，这是因为网络通讯往往是间断性的，一串字节往往分几批进行发送。
		 * 本地程序调用available()方法有时得到0，这可能是对方还没有响应，也可能是对方已经响应了，但是数据还没有送达本地。
		 * 对方发送了1000个字节给你，也许分成3批到达，这你就要调用3次available()方法才能将数据总数全部得到。
		 * 
		 * 经常出现size为0的情况，导致下面readCount为0使之死循环(while (readCount != -1)
		 * {xxxx})，出现死机问题
		 */
		int size = 0;
		try {
			size = in.available();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}

		System.out.println("back stream len:" + size);
		if (size == 0) {
			size = 1024;
		}
		byte[] readByte = new byte[size];
		// 读取返回的内容
		int readCount = -1;
		try {
			readCount = in.read(readByte, 0, size);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (readCount != -1) {
			baos.write(readByte, 0, readCount);
			try {
				readCount = in.read(readByte, 0, size);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		try {
			responseBody = baos.toByteArray();
		} finally {
			if (httpCon != null) {
				httpCon.disconnect();
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return responseBody;
	}


	public static Base sendMailMsgToGame(String url, Base msg) {
		try {
			byte[] result = sendPbByte(url, msg.toByteArray());

			short len = PbHelper.getShort(result, 0);

			byte[] data = new byte[len];
			System.arraycopy(result, 2, data, 0, len);
			return Base.parseFrom(data, MessageHandle.PB_EXTENDSION_REGISTRY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 从request中获得参数Map，并返回可读的Map
	 *
	 * @param request
	 * @return
	 */
	public static Map getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();

		try {
			Iterator<?> entries = properties.entrySet().iterator();
			Map.Entry entry;
			String name = "";
			String value = "";
			while (entries.hasNext()) {
				entry = (Map.Entry) entries.next();
				name = (String) entry.getKey();
				Object valueObj = entry.getValue();
				if (null == valueObj) {
					value = "";
				} else if (valueObj instanceof String[]) {
					String[] values = (String[]) valueObj;
					for (int i = 0; i < values.length; i++) {
						value = values[i] + ",";
					}
					value = value.substring(0, value.length() - 1);
				} else {
					value = valueObj.toString();
				}
				returnMap.put(name, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnMap;
	}
}

package com.humanvoyage.utils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HTTP 请求工具类
 * 
 *
 */
public class HttpClientUtils {
	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000)
			.setConnectionRequestTimeout(15000).build();

	/**
	 * 发送HTTP POST请求,支持带多个String参数
	 * 
	 * @param url
	 *            链接
	 * @param paramMap
	 *            参数
	 */
	public static String sendHttpPost(String url, Map<String, String> paramMap) throws Exception {
		CloseableHttpClient httpclient = HttpClientUtils.getHttpClient();
		return sendHttpPost(url, paramMap, httpclient);
	}

	/**
	 * 发送HTTP POST请求,支持多个参数(注：多个参数需拼接)
	 * 
	 * @param url
	 *            链接
	 * @param params
	 *            参数(格式:key1=value1&amp;key2=value2)
	 */
	public static String sendHttpPost(String url, String params) throws Exception {
		CloseableHttpClient httpclient = HttpClientUtils.getHttpClient();
		return sendHttpPost(url, params, httpclient);
	}

	/**
	 * 发送HTTP POST请求,支持带一个文件参数
	 * 
	 * @param url
	 *            链接
	 * @param file
	 *            文件
	 */
	public static String sendHttpPost(String url, File file) throws Exception {
		CloseableHttpClient httpclient = HttpClientUtils.getHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(file), -1,
					ContentType.APPLICATION_OCTET_STREAM);
			reqEntity.setChunked(true);
			httpPost.setEntity(reqEntity);
			return sendHttpPost(httpPost, httpclient);
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 发送HTTP POST请求(客户端采用二进制流发送,服务端采用二进制流接收)
	 * 
	 * @param url
	 *            链接
	 * @param binaryStreamsStr
	 *            参数
	 */
	public static String sendHttpPostByStream(String url, String binaryStreamsStr) throws Exception {
		final Base64 base64 = new Base64();
		final String text = "字串文字";
		final byte[] textByte = binaryStreamsStr.getBytes("UTF-8");
		//编码
		byte[] encode = base64.encode(textByte);
		//System.out.println(encodedText);
		CloseableHttpClient httpclient = HttpClientUtils.getHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			
			HttpEntity reqEntity = new ByteArrayEntity(encode,
					ContentType.APPLICATION_JSON);
			httpPost.setEntity(reqEntity);
			return sendHttpPost(httpPost, httpclient);
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 发送HTTP GET请求,不带参数(注：可将参数加在url后面)
	 * 
	 * @param url
	 *            链接
	 */
	public static String sendHttpGet(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClientUtils.getHttpClient();
		try {
			return sendHttpGet(url, httpclient);
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 发送HTTP GET请求,不带参数,返回byte数组
	 * 
	 * @param url
	 *            链接
	 */
	public static byte[] sendHttpGetResByte(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClientUtils.getHttpClient();
		try {
			return sendHttpGetResByte(url, httpclient);
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 发送HTTP GET请求,支持多个参数(注：多个参数需拼接)
	 * 
	 * @param url
	 *            链接
	 * @param params
	 *            参数(格式:key1=value1&amp;key2=value2)
	 */
	public static String sendHttpGet(String url, String params) throws Exception {
		CloseableHttpClient httpclient = HttpClientUtils.getHttpClient();
		return sendHttpGet(url, params, httpclient);
	}

	/**
	 * 发送HTTPS GET请求,支持多个参数(注：多个参数需拼接)
	 * 
	 * @param url
	 *            链接
	 * @param params
	 *            参数(格式:key1=value1&amp;key2=value2)
	 */
	public static String sendHttpsGet(String url, String params) throws Exception {
		CloseableHttpClient httpclient = HttpClientUtils.getHttpsClient(url);
		return sendHttpGet(url, params, httpclient);
	}

	/**
	 * 发送HTTPS GET请求,不带参数(注：可将参数加在url后面)
	 * 
	 * @param url
	 *            链接
	 */
	public static String sendHttpsGet(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClientUtils.getHttpsClient(url);
		try {
			return sendHttpGet(url, httpclient);
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 发送HTTPS POST请求,支持带多个String参数
	 * 
	 * @param url
	 *            链接
	 * @param paramMap
	 *            参数
	 */
	public static String sendHttpsPost(String url, Map<String, String> paramMap) throws Exception {
		CloseableHttpClient httpclient = HttpClientUtils.getHttpsClient(url);
		return sendHttpPost(url, paramMap, httpclient);
	}

	/**
	 * 发送HTTPS POST请求,支持多个参数(注：多个参数需拼接)
	 * 
	 * @param url
	 *            链接
	 * @param params
	 *            参数(格式:key1=value1&amp;key2=value2)
	 */
	public static String sendHttpsPost(String url, String params) throws Exception {
		CloseableHttpClient httpclient = HttpClientUtils.getHttpsClient(url);
		return sendHttpPost(url, params, httpclient);
	}

	/**
	 * 发送HTTP GET请求
	 */
	private static String sendHttpGet(String url, String params, CloseableHttpClient httpclient) throws Exception {
		try {
			StringBuilder sb = new StringBuilder().append(url).append("?").append(params);

			return sendHttpGet(sb.toString(), httpclient);
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 发送HTTP POST请求
	 */
	private static String sendHttpPost(String url, Map<String, String> paramMap, CloseableHttpClient httpclient)
			throws Exception {
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			for (String key : paramMap.keySet()) {
				nvps.add(new BasicNameValuePair(key, paramMap.get(key)));
			}

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			return sendHttpPost(httpPost, httpclient);
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 发送HTTP POST请求
	 */
	private static String sendHttpPost(String url, String params, CloseableHttpClient httpclient) throws Exception {
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(params, Consts.UTF_8));
			return sendHttpPost(httpPost, httpclient);
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 获取HttpClient
	 */
	private static CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}

	/**
	 * 获取HTTPS HttpClient
	 */
	private static CloseableHttpClient getHttpsClient(String url) throws Exception {
		PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(url));

		DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();

		return httpclient;
	}

	/**
	 * 发送HTTP POST请求
	 */
	private static String sendHttpPost(HttpPost httpPost, CloseableHttpClient httpclient) throws Exception {
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = httpclient.execute(httpPost);
		try {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, Consts.UTF_8);
		} finally {
			response.close();
		}
	}

	/**
	 * 发送HTTP GET请求
	 */
	private static String sendHttpGet(String url, CloseableHttpClient httpclient) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = httpclient.execute(httpGet);

		try {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, Consts.UTF_8);
		} finally {
			response.close();
		}
	}

	/**
	 * 发送HTTP GET请求
	 */
	private static byte[] sendHttpGetResByte(String url, CloseableHttpClient httpclient) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = httpclient.execute(httpGet);

		try {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toByteArray(entity);
		} finally {
			response.close();
		}
	}

}
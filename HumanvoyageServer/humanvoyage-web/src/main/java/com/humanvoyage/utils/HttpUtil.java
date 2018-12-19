package com.humanvoyage.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	// private static Logger log = Logger.getLogger(HttpUtil.class.getName());
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPostWeixin1(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			conn.setRequestProperty("Content-type", "text/xml; charset=utf-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			
			// 发送请求参数
			//out.print(URLDecoder.decode(param, "utf-8"));
			out.print(param);
			
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			result="{\"errorCode\":777,\"result\":[]}";
			System.out.println("发送 POST 请求出现异常！" + e);
			
			e.printStackTrace();
			

		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			
			// 发送请求参数
			//out.print(URLDecoder.decode(param, "utf-8"));
			out.print(param);
			
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			result="{\"errorCode\":777,\"result\":[]}";
			System.out.println("发送 POST 请求出现异常！" + e);
			
			e.printStackTrace();
			

		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	
	
	
	
	 public static String sendGet(String url, String param) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            
	            // 建立实际的连接
	            connection.connect();
	            // 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
	            /*
	            for (String key : map.keySet()) {
	                System.out.println(key + "--->" + map.get(key));
	            }
	            */
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream(),"UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送GET请求出现异常！" + e);
	            e.printStackTrace();
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }
	
	 public static String sendGetNoParam(String url) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url;
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            
	            // 建立实际的连接
	            connection.connect();
	            // 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
	            /*
	            for (String key : map.keySet()) {
	                System.out.println(key + "--->" + map.get(key));
	            }
	            */
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream(),"UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送GET请求出现异常！" + e);
	            e.printStackTrace();
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }
	
	
	public static String getRequestContent(HttpServletRequest request) {
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			// log.error("getRequestContentError", e);
			return null;
		}
	}

	public static void writeResponseContent(HttpServletResponse response,
			String str) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			String content = str;
			out.print(content);
			out.flush();
		} catch (Exception e) {
			// log.error("writeResponseContent error:\n" + e);
		}
	}

	public static boolean isLogin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null)
			return false;
		else {
			// log.debug("sessionid:"+session.getId()+"  uid:"+session.getAttribute("uid"));
			return true;
		}
	}

	public static <T> boolean isNull(T t, HttpServletResponse response,
			String error) {
		if (t == null) {
			// log.error(error);
			// HttpUtil.writeResponseContent(response,
			// "{errorCode:\""+ErrorCode.REQUEST_FAIL+"\"}");
			return true;
		}
		return false;

	}
	public static String  sentPostNew(String url,String paramNew){
		Map<String, Object> sParaTemp =new HashMap<String, Object>();
		if(paramNew!=null&&!("".equals(paramNew))){
			String[] split = paramNew.split("&");
			for(int i=0;i<split.length;i++){
				if(split[i]!=null&&!("".equals(split[i]))){
					String[] split2 = split[i].split("=");
					if(split2.length<2){
						
						sParaTemp.put(split2[0], "");
						
					}else{
						sParaTemp.put(split2[0], split2[1]);
						
					}
					
					
				}
				
				
			}
			
			
			
		}
			
			
			
		
		
		
		
		
		
		
		
		
		 //HttpClient httpClient = new HttpClient();
		 HttpClient httpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true));
	        PostMethod post = new PostMethod(url);
	        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

	        List<String> keys = new ArrayList<String>(sParaTemp.keySet());
	        NameValuePair[] param = new NameValuePair[keys.size()+1];
	        for (int i = 0; i < keys.size(); i++) {
	            String name = keys.get(i);
	            Object object = sParaTemp.get(name);
	            String value = "";
	            if (object != null) {
	                value = (String) sParaTemp.get(name);
	            }
	            //添加参数
	            param[i] = new NameValuePair(name, value);
	            post.setParameter(param[i].getName(),param[i].getValue());
	            //System.out.println(param[i].getName());
	        }
	        HttpMethod method = post;
	        String result=null;
	        try {
				httpClient.executeMethod(method);
				result = method.getResponseBodyAsString();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				
			} catch(ConnectException e) {
				result="{\"errorCode\":777,\"result\":[]}";
				e.printStackTrace();
				
				
			}catch(IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				
			}catch (Exception e) {
				
				e.printStackTrace();
				
			}finally{
				post.releaseConnection();
				
			}
	        
			
	        
	       return result;
		
	}
	
	/**
	 * 未实现的方法
	 * 
	 * 
	 * @return
	 */
	
	
	public static String sendPostWeixin(String url,String xml){
		
		
		
		PostMethod post = new PostMethod(url);//请求地址  
		  
		// 设置请求的内容直接从文件中读取  
		   //   post.setRequestBody( new FileInputStream(input));   
		     // if (input.length() < Integer.MAX_VALUE)  
		     //    post.setRequestContentLength(input.length());  
		    //  else  
		     //  post.setRequestContentLength(EntityEnclosingMethod.CONTENT_LENGTH_CHUNKED);  
		  
		post.setRequestBody(xml);//这里添加xml字符串  
		  
		// 指定请求内容的类型  
		post.setRequestHeader("Content-type", "text/xml; charset=utf-8");  
		HttpClient httpclient = new HttpClient();//创建 HttpClient 的实例  
		String result="";  
		try {  
		int result1 = httpclient.executeMethod(post);  
		//System.out.println("Response status code: " + result);//返回200为成功  
		//System.out.println("Response body: ");  
			 result=post.getResponseBodyAsString();//返回的内容  
		//System.out.println(post.getResponseBodyAsString());//返回的内容  
		post.releaseConnection();//释放连接  
		} catch (HttpException e) {  
		// TODO Auto-generated catch block  
		e.printStackTrace();  
		} catch (IOException e) {  
		// TODO Auto-generated catch block  
		e.printStackTrace();  
		}  
		
		
		
		return result;
	}
	
	public static String sendjson(String url,JSONObject  obj ){
		String responseBody="";
		 try {
	            
	            CloseableHttpClient httpclient = HttpClients.createDefault();
	            System.out.println(obj);

	            HttpPost httpPost = new HttpPost(url);
	            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");

	            // 解决中文乱码问题
	            StringEntity stringEntity = new StringEntity(obj.toString(), "UTF-8");
	            stringEntity.setContentEncoding("UTF-8");

	            httpPost.setEntity(stringEntity);

	            // CloseableHttpResponse response =
	            // httpclient.execute(httpPost);

	            System.out.println("Executing request " + httpPost.getRequestLine());

	            // Create a custom response handler
	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	                @Override
	                public String handleResponse(final HttpResponse response)
	                        throws ClientProtocolException, IOException {//
	                    int status = response.getStatusLine().getStatusCode();
	                    if (status >= 200 && status < 300) {

	                        HttpEntity entity = response.getEntity();

	                        return entity != null ? EntityUtils.toString(entity) : null;
	                    } else {
	                        throw new ClientProtocolException(
	                                "Unexpected response status: " + status);
	                    }
	                }
	            };
	            responseBody = httpclient.execute(httpPost, responseHandler);
	           
	            

	        } catch (Exception e) {
	            System.out.println(e);
	        }
		     return  responseBody;
	    }
	  public static String sendJson(String url,JSONObject  obj ){
		String responseBody="";
		 try {
	            
	            CloseableHttpClient httpclient = HttpClients.createDefault();
	            System.out.println(obj);

	            HttpPost httpPost = new HttpPost(url);
	            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");

	            // 解决中文乱码问题
	            StringEntity stringEntity = new StringEntity(obj.toString(), "UTF-8");
	            stringEntity.setContentEncoding("UTF-8");

	            httpPost.setEntity(stringEntity);

	            // CloseableHttpResponse response =
	            // httpclient.execute(httpPost);

	           // System.out.println("Executing request " + httpPost.getRequestLine());

	            // Create a custom response handler
	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	                @Override
	                public String handleResponse(final HttpResponse response)
	                        throws ClientProtocolException, IOException {//
	                    int status = response.getStatusLine().getStatusCode();
	                    if (status >= 200 && status < 300) {

	                        HttpEntity entity = response.getEntity();

	                        return entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
	                    } else {
	                        throw new ClientProtocolException(
	                                "Unexpected response status: " + status);
	                    }
	                }
	            };
	            responseBody = httpclient.execute(httpPost, responseHandler);
	           
	            

	        } catch (Exception e) {
	            System.out.println(e);
	        }
		     return  responseBody;
	    }
	
	
	
}

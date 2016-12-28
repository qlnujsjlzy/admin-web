
package com.angular.admin.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;


/**
 * @Description:
 * @author:
 */
public class HttpUtil {


	private static Log log = LogFactory.getLog(HttpUtil.class);
	private static DefaultHttpClient httpClient;
	private static DefaultHttpClient httpClient_proxy;

	public static final String text_plain_ContentType="text/plain";
	public static final String multipart_form_data_ContentType="multipart/form-data";
	public static final String default_ContentType="application/x-www-form-urlencoded";


	private static String proxyHost = "10.99.60.201";//B2CMainConfig.getProxyHost();// 代理ip地址
	private static int proxyPort = 8080;// B2CMainConfig.getProxyPort();// 代理端口
	
	private static int maxConLifeTimeMs = 300000;
	private static int defaultMaxConPerHost = 50;
	private static int maxTotalConn = 10000;
	private static int conTimeOutMs = 10000;
	private static int soTimeOutMs = 30000;
	
	static {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
//	        TrustManager[] tm = { new MyX509TrustManager() };
			
			Scheme http = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			//sslcontext.init(null, tm, new java.security.SecureRandom());
			sslcontext.init(null, null, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);//STRICT_HOSTNAME_VERIFIER
			Scheme https = new Scheme("https", 443, sf);
			SchemeRegistry sr = new SchemeRegistry();
			sr.register(http);
			sr.register(https);
			PoolingClientConnectionManager cm = new PoolingClientConnectionManager(sr, maxConLifeTimeMs, TimeUnit.MILLISECONDS);
			cm.setMaxTotal(maxTotalConn);
			cm.setDefaultMaxPerRoute(defaultMaxConPerHost);
			//普通http客户端
			httpClient = new DefaultHttpClient(cm);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, conTimeOutMs);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeOutMs);
			httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
			httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
				public void process(final HttpResponse response, final HttpContext context) throws HttpException, IOException {
					HttpEntity entity = response.getEntity();
					Header ceheader = entity.getContentEncoding();
					if (ceheader != null && ceheader.getValue().toLowerCase().contains("gzip")) {
						response.setEntity(new GzipDecompressingEntity(response.getEntity()));
					}
				}
			});
			//代理http客户端
			httpClient_proxy = new DefaultHttpClient(cm);
			httpClient_proxy.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, conTimeOutMs);
			httpClient_proxy.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeOutMs);
			httpClient_proxy.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
			HttpHost proxy = new HttpHost(proxyHost, proxyPort);
			httpClient_proxy.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		} catch (Exception e) {
			log.error("HttpExecutor init error", e);
		}
	}

	/**
	 * 
	  * @Description:无代理调用
	  * @param request
	  * @return
	  * @throws Exception HttpResponse
	  * @Created:lining 2013年12月26日下午4:57:17
	  * @Modified:
	 */
	public static HttpResponse execute(HttpUriRequest request) throws Exception {
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(request);
		} catch (Exception e) {
			throw e;
		}
		return httpResponse;
	}
	
	/**
	 * 
	  * @Description:有代理调用
	  * @param request
	  * @return
	  * @throws Exception HttpResponse
	  * @Created:lining 2013年12月26日下午4:57:33
	  * @Modified:
	 */
	public static HttpResponse executeProxy(HttpUriRequest request) throws Exception {
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient/*_proxy*/.execute(request);
		} catch (Exception e) {
			throw e;
		}
		return httpResponse;
	}
	
	/**
	 * @description 发送httpClient post 请求，json 格式返回
	 * @author qinhc
	 * @2015下午6:03:09
	 * @param url
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static String executeHttpPost(String url, String body)
			throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost method = new HttpPost(url);
		StringEntity entity = new StringEntity(body, "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		method.setEntity(entity);
		String resData = "";
		// 请求超时
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		// 读取超时
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				20000);
		try {
			HttpResponse result = httpClient.execute(method);
			// 请求结束，返回结果
			resData = EntityUtils.toString(result.getEntity());
			log.info("executeHttpPost返回：" + resData);
		} catch (Exception e) {
			log.error("executeHttpPost请求出错：" + e.getMessage());
		} finally {
			method.releaseConnection();
		}
		return resData;
	}
	
	/**
	 * @description
	 * @author qinhc
	 * @2015下午6:00:41
	 * @param url
	 * @param body
	 * @param type 请求的类型
	 * @return
	 * @throws Exception
	 */
	public static String executeHttpPostType(String url, String body,String type)
			throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost method = new HttpPost(url);
		StringEntity entity = new StringEntity(body, "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType(type);
		method.setEntity(entity);
		String resData = "";
		// 请求超时
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		// 读取超时
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				20000);
		try {
			HttpResponse result = httpClient.execute(method);
			// 请求结束，返回结果
			resData = EntityUtils.toString(result.getEntity());
			log.info("executeHttpPostType返回的数据：" + resData);
		} catch (Exception e) {
			log.error("executeHttpPostType请求出错：" + e.getMessage());
		} finally {
			method.releaseConnection();
		}
		return resData;
	}
	
	/**
	 * 
	* @Description: post 提交
	* @author yuzj7@lenovo.com  
	* @date 2015年5月15日 上午10:41:49
	* @param url
	* @param params
	* @return
	 */
	public static String postStr(String url, Map<String, String> params) throws UnsupportedEncodingException{
		return post(url,params,default_ContentType);
	}

	/**
	 * @Author zhanghs 【zhanghs6@lenovo.com】
	 * @Description:
	 * @param url
	 * @param params
	 * @param contentType
	 * @return
	 * @date 2016/5/6 17:19
	 * @return
	 */


	private static String post(String url, Map<String, String> params,String contentType) throws UnsupportedEncodingException{
		HttpClient httpclient =new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", contentType+"; charset=utf-8");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
		String line = null;
		String str="";
		try {
			HttpResponse response=httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			InputStreamReader inputstream = new InputStreamReader(entity.getContent(), "UTF-8");
			BufferedReader reader = new BufferedReader(inputstream);
			// 显示结果
			while ((line = reader.readLine()) != null) {
				str+=line;
			}
			reader.close();
			inputstream.close();
		}catch(Exception e){
			log.error("Http调用异常",e);
		}finally{
			if(httpclient != null){
				httpclient.getConnectionManager().shutdown();
			}
		}
		return str;
	}

	/**
	 * post 方法
	 * @param url
	 * @return
	 * @author mamj
	 * @throws UnsupportedEncodingException 
	 * @date 2013-11-19 下午03:46:58
	 */
	public static String PostStr(String url, Map<String, String> params,String referer,String cookie,boolean isproxy,String charset) throws UnsupportedEncodingException{
		HttpClient httpclient =new DefaultHttpClient();
		// 请求超时
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        // 读取超时
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000    );
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		if(!StringUtils.isEmpty(cookie)){
			httpPost.setHeader("Cookie", cookie);
		}
		if(!StringUtils.isEmpty(referer)){
			httpPost.addHeader("Referer", referer);
		}
         
         if(StringUtils.isEmpty(charset)){
        	 charset = "utf-8";
         }
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
		String line = null;   
		 StringBuffer str = new StringBuffer("");  
		 InputStreamReader inreader = null;
		 BufferedReader reader = null;
		 try { 
			HttpResponse response=httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			inreader = new InputStreamReader(entity.getContent(), charset);
			reader  = new BufferedReader(inreader);   
			// 显示结果   
			while ((line = reader.readLine()) != null) {   
				str.append(line);
			} 
		 }catch(Exception e){
			log.error(e);
		 }finally{
			 if (reader != null) {
	        	  try {
	        		  reader.close();// 最后要关闭BufferedReader  
				} catch (IOException e) {
					log.error(e);
				}
	          }
	    	  if(inreader != null){
	    		  try {
	    			  inreader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error(e);
				}
	    	  }
			 if(httpclient != null){
				 httpclient.getConnectionManager().shutdown();
			 }
		 }
		return str.toString();
	}
	
	
	/**
	 * 
	* @Description: http get 请求
	* @author yuzj7@lenovo.com  
	* @date 2015年6月11日 下午5:53:28
	* @param url
	* @return
	 */
	public static String getStr(String url) {
		
		  BufferedReader in = null;  
	      // 定义HttpClient  
	      HttpClient client = new DefaultHttpClient();
	      // 实例化HTTP方法  
	      HttpGet request = new HttpGet();
	      String line = "";  
	      String tmp="";
	      try {  
		  //修改特殊字符bu
			  client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 600000);
			  // 读取超时
			  client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 600000    );
		     URL url1 = new URL(url);
		     URI uri = new URI(url1.getProtocol(),url1.getUserInfo(),url1.getHost(),url1.getPort(),
					 url1.getPath(), url1.getQuery(), null);
//		      request.setURI(new URI(url));
			  request.setURI(uri);
		      request.setHeader("Content-Type", "text/html;charset=UTF-8");
		      HttpResponse response = client.execute(request);
	          in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));   
	          while ((tmp=in.readLine()) != null) {   
	            line+=tmp;   
	          }
	      }catch (Exception e) {  
	    	  log.error("getStr请求出错：",e);
	      }finally{
	    	  if (in != null) {
	        	  try {
					in.close();// 最后要关闭BufferedReader  
				} catch (IOException e) {
					log.error(e);
				}
	          }
	    	  client.getConnectionManager().shutdown();
	      }  
	      return line;  
	            
	}


	public static void main(String[] args) throws Exception{
		String url = "http://localhost:9000/greeting";
		System.out.println("url :" + url);
		Map<String, String> params = new HashMap<>();
		params.put("name", "lizhaoyang1231231");
		System.out.println("params :" + JsonUtil.toJson(params));
		postStr(url, params);
		HttpUtil.getStr("http://localhost:9000/greeting?name=lizhaoyang21231232");
	}
	}

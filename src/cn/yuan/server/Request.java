package cn.yuan.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 封装请求信息
 * @author yuanzhixiangsuse
 *
 */
public class Request {
	//请求方式
		private String method;
		//请求资源
		private String url;
		//请求参数
		private Map<String,List<String>> parameterMapValues;
		//内部
		public static final String CRLF="\r\n";
		private InputStream is;
		private String requestInfo;
		public Request() {
			method="";
			url="";
			parameterMapValues=new HashMap<String,List<String>>();
			requestInfo="";
		}
		public Request(InputStream is) {
			this();
			this.is=is;
			byte[] data=new byte[20480];
			try {
				int len=is.read(data);
				requestInfo=new String(data, 0, len);
			} catch (IOException e) {
				return;
			}
			//分析头信息
			parseRequestInfo();
		}
		/**
		 * 分析头信息
		 */
	   private	void parseRequestInfo()
	   {
		   if (requestInfo==null || requestInfo.trim().equals("")) {
			return;
		}
		   /**
		    * =================================================================
		    * 从信息的首行分析出：请求方式，请求路径，请求参数（get可能存在）
		    * 如果为Post方式，请求参数可能在最后正文中
		    * 
		    * 
		    * 
		    * ==================================================================
		    */
		   String paramString="";//接收请求参数
		   //1.获取请求方式
		   String firstLine=requestInfo.substring(0, requestInfo.indexOf(CRLF));
		   int idx=requestInfo.indexOf("/");//“/”的位置
		   this.method=firstLine.substring(0,idx).trim();
		   String urlString=firstLine.substring(idx,firstLine.indexOf("HTTP/")).trim();;
		   if(this.method.equalsIgnoreCase("post"))
		   {
			   this.url=urlString;
			   paramString=requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
		   }
		   else if (this.method.equalsIgnoreCase("get")) {
			if(urlString.contains("?"))//是否存在参数
			{
				String[] urlArray=urlString.split("\\?");
				this.url=urlArray[0];
				paramString=urlArray[1];
			}
			else
			{
				 this.url=urlString;
			}
		}
		   //2.将请求参数封装到MAp中
		   if(!paramString.equals(""))
		   {
		   parseParam( paramString);
		   }
	   }
	   private void parseParam(String paramString)
	   {
		   //分割，将字符串转成数组
		   StringTokenizer tokenizer=new StringTokenizer(paramString, "&");
		   while (tokenizer.hasMoreTokens()) {
			   String keyValue=tokenizer.nextToken();	
			   String[] keyValues=keyValue.split("=");
			   if(keyValues.length==1)
			   {
				   keyValues=Arrays.copyOf(keyValues, 2);
				   keyValues[1]=null;
			   }
			   String key= keyValues[0].trim();
			   String value= null==keyValues[1]?null:decode(keyValues[1].trim(),"gbk");
			   //转换成map
			   if(!parameterMapValues.containsKey(key))
			   {
				   parameterMapValues.put(key,new ArrayList<>());
				   List<String> values=parameterMapValues.get(key);
				   values.add(value);
			   }
		}
		   
	   }
	   public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	    * 解决中文问题
	    * @param value
	    * @param code
	    * @return
	    */
	   private String decode(String value,String code) {
		try {
		  return URLDecoder.decode(value, code);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	   /**
	    * 根据页面的name获取对应的多个值
	    */
	   public String[] getParameterValues(String name) {
		   List<String> values=null;
		if((values=parameterMapValues.get(name))==null)
		{
			return null;
		}
		else
		{
			return values.toArray(new String[0]);
		}
	}
	   /**
	    * 根据页面的name获取对应的值
	    */
	   public String getParameter(String name) {
		String[] values=getParameterValues(name);
		if(values==null)
		{
			return null;
		}
		else
		{
			return values[0];
		}
	}
}

package cn.yuan.server;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import cn.yuan.servlet.Servlet;

public class WebApp {
	private static ServletContext context;
	static{
		try {
			//1.解析工厂
			SAXParserFactory factory=SAXParserFactory.newInstance();
			//2.析工厂获取默认解析器
			SAXParser parser=factory.newSAXParser();
			//3.文档注册处理器
			//4.编写处理器
			WebHandler webHandle=new WebHandler();
			parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("WEB_INFO/web.xml"),
					webHandle);
			context=new ServletContext();	
			 Map<String,String> mapping=context.getMapping();
			 Map<String,String> servlet=context.getServlet();
			 //servlet-name servlet-class
			for(Entity entity:webHandle.getEntityList())
			{
				servlet.put(entity.getName(), entity.getClz());
			}
			for(Mapping map:webHandle.getMappingList())
			{
				List<String> urList=map.getUrlPattern();
				for(String url:urList){
					mapping.put(url, map.getName());
				}
			
			}
			/* mapping.put("/login", "login");
			 mapping.put("/log", "login");
			 mapping.put("/reg", "register");
		
			 servlet.put("login","cn.yuan.server.LoginServlet");
			 servlet.put("register", "cn.yuan.server.RegisterServlet");*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	
	}
	public static Servlet getServlet(String url) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		if(null == url || url.trim().equals(""))
		{
			return null;
		}
		//根据字符串创建对象（完整路径）
		String name=context.getServlet().get(context.getMapping().get(url));
		return (Servlet)Class.forName(name).newInstance();
		//return context.getServlet().get(context.getMapping().get(url)) ;
	}
}

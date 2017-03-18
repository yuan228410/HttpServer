package cn.yuan.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Servlet上下文
 * @author yuanzhixiangsuse
 *
 */
public class ServletContext {
	//为每个Servlet起个别名 example：Login->LoginServlet(包名+类名)
	private Map<String,String> servlet;
	//url->Login
	private Map<String,String> mapping;
	public ServletContext() {
		servlet=new HashMap<String,String>();
		mapping=new HashMap<String,String>();
	}
	public Map<String, String> getServlet() {
		return servlet;
	}
	public void setServlet(Map<String, String> servlet) {
		this.servlet = servlet;
	}
	public Map<String, String> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
}

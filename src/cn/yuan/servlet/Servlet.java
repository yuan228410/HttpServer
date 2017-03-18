package cn.yuan.servlet;

import cn.yuan.server.Request;
import cn.yuan.server.Response;

/**
 * 抽象一个父类
 * @author yuanzhixiangsuse
 *
 */
public abstract class Servlet {
	public void service(Request req,Response rep) throws Exception {
		this.doGet(req,rep);
		this.doPost(req,rep);
	}
	protected abstract void doGet(Request req,Response rep) throws Exception ;
	protected abstract void doPost(Request req,Response rep) throws Exception ;
}

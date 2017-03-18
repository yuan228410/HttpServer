package cn.yuan.servlet;

import cn.yuan.server.Request;
import cn.yuan.server.Response;

public class RegisterServlet extends Servlet {

	@Override
	public void doGet(Request req, Response rep) throws Exception {
		rep.print("欢迎:").print(req.getParameter("uname")).print("注册");

	}

	@Override
	public void doPost(Request req, Response rep) throws Exception {
		doGet(req,rep);

	}

}

package cn.yuan.servlet;

import cn.yuan.server.Request;
import cn.yuan.server.Response;

public class LoginServlet extends Servlet {

	@Override
	public void doGet(Request req, Response rep) throws Exception {
		rep.print("welcom:").print(req.getParameter("uname")).print("come");
	}

	@Override
	public void doPost(Request req, Response rep) throws Exception {
		doGet(req,rep);
		
	}


}

package cn.yuan.server;

import java.io.IOException;
import java.net.Socket;

import cn.yuan.servlet.Servlet;

/**
 * 一个请求与响应就一个此对象
 * 
 * @author yuanzhixiangsuse
 *
 */
public class Dispatcher implements Runnable {
	private Socket client;
	private Request req;
	private Response rep;
	private int code = 200;

	public Dispatcher(Socket client) {
		this.client = client;
		try {
			req = new Request(client.getInputStream());
			rep = new Response(client.getOutputStream());
		} catch (IOException e) {
			code = 500;
			return;
		}
	}

	@Override
	public void run() {
		try {
			Servlet servlet = WebApp.getServlet(req.getUrl());
			if (null == servlet) {
				this.code = 404;// 找不到对应处理
			} else {
				servlet.service(req, rep);
				rep.pushToClient(code);
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.code = 500;
			try {
				rep.pushToClient(code);
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				//ex.printStackTrace();
			}
		}	
		CloseUtil.closeSocket(client);
	}

}

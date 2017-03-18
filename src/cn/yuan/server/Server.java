package cn.yuan.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 创建服务器并启动
 * @author yuanzhixiangsuse
 *
 */
public class Server {
	private ServerSocket server;
	private boolean isShutDown=false;
	public Server() {
	// TODO Auto-generated constructor stub
}
	public static void main(String[] args) {
		Server server=new Server();
		server.start();
	}
	/**
	 * 默认端口启动服务
	 */
	public void start() {
		start(8888) ;
	}
	/**
	 * 指定端口的启动服务
	 */
	public void start(int port) {
		try {
		 server=new ServerSocket(port);
		
		 this.receive();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			stop();
		}
	}
	/**
	 * 接收连接
	 */
	private void receive() {
		try {
			while (!isShutDown) {
				new Thread(new Dispatcher(server.accept())).start() ;
			}		
		} catch (IOException e) {
			stop();
		}
	}
	/**
	 * 停止服务
	 */
	public void stop() {
		isShutDown=true;
		CloseUtil.closeServerSocket(server);
	}
}

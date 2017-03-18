package cn.yuan.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 关闭流的方法
 * @author yuanzhixiangsuse
 *
 */
public class CloseUtil {
	public static void closeAll(Closeable ...io) {
		for(Closeable temp:io)
		{
			if(null!=temp)
			{
				try {
					temp.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 使用泛型方法实现关闭IO流
	 * @param io
	 */
	public static <T extends Closeable> void closeIO(T... io) {
		for(Closeable temp:io)
		{
			if(null!=temp)
			{
				try {
					temp.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 关闭Socket
	 * @param client
	 */
	public static void closeSocket(Socket client) {
		try {
			if(client!=null)
			{
			client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 关闭Socket
	 * @param client
	 */
	public static void closeServerSocket(ServerSocket client) {
		try {
			if(client!=null)
			{
			client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

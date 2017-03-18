package cn.yuan.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;


/*
 * 封装响应信息
 */
public class Response {
	public static final String CRLF="\r\n";
	public static final String BLANK=" ";
	//存储头信息
	private StringBuilder headInfo;
	//存储正文信息
	private StringBuilder content;
	BufferedWriter bw;
	//存储正文长度
	private int len=0;
	public Response() {
		headInfo=new StringBuilder();
		content=new StringBuilder();
	}
	public Response(OutputStream outputStream) {
		this();
		bw=new BufferedWriter(new OutputStreamWriter(outputStream));
	}
	public Response(Socket client){
		this();
		try {
			bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			headInfo=null;
		}
	}
	/*
	 * 构建响应头
	 */
	private void createHeadInfo(int code) {
		//1)http协议版本，状态代码，描述
		headInfo.append("Http/1.1").append(BLANK).append(code).append(BLANK);
		switch (code) {
		case 200:
			headInfo.append("OK");
			break;
		case 404:
			headInfo.append("NOT FOUNT");
			break;
		case 505:
			headInfo.append("SERVER EROR");
			break;
		default:
			break;
		}
		headInfo.append(CRLF);
		//2)响应头
		headInfo.append("Servr:bjsxt Server/6.0.2").append(CRLF);
		headInfo.append("Date:").append(new Date().toString()).append(CRLF);
		headInfo.append("Content-type:text/html;charset=GBK").append(CRLF);
		headInfo.append("Content-Length:").append(len).append(CRLF);
		headInfo.append(CRLF);
	}
	/**
	 * 构建正文
	 */
	public Response print(String info) {
		content.append(info);
		len+=info.getBytes().length;
		return this;
	}
	/**
	 * 构建正文+回车换行
	 */
	public Response println(String info) {
		content.append(info).append(CRLF);
		len+=(info+CRLF).getBytes().length;
		return this;
	}
	/**
	 *推送到客户端
	 * @throws IOException 
	 */
	public void pushToClient(int code) throws IOException {
		if(headInfo==null)
		{
			code=500;
		}
		createHeadInfo(code);
		//头信息加分隔符
		bw.append(headInfo.toString());
		bw.append(content.toString());
		bw.flush();
	}
	public void close() {
		CloseUtil.closeAll(bw);
	}
}

package com.ykkj.apache;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * web  服务端 
 * @author Administrator
 *
 */
public class WebServer {
	
	public  WebServer(int port ){
		try {
			ServerSocket serverSocket=new ServerSocket(port);
			
			while(true){
				Socket socket=serverSocket.accept();
			new Processor(socket).start();
			}
		} catch (IOException e) {
		    System.out.println("服务器端口初始化失败，端口"+port+" 或不存在，或已被占用");
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) {
		new WebServer(808);
	}
}

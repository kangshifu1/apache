package com.ykkj.apache;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * web  ����� 
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
		    System.out.println("�������˿ڳ�ʼ��ʧ�ܣ��˿�"+port+" �򲻴��ڣ����ѱ�ռ��");
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) {
		new WebServer(808);
	}
}

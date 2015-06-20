package com.ykkj.apache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Processor extends Thread {
	private Socket socket=null;
	private InputStream inputStream=null;
	private PrintStream outputStream=null;
	private final static String WEB_ROOT="F:\\jeecg架构师\\2\\html";
	public Processor(Socket socket){
		this.socket=socket;
		try {
			inputStream=socket.getInputStream();
			outputStream=new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("嚴重 ： 輸入輸出流初始化 失敗");
			e.printStackTrace();
		}
	}
	
	public void run(){
		String filenString =parse(inputStream);
		sendFileMessage(filenString);
		
	}
	public String parse(InputStream inputStream){
		String filename="";
		 BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
		 try {
			String http=bufferedReader.readLine();
		 if(http==null){
			 return "";
		 }
			String[] content=http.split(" ");
			 if(content.length!=3){
				 sendErrorMessage(400, "資源請求錯誤");
				 return "";
			 }
			 filename=content[1];
		 
		} catch (IOException e) {
			
			e.printStackTrace();
			return "";
		}
		return filename;
	}
	/**
	 * 	
	 * @param errorcode
	 * @param errorMessge
	 */
	public void sendErrorMessage(int errorcode ,String errorMessge){
		outputStream.println("HTTP/1.0 "+errorcode+"  error");
		outputStream.println("content-type: text/html");
		outputStream.println();
		outputStream.println("<html>");
		outputStream.println("<title>Error Page</title>");
		outputStream.println("<body>");
		outputStream.println("<h1>");
		outputStream.println("ErrorCode："+errorcode +" </br>   ");
		outputStream.println("ErrorMessage: "+errorMessge);
		outputStream.println("</h1>");
		outputStream.println("</body>");
		outputStream.println("</html>");
		outputStream.flush();
		outputStream.close();
		try {
			inputStream.close();
		} catch (IOException e) {
		 
			e.printStackTrace();
		}
		
		}
	public void sendFileMessage(String file){
		
		if("".equals(file)){
			sendErrorMessage(404, "资源请求不到");
		}
		File file2=new File(Processor.WEB_ROOT+file);
		System.out.println(Processor.WEB_ROOT+file);
		try {
			InputStream fileReader=new FileInputStream(file2);
			byte[] bytes=new byte[(int)file2.length()];
			 fileReader.read(bytes);
		
		
		outputStream.println("HTTP/1.0  200 ok");
		outputStream.println("content-length"+file2.length());
		outputStream.println();
		outputStream.write(bytes);
		outputStream.flush();
		outputStream.close();
	
			inputStream.close();
		} catch (IOException e) {
		 
			e.printStackTrace();
			sendErrorMessage(404, "资源文件未请求到");
		}
	}	
		
}

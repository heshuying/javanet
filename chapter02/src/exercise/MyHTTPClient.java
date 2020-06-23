package exercise;

import java.net.*;
import java.io.*;

public class MyHTTPClient{
    String host="www.javathinker.net";
    int port = 80;
    Socket socket;    
    public void createSocket() throws Exception    {
      socket = new Socket(host, 80);
    }

    public void communicate() throws Exception {
      StringBuffer sb = new StringBuffer("GET " + "/index.jsp" + " HTTP/1.1\r\n");
      sb.append("Host: "+host+"\r\n");
      sb.append("Accept: */*\r\n");
     sb.append("Accept-Language: zh-cn\r\n");
     sb.append("Accept-Encoding: gzip, deflate\r\n");
     sb.append("User-Agent: MyHTTPClient\r\n");
     sb.append("Connection: Keep-Alive\r\n\r\n");

     // 发出HTTP请求
     OutputStream socketOut = socket.getOutputStream();
     socketOut.write(sb.toString().getBytes());
     socketOut.flush();

    // 接收响应结果
    InputStream socketIn = socket.getInputStream();
    FileOutputStream fileOut = new FileOutputStream("response.data");
    byte[] buff = new byte[1024];
    int len = -1;
    while ((len = socketIn.read(buff)) != -1)	{
      fileOut.write(buff, 0, len);	
    }
    fileOut.close();
    socket.close();
    System.out.println("响应数据已经保存到response.data文件中");
  }

  public static void main(String args[]) throws Exception  {
    MyHTTPClient client = new MyHTTPClient();
    client.createSocket();
    client.communicate();
  }
}



/****************************************************
 * 作者：方伟                                                                  *
 * 来源：<<Java网络编程核心技术详解>>                    *
 * 技术支持网址：www.javathinker.net                        *
 ***************************************************/

import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import java.security.*;

public class HTTPSClient {
  String host="www.alipay.com";
  int port=443;
  SSLSocketFactory factory;
  SSLSocket socket;

  public void createSocket()throws Exception{
    factory=(SSLSocketFactory)SSLSocketFactory.getDefault();
    socket=(SSLSocket)factory.createSocket(host,port);
    String[] supported=socket.getSupportedCipherSuites();
    socket.setEnabledCipherSuites(supported);
  }
  
  public void communicate()throws Exception{
    StringBuffer sb=new StringBuffer("GET http://"+host+"/ HTTP/1.1\r\n");
    sb.append("Host:"+host+"\r\n");
    sb.append("Accept: */*\r\n");
    sb.append("\r\n");

    //发出HTTP请求
    OutputStream socketOut=socket.getOutputStream();
    socketOut.write(sb.toString().getBytes());
    socketOut.flush();
    
    //接收响应结果
    System.out.println("开始接收响应结果");
    InputStream socketIn=socket.getInputStream();
    BufferedReader in = new BufferedReader(new InputStreamReader(socketIn));

    String line;
    while ((line = in.readLine()) != null) 
      System.out.println(line);

     socket.close();
  } 

  public static void main(String args[])throws Exception{
    HTTPSClient client=new HTTPSClient();
    client.createSocket();
    client.communicate();
  } 
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

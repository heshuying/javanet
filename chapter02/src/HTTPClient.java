import java.net.*;
import java.io.*;

public class HTTPClient {
  String host="www.baidu.com";
  int port=80;
  Socket socket;
  
  public void createSocket()throws Exception{
    socket=new Socket(host,80);
  }
  

  public void communicate()throws Exception{
    StringBuffer sb=new StringBuffer("GET "+"/"+" HTTP/1.1\r\n");
    sb.append("Host: "+host+"\r\n");
    sb.append("Accept: */*\r\n");
    sb.append("Accept-Language: zh-cn\r\n");
    sb.append("Accept-Encoding: gzip, deflate\r\n");
    sb.append("User-Agent: HTTPClient\r\n");
    sb.append("Connection: Keep-Alive\r\n\r\n");

    //����HTTP����
    OutputStream socketOut=socket.getOutputStream();
    socketOut.write(sb.toString().getBytes());
    socketOut.flush();

    //������Ӧ���
    InputStream socketIn=socket.getInputStream();
    ByteArrayOutputStream buffer=new ByteArrayOutputStream();
    byte[] buff=new byte[1024];  
    int len=-1;
    while((len=socketIn.read(buff))!=-1){
      buffer.write(buff,0,len);
    }
    
    System.out.println(new String(buffer.toByteArray()));  //���ֽ�����ת��Ϊ�ַ���

/*
    InputStream socketIn=socket.getInputStream();
    BufferedReader br=new BufferedReader(new InputStreamReader(socketIn));
    String data;
    while((data=br.readLine())!=null){
      System.out.println(data);
    }
*/

    socket.close();
  }
  
  public static void main(String args[])throws Exception{
    HTTPClient client=new HTTPClient();
    client.createSocket();
    client.communicate();
  } 
}




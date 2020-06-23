package exercise;

import java.io.*;
import java.net.*;

public class EchoServer{
    private int port = 8000;
    private ServerSocket serverSocket;

    public EchoServer() throws IOException {
      serverSocket = new ServerSocket(port);
      System.out.println("服务器启动");
    }

    public String echo(Object msg){
      return "echo:" + msg;
    }

    private ObjectOutputStream getOutputStream(Socket socket) throws IOException {
      
      OutputStream socketOut = socket.getOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(socketOut);
      return oos;
    }

    private ObjectInputStream getInputStream(Socket socket) throws IOException {
      InputStream socketIn = socket.getInputStream();
      ObjectInputStream ois = new ObjectInputStream(socketIn);
      return ois;
    }

    public void service() {
      while (true){
          Socket socket = null;
          try{
      	socket = serverSocket.accept(); // 等待客户连接
      	System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
      
             ObjectOutputStream oos = getOutputStream(socket);
      	ObjectInputStream ois = getInputStream(socket);
      
      	Object msg = null;
      	while ((msg = ois.readObject()) != null){
      	    System.out.println("receive msg :" + msg);
      	    oos.writeObject(echo(msg));
      
      	    if (msg.equals("bye")){ // 如果客户发送的消息为“bye”，就结束通信
                     System.out.println("Finish talking. Bye!");
            	       break;
      	    }
      	    
      	}
          } catch (Exception e){
      	e.printStackTrace();
          } finally{
      	try{
      	   if (socket != null)
      	     socket.close(); // 断开连接
      	} catch (IOException e){
      	   e.printStackTrace();
      	}
          }
      }
    }

    public static void main(String args[]) throws IOException{
      new EchoServer().service();
    }
}



/****************************************************
 * 作者：方伟                                                                 *
 * 来源：<<Java网络编程核心技术详解>>                   *
 * 技术支持网址：www.javathinker.net                        *
 ***************************************************/

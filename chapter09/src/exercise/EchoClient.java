package exercise;

import java.net.*;
import java.io.*;
import java.util.*;

public class EchoClient{
  private String host = "localhost";
  private int port = 8000;
  private Socket socket;

  public EchoClient() throws IOException    {
    socket = new Socket(host, port);
  }

  public static void main(String args[]) throws IOException    {
    new EchoClient().talk();
  }

  private ObjectOutputStream getOutputStream(Socket socket) throws IOException    {
    OutputStream socketOut = socket.getOutputStream();
    ObjectOutputStream oopm=new ObjectOutputStream(socketOut);
    return oopm;
  }

  private ObjectInputStream getInputStream(Socket socket) throws IOException    {
    InputStream socketIn = socket.getInputStream();
    ObjectInputStream ooim=new ObjectInputStream(socketIn);
    return ooim;
  }

  public void talk() throws IOException    {
    try{
        ObjectInputStream ois = getInputStream(socket);
        ObjectOutputStream oos = getOutputStream(socket);
        BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
        String msg = null;
        while ((msg = localReader.readLine()) != null)  {
    
          oos.writeObject(msg);		
          System.out.println(ois.readObject());
    
          if (msg.equals("bye"))
             break;
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        try {
    	socket.close();
        } catch (IOException e){
    	e.printStackTrace();
        }
    }
  }
}



/****************************************************
 * 作者：方伟                                                                 *
 * 来源：<<Java网络编程核心技术详解>>                   *
 * 技术支持网址：www.javathinker.net                        *
 ***************************************************/
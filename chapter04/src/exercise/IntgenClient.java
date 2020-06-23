package exercise;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.io.IOException;

public class IntgenClient {
    
  public static int DEFAULT_PORT = 8000;
  
  public static void main(String[] args) {
    String host="localhost";
    int port= DEFAULT_PORT;

    try {
      SocketAddress address = new InetSocketAddress(host, port);
      SocketChannel client  = SocketChannel.open(address);
      ByteBuffer    buffer  = ByteBuffer.allocate(4);
      IntBuffer     view    = buffer.asIntBuffer();
      
      for (int expected = 0; ; expected++) {
        client.read(buffer);
        int actual = view.get();
        buffer.clear();
        view.rewind();

        //判断实际收到的int数据与预期应该收到的数据是否一致
        if (actual != expected) {
          System.err.println("预期应该收到的数据： " + expected + "; 实际收到的数据： " + actual);
          break;
        }
        System.out.println(actual);
      }     
    } catch(IOException ex) {
      ex.printStackTrace();   
    }
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/
package exercise;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.io.IOException;

public class IntgenServer {
    
  public static int DEFAULT_PORT = 8000;
  
  public static void main(String[] args) {
  
    int port;
    try {
      port = Integer.parseInt(args[0]);
    } catch (RuntimeException ex) {
      port = DEFAULT_PORT;   
    }
    System.out.println("Listening for connections on port " + port);
    
    ServerSocketChannel serverChannel;
    Selector selector;
    try {
      serverChannel = ServerSocketChannel.open();
      ServerSocket ss = serverChannel.socket();
      InetSocketAddress address = new InetSocketAddress(port);
      ss.bind(address);
      serverChannel.configureBlocking(false);
      selector = Selector.open();
      serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    } catch (IOException ex) {
      ex.printStackTrace();
      return;   
    }
    
    while (true) {
      try {
        selector.select();
      } catch (IOException ex) {
        ex.printStackTrace();
        break;
      }
        
      Set<SelectionKey> readyKeys = selector.selectedKeys();
      Iterator<SelectionKey> iterator = readyKeys.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        iterator.remove();
        try {
          if (key.isAcceptable()) {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            System.out.println("Accepted connection from " + client);
            client.configureBlocking(false);
            SelectionKey key2 = client.register(selector, SelectionKey.
                                                                     OP_WRITE);
            ByteBuffer outputBuffer = ByteBuffer.allocate(4);
            outputBuffer.putInt(0);  //向缓冲区放入第一个int数据
            outputBuffer.flip();
            key2.attach(outputBuffer);
          } else if (key.isWritable()) {
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer outputBuffer = (ByteBuffer) key.attachment();
            if (! outputBuffer.hasRemaining()) {
              outputBuffer.rewind();
              int value = outputBuffer.getInt();
              outputBuffer.clear();
              outputBuffer.putInt(value + 1); //向缓冲区放入递增的int数据
              outputBuffer.flip();
            }
            client.write(outputBuffer);  //输出缓冲区的数据
          }
        } catch (IOException ex) {
          key.cancel();
          try {
            key.channel().close();
          }
          catch (IOException cex) {}
        }
      }
    }
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/
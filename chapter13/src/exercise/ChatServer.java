package exercise;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;  

public class ChatServer {
  public static void main( String args[] ){
    try{
      System.setProperty("java.security.policy",ChatServer.class.getResource("secure.policy").toString());
      if (System.getSecurityManager() == null) {
        System.setSecurityManager(new SecurityManager());
      }

      ChatModel chatModel=new ChatModelImpl();
      Registry registry = LocateRegistry.createRegistry(1099);  
      registry.rebind( "chatModel", chatModel );
      System.out.println( "服务器注册了ChatModel对象" );
    }catch( Exception e ){
      e.printStackTrace();
    }
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

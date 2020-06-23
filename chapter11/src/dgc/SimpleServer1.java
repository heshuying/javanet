package dgc;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;

public class SimpleServer1{
  public static void main( String args[] ){
    try{
       System.setProperty("java.rmi.dgc.leaseValue","30000");
       HelloService service = new HelloServiceImpl();
       Registry registry = LocateRegistry.createRegistry(1099);
       registry.rebind( "HelloService", service );
       System.out.println( "服务器注册了一个HelloServiceImpl对象" );
       
       //等待客户端获得该远程对象的引用
       while(!service.isAccessed())Thread.sleep(500);
       
       registry.unbind( "HelloService");
       System.out.println( "服务器注销了一个HelloServiceImpl对象" );
  
    }catch(Exception e){
       e.printStackTrace();
    } 
  }
}





/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

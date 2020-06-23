package hello;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;  

public class SimpleServer{
  public static void main( String args[] ){
    try{
       HelloService service1 = new HelloServiceImpl("service1");
       HelloService service2 = new HelloServiceImpl("service2");

       //创建并启动注册器
       Registry registry = LocateRegistry.createRegistry(1099);  
       //Registry registry = LocateRegistry.getRegistry(1099);  
       registry.rebind( "HelloService1", service1 );
       registry.rebind( "HelloService2", service2 );
 
       System.out.println( "服务器注册了两个HelloService对象" );
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

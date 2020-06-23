package exercise;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;  

public class EchoServer{
  public static void main( String args[] ){
    try{
       EchoService service = new EchoServiceImpl();

       //创建并启动注册器
       Registry registry = LocateRegistry.createRegistry(1099);  
       registry.rebind( "EchoService", service );
 
       System.out.println( "服务器注册了一个EchoService对象" );
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

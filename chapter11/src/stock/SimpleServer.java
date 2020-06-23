package stock;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;


public class SimpleServer{
  public static void main( String args[] ){
    try{
       StockQuoteRegistryImpl quoteRegistry=new StockQuoteRegistryImpl();
 
       Registry registry = LocateRegistry.createRegistry(1099);
       registry.rebind( "StockQuoteRegistry",quoteRegistry);
       System.out.println( "服务器注册了一个StockQuoteRegistry对象" );

       new Thread(quoteRegistry).start();
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

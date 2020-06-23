package activate;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;  

public class SimpleClient{
  public static void showRemoteObjects(Registry registry)throws Exception{
    //列出所有注册的名字
    String[] names=registry.list();
    for(String name:names) 
     System.out.println(name);
  }

  public static void main( String args[] ){
    try{
      Registry registry = LocateRegistry.getRegistry(1099);
      HelloService service1=(HelloService)registry.lookup("HelloService1");
      HelloService service2=(HelloService)registry.lookup("HelloService2"); 
      
      Class stubClass=service1.getClass();
      System.out.println("service1是"+stubClass.getName()+"的实例"); 
      Class[] interfaces=stubClass.getInterfaces();
      for(int i=0;i<interfaces.length;i++)  
        System.out.println("存根类实现了"+interfaces[i].getName()); 

      System.out.println(service1.echo("hello")); 
      System.out.println(service1.getTime()); 

      System.out.println(service2.echo("hello")); 
      System.out.println(service2.getTime()); 

      showRemoteObjects(registry);
    }catch( Exception e){
       e.printStackTrace();
    }
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

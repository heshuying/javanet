package activate;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;  
import java.rmi.activation.*;
import java.util.Properties;

public class Setup{
  public static void main( String args[] ){
    try{
       Properties prop=new Properties();
       prop.put("java.security.policy",SimpleClient.class.getResource("server.policy").toString());
       ActivationGroupDesc group=new ActivationGroupDesc(prop,null);
        //注册ActivationGroup
       ActivationGroupID id=ActivationGroup.getSystem().registerGroup(group);
       String classURL=System.getProperty("java.rmi.server.codebase");
       MarshalledObject<String> param1=new MarshalledObject<String>("service1");
       MarshalledObject<String> param2=new MarshalledObject<String>("service2");

       ActivationDesc desc1=
new ActivationDesc(id,"activate.HelloServiceImpl",classURL,param1);
       ActivationDesc desc2=
new ActivationDesc(id,"activate.HelloServiceImpl",classURL,param2);
       //向rmid注册两个激活对象
       HelloService s1=(HelloService)Activatable.register(desc1);
       HelloService s2=(HelloService)Activatable.register(desc2);
       System.out.println(s1.getClass().getName());

       Registry registry = LocateRegistry.getRegistry(1099);
       //向RMI注册器注册两个激活对象
       registry.rebind( "HelloService1", s1 );
       registry.rebind( "HelloService2", s2 );
    
       System.out.println( "服务器注册了两个可激活的 HelloService对象" );
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

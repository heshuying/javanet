package exercise;
import java.io.*;
import java.net.*;
import java.util.*;

public class EchoClient {
  ProxyFactory proxyFactory; 
  EchoService echoService;

  public EchoClient()throws Exception{
      proxyFactory=new ProxyFactory("localhost",8000);
      //创建动态代理类实例
      echoService=
           (EchoService)proxyFactory.getProxy(EchoService.class);
  }
  public static void main(String args[])throws Exception {
      new EchoClient().talk();
  }

  public void talk()throws Exception {
    try{
      BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
      String msg=null;
      while((msg=localReader.readLine())!=null){
        System.out.println(echoService.echo(msg));

        if(msg.equals("bye"))
          break;
      }
    }catch(Exception e){
       e.printStackTrace();
    }finally{
       proxyFactory.close();
    }
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

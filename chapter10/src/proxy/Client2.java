package proxy;
public class Client2{
  public static void main(String args[]){
    HelloService helloService=new HelloServiceImpl();
    HelloService helloServiceProxy=
               HelloServiceProxyFactory.getHelloServiceProxy(helloService);
    System.out.println("动态代理类的名字为"
                       +helloServiceProxy.getClass().getName());
    System.out.println(helloServiceProxy.echo("Hello"));
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

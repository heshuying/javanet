package exercise;
import java.lang.reflect.*;
public class ProxyFactory {
  private Connector connector=null;

  public ProxyFactory(String host,int port)throws Exception{
    connector=new Connector(host,port);
  }
  public Object getProxy(final Class classType){
    InvocationHandler handler=new InvocationHandler(){
      public Object invoke(Object proxy,Method method,Object args[])
                                                       throws Exception{
         Call call=new Call(classType.getName(),
              method.getName(),method.getParameterTypes(),args); 
        connector.send(call);
        call=(Call)connector.receive();
        Object result=call.getResult();
        if(result instanceof Throwable)
          throw new RemoteException((Throwable)result);
        else
          return result;
      }
    };

    return Proxy.newProxyInstance(classType.getClassLoader(),
                                          new Class[]{classType},
                                          handler);
  }

  public void close(){
    if(connector!=null)
      connector.close();
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

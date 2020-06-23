package exercise;
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.reflect.*;
public class EchoServer {
  private Map<String,Object> remoteObjects=new HashMap<String,Object>();
  
  public void register(String className,Object remoteObject){
    remoteObjects.put( className,remoteObject);
  } 
  public void service()throws Exception{
    ServerSocket serverSocket = new ServerSocket(8000);
    System.out.println("服务器启动.");
    while(true){
      Socket socket=serverSocket.accept();
      OutputStream out=socket.getOutputStream();
      ObjectOutputStream oos=new ObjectOutputStream(out);
      InputStream in=socket.getInputStream();
      ObjectInputStream ois=new ObjectInputStream(in);
      while(true){
        Call call=(Call)ois.readObject();
         System.out.println(call);
         call=invoke(call);
         oos.writeObject(call);

        Object[] params=call.getParams();
        if(((String)params[0]).equals("bye")){
          ois.close();
          oos.close();
          socket.close();
          break; 
        }
      }
    }
  }

  public Call invoke(Call call){
    Object result=null;
    try{
      String className=call.getClassName();
      String methodName=call.getMethodName();
      Object[] params=call.getParams();
      Class<?> classType=Class.forName(className); 
      Class[] paramTypes=call.getParamTypes();
      Method method=classType.getMethod(methodName,paramTypes);  
      Object remoteObject=remoteObjects.get(className);
      if(remoteObject==null){
        throw new Exception(className+"的远程对象不存在");
      }else{
        result=method.invoke(remoteObject,params);
      }
    }catch(Exception e){result=e;}

    call.setResult(result);
    return call;
  }

  public static void main(String args[])throws Exception {
    EchoServer server=new EchoServer();
    server.register("exercise.EchoService",new EchoServiceImpl());
    server.service();
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

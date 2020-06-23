package exercise;

import java.util.*;
import java.sql.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class ChatModelImpl extends UnicastRemoteObject implements ChatModel{
  private Map<String,ChatView> clients=new HashMap<String,ChatView>();
  private static int count=1;

  public ChatModelImpl()throws ChatException,RemoteException{ }
  
  /** 注册客户和视图，可以回调视图的刷新界面的方法 */
  public String registerClient(ChatView cv) throws RemoteException{
    String name="Client"+count++;
    clients.put(name,cv);
    fireModelChangeEvent(toArray(clients.keySet()));
    return name;
  }
 
  /* 注销一个聊天客户 */ 
  public void unregisterClient(String clientName) throws RemoteException{
    clients.remove(clientName);
    fireModelChangeEvent(toArray(clients.keySet()));
  }

  /* 把集合转换为String[]，因为String[]可以被序列化，可以在网络上传输 */ 
  private String[] toArray(Set set){ 
      String[] result=new String[set.size()];
      int i=0;
      for(Object o: set)
         result[i++]=(String)o;

      return result;
  }  

  /** 当数据库中客户信息发生变化时，同步刷新所有的视图 */
  private void fireModelChangeEvent(Object o){
    for(String name:clients.keySet()){
      try{
        ChatView v=clients.get(name); 
        v.handleInfoChange(o);
      }catch(Exception e){
        try{
          if(e instanceof ConnectException) //如果出现连接异常，就注销该客户
             unregisterClient(name); 
        }catch(RemoteException ex){ex.printStackTrace();} 
        System.out.println(e.toString());
      }
    }
  }
  
   public String[] getClients() throws RemoteException{
        return toArray(clients.keySet());
   }

   public void transferMsg(String sendFrom,String sendTo,String msg) throws RemoteException{
     if(sendTo.equals("all"))
       fireModelChangeEvent(msg);
     else{
        ChatView from=clients.get(sendFrom);
        ChatView to=clients.get(sendTo);

     try{
         from.handleInfoChange(msg);
     }catch(Exception e){
         if(e instanceof ConnectException) //如果出现连接异常，就注销该客户
          unregisterClient(sendFrom); 
          System.out.println(e.toString());
      }

      try{
         if(!from.equals(to))
           to.handleInfoChange(msg); 
      }catch(Exception e){
         if(e instanceof ConnectException) //如果出现连接异常，就注销该客户
          unregisterClient(sendTo); 
          System.out.println(e.toString());
      }
    } 
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

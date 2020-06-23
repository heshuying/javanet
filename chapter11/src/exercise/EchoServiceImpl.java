package exercise;
import java.util.Date;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
public class EchoServiceImpl extends UnicastRemoteObject implements EchoService{
  public EchoServiceImpl()throws RemoteException{ }
  public String echo(String msg)throws RemoteException{
    return "echo:"+msg;
  }
} 


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

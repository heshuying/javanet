package exercise;
import java.rmi.*;
public interface EchoService extends Remote{
  public String echo(String msg) throws RemoteException;
} 


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

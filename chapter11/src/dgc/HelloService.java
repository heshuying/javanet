package dgc;
import java.util.Date;
import java.rmi.*;
public interface HelloService extends Remote{
  public boolean isAccessed() throws RemoteException;
  public void access() throws RemoteException;
  public void bye() throws RemoteException;
} 


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

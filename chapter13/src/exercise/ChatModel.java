package exercise;
import java.rmi.*;
import java.util.*;

public interface ChatModel extends Remote{
    /** 注册一个聊天客户，为了简化程序，可以由该方法为客户分配一个临时的唯一的客户名，
     *  比如第一个注册的客户为“client1”，第二个注册的客户为“client2”，依次类推。
    */
    public String registerClient(ChatView client)throws RemoteException;
    /**  注销一个聊天客户*/
    public void unregisterClient(String client)throws RemoteException;
    /** 转发消息，参数sendFrom表示发送者的客户名，参数sendTo表示接收者的客户名 */
    public void transferMsg(String sendFrom,String sendTo,String msg) throws RemoteException;
    /** 获得所有聊天客户的客户名 */
    public String[] getClients()throws RemoteException;
}



/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

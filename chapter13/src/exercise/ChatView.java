package exercise;
import java.rmi.*;

public interface ChatView extends Remote{
  /** 注册处理用户动作的监听器，即ChatController */
  public void addUserGestureListener(ChatController ctrl)throws ChatException,RemoteException;

  /** 在图形界面上显示数据， 参数display表示待显示的数据 */
  public void showDisplay(Object display)throws ChatException,RemoteException;

  /** 当有客户发出聊天信息或者有客户刚注册，同步刷新相关客户的图形界面 */
  public void handleInfoChange(Object o)throws ChatException,RemoteException;
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

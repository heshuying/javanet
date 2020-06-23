package exercise;
import java.util.*;
import java.io.Serializable;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.rmi.*;
import java.rmi.server.*;

public class ChatViewImpl extends UnicastRemoteObject
implements ChatView,Serializable{
  private transient ChatGui gui;
  private ChatModel chatmodel;
  private Object display;
  private String name;
  private ArrayList<ChatController> chatControllers=new ArrayList<ChatController>(10);

  public ChatViewImpl(ChatModel model)throws RemoteException {
    try{
       chatmodel=model;
       name=model.registerClient(this);
    }catch(Exception e){
      System.out.println("ChatViewImpl constructor "+e);
    }

    gui=new ChatGui(name);
    gui.refreshAllClients(model.getClients());
    //向图形界面注册监听器
    gui.addSendButtonListener(sendButtonHandler);
  }
  
  /** 加入控制器*/
  public void addUserGestureListener(ChatController ctr) throws ChatException,RemoteException{
    chatControllers.add(ctr);
  }
  /** 在图形界面上展示参数display指定的数据 */
  public void showDisplay(Object display) throws ChatException,RemoteException{
    if(!(display instanceof Exception))this.display=display;

    if(display instanceof String){
       gui.refreshChatMsg((String)display);
    }

    if(display instanceof String[]){
       gui.refreshAllClients((String[])display);
    }

    if(display instanceof Exception){
         gui.refreshChatMsg(((Exception)display).getMessage());
    }

  }

  /** 刷新界面上的信息*/
  public void handleInfoChange(Object obj)throws ChatException,RemoteException{
    if(gui==null)return;
    try{
      if(obj instanceof String[]){  //刷新客户列表
         gui.refreshAllClients((String[])obj);
      }else if(obj instanceof String){  //刷新聊天消息
         gui.refreshChatMsg((String)obj);
      }
    }catch(Exception e){
      System.out.println("ChatViewImpl:"+e);
    }
  }
  


  /** 监听图形界面上发送按钮的ActionEvent的监听器 */
  transient ActionListener sendButtonHandler=new ActionListener(){
     public void actionPerformed(ActionEvent e){
        ChatController ctr;
        for(int i=0;i<chatControllers.size();i++){
          ctr=chatControllers.get(i);
          ctr.handleSendMsgGesture(name,
                                     gui.getToClient(),
                                     name+":"+gui.getMessage());
        }

     }
  };
  
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

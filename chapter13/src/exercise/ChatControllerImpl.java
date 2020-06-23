package exercise;
import java.util.*;
public class ChatControllerImpl implements ChatController{
  private ChatModel chatModel;
  private ChatView chatView;
  public ChatControllerImpl(ChatModel model, ChatView view ) {
    try{
      chatModel=model;
      chatView=view;
      view.addUserGestureListener(this); //向视图注册控制器自身
    }catch(Exception e){
      reportException(e);
    }
  }
  
  /** 报告异常信息 */
  private void reportException(Object o){
    try{
      chatView.showDisplay(o);
    }catch(Exception e){
      System.out.println("ChatControllerImpl reportException"+e);
    }
  }
  
  /** 处理发送消息的动作 */
  public void handleSendMsgGesture(String from,String to,String msg){
    try{
      chatModel.transferMsg(from,to,msg);
    }catch(Exception e){
       reportException(e);
    }
  }

}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

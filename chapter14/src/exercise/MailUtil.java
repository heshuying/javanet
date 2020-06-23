package exercise;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;

public class MailUtil{
  protected Session session;
  private String sendHost="smtp.126.com";
  private String username = "java_mailtest";
  private String accessCode="access1234"; //授权码

  public void init()throws Exception{
    //设置属性
    Properties  props = new Properties();
    props.put("mail.smtp.host", sendHost);
    props.put("mail.smtp.auth","true"); //SMTP服务器需要身份验证
  
    session = Session.getDefaultInstance(props);
  }
  
  public void sendMessage(Message msg)
                                       throws Exception{

    Transport transport = session.getTransport("smtp"); 
    //连接SMTP服务器
    transport.connect(sendHost, username, accessCode); 
		  
    //发送邮件
    transport.sendMessage(msg,msg.getAllRecipients());
    System.out.println("邮件发送完毕");
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

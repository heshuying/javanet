import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;

public class MailClientFor126 extends MailClient{
  private String sendHost="smtp.126.com";
  private String receiveHost="pop.126.com";
  private String receiveProtocol="pop3";
  private String username = "java_mailtest";
  private String accessCode="access1234"; //授权码
  private String fromAddr="java_mailtest@126.com";  //发送者地址
  private String toAddr="javathinker_mail@sina.com"; //接收者地址

  public void init()throws Exception{
    //设置属性
    Properties  props = new Properties();
    props.put("mail.smtp.host", sendHost);
    props.put("mail.smtp.auth","true"); //SMTP服务器需要身份验证
  
    session = Session.getDefaultInstance(props);

    // 创建Store对象
    store = session.getStore(receiveProtocol);
    //连接到邮件服务器上的账户
    store.connect(receiveHost,username, accessCode);
  }
  
  public void sendMessage(String fromAddr,String toAddr)
                                       throws Exception{
    //创建一个邮件
    Message msg = createSimpleMessage(fromAddr,toAddr);

    Transport transport = session.getTransport("smtp"); 
    //连接SMTP服务器
    transport.connect(sendHost, username, accessCode); 
		  
    //发送邮件
    transport.sendMessage(msg,msg.getAllRecipients());
    System.out.println("邮件发送完毕");
  }
  public static void main(String[] args)throws Exception {
    MailClientFor126 client=new MailClientFor126();
    client.init();
    client.receiveMessage();
    client.sendMessage(client.fromAddr,client.toAddr);
    client.close();
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

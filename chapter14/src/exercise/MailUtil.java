package exercise;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;

public class MailUtil{
  protected Session session;
  private String sendHost="smtp.126.com";
  private String username = "java_mailtest";
  private String accessCode="access1234"; //��Ȩ��

  public void init()throws Exception{
    //��������
    Properties  props = new Properties();
    props.put("mail.smtp.host", sendHost);
    props.put("mail.smtp.auth","true"); //SMTP��������Ҫ�����֤
  
    session = Session.getDefaultInstance(props);
  }
  
  public void sendMessage(Message msg)
                                       throws Exception{

    Transport transport = session.getTransport("smtp"); 
    //����SMTP������
    transport.connect(sendHost, username, accessCode); 
		  
    //�����ʼ�
    transport.sendMessage(msg,msg.getAllRecipients());
    System.out.println("�ʼ��������");
  }
}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;

public class MailClientFor126 extends MailClient{
  private String sendHost="smtp.126.com";
  private String receiveHost="pop.126.com";
  private String receiveProtocol="pop3";
  private String username = "java_mailtest";
  private String accessCode="access1234"; //��Ȩ��
  private String fromAddr="java_mailtest@126.com";  //�����ߵ�ַ
  private String toAddr="javathinker_mail@sina.com"; //�����ߵ�ַ

  public void init()throws Exception{
    //��������
    Properties  props = new Properties();
    props.put("mail.smtp.host", sendHost);
    props.put("mail.smtp.auth","true"); //SMTP��������Ҫ�����֤
  
    session = Session.getDefaultInstance(props);

    // ����Store����
    store = session.getStore(receiveProtocol);
    //���ӵ��ʼ��������ϵ��˻�
    store.connect(receiveHost,username, accessCode);
  }
  
  public void sendMessage(String fromAddr,String toAddr)
                                       throws Exception{
    //����һ���ʼ�
    Message msg = createSimpleMessage(fromAddr,toAddr);

    Transport transport = session.getTransport("smtp"); 
    //����SMTP������
    transport.connect(sendHost, username, accessCode); 
		  
    //�����ʼ�
    transport.sendMessage(msg,msg.getAllRecipients());
    System.out.println("�ʼ��������");
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
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;

public class MailClientSendAttach extends MailClientFor126{
  private String fromAddr="java_mailtest@126.com";  //�����ߵ�ַ
  private String toAddr="javathinker_mail@sina.com"; //�����ߵ�ַ
  private String sendHost="smtp.126.com";
  private String username = "java_mailtest";
  private String accessCode="access1234"; //��Ȩ��

  public void sendMessage(String fromAddr,String toAddr) throws Exception{
    //����һ���ʼ�
    Message msg = new MimeMessage(session);
    InternetAddress[] toAddrs =InternetAddress.parse(toAddr, false);
    msg.setRecipients(Message.RecipientType.TO, toAddrs);
    msg.setSubject("This is a test mail.");
    msg.setSentDate(new Date());
    msg.setFrom(new InternetAddress(fromAddr));

    //���¸����ٶ�λ�ڵ�ǰĿ¼�£�Ҳ���Ը��������ľ����ļ�·����
    //����ȷ����ǰĿ¼�´�������������������������л��׳���ָ���쳣
    String attch1="attch1.rar";  //��ӵĸ���1
    String attch2="attch2.rar";  //��ӵĸ���2

    MimeMultipart multipart=new MimeMultipart();
    
    //�����ı�����
    MimeBodyPart mimeBodyPart1=new MimeBodyPart(); 
    mimeBodyPart1.setText("How are you");
    multipart.addBodyPart(mimeBodyPart1); 
    
    //�����һ������
    MimeBodyPart mimeBodyPart2=new MimeBodyPart(); 
    FileDataSource fds=new FileDataSource(attch1); //�õ�����Դ 
    DataHandler handler=new DataHandler(fds);
    mimeBodyPart2.setDataHandler(handler); 
    mimeBodyPart2.setDisposition(Part.ATTACHMENT);
    mimeBodyPart2.setFileName(handler.getName()); //�����ļ���
    multipart.addBodyPart(mimeBodyPart2); 

    //����ڶ�������
    MimeBodyPart mimeBodyPart3=new MimeBodyPart(); 
    fds=new FileDataSource(attch2); //�õ�����Դ 
    handler=new DataHandler(fds);
    mimeBodyPart3.setDataHandler(handler); 
    mimeBodyPart3.setDisposition(Part.ATTACHMENT);
    mimeBodyPart3.setFileName(handler.getName()); //�����ļ���
    multipart.addBodyPart(mimeBodyPart3); 

    multipart.setSubType("mixed");

     //�����ʼ�������
    msg.setContent(multipart); 
    
    Transport transport = session.getTransport("smtp"); 
    //����SMTP������
    transport.connect(sendHost, username, accessCode); 
		  
    //�����ʼ�
    transport.sendMessage(msg,msg.getAllRecipients());
    System.out.println("�ʼ��������");
  }
  
  public static void main(String[] args)throws Exception {
    MailClientSendAttach client=new MailClientSendAttach();
    client.init();
    client.sendMessage(client.fromAddr,client.toAddr);
    client.receiveMessage();
    client.close();
  }
}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/

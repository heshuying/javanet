import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;

public class MailClientSendAttach extends MailClientFor126{
  private String fromAddr="java_mailtest@126.com";  //发送者地址
  private String toAddr="javathinker_mail@sina.com"; //接收者地址
  private String sendHost="smtp.126.com";
  private String username = "java_mailtest";
  private String accessCode="access1234"; //授权码

  public void sendMessage(String fromAddr,String toAddr) throws Exception{
    //创建一个邮件
    Message msg = new MimeMessage(session);
    InternetAddress[] toAddrs =InternetAddress.parse(toAddr, false);
    msg.setRecipients(Message.RecipientType.TO, toAddrs);
    msg.setSubject("This is a test mail.");
    msg.setSentDate(new Date());
    msg.setFrom(new InternetAddress(fromAddr));

    //以下附件假定位于当前目录下，也可以给定附件的绝对文件路径。
    //必须确保当前目录下存在这两个附件，否则程序运行会抛出空指针异常
    String attch1="attch1.rar";  //添加的附件1
    String attch2="attch2.rar";  //添加的附件2

    MimeMultipart multipart=new MimeMultipart();
    
    //加入文本内容
    MimeBodyPart mimeBodyPart1=new MimeBodyPart(); 
    mimeBodyPart1.setText("How are you");
    multipart.addBodyPart(mimeBodyPart1); 
    
    //加入第一个附件
    MimeBodyPart mimeBodyPart2=new MimeBodyPart(); 
    FileDataSource fds=new FileDataSource(attch1); //得到数据源 
    DataHandler handler=new DataHandler(fds);
    mimeBodyPart2.setDataHandler(handler); 
    mimeBodyPart2.setDisposition(Part.ATTACHMENT);
    mimeBodyPart2.setFileName(handler.getName()); //设置文件名
    multipart.addBodyPart(mimeBodyPart2); 

    //加入第二个附件
    MimeBodyPart mimeBodyPart3=new MimeBodyPart(); 
    fds=new FileDataSource(attch2); //得到数据源 
    handler=new DataHandler(fds);
    mimeBodyPart3.setDataHandler(handler); 
    mimeBodyPart3.setDisposition(Part.ATTACHMENT);
    mimeBodyPart3.setFileName(handler.getName()); //设置文件名
    multipart.addBodyPart(mimeBodyPart3); 

    multipart.setSubType("mixed");

     //设置邮件的正文
    msg.setContent(multipart); 
    
    Transport transport = session.getTransport("smtp"); 
    //连接SMTP服务器
    transport.connect(sendHost, username, accessCode); 
		  
    //发送邮件
    transport.sendMessage(msg,msg.getAllRecipients());
    System.out.println("邮件发送完毕");
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
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

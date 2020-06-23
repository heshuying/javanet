package exercise;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MailGui extends JFrame {
  MailUtil mailUtil;

  //�������Ҫ�������
  protected Container contentPane;

  protected JPanel northPanel=new JPanel();
  protected JPanel sendPanel=new JPanel();
  protected JPanel toPanel=new JPanel();
  protected JPanel titlePanel=new JPanel();
  protected JPanel southPanel=new JPanel();

  protected JLabel sendLabel=new JLabel("������");
  protected JLabel toLabel=new JLabel("������");
  protected JLabel titleLabel=new JLabel("����");
  
  protected JTextField sendTextField=new JTextField("java_mailtest@126.com");
  protected JTextField toTextField=new JTextField("javathinker_mail@sina.com");
  protected JTextField titleTextField=new JTextField(40);

  protected JTextArea msgTextArea=new JTextArea(9,50);
  protected JScrollPane msgSp=new JScrollPane(msgTextArea);
  
  protected JButton attachButton=new JButton("�ϴ�����");
  protected JLabel attachLabel=new JLabel();

  protected JButton sendButton=new JButton("�����ʼ�");
  protected JLabel sendResultLabel=new JLabel();
  protected File attachFile;

  /** ���췽�� */
  public MailGui(){
    super("�����ʼ�");
    try{
      mailUtil=new MailUtil();
      mailUtil.init();
    }catch(Exception e){
       e.printStackTrace();
       return;
    }
    buildDisplay();
  }
  
  /** ����ͼ�ν��� */
  private void buildDisplay(){
   
    contentPane=getContentPane();
    
    sendPanel.add(sendLabel);
    sendPanel.add(sendTextField);
    toPanel.add(toLabel);
    toPanel.add(toTextField);
    titlePanel.add(titleLabel);
    titlePanel.add(titleTextField);
    ((FlowLayout)sendPanel.getLayout()).setAlignment(FlowLayout.LEFT);
    ((FlowLayout)toPanel.getLayout()).setAlignment(FlowLayout.LEFT);
    ((FlowLayout)titlePanel.getLayout()).setAlignment(FlowLayout.LEFT);
    northPanel.setLayout(new GridLayout(3,1));
    northPanel.add(sendPanel);
    northPanel.add(toPanel);
    northPanel.add(titlePanel);
    southPanel.add(attachButton);
    southPanel.add(attachLabel);
    southPanel.add(sendButton);
    southPanel.add(sendResultLabel);
   
    contentPane.add(northPanel,BorderLayout.NORTH);
    contentPane.add(msgSp,BorderLayout.CENTER);
    contentPane.add(southPanel,BorderLayout.SOUTH);
    
    addListener();

    setSize(500,300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
  
  public Message getMessage()throws Exception{
     //����һ���ʼ�
     Message msg = new MimeMessage(mailUtil.session);
    InternetAddress[] toAddrs =InternetAddress.parse(toTextField.getText(), false);
    msg.setRecipients(Message.RecipientType.TO, toAddrs);
    msg.setSubject(titleTextField.getText());
    msg.setSentDate(new Date());
    msg.setFrom(new InternetAddress(sendTextField.getText()));

    MimeMultipart multipart=new MimeMultipart();
    
    //�����ı�����
    MimeBodyPart mimeBodyPart1=new MimeBodyPart(); 
    mimeBodyPart1.setText("How are you");
    multipart.addBodyPart(mimeBodyPart1); 
    if(attachFile!=null){    
      //���븽��
      MimeBodyPart mimeBodyPart=new MimeBodyPart(); 
      FileDataSource fds=new FileDataSource(attachFile); //�õ�����Դ 
      DataHandler handler=new DataHandler(fds);
      mimeBodyPart.setDataHandler(handler); 
      mimeBodyPart.setDisposition(Part.ATTACHMENT);
      mimeBodyPart.setFileName(handler.getName()); //�����ļ���
      multipart.addBodyPart(mimeBodyPart); 

      multipart.setSubType("mixed");
    }
     //�����ʼ�������
    msg.setContent(multipart); 
    return msg;
  } 

  public void addListener(){
    sendButton.addActionListener(new SendHandler());
    attachButton.addActionListener(new AttachHandler());
  }

  class AttachHandler implements ActionListener{
     public void actionPerformed(ActionEvent event){
       JFileChooser jc = new JFileChooser();
       int rVal = jc.showOpenDialog(MailGui.this); //��ʾ���ļ��ĶԻ���
       if(rVal == JFileChooser.APPROVE_OPTION) {
          File dir=jc.getCurrentDirectory();
          File file=jc.getSelectedFile();
          try{
            attachFile=new File(dir,file.getName());
            attachLabel.setText(attachFile.getCanonicalPath());
          }catch(IOException e){attachLabel.setText(e.getMessage());}
        }
     }
  }

  class SendHandler implements ActionListener{
     public void actionPerformed(ActionEvent event){
       try{
         mailUtil.sendMessage(getMessage()); 
         sendResultLabel.setText("�ʼ��ѷ��͡�");
       }catch(Exception e){
         e.printStackTrace(); 
         sendResultLabel.setText("�ʼ�����ʧ�ܡ�");
       }
     }
  }

  public static void main(String args[]){
     new MailGui();
  }
  
}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/

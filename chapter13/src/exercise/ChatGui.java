package exercise;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

public class ChatGui {
  
  //�������Ҫ�������
  protected JFrame frame;
  protected Container contentPane;
  protected JTextArea msgTextArea=new JTextArea(9,50);
  protected JScrollPane msgSp=new JScrollPane(msgTextArea);
  
  protected JPanel bottomPanel=new JPanel();
  protected JComboBox<String> clientsComboBox=new JComboBox<String>();
  protected JTextField textField=new JTextField(30);
  protected JButton sendButton=new JButton("����");
  
  /** ��ʾ��ˢ��������Ϣ */
  public void refreshChatMsg(String msg){
      msgTextArea.append(msg+"\r\n");
  }

  /** ��ʾ��ˢ�¿ͻ��б� */
  public void refreshAllClients(String[] names){
      clientsComboBox.removeAllItems();

      for(String name:names)
         clientsComboBox.addItem(name);
   
      clientsComboBox.addItem("all");
  }
 
  /** ����û��������Ϣ*/
  public String getMessage(){
      return textField.getText();
   }
  
  /** �����Ϣ������ */
  public String getToClient(){
      return (String)clientsComboBox.getSelectedItem();
   }


  /** ���췽�� */
  public ChatGui(String client){
    buildDisplay(client);
  }
  
  /** ����ͼ�ν��� */
  private void buildDisplay(String client){
    frame=new JFrame(client);
    contentPane=frame.getContentPane();
    contentPane.add(msgSp,BorderLayout.CENTER);
   
    bottomPanel.add(clientsComboBox);
    bottomPanel.add(textField);
    bottomPanel.add(sendButton);
    contentPane.add(bottomPanel,BorderLayout.SOUTH);

    msgTextArea.setEditable(false);

    frame.setSize(500,300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
 

  /** Ϊ���Ͱ�ť�Լ��ı���ע������� */
  public void addSendButtonListener(ActionListener actionListener){
    sendButton.addActionListener(actionListener);
    textField.addActionListener(actionListener);
  }
  
}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/

package exercise;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

public class ChatGui {
  
  //界面的主要窗体组件
  protected JFrame frame;
  protected Container contentPane;
  protected JTextArea msgTextArea=new JTextArea(9,50);
  protected JScrollPane msgSp=new JScrollPane(msgTextArea);
  
  protected JPanel bottomPanel=new JPanel();
  protected JComboBox<String> clientsComboBox=new JComboBox<String>();
  protected JTextField textField=new JTextField(30);
  protected JButton sendButton=new JButton("发送");
  
  /** 显示并刷新聊天消息 */
  public void refreshChatMsg(String msg){
      msgTextArea.append(msg+"\r\n");
  }

  /** 显示并刷新客户列表 */
  public void refreshAllClients(String[] names){
      clientsComboBox.removeAllItems();

      for(String name:names)
         clientsComboBox.addItem(name);
   
      clientsComboBox.addItem("all");
  }
 
  /** 获得用户输入的消息*/
  public String getMessage(){
      return textField.getText();
   }
  
  /** 获得消息接受者 */
  public String getToClient(){
      return (String)clientsComboBox.getSelectedItem();
   }


  /** 构造方法 */
  public ChatGui(String client){
    buildDisplay(client);
  }
  
  /** 创建图形界面 */
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
 

  /** 为发送按钮以及文本框注册监听器 */
  public void addSendButtonListener(ActionListener actionListener){
    sendButton.addActionListener(actionListener);
    textField.addActionListener(actionListener);
  }
  
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

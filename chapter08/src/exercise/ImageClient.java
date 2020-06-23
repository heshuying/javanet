package exercise;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class ImageClient extends JFrame implements  Runnable, ActionListener {
  public static final int PORT=8899;
  JButton b=new JButton("显示图片");
  ImagePanel imagePanel;
  public ImageClient() {
      super("图片展示");
      b.addActionListener(this);
      Container container=getContentPane();
 
      container.add(b,BorderLayout.NORTH);
      imagePanel=new ImagePanel();
      container.add(imagePanel,BorderLayout.CENTER);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(600,600);
      setVisible(true);
  }

  public void actionPerformed(ActionEvent event){
       byte b[]="please send picture".trim().getBytes();
       try{
         InetAddress address=InetAddress.getByName("localhost");
         DatagramPacket data=new DatagramPacket(b,b.length,address,ImageServer.PORT);
         DatagramSocket mailSend=new DatagramSocket();
         mailSend.send(data);
         System.out.println("客户端请求获取图片");

         Thread thread=new Thread(this);
         thread.start();         
       } catch(Exception e){
         e.printStackTrace();
       }
  }

  public void run() {
     DatagramPacket pack=null;
     DatagramSocket clientSocket=null;
     byte b[]=new byte[8192];
     ByteArrayOutputStream out=new ByteArrayOutputStream();
     try {
       pack=new DatagramPacket(b,b.length);
       clientSocket=new DatagramSocket(PORT);
     }catch(Exception e){
          e.printStackTrace();
    }

    try {
       while(true){
         clientSocket.receive(pack);
         String message=new String(pack.getData(),0,pack.getLength());
         if(message.startsWith("end")) {
           System.out.println("图片数据接收完毕");
           break;
         }
         out.write(pack.getData(),0,pack.getLength());
         System.out.println("正在接收图片数据");
      }
      byte imagebyte[]=out.toByteArray();
      out.close();
      Toolkit tool=getToolkit();
      Image image=tool.createImage(imagebyte);
      imagePanel.setImage(image);
      imagePanel.repaint();
      validate();

   }catch(IOException e){
       e.printStackTrace();
    }finally{
      try{   clientSocket.close(); }catch(Exception e){}
    }
  }

   public static void main(String args[]) {
     new ImageClient();
   }

}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

package gui;
import javax.swing.text.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.channels.*;
import java.util.List;

public class AsynEchoClient extends JFrame implements ActionListener{
  private JLabel clientLabel=new JLabel("客户端输入内容：");
  private JTextField clientTextField=new JTextField();
  private JLabel serverLabel=new JLabel("服务器返回的响应结果");
  private JTextPane serverTextPane=new JTextPane();
 
  private SocketChannel socketChannel = null;
 
  public AsynEchoClient(String title){
    super(title);
   
    clientTextField.addActionListener(this);
    serverTextPane.setEditable(false);

    JScrollPane serverScrollPane=new JScrollPane(serverTextPane);

    JPanel clientPanel=new JPanel();
    clientPanel.setLayout(new BorderLayout());
    clientPanel.add(clientLabel,BorderLayout.NORTH);
    clientPanel.add(clientTextField,BorderLayout.SOUTH);

    JPanel serverPanel=new JPanel();
    serverPanel.setLayout(new BorderLayout());
    serverPanel.add(serverLabel,BorderLayout.NORTH);
    serverPanel.add(serverScrollPane,BorderLayout.CENTER);

    Container container=getContentPane();

    container.add(clientPanel,BorderLayout.NORTH);
    container.add(serverPanel,BorderLayout.CENTER);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500,300);
    setVisible(true);
    
    connect();    //连接服务器
  }
    
  public void connect(){  
    try{   //连接服务器
      socketChannel = SocketChannel.open();
      InetAddress ia = InetAddress.getLocalHost();
      InetSocketAddress isa = new InetSocketAddress(ia,8000);
      socketChannel.connect(isa);
      setServerTextPane("与服务器的连接建立成功");
   }catch(Exception e){
       setServerTextPane("与服务器连接失败");
   }
  }

  public void setServerTextPane(String text){
      serverTextPane.setText(serverTextPane.getText()+"\r\n"+text);
  }

  public static void main(String[] args){
    //向EDT线程提交创建AsynEchoClient任务
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
           AsynEchoClient echoClient=new AsynEchoClient("EchoClient");
        } catch (Exception e) {
           e.printStackTrace();
        }
      }
    });
  }

  private PrintWriter getWriter(Socket socket)throws IOException{
     OutputStream socketOut = socket.getOutputStream();
     return new PrintWriter(socketOut,true);
  }

  private BufferedReader getReader(Socket socket)throws IOException{
     InputStream socketIn = socket.getInputStream();
     return new BufferedReader(new InputStreamReader(socketIn));
  }

  public String talk()throws IOException {
       BufferedReader br=getReader(socketChannel.socket());
       PrintWriter pw=getWriter(socketChannel.socket());
       String msg=clientTextField.getText();
       pw.println(msg);
       return br.readLine();
  }

  /* 处理用户在clientTextField文本框中回车的事件 */
  public void actionPerformed(ActionEvent evt){
     new ActionEventHandler().execute();
  }
  
  class ActionEventHandler extends SwingWorker<String, Void> {
        
    /*  后台任务（SwingWorker工作线程执行）: 接受服务器端的响应结果 */
    protected String doInBackground() throws Exception {
      return talk();
    }

    /* 前台任务（EDT线程执行）：显示服务器端的响应结果 */
    protected void done(){
       try{
         String result=get();
         setServerTextPane(result);
       }catch(Exception e){setServerTextPane(e.getMessage());}
    }
  }
}   


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

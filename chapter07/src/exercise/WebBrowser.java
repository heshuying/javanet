package exercise;
import javax.swing.text.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.html.*;
import java.beans.*;
import java.util.List;
import java.util.LinkedList;

public class WebBrowser extends JFrame implements HyperlinkListener,ActionListener{
  private JLabel progressLabel=new JLabel();
  private JTextField jtf=new JTextField(40); 
  private JEditorPane jep=new JEditorPane();
  private String initialPage="http://www.javathinker.net/helloapp/index.htm";
  private String currentURL="";

  public WebBrowser(String title){
    super(title);
   
    jtf.setText(initialPage);
    jtf.addActionListener(this);
    jep.setEditable(false);
    jep.setContentType("text/html"); 
    jep.addHyperlinkListener(this);
   
    HTMLEditorKit editorKit=new HTMLEditorKit();
    jep.setEditorKit(editorKit);
    //改为由程序来手工处理提交表单的FormSubmitEvent事件
    editorKit.setAutoFormSubmission(false);

    JScrollPane scrollPane=new JScrollPane(jep);
    Container container=getContentPane();
    JPanel northPanel=new JPanel();
    northPanel.setLayout(new BorderLayout());
    northPanel.add(jtf,BorderLayout.CENTER);
    northPanel.add(progressLabel,BorderLayout.EAST);
      
    container.add(northPanel,BorderLayout.NORTH);
    container.add(scrollPane,BorderLayout.CENTER);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500,300);
    setVisible(true);
    setPage(initialPage,null);
  }
    
  public void showError(String urlStr){
     setPage("http://www.javathinker.net/helloapp/error.htm",null);
      jtf.setText(urlStr);
   }

  public static void main(String[] args){

    //向EDT线程提交创建WebBrowser任务
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
           WebBrowser browser=new WebBrowser("Web Browser");
        } catch (Exception e) {
           e.printStackTrace();
        }
      }
    });
  }

  public void testURL(String url)throws Exception{
     try{
       System.out.println("Visiting URL:"+url);
       new URL(url).openStream().close();
     }catch(Exception e){
        e.printStackTrace();
        throw new Exception("无效的URL");  
     }
  }  
 
  public void dealWithActionEvent(){
    setPage(jtf.getText(),null);
  }

  public void dealWithHyperlinkEvent(HyperlinkEvent evt){

    if (evt.getClass() == FormSubmitEvent.class) {  //处理提交表单事件
      FormSubmitEvent fevt = (FormSubmitEvent)evt;
      URL url=fevt.getURL(); //获得URL
      String method=fevt.getMethod().toString(); //获得请求方式
      String data=fevt.getData(); //获得表单数据

      if(method.equals("GET")){  //如果为GET请求方式
         setPage(url.toString()+"?"+data,null);
      }else if(method.equals("POST")){  //如果为POST请求方式
         setPage(url.toString(),data);
      }
    }else if(evt.getEventType()==HyperlinkEvent.EventType.ACTIVATED){  
      setPage(evt.getURL().toString(),null);
    } 
  }

  /* 展示HTML页面，参数content表示POST方式下的正文
    * 如果为GET方式，参数content为null 
    */
  public void setPage(String urlStr,String content){
    jtf.setText(urlStr); //把文本框设为用户选择的超级链接  
    try{
      testURL(urlStr);

      URL url=new URL(urlStr);
      URLConnection uc=url.openConnection(); 
      if(content!=null){ 
        //发送HTTP响应正文
        uc.setDoOutput(true);
        OutputStreamWriter out=new OutputStreamWriter(uc.getOutputStream()); 
        out.write(content);
        out.close();
      }
      jep.setPage(url);
    }catch(Exception e){
       showError(urlStr);
    }
  } 

  /* 处理用户在文本框输入URL的事件 */
  public void actionPerformed(ActionEvent evt){
     dealWithNewEvent(new ActionEventHandler(jtf.getText()),jtf.getText());
  }

  /** 处理用户选择超级链接或者提交表单事件 */
  public void hyperlinkUpdate(HyperlinkEvent evt){
    if(evt.getEventType()==HyperlinkEvent.EventType.ACTIVATED ||
                     evt.getClass() == FormSubmitEvent.class ) {
       dealWithNewEvent(new HyperlinkEventHandler(evt),evt.getURL().toString());
    }
  }  

  public void  dealWithNewEvent(SwingWorker handler,String url){
    currentURL=url;
    handler.execute();  //执行最新发生的任务
  }
  
  class ActionEventHandler extends SwingWorker<Void, String> {
    private String urlAddr;
    public ActionEventHandler(String urlAddr){
        this.urlAddr=urlAddr;
    }   
    /*  后台任务: 加载特定的网页 */
    protected Void doInBackground() throws Exception {
      if(!(urlAddr.equals(currentURL)) )
        return null;  
      publish("正在加载中......  ");
      dealWithActionEvent();
      return null;
    }
    
    /* 前台任务：显示加载进度信息  */
    protected void process(List<String> chunks) {
      if(!(urlAddr.equals(currentURL)) )
        return ;  
      progressLabel.setText(chunks.get(chunks.size()-1));
    }

    protected void done(){
      if(!(urlAddr.equals(currentURL)) )
        return ;  

      publish(""); 
    }
  }

  class HyperlinkEventHandler extends SwingWorker<Void, String> {
    HyperlinkEvent evt;
    String urlAddr;
    
    public  HyperlinkEventHandler(HyperlinkEvent evt){
       this.evt=evt;
       urlAddr=evt.getURL().toString();
    }    

    /*  后台任务: 加载特定的网页 */
    protected Void doInBackground() throws Exception {
      if(!(urlAddr.equals(currentURL)) )
        return null;  

      publish("正在加载中......  ");
      dealWithHyperlinkEvent(evt);  
      return null;
    }
    
    /* 前台任务：显示加载进度信息  */
    protected void process(List<String> chunks) {
      if(!(urlAddr.equals(currentURL)) )
        return ;  

      progressLabel.setText(chunks.get(chunks.size()-1));
    }

    protected void done(){
      if(!(urlAddr.equals(currentURL)) )
        return ;  

      if(evt.getEventType()==HyperlinkEvent.EventType.ACTIVATED ||
           evt.getClass() == FormSubmitEvent.class ) {
        publish(""); 
     }
    }
  }
}   


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *

 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

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
    //��Ϊ�ɳ������ֹ������ύ����FormSubmitEvent�¼�
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

    //��EDT�߳��ύ����WebBrowser����
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
        throw new Exception("��Ч��URL");  
     }
  }  
 
  public void dealWithActionEvent(){
    setPage(jtf.getText(),null);
  }

  public void dealWithHyperlinkEvent(HyperlinkEvent evt){

    if (evt.getClass() == FormSubmitEvent.class) {  //�����ύ���¼�
      FormSubmitEvent fevt = (FormSubmitEvent)evt;
      URL url=fevt.getURL(); //���URL
      String method=fevt.getMethod().toString(); //�������ʽ
      String data=fevt.getData(); //��ñ�����

      if(method.equals("GET")){  //���ΪGET����ʽ
         setPage(url.toString()+"?"+data,null);
      }else if(method.equals("POST")){  //���ΪPOST����ʽ
         setPage(url.toString(),data);
      }
    }else if(evt.getEventType()==HyperlinkEvent.EventType.ACTIVATED){  
      setPage(evt.getURL().toString(),null);
    } 
  }

  /* չʾHTMLҳ�棬����content��ʾPOST��ʽ�µ�����
    * ���ΪGET��ʽ������contentΪnull 
    */
  public void setPage(String urlStr,String content){
    jtf.setText(urlStr); //���ı�����Ϊ�û�ѡ��ĳ�������  
    try{
      testURL(urlStr);

      URL url=new URL(urlStr);
      URLConnection uc=url.openConnection(); 
      if(content!=null){ 
        //����HTTP��Ӧ����
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

  /* �����û����ı�������URL���¼� */
  public void actionPerformed(ActionEvent evt){
     dealWithNewEvent(new ActionEventHandler(jtf.getText()),jtf.getText());
  }

  /** �����û�ѡ�񳬼����ӻ����ύ���¼� */
  public void hyperlinkUpdate(HyperlinkEvent evt){
    if(evt.getEventType()==HyperlinkEvent.EventType.ACTIVATED ||
                     evt.getClass() == FormSubmitEvent.class ) {
       dealWithNewEvent(new HyperlinkEventHandler(evt),evt.getURL().toString());
    }
  }  

  public void  dealWithNewEvent(SwingWorker handler,String url){
    currentURL=url;
    handler.execute();  //ִ�����·���������
  }
  
  class ActionEventHandler extends SwingWorker<Void, String> {
    private String urlAddr;
    public ActionEventHandler(String urlAddr){
        this.urlAddr=urlAddr;
    }   
    /*  ��̨����: �����ض�����ҳ */
    protected Void doInBackground() throws Exception {
      if(!(urlAddr.equals(currentURL)) )
        return null;  
      publish("���ڼ�����......  ");
      dealWithActionEvent();
      return null;
    }
    
    /* ǰ̨������ʾ���ؽ�����Ϣ  */
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

    /*  ��̨����: �����ض�����ҳ */
    protected Void doInBackground() throws Exception {
      if(!(urlAddr.equals(currentURL)) )
        return null;  

      publish("���ڼ�����......  ");
      dealWithHyperlinkEvent(evt);  
      return null;
    }
    
    /* ǰ̨������ʾ���ؽ�����Ϣ  */
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
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *

 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/

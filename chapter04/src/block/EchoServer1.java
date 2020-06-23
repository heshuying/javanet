/** ��������ʾ������ģʽ�£�����SocketChannel�ͻ���������ȡһ������ */
package block;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class EchoServer1 {
  private int port=8000;
  private ServerSocketChannel serverSocketChannel = null;
  private ExecutorService executorService;  //�̳߳�
  private static final int POOL_MULTIPLE = 4;  //�̳߳��й����̵߳���Ŀ
  
  public EchoServer1() throws IOException {
    //����һ���̳߳�
    executorService= Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors() * POOL_MULTIPLE); 
    //����һ��ServerSocketChannel����
    serverSocketChannel= ServerSocketChannel.open();
    //ʹ����ͬһ�������Ϲر��˷��������򣬽������������÷���������ʱ��
    //����˳������ͬ�Ķ˿�
    serverSocketChannel.socket().setReuseAddress(true);
    //�ѷ�����������һ�����ض˿ڰ�
    serverSocketChannel.socket().bind(new InetSocketAddress(port));
    System.out.println("����������");
  }

  public void service() {
    while (true) {
      SocketChannel socketChannel=null;
      try {
        socketChannel = serverSocketChannel.accept();
         //����ͻ�����   
        executorService.execute(new Handler(socketChannel));  
      }catch (IOException e) {
         e.printStackTrace();
      }
    }
  }
 
  public static void main(String args[])throws IOException {
    new EchoServer1().service();
  }
}

class Handler implements Runnable{  //����ͻ�����
  private SocketChannel socketChannel;
  public Handler(SocketChannel socketChannel){
    this.socketChannel=socketChannel;
  }
  public void run(){
    handle(socketChannel);
  }
   
  public void handle(SocketChannel socketChannel){
    try {
        //�����socketChannel������Socket����
        Socket socket=socketChannel.socket();  
        System.out.println("���յ��ͻ����ӣ�����: " +
        socket.getInetAddress() + ":" +socket.getPort());
   
        BufferedReader br =getReader(socket);
        PrintWriter pw = getWriter(socket);

        String msg = null;
        while ((msg = readLine(socketChannel)) != null) {
          System.out.println(msg);
          pw.println(echo(msg));
          if (msg.equals("bye"))
            break;
        }
      }catch (IOException e) {
         e.printStackTrace();
      }finally {
         try{
           if(socketChannel!=null)socketChannel.close();
         }catch (IOException e) {e.printStackTrace();}
      }  
  }
  private PrintWriter getWriter(Socket socket)throws IOException{
    OutputStream socketOut = socket.getOutputStream();
    return new PrintWriter(socketOut,true);
  }
  private BufferedReader getReader(Socket socket)throws IOException{
    InputStream socketIn = socket.getInputStream();
    return new BufferedReader(new InputStreamReader(socketIn));
  }
  
  public String echo(String msg) {
    return "echo:" + msg;
  }


  public String readLine(SocketChannel socketChannel)throws IOException{
    //������ж��������ݣ��ٶ�һ���ַ�����Ӧ���ֽ����еĳ���С��1024
    ByteBuffer buffer=ByteBuffer.allocate(1024);
    //���һ�ζ��������ݣ�һ��ֻ��һ���ֽ�
    ByteBuffer tempBuffer=ByteBuffer.allocate(1);
    boolean isLine=false;  //��ʾ�Ƿ������һ���ַ���
    boolean isEnd=false;  //��ʾ�Ƿ񵽴�����������ĩβ
    String data=null;
    while(!isLine && !isEnd){
      tempBuffer.clear();  //��ջ�����
      //������ģʽ�£�ֻ�еȶ�����1���ֽڻ��߶���������ĩβ�ŷ���
      //�ڷ�����ģʽ�£��п��ܷ����� 
      int n=socketChannel.read(tempBuffer);  
      if(n==-1){
        isEnd = true;  //������������ĩβ
        break;
      }
      if(n==0)
        continue;
      tempBuffer.flip();  //�Ѽ�����Ϊλ�ã���λ����Ϊ0
      buffer.put(tempBuffer); //��tempBuffer�е����ݿ�����buffer��
      buffer.flip();
      Charset charset=Charset.forName("GBK");
      CharBuffer charBuffer=charset.decode(buffer);  //����
      data=charBuffer.toString();
      if(data.indexOf("\r\n")!=-1){
        isLine = true; //������һ���ַ���
        data=data.substring(0,data.indexOf("\r\n"));  
        break;
      }
      buffer.position(buffer.limit());  //��λ����Ϊ���ޣ�Ϊ�´ζ�������׼��
      buffer.limit(buffer.capacity());  //�Ѽ�����Ϊ������Ϊ�´ζ�������׼��    
    }//#while
    //���������һ���ַ������ͷ��������ַ������������н�������\r\n��
    //���������������ĩβ���ͷ���null
    return data;
  }

}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/


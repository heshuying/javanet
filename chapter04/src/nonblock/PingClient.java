package nonblock;
import java.net.*;
import java.nio.channels.*;
import java.nio.*;
import java.io.*;
import java.util.concurrent.*;
import java.nio.charset.*;
import java.util.*;

class PingResult {  //��ʾ����һ�������Ľ��
  InetSocketAddress address;
  long connectStart;  //��ʼ����ʱ��ʱ��
  long connectFinish = 0;  //���ӳɹ�ʱ��ʱ��
  String failure;
  Future<Void> connectResult;  //���Ӳ������첽������
  AsynchronousSocketChannel socketChannel;
  String host;
  final String ERROR="����ʧ��";

  PingResult(String host) {
      try {
          this.host=host;
          address = 
              new InetSocketAddress(InetAddress.getByName(host),80);
      } catch (IOException x) {
          failure = ERROR;
      }
  }  

  public void print() {  //��ӡ����һ��������ִ�н��
      String result;
      if (connectFinish != 0)
          result = Long.toString(connectFinish - connectStart) + "ms";
      else if (failure != null)
          result = failure;
      else
          result = "Timed out";
      System.out.println("ping "+ host+"�Ľ��" + " : " + result);
  }
}

public class PingClient{
  //�������PingResult����Ķ���
  private LinkedList<PingResult> pingResults=
               new LinkedList<PingResult>();
  boolean shutdown=false;
  ExecutorService executorService;

  public PingClient()throws IOException{
    executorService= Executors.newFixedThreadPool(4);
    executorService.execute(new Printer());
    receivePingAddress();
  }

  public static void main(String args[])throws IOException{
    new PingClient();
  }
  
  /* �����û������������ַ�����̳߳�ִ��PingHandler���� */   
  public void receivePingAddress(){
    try{
      BufferedReader localReader=new BufferedReader(
                    new InputStreamReader(System.in));
      String msg=null;
      //�����û������������ַ
      while((msg=localReader.readLine())!=null){
        if(msg.equals("bye")){
          shutdown=true;
          executorService.shutdown();
          break; 
        }
        executorService.execute(new PingHandler(msg));
      }
    }catch(IOException e){ }
  }
  
  /* ���������ض����������Ұ����������뵽PingResults��������� */
  public void addPingResult(PingResult pingResult) {
     AsynchronousSocketChannel socketChannel = null;
     try {
       socketChannel = AsynchronousSocketChannel.open();
        
       pingResult.socketChannel=socketChannel;
       pingResult.connectStart = System.currentTimeMillis();

       synchronized (pingResults) {
         //��pingResults�����м���һ��PingResult����
         pingResults.add(pingResult);
         pingResults.notify();
       }

       Future<Void> connectResult=
           socketChannel.connect(pingResult.address);
       pingResult.connectResult = connectResult;
    }catch (Exception x) {
      if (socketChannel != null) {
        try {
          socketChannel.close();
        } catch (IOException e) {}
      }
      pingResult.failure = pingResult.ERROR;
    }
  }

  /* ��ӡPingResults����������Ѿ�ִ����ϵ�����Ľ�� */
  public void printPingResults() {
    PingResult pingResult = null;
    while(!shutdown ){
      synchronized (pingResults) {
        while (!shutdown && pingResults.size() == 0 ){
          try{
            pingResults.wait(100);
          }catch(InterruptedException e){e.printStackTrace();}
        }

        if(shutdown  && pingResults.size() == 0 )break;

        pingResult=pingResults.getFirst();
              
        try{
          if(pingResult.connectResult!=null) 
            pingResult.connectResult.get(500,TimeUnit.MILLISECONDS);
        }catch(Exception e){
            pingResult.failure= pingResult.ERROR;
        }

        if(pingResult.connectResult!=null 
           && pingResult.connectResult.isDone()){

          pingResult.connectFinish = System.currentTimeMillis();
        }
              
        if(pingResult.connectResult!=null 
           && pingResult.connectResult.isDone() 
           || pingResult.failure!=null){

           pingResult.print();
           pingResults.removeFirst();
           try {
              pingResult.socketChannel.close();
            } catch (IOException e) { }
         }
      }
    }
  }

  /* ���������ض�����������һ��PingResult����
     �������뵽PingResults��������� */
  public class PingHandler implements Runnable{
    String msg;
    public PingHandler(String msg){
        this.msg=msg;  
    }
    public void run(){
        if(!msg.equals("bye")){
          PingResult pingResult=new PingResult(msg);
          addPingResult(pingResult);
        }
    }
  }

  /* ��ӡPingResults����������Ѿ�ִ����ϵ�����Ľ�� */
  public class Printer implements Runnable{
    public void run(){
        printPingResults();
    }
  }
}



/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/

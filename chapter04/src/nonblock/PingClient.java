package nonblock;
import java.net.*;
import java.nio.channels.*;
import java.nio.*;
import java.io.*;
import java.util.concurrent.*;
import java.nio.charset.*;
import java.util.*;

class PingResult {  //表示连接一个主机的结果
  InetSocketAddress address;
  long connectStart;  //开始连接时的时间
  long connectFinish = 0;  //连接成功时的时间
  String failure;
  Future<Void> connectResult;  //连接操作的异步运算结果
  AsynchronousSocketChannel socketChannel;
  String host;
  final String ERROR="连接失败";

  PingResult(String host) {
      try {
          this.host=host;
          address = 
              new InetSocketAddress(InetAddress.getByName(host),80);
      } catch (IOException x) {
          failure = ERROR;
      }
  }  

  public void print() {  //打印连接一个主机的执行结果
      String result;
      if (connectFinish != 0)
          result = Long.toString(connectFinish - connectStart) + "ms";
      else if (failure != null)
          result = failure;
      else
          result = "Timed out";
      System.out.println("ping "+ host+"的结果" + " : " + result);
  }
}

public class PingClient{
  //存放所有PingResult结果的队列
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
  
  /* 接收用户输入的主机地址，由线程池执行PingHandler任务 */   
  public void receivePingAddress(){
    try{
      BufferedReader localReader=new BufferedReader(
                    new InputStreamReader(System.in));
      String msg=null;
      //接收用户输入的主机地址
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
  
  /* 尝试连接特定主机，并且把运算结果加入到PingResults结果队列中 */
  public void addPingResult(PingResult pingResult) {
     AsynchronousSocketChannel socketChannel = null;
     try {
       socketChannel = AsynchronousSocketChannel.open();
        
       pingResult.socketChannel=socketChannel;
       pingResult.connectStart = System.currentTimeMillis();

       synchronized (pingResults) {
         //向pingResults队列中加入一个PingResult对象
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

  /* 打印PingResults结果队列中已经执行完毕的任务的结果 */
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

  /* 尝试连接特定主机，生成一个PingResult对象，
     把它加入到PingResults结果队列中 */
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

  /* 打印PingResults结果队列中已经执行完毕的任务的结果 */
  public class Printer implements Runnable{
    public void run(){
        printPingResults();
    }
  }
}



/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

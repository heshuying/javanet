package exercise;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class FileServer{
  private int   port= 8000;
  private ServerSocket  serverSocket;
  private ExecutorService executorService;  //线程池
  private final int POOL_SIZE=4;  //单个CPU时线程池中工作线程的数目

   public FileServer() throws IOException   {
       serverSocket = new ServerSocket(port);
       executorService= Executors.newFixedThreadPool( 
               Runtime.getRuntime().availableProcessors() * POOL_SIZE);

        System.out.println("服务器启动");	
    }

    public void service() {
         while (true) {
             Socket socket=null;
             try {
                socket = serverSocket.accept();
                executorService.execute(new Handler(socket));
             }catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }  

    /* 接受客户端下载文件请求 */
    private void getFile(OutputStream socketoustream, String[] data) throws IOException {

      DataOutputStream socketout = new DataOutputStream(socketoustream);
      String filename =data[1];
      java.io.File file = new java.io.File("server//" + filename);

      if (!file.exists()){
        socketout.writeInt(0);// 文件不存在，直接写长度为0
        return;
      } else{
         String msgString = "ok:" + file.length();
         byte[] msgbytes = msgString.getBytes();
         System.out.println("server :Server Response '" + msgString + "' .");
         
         socketout.writeInt(msgbytes.length);// 写入消息长度
         socketout.write(msgbytes);// 写入消息内容
       }

      //载入服务端文件 
      FileInputStream fInputStream = new FileInputStream("server//" + filename);
   
      byte[] buff = new byte[1024];
      int len = -1;
      while ((len = fInputStream.read(buff)) != -1){//将服务端文件发送到客户端
            socketout.write(buff, 0, len);
      }
      fInputStream.close();
      System.out.println(String.format("server :finish sending file %1s !", filename));
      System.out.println();

   }
   
   /* 接受客户端上传文件请求 */
   private void putFile(InputStream socketIn, String[] data) throws IOException   {
     String filename = data[1];
     int length = Integer.parseInt(data[2]);		
     String path="server//" + filename;
     
     FileOutputStream fileOut = new FileOutputStream(path);
     byte[] buff = new byte[1024];
     
     int m = length / buff.length;
     int n = length % buff.length;
     int len = 0;
     for (int i = 0; i < m + 1; i++) {
        if (i == m){
        	len = socketIn.read(buff, 0, n);
        	fileOut.write(buff, 0, len);
        } else{
        	len = socketIn.read(buff);
        	fileOut.write(buff, 0, len);
        }
     }
     fileOut.close();
     System.out.println(String.format("server :finish receiving file %1s !", filename));
     System.out.println();
  }

  /* 以空格为单位分隔消息 */
  private String[] splistparams(String msg) {
    String[] params = msg.split(" ");
    return params;
  }

  public static void main(String args[]) throws IOException  {
    new FileServer().service();
  }


  class Handler implements Runnable{
    private Socket socket;
    public Handler(Socket socket){
      this.socket=socket;
    }

    public void run(){
       try{                
         String clientAddress= socket.getInetAddress() + ":" + socket.getPort();
         System.out.println("server :New connection accepted " + clientAddress);
    
         DataInputStream dInputStream = new DataInputStream(socket.getInputStream());
                                             int msglen=0;;
                                             String msg=null; 
         do{
            msglen = dInputStream.readInt();// 读出消息长度
         
            if (msglen == 0)
         	continue;// 若消息长度为 0，不做任何处理
         
            byte[] buffer = new byte[msglen];
            dInputStream.read(buffer);
            msg = new String(buffer); // 根据指示的消息长度，读取字符串内容
            if (msg != null)  {
  	System.out.println("server :Client("+clientAddress +") Request is  " + msg);
  
  	if (msg.equals("bye")){
    	  break; // 如果客户发送的消息为“bye”，就结束本次通信
                  }
  
                 String[] data = splistparams(msg);
                 String command = data[0];
                 switch (command){
                   case "put":
            		putFile(socket.getInputStream(), data);//接受客户端上传文件请求
            		break;
            
            	  case "get":
            		getFile(socket.getOutputStream(), data);//接受客户端下载文件请求
            
            		break;
            
            	  default:
            		break;
            
  	}

           }
        } while(msg!=null);
     } catch (IOException e) {
       	e.printStackTrace();
     } finally {
         try{
           if (socket != null)
              socket.close(); // 断开连接
         } catch (IOException e){
           e.printStackTrace();
          }
       }
    }
  }
}

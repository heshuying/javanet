package exercise;

import java.net.*;
import java.io.*;
import java.util.*;

import javax.imageio.IIOException;

public class FileClient{
  private String  urlAddress= "myftp://localhost:8000";
  private String filename= "demofile.txt";
  FileURLConnection connection; 

  public FileClient() throws IOException{}

  public void talk() throws IOException{
    try{
      //设置URLStreamHandlerFactory
      URL.setURLStreamHandlerFactory(new FileURLStreamHandlerFactory());
      //设置ContentHandlerFactory
      URLConnection.setContentHandlerFactory(new FileContentHandlerFactory());
      URL url=new URL(urlAddress);
      connection=(FileURLConnection)url.openConnection();
      connection.setDoOutput(true);
      DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

       System.out.println(">请输入命令 : get，put，bye");
       BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
	
       String msg = null;		
       while ((msg = localReader.readLine()) != null)	{
	
         if (msg.equals("bye")){
            //先获得消息字节数组
            byte[] byesmsg = msg.getBytes();
            //写入字节长度
            outputStream.writeInt(byesmsg.length);
            //写入消息内容
            outputStream.write(byesmsg);
            break;
          }
	
         if (msg.equals("get")){
           getFile( filename);
         }

         if (msg.equals("put")){
           putFile( filename );
         }

      }
    } catch (IOException e){
      e.printStackTrace();
    } finally{ 
      try{
        if(connection!=null)
          connection.disconnect();
      } catch (IOException e){
        e.printStackTrace();
      }  
    }
  }

  private void putFile(String fName) throws IOException{
    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

    String filename = fName;
    java.io.File file = new java.io.File("client//" + filename);

    if (!file.exists()){
      System.out.println(">文件"+filename+"不存在");
      return;
    } else{
      String msg = "put" + " " + filename + " " + file.length();
      byte[] msgbytes = msg.getBytes("utf-8");
      outputStream.writeInt(msgbytes.length);
      outputStream.write(msgbytes);
    }

    FileInputStream fInputStream = new FileInputStream("client//" + filename);

    byte[] buff = new byte[1024];
    int len = -1;
    while ((len = fInputStream.read(buff)) != -1){
       outputStream.write(buff, 0, len);
    }
    fInputStream.close();
    System.out.println(">上传文件"+filename+"成功");
    System.out.println();
  }

  private void getFile(String fName) throws IOException{
    //步骤 1 先告诉服务器要下载文件
    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
    byte[] msgbytes = ("get" + " " + filename).getBytes();
    outputStream.writeInt(msgbytes.length);
    outputStream.write(msgbytes);
    
    //步骤2 服务器应答后获得消息长度
    DataInputStream inputStream = new DataInputStream(connection.getInputStream());
    int msglen = inputStream.readInt(); // 消息长度
    if (msglen == 0){  // 文件不存在
      System.out.println("文件"+filename+"不存在");
      return;
    }
    //步骤3 根据消息长度读出指定长度的字节内容
    byte[] buffer = new byte[msglen];
    inputStream.read(buffer);
    String msg = new String(buffer); // 消息内容
    
    if (msg.indexOf("ok") == -1)  {
      throw new IOException("IOException");
    }
    
    //步骤4 从消息内容中获知要接收的文件长度length
    String filename = fName;
    int length = getfilelenFromMsg(msg);
    String path="client//" + filename;
    
    //步骤5 接收 length长度的文件
    FileOutputStream fileOut = new FileOutputStream(path);
    byte[] buff = new byte[1024];
    int m = length / buff.length;
    int n = length % buff.length;
    int len = 0;
    for (int i = 0; i < m + 1; i++)  {
      if (i == m){
        len = inputStream.read(buff, 0, n);
        fileOut.write(buff, 0, len);
      } else{
        len = inputStream.read(buff);
        fileOut.write(buff, 0, len);
      }
    }
    
    fileOut.close();
    System.out.println(">下载文件"+filename+"成功");
    System.out.println();
  }
  
  /* 以空格为单位分隔消息，获得文件长度 */
  private int getfilelenFromMsg(String msg) {
    String[] arrays = msg.split(":");
     return Integer.parseInt(arrays[1]);
  } 
  
  public static void main(String args[]) throws IOException  {	
    new FileClient().talk();
  }

}

/****************************************************
 * 作者：方伟                                                                 *
 * 来源：<<Java网络编程核心技术详解>>                   *
 * 技术支持网址：www.javathinker.net                        *
 ***************************************************/
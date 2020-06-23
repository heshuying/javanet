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
      //����URLStreamHandlerFactory
      URL.setURLStreamHandlerFactory(new FileURLStreamHandlerFactory());
      //����ContentHandlerFactory
      URLConnection.setContentHandlerFactory(new FileContentHandlerFactory());
      URL url=new URL(urlAddress);
      connection=(FileURLConnection)url.openConnection();
      connection.setDoOutput(true);
      DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

       System.out.println(">���������� : get��put��bye");
       BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
	
       String msg = null;		
       while ((msg = localReader.readLine()) != null)	{
	
         if (msg.equals("bye")){
            //�Ȼ����Ϣ�ֽ�����
            byte[] byesmsg = msg.getBytes();
            //д���ֽڳ���
            outputStream.writeInt(byesmsg.length);
            //д����Ϣ����
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
      System.out.println(">�ļ�"+filename+"������");
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
    System.out.println(">�ϴ��ļ�"+filename+"�ɹ�");
    System.out.println();
  }

  private void getFile(String fName) throws IOException{
    //���� 1 �ȸ��߷�����Ҫ�����ļ�
    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
    byte[] msgbytes = ("get" + " " + filename).getBytes();
    outputStream.writeInt(msgbytes.length);
    outputStream.write(msgbytes);
    
    //����2 ������Ӧ�������Ϣ����
    DataInputStream inputStream = new DataInputStream(connection.getInputStream());
    int msglen = inputStream.readInt(); // ��Ϣ����
    if (msglen == 0){  // �ļ�������
      System.out.println("�ļ�"+filename+"������");
      return;
    }
    //����3 ������Ϣ���ȶ���ָ�����ȵ��ֽ�����
    byte[] buffer = new byte[msglen];
    inputStream.read(buffer);
    String msg = new String(buffer); // ��Ϣ����
    
    if (msg.indexOf("ok") == -1)  {
      throw new IOException("IOException");
    }
    
    //����4 ����Ϣ�����л�֪Ҫ���յ��ļ�����length
    String filename = fName;
    int length = getfilelenFromMsg(msg);
    String path="client//" + filename;
    
    //����5 ���� length���ȵ��ļ�
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
    System.out.println(">�����ļ�"+filename+"�ɹ�");
    System.out.println();
  }
  
  /* �Կո�Ϊ��λ�ָ���Ϣ������ļ����� */
  private int getfilelenFromMsg(String msg) {
    String[] arrays = msg.split(":");
     return Integer.parseInt(arrays[1]);
  } 
  
  public static void main(String args[]) throws IOException  {	
    new FileClient().talk();
  }

}

/****************************************************
 * ���ߣ���ΰ                                                                 *
 * ��Դ��<<Java�����̺��ļ������>>                   *
 * ����֧����ַ��www.javathinker.net                        *
 ***************************************************/
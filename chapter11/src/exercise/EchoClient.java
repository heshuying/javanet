package exercise;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;  
import java.io.*;

public class EchoClient{

  public static void main( String args[] ){
    try{

      Registry registry = LocateRegistry.getRegistry(1099);  
      EchoService service=(EchoService)registry.lookup("EchoService");
      
      BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
      String msg=null;
      while((msg=localReader.readLine())!=null){
         System.out.println(service.echo(msg));

         if(msg.equals("bye"))
            break;
      }
    }catch( Exception e){
       e.printStackTrace();
    }
  }
}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/

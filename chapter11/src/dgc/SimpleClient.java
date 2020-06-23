package dgc;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;

public class SimpleClient{
  public static void main( String args[] ){

    try{
      Registry registry = LocateRegistry.getRegistry(1099);
      HelloService service=(HelloService)registry.lookup("HelloService");
      service.access();
      Thread.sleep(10000);
      service.bye();
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

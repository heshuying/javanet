package stock;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;
public class SimpleClient{

  public static void main( String args[] ){

    try{
      Registry registry = LocateRegistry.getRegistry(1099);     
      StockQuoteRegistry quoteRegistry=(StockQuoteRegistry)registry.lookup("StockQuoteRegistry");
      
      StockQuote client=new StockQuoteImpl();
      quoteRegistry.registerClient(client);    
  
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

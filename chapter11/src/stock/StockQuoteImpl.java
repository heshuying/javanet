 
package stock;

import java.rmi.*;
import java.rmi.server.*;

public class StockQuoteImpl extends UnicastRemoteObject
                                      implements StockQuote{
  public StockQuoteImpl()throws RemoteException{}

  public void quote(String symbol, double value)throws RemoteException{
    System.out.println(symbol+": "+value);
  }
}
                


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

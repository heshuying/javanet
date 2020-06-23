package stock;

import java.rmi.*;
public interface StockQuoteRegistry extends Remote{
  public void registerClient(StockQuote client)throws RemoteException;
  public void unregisterClient(StockQuote client)throws RemoteException;
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

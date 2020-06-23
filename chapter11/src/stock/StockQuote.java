package stock;
import java.rmi.*;
/** 客户端的远程对象接口 */
public interface StockQuote extends Remote{
    public void quote(String stockSymbol, double value)throws RemoteException;
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

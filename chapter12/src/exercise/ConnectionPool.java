package exercise;
import java.sql.*;
public interface ConnectionPool{
  /** 从连接池中取出连接 */
  public Connection getConnection()throws SQLException; 
    
  /** 把连接返回连接池 */
  public void releaseConnection(Connection con)throws SQLException;

  /** 关闭连接池*/	
  public void close();
}









/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

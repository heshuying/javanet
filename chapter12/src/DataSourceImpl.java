import java.sql.*;
import javax.sql.DataSource;
import java.io.*;
import java.util.logging.*;

public class DataSourceImpl implements DataSource{
  private ConnectionPool pool=new ConnectionPoolImpl2(); 
  public Connection getConnection()throws SQLException{return pool.getConnection();} 
  public Connection getConnection(String username, String password)throws SQLException{
    throw new UnsupportedOperationException();
  } 
  public int getLoginTimeout()throws SQLException{
    return DriverManager.getLoginTimeout();
  } 
  public PrintWriter getLogWriter()throws SQLException{
    return DriverManager.getLogWriter();
  } 
  public void setLoginTimeout(int seconds)throws SQLException{
    DriverManager.setLoginTimeout(seconds);
  } 
  public void setLogWriter(PrintWriter out)throws SQLException{
    DriverManager.setLogWriter(out);
  } 
  
  public Logger getParentLogger()throws SQLFeatureNotSupportedException{
    return null;
  }

  public <T> T unwrap(Class<T> iface) throws SQLException {
    return null;
  }
 
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

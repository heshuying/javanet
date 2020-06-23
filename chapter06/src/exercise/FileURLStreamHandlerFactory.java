package exercise;
import java.net.*;
import java.io.*;

public class FileURLStreamHandlerFactory implements URLStreamHandlerFactory{
  public URLStreamHandler createURLStreamHandler(String protocol){
    if(protocol.equals("myftp"))
      return new FileURLStreamHandler();
    else
      return null;
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

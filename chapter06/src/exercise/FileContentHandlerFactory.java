package exercise;
import java.net.*;
public class FileContentHandlerFactory implements ContentHandlerFactory{
  public ContentHandler createContentHandler(String mimetype){
    if(mimetype.equals("application/octet-stream")){
      return new FileContentHandler();
    }else{
      return null;
    }
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

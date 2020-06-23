package exercise;
public class ChatException extends Exception{
  public ChatException() {
    this("ChatException");
  }
  public ChatException(String msg) {
    super(msg);
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

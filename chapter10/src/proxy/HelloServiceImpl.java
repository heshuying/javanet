package proxy;
import java.util.Date;
public class HelloServiceImpl implements HelloService{
  public String echo(String msg){
    return "echo:"+msg;
  }
  public Date getTime(){
    return new Date();
  }
} 


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

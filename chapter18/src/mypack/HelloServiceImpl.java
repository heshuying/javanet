package mypack;
import javax.jws.WebService;

@WebService
public class HelloServiceImpl implements HelloService {
   @Override
   public String sayHello(String username) {
     return "Hello:"+username;
   }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

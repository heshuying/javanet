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
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/

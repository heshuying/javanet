import java.net.*;
public class SpamCheck {
  public static final String BLACKHOLE = "sbl.spamhaus.org";

  public static void main(String[] args) {
    for (String arg : args) {
      if (isSpammer(arg)) {
        System.out.println(arg + "是已知的垃圾邮件发送者");
      }else {
        System.out.println(arg + "是已知的合法邮件发送者");
      }
    }
  }

  private static boolean isSpammer(String str) {
    try {
      InetAddress address = InetAddress.getByName(str);
      byte[] quad = address.getAddress();// 获取主机的IP地址
      String query = BLACKHOLE;// 黑洞列表

      //逆置这个地址的字节，并添加黑洞服务的域
      //例如对于IP地址"108.33.56.27"，
      //query的取值为"27.56.33.108.sbl.spamhaus.org"
      for (byte octet : quad) {
        int unsignedByte = octet < 0 ? octet +256 : octet;
        query = unsignedByte + "." + query;
      }
      //查找这个地址
      InetAddress.getByName(query);

      return true;
    } catch (UnknownHostException e) {
      return false;
    }
  }
}

/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/
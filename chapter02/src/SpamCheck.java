import java.net.*;
public class SpamCheck {
  public static final String BLACKHOLE = "sbl.spamhaus.org";

  public static void main(String[] args) {
    for (String arg : args) {
      if (isSpammer(arg)) {
        System.out.println(arg + "����֪�������ʼ�������");
      }else {
        System.out.println(arg + "����֪�ĺϷ��ʼ�������");
      }
    }
  }

  private static boolean isSpammer(String str) {
    try {
      InetAddress address = InetAddress.getByName(str);
      byte[] quad = address.getAddress();// ��ȡ������IP��ַ
      String query = BLACKHOLE;// �ڶ��б�

      //���������ַ���ֽڣ�����Ӻڶ��������
      //�������IP��ַ"108.33.56.27"��
      //query��ȡֵΪ"27.56.33.108.sbl.spamhaus.org"
      for (byte octet : quad) {
        int unsignedByte = octet < 0 ? octet +256 : octet;
        query = unsignedByte + "." + query;
      }
      //���������ַ
      InetAddress.getByName(query);

      return true;
    } catch (UnknownHostException e) {
      return false;
    }
  }
}

/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/
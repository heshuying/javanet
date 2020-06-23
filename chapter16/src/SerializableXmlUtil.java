import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class SerializableXmlUtil {
  /** 把XML数据反序列化为 JavaBean*/
  public static <T> T parseXML(String xmlText) {
    XMLDecoder decoder=null;
    try{
      ByteArrayInputStream in = new ByteArrayInputStream(xmlText.getBytes());
      decoder = new XMLDecoder(new BufferedInputStream(in));
      return (T) decoder.readObject();
    }finally{
     decoder.close();
    }
  }

  /** 把JavaBean序列化为XML数据 */
  public static <T> String formatXML(T entity) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(out));
    encoder.writeObject(entity);
    encoder.close();
    return out.toString();
  }

  public static void main(String[] args) throws Exception {
    Customer customer=new Customer(1,"Tom","shanghai",25);
    String xmlText = formatXML(customer);
    System.out.println("序列化为XML数据:\n" + xmlText);
    customer = parseXML(xmlText);
    System.out.println("反序列化生成JavaBean:\n" + customer);
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

import java.io.*;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class SaxDemo {
  public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
    String xmlFile="customer.xml"; //待解析的XML文档

    //创建SAX解析工厂对象  
    SAXParserFactory  factory=SAXParserFactory.newInstance();

    //通过SAX解析工厂创建SAX解析器
    SAXParser parser=factory.newSAXParser();

    CustomerHandler handler=new CustomerHandler();
    //用CustomerHandler文档处理器 解析指定的XML文件
    parser.parse( new FileInputStream(xmlFile) , handler);
    List<Customer> customers=handler.getCustomers();    
        
    for(Customer c:customers) {
      System.out.println(c.getId()+","+c.getName()+","+c.getAge()+","+c.getAddr());
    }
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

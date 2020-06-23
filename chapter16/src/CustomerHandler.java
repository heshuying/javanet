import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CustomerHandler extends DefaultHandler {
  private List<Customer> customers;
  private Customer customer;  //当前正在处理的Customer对象
  private String tag;  //当前正在处理的元素名

  /** 开始解析文档时调用此方法 */
  public void startDocument() throws SAXException {
    System.out.println("文档解析开始");
    customers=new ArrayList<Customer>();
  }

  /** 开始解析一个元素时调用此方法 */
  public void startElement(String uri, String localName, String qName, 
                                           Attributes attributes) throws SAXException {
     System.out.println("开始解析一个元素"+qName);
     if(null!=qName) {
       tag=qName;
     }
     if(null!=qName&&qName.equals("customer")) {
       customer=new Customer();
     }
  }
   
  /** 处理各个元素的文本内容 ，把它们保存为Customer对象的属性 */
  public void characters(char[] ch, int start, int length) 
                                                       throws SAXException {
    String str=new String(ch,start,length);
    if(null!=tag&&tag.equals("name")) {
      customer.setName(str);
    }else if(null!=tag&&tag.equals("age")) {
      Integer age=Integer.valueOf(str);
      customer.setAge(age);
    }else if(null!=tag&&tag.equals("address")) {
      customer.setAddr(str);
    }else if(null!=tag&&tag.equals("id")) {
      Long id=Long.valueOf(str);
      customer.setId(id);
    }
  }

  /** 解析元素结尾时调用此方法 */
  public void endElement(String uri, String localName, String qName)
                                                                                 throws SAXException {
    System.out.println("结束解析一个元素"+qName);
    if(qName.equals("customer")) {
      this.customers.add(customer);
    }
    tag=null;
  }

  /** 解析文档结束时调用此方法 */
  public void endDocument() throws SAXException {
    System.out.println("文档解析结束");
  }

  public List<Customer> getCustomers() {
    return customers;
  }

  public void setCustomers(List<Customer> customers) {
    this.customers = customers;
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

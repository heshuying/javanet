import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class JdomDemo {
   /** 把Customer对象保存到参数xmlFile指定的XML文件中 */
  public int saveCustomersToXml(List<Customer> customerList, String xmlFile) {
    int count = 0;
    //创建文档对象
    Document document = new Document();
    //根元素
    Element elementRoot = new Element("customers");
    document.addContent(elementRoot);
    //遍历所有Customer对象，转换成XML数据
    for(Customer customer:customerList) {
      Element elementChild = new Element("customer");
      Element elementChildId = new Element("id");
      Element elementChildName = new Element("name");
      Element elementChildAge = new Element("age");
      Element elementChildAddress = new Element("address");
      elementChildId.setText(Long.valueOf(customer.getId()).toString());
      elementChildName.setText(customer.getName());
      elementChildAge.setText(Integer.valueOf(customer.getAge()).toString());
      elementChildAddress.setText(customer.getAddr());
      //添加到父类节点
      elementRoot.addContent(elementChild);
      elementChild.addContent(elementChildId);
      elementChild.addContent(elementChildName);
      elementChild.addContent(elementChildAge);
      elementChild.addContent(elementChildAddress);

      count++;
    }

    //输出到XML文件
    XMLOutputter outPut = new XMLOutputter();
    try {
       outPut.output(document, new FileOutputStream(xmlFile));
     } catch (IOException e) {
        e.printStackTrace();
     }
     return count;
  }

  /** 把参数xmlFile指定的XML文件中的数据读取出来，转换成Customer对象 */
  public List<Customer> getCustomersFromXml(String xmlFile) {
    //创建一个用于存放Customer对象的列表
    List<Customer> customerlist = new ArrayList<Customer>();
    //创建SAX解析器
    SAXBuilder builder = new SAXBuilder();

     try {
       //创建文档对象
       Document document =builder.build(new FileInputStream(xmlFile));
       //得到<customers>根元素
       Element elementRoot = document.getRootElement();
       //得到<customer>子元素
       List<Element> customerEles = elementRoot.getChildren();
       //遍历所有<customer>元素
       for(Element customerEle:customerEles) {
         Customer customer = new Customer();
         List<Element> childOfCustomerEles=customerEle.getChildren();

         //遍历一个<customer>元素中的所有子元素
         for(Element childOfCustomerEle: childOfCustomerEles){
           String tagName=childOfCustomerEle.getName();
           String text= childOfCustomerEle.getText();
           switch(tagName){
             case "id":
               customer.setId(Long.parseLong(text));
               break;
             case "name":
               customer.setName(text);
               break;
             case "age":
               customer.setAge(Integer.parseInt(text));
               break;
             case "address":
               customer.setAddr(text);
           }
         }
         //添加到列表中
         customerlist.add(customer);
      }
    } catch (JDOMException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return customerlist;
  }

  public static void main(String args[]){
    String xmlFile="customer.xml"; 
    String xmlFileCopy="customerCopy.xml";
    JdomDemo demo=new JdomDemo();
    List<Customer> customers=demo. getCustomersFromXml(xmlFile) ;
    for(Customer c:customers) {
      System.out.println(c.getId()+","+c.getName()+","+c.getAge()+","+c.getAddr());
    }
    System.out.println("共保存了"+demo.saveCustomersToXml(customers,xmlFileCopy)+"个Customer对象");
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

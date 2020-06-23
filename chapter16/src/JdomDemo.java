import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class JdomDemo {
   /** ��Customer���󱣴浽����xmlFileָ����XML�ļ��� */
  public int saveCustomersToXml(List<Customer> customerList, String xmlFile) {
    int count = 0;
    //�����ĵ�����
    Document document = new Document();
    //��Ԫ��
    Element elementRoot = new Element("customers");
    document.addContent(elementRoot);
    //��������Customer����ת����XML����
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
      //��ӵ�����ڵ�
      elementRoot.addContent(elementChild);
      elementChild.addContent(elementChildId);
      elementChild.addContent(elementChildName);
      elementChild.addContent(elementChildAge);
      elementChild.addContent(elementChildAddress);

      count++;
    }

    //�����XML�ļ�
    XMLOutputter outPut = new XMLOutputter();
    try {
       outPut.output(document, new FileOutputStream(xmlFile));
     } catch (IOException e) {
        e.printStackTrace();
     }
     return count;
  }

  /** �Ѳ���xmlFileָ����XML�ļ��е����ݶ�ȡ������ת����Customer���� */
  public List<Customer> getCustomersFromXml(String xmlFile) {
    //����һ�����ڴ��Customer������б�
    List<Customer> customerlist = new ArrayList<Customer>();
    //����SAX������
    SAXBuilder builder = new SAXBuilder();

     try {
       //�����ĵ�����
       Document document =builder.build(new FileInputStream(xmlFile));
       //�õ�<customers>��Ԫ��
       Element elementRoot = document.getRootElement();
       //�õ�<customer>��Ԫ��
       List<Element> customerEles = elementRoot.getChildren();
       //��������<customer>Ԫ��
       for(Element customerEle:customerEles) {
         Customer customer = new Customer();
         List<Element> childOfCustomerEles=customerEle.getChildren();

         //����һ��<customer>Ԫ���е�������Ԫ��
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
         //��ӵ��б���
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
    System.out.println("��������"+demo.saveCustomersToXml(customers,xmlFileCopy)+"��Customer����");
  }
}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/

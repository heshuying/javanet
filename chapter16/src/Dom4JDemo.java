import java.util.List;
import org.dom4j.*;
import org.dom4j.io.*;
import java.io.*;

public class Dom4JDemo {
  String xmlFile; //����ȡ��ԴXML�ļ�
  String xmlFileCopy; //�������Ŀ��XML�ļ�
  SAXReader saxReader;  //SAX������
  Document document ;  //�ĵ�����
  Element element ;  //��Ԫ��  
      
  public Dom4JDemo(String xmlFile,String xmlFileCopy)throws DocumentException{
     this.xmlFile=xmlFile; 
     this.xmlFileCopy=xmlFileCopy; 
     //����SAX������
     saxReader = new SAXReader();
     //����Document����
     document = saxReader.read(xmlFile);
     //��ȡ��Ԫ��
     element = document.getRootElement();
  }

  /** ��ȡ����ӡ����nameԪ�ص����� */
  public void readElements() throws Exception {
    //��ȡ����<customer>Ԫ��
    List<Element> list = element.elements("customer");
    //����<customer>Ԫ��
    for (Element c : list) {
      //��ȡÿ��customerԪ���е�nameԪ��
      Element name = c.element("name");
      //��ȡnameԪ�ص�����
      String str = name.getText();
      System.out.println("name:"+str);
    }
  }

  /** �ڵ�һ��customerԪ���������һ����Ԫ��<sex>Ů</sex> */
  public void addElement() throws Exception {
    //��ȡ��һ��customerԪ��
    Element customer = element.element("customer");
    //��customerԪ���������һ��sex��Ԫ��
    Element sex = customer.addElement("sex");
    //������ӵ���Ԫ������д������
    sex.setText("Ů");
    //����xml�ļ�
    saveToXmlFile();
  }

  /** �޸ĵ�һ��customerԪ���е�age��Ԫ�ص�ֵ */
  public  void updateElement() throws Exception {
     //��ȡ��һ��customerԪ��
     Element customer = element.element("customer");
     //��ȡ��һ��customerԪ�ص�age��Ԫ��
     Element age = customer.element("age");
     //�޸�ageԪ�ص�ֵΪ30
     age.setText("30");
     //����xml
     saveToXmlFile();
  }

  /** ɾ���ڶ���customerԪ�ص�name��Ԫ�� */
  public  void removeElement() throws Exception {
    //��ȡ����customerԪ��
    List<Element> list = element.elements("customer");
    //��ȡ�ڶ���customerԪ��
    Element customer = list.get(1);
    //��ȡ�ڶ���customerԪ�ص�nameԪ��
    Element name = customer.element("name");
    //ͨ����Ԫ��ɾ����Ԫ�أ�Ҫ��ɾ����Ԫ�أ�
    customer.remove(name);
    //����xml�ļ�
    saveToXmlFile();
  }

  public void saveToXmlFile()throws IOException{
    //��Document�����а��������ݱ��浽XML�ļ�
    OutputFormat format = OutputFormat.createPrettyPrint();
    XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(xmlFileCopy),format);
    xmlWriter.write(document);
    xmlWriter.close();
  }

  public static void main(String[] args) throws Exception {
    String xmlFile="customer.xml";
    String xmlFileCopy="customerCopy.xml";
    Dom4JDemo demo=new Dom4JDemo(xmlFile,xmlFileCopy);
    demo.readElements(); //��ȡԪ��
    demo.addElement();  //���Ԫ��
    demo.updateElement();  //����Ԫ��
    demo.removeElement();  //ɾ��Ԫ��
  }  
}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/

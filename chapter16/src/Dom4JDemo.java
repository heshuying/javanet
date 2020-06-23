import java.util.List;
import org.dom4j.*;
import org.dom4j.io.*;
import java.io.*;

public class Dom4JDemo {
  String xmlFile; //待读取的源XML文件
  String xmlFileCopy; //待输出的目标XML文件
  SAXReader saxReader;  //SAX解析器
  Document document ;  //文档对象
  Element element ;  //根元素  
      
  public Dom4JDemo(String xmlFile,String xmlFileCopy)throws DocumentException{
     this.xmlFile=xmlFile; 
     this.xmlFileCopy=xmlFileCopy; 
     //创建SAX解析器
     saxReader = new SAXReader();
     //创建Document对象
     document = saxReader.read(xmlFile);
     //获取根元素
     element = document.getRootElement();
  }

  /** 读取并打印所有name元素的内容 */
  public void readElements() throws Exception {
    //获取所有<customer>元素
    List<Element> list = element.elements("customer");
    //遍历<customer>元素
    for (Element c : list) {
      //获取每个customer元素中的name元素
      Element name = c.element("name");
      //获取name元素的内容
      String str = name.getText();
      System.out.println("name:"+str);
    }
  }

  /** 在第一个customer元素里面添加一个子元素<sex>女</sex> */
  public void addElement() throws Exception {
    //获取第一个customer元素
    Element customer = element.element("customer");
    //在customer元素里面添加一个sex子元素
    Element sex = customer.addElement("sex");
    //在新添加的子元素里面写入内容
    sex.setText("女");
    //更新xml文件
    saveToXmlFile();
  }

  /** 修改第一个customer元素中的age子元素的值 */
  public  void updateElement() throws Exception {
     //获取第一个customer元素
     Element customer = element.element("customer");
     //获取第一个customer元素的age子元素
     Element age = customer.element("age");
     //修改age元素的值为30
     age.setText("30");
     //更新xml
     saveToXmlFile();
  }

  /** 删除第二个customer元素的name子元素 */
  public  void removeElement() throws Exception {
    //获取所有customer元素
    List<Element> list = element.elements("customer");
    //获取第二个customer元素
    Element customer = list.get(1);
    //获取第二个customer元素的name元素
    Element name = customer.element("name");
    //通过父元素删除子元素（要被删除的元素）
    customer.remove(name);
    //更新xml文件
    saveToXmlFile();
  }

  public void saveToXmlFile()throws IOException{
    //把Document对象中包含的数据保存到XML文件
    OutputFormat format = OutputFormat.createPrettyPrint();
    XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(xmlFileCopy),format);
    xmlWriter.write(document);
    xmlWriter.close();
  }

  public static void main(String[] args) throws Exception {
    String xmlFile="customer.xml";
    String xmlFileCopy="customerCopy.xml";
    Dom4JDemo demo=new Dom4JDemo(xmlFile,xmlFileCopy);
    demo.readElements(); //读取元素
    demo.addElement();  //添加元素
    demo.updateElement();  //更新元素
    demo.removeElement();  //删除元素
  }  
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

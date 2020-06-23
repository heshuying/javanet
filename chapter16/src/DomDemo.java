import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomDemo {
  public static void parseXML(String xmlFile,String rootElementName){
    //创建解析器工厂对象 DocumentBuildFactory对象
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = null;
    Document dom = null;
    try{
      //由解析器工厂对象创建解析器对象，即DocumentBuilder对象
      db = dbf.newDocumentBuilder();

      //由解析器对象对指定XML文件进行解析，创建Document对象
      dom = db.parse(xmlFile);
     
      /* 在Document对象表示的DOM树中进行查询，
         返回以rootElementName为元素名的所有元素节点的列表NoteList */
      NodeList  nodelist = dom.getElementsByTagName(rootElementName);
      // 遍历所有的节点
      for (int i = 0; i <  nodelist.getLength(); i++) {
        Node  node =  nodelist.item(i);
        if(node instanceof Element)
          walkThroughTree((Element)node);
      }    
    } catch (ParserConfigurationException e) {
       e.printStackTrace();
    } catch (SAXException e) {
       e.printStackTrace();
    } catch (IOException e) {
       e.printStackTrace();
    } finally {
      System.out.println("解析结束");
    }
  }
  
  /** 递归遍历以参数element为根元素的分支树 */
  public static void walkThroughTree(Element element){
    String elementName = element.getTagName();
    String elementContent = element.getTextContent();
    System.out.println("元素名："+elementName+",元素内容："+elementContent);
    // 遍历子节点
    NodeList childList = element.getChildNodes();
    for (int j = 0; j < childList.getLength(); j++) {
      Node childNode = childList.item(j);
      if(childNode instanceof Element)
        walkThroughTree((Element)childNode);       
    }
  }

  public static void main(String[] agrs) {
    String xmlFile="mail.xml"; //待解析的XML文件
    String rootElementName="Mail"; //mail.xml中根元素的名字
    parseXML(xmlFile,rootElementName);
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

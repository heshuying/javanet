package exercise;
import java.io.*;
public class Customer implements Serializable {
  private long id;
  private String name="";
  private String addr="";
  private int age;
  public Customer(long id,String name,String addr,int age) {
    this.id=id;
    this.name=name;
    this.addr=addr;
    this.age=age;
  }
  
  public Customer(long id){
    this.id=id;
  }
  public Long getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public String getAddr(){
    return addr;
  }
  
  public int getAge(){
    return age;
  }

  public void setName(String name){
    this.name=name;
  }

  public void setAddr(String addr){
    this.addr=addr;
  }
  
  public void setAge(int age){
    this.age=age;
  }

  public String toString(){
    return "ID: "+id+", 姓名："+name+", 地址："+addr+", 年龄："+age+"\r\n";
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

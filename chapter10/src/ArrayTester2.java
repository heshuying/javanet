import java.lang.reflect.*;
public class ArrayTester2{
  public static void main(String args[]) {
    int dims[] = new int[]{5, 10, 15};
    Object array = Array.newInstance(Integer.TYPE, dims);
    Object arrayObj = Array.get(array, 3);

    //获取当前arrayObj数组的元素的类型
    Class<?> arrayObjComponentType =
               arrayObj.getClass().getComponentType();

    //打印 "class [[I  "，表明这是两维数组
    System.out.println(arrayObj.getClass());
   //打印 "class [I  "，表明这是一维数组
    System.out.println( arrayObjComponentType);

    arrayObj = Array.get(arrayObj, 5);
    Array.setInt(arrayObj, 10, 37);
    int arrayCast[][][] = (int[][][]) array;
    System.out.println(arrayCast[3][5][10]);
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

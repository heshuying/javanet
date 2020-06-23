package flight;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;  

public class SimpleClient{
  public static void main(String[] args){
    try{
      Registry registry = LocateRegistry.getRegistry(1099);  
      FlightFactory factory=(FlightFactory) registry.lookup("FlightFactory");

      Flight flight1 = factory.getFlight("795");
      flight1.setOrigin("Shanghai");
      flight1.setDestination("Beijing");
      System.out.println("Flight "+flight1.getFlightNumber()+":");
      System.out.println("From "+flight1.getOrigin()+" to "+
                                        flight1.getDestination());
      
      Flight flight2 = factory.getFlight("795");
      System.out.println("Flight "+flight2.getFlightNumber()+":");
      System.out.println("From "+flight2.getOrigin()+" to "+
                                        flight2.getDestination());
      System.out.println("flight1是"+flight1.getClass().getName()+"的实例");
      System.out.println("flight2是"+flight2.getClass().getName()+"的实例");
      System.out.println("flight1==flight2:"+(flight1==flight2));
      System.out.println("flight1.equals(flight2):"+(flight1.equals(flight2)));
    }catch(Exception e){
       e.printStackTrace();
    }
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

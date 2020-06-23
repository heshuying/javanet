package store;
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;  
import java.rmi.*;

public class StoreClient {
  public static void main( String args[] ){
    System.setProperty("java.security.policy",StoreClient.class.getResource("secure.policy").toString());
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }
    try{
      StoreModel model;
      StoreView view;
      StoreController ctrl;
            
      Registry registry = LocateRegistry.getRegistry(1099);  
      model=(StoreModel)registry.lookup("storeModel");
      view=new StoreViewImpl(model);
      ctrl=new StoreControllerImpl(model,view);
    }catch( Exception e ){
       e.printStackTrace();
    }
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

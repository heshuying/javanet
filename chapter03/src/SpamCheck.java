import java.net.*;
import java.util.concurrent.*;
import java.util.*;
import java.io.*;

public class SpamCheck {
  private static final int POOL_SIZE=4;  //单个CPU时线程池中工作线程的数目

  public static void main(String[] args)throws Exception {
    ExecutorService executorService;  //线程池
  
     executorService= Executors.newFixedThreadPool( 
	    Runtime.getRuntime().availableProcessors() * POOL_SIZE);

     BufferedReader fileReader=new BufferedReader(new InputStreamReader(
                new FileInputStream("address.txt")));
      String addr=null;
      Set< Future<String>> futureResults=new HashSet< Future<String>>();
      while((addr=fileReader.readLine())!=null){
         LookupTask task=new LookupTask(addr);
         Future<String> future=executorService.submit(task);
         futureResults.add(future);
     }

/*
     for(Future<String> result:futureResults){
        //如果任务还没完成，get()方法会阻塞，直到任务完成，返回结果。
        System.out.println(result.get());
     }
*/
    //采用轮询方式，不断遍历访问结果集，打印任务已经完成的结果。
     while(!futureResults.isEmpty()){
        Iterator<Future<String>> it=futureResults.iterator();
        while(it.hasNext()){
          Future<String> result=it.next();
          if(result.isDone()){
            System.out.println(result.get());
            it.remove(); 
          }
        }
     }
     executorService.shutdown();
  }
}

class LookupTask implements Callable<String>{
   public static final String BLACKHOLE = "sbl.spamhaus.org";
   String addr;
   public LookupTask(String addr){
       this.addr=addr;
   }

   public String call(){
      try {
        InetAddress address = InetAddress.getByName(addr);
        byte[] quad = address.getAddress();// 获取主机的IP地址
        String query = BLACKHOLE;// 黑洞列表

        //逆置这个地址的字节，并添加黑洞服务的域
        //例如对于IP地址"108.33.56.27"，
        //query的取值为"27.56.33.108.sbl.spamhaus.org"
        for (byte octet : quad) {
          int unsignedByte = octet < 0 ? octet +256 : octet;
          query = unsignedByte + "." + query;
        }
        //查找这个地址
        InetAddress.getByName(query);

        return addr + "是已知的垃圾邮件发送者";
    } catch (UnknownHostException e) {
        return addr + "是已知的合法邮件发送者";
    }
  }
}


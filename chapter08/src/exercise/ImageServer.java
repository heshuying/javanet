package exercise;
import java.net.*;
import java.io.*;
 
public class ImageServer {
    public static final int PORT=7000;
    public static void main(String[] args) throws Exception{
        
        DatagramPacket pack=null;
        DatagramSocket mainSocket=null;
        byte b[]=new byte[8192];
        InetAddress address=null;
        pack=new DatagramPacket(b,b.length);
    
        mainSocket=new DatagramSocket(PORT);
        System.out.println("服务器启动");

        while(true){
           try {
                mainSocket.receive(pack);
                address=pack.getAddress();
                System.out.println("收到来自客户端的请求:"+address);
              
           } catch(IOException e){
                System.out.println(e.getMessage());
           }
           
           if(address!=null) {
               new ImageSender(address).start();
           } else {
              continue;        
           }        
                
        }
    
   }
}

class ImageSender extends Thread{
    InetAddress address;
    DataOutputStream out=null;
    DataInputStream  in=null;
    String s=null;
    ImageSender(InetAddress address) {
        this.address=address;
    }

    public void run()  {
        FileInputStream in;
        byte b[]=new byte[8192];
        DatagramSocket imageSocket=null;

        try {
            imageSocket=new DatagramSocket();
            in=new FileInputStream("javathinker.png");
            int n=-1;
            while((n=in.read(b))!=-1) {
                DatagramPacket data=new DatagramPacket(b,n,address,ImageClient.PORT);
                     imageSocket.send(data);
                System.out.println("正在发送图片数据");    
            }
            in.close();

            byte end[]="end".getBytes();
            DatagramPacket data=new DatagramPacket(end,end.length,address,ImageClient.PORT);
            imageSocket.send(data);
            System.out.println("图片数据发送完毕");
           
        }  catch(Exception e){
            e.printStackTrace();
        }finally{ 
           try{ imageSocket.close();}catch(Exception e){}
        }
    }
    
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/


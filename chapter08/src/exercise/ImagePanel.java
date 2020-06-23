package exercise;
import java.awt.*;
import javax.swing.*;

public class ImagePanel extends JPanel{
    Image image=null;

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(image!=null) {
            g.drawImage(image,0,0,this);
        } 
    }

    public void setImage(Image image) {
        this.image=image;
    }

}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/

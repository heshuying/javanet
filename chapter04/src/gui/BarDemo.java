package gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;

public class BarDemo extends JFrame {

  private JPanel contentPane;
  private JProgressBar progressBar;

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          BarDemo bar = new BarDemo("ProgressBar Demo");
        } catch (Exception e) { e.printStackTrace();}
      }
    });
  }

  public BarDemo(String title) {
    super(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    progressBar = new JProgressBar(0, 100);
    contentPane.add(progressBar, BorderLayout.NORTH);

    JButton btnBegin = new JButton("Begin");
    btnBegin.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
           new ProgressBarHandler().execute();
       }
    });
    contentPane.add(btnBegin, BorderLayout.SOUTH);
    setVisible(true);
  }

  class ProgressBarHandler extends SwingWorker<Void, Integer> {

    /* SwingWorker工作线程执行,后台任务,每隔一秒，发送一个中间结果 */
    protected Void doInBackground() throws Exception {
       FileOutputStream out=new FileOutputStream("data.txt"); 
       PrintWriter pw=new PrintWriter(out,true);
       // 模拟一个很耗时的任务，每次睡1秒，再向文件中写入一行文本。
       for (int i = 0; i < 100; i++) {
          Thread.sleep(1000);
          pw.println("data"+i);
          publish(i);//将当前进度信息加入chunks中
       }
       pw.close();
       return null;
    }

    /* EDT线程执行，依据当前的中间结果，更新进度条的信息 */
    protected void process(List<Integer> chunks) {
      progressBar.setValue(chunks.get(chunks.size() - 1));
    }
    
    /* EDT线程执行，显示任务完成的消息 */
    protected void done() {
      JOptionPane.showMessageDialog(null, "任务完成！");
    }
  }
}

/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程核心技术详解>>                       *
 * 技术支持网址：www.javathinker.net                *
 ***************************************************/
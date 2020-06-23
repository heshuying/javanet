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

    /* SwingWorker�����߳�ִ��,��̨����,ÿ��һ�룬����һ���м��� */
    protected Void doInBackground() throws Exception {
       FileOutputStream out=new FileOutputStream("data.txt"); 
       PrintWriter pw=new PrintWriter(out,true);
       // ģ��һ���ܺ�ʱ������ÿ��˯1�룬�����ļ���д��һ���ı���
       for (int i = 0; i < 100; i++) {
          Thread.sleep(1000);
          pw.println("data"+i);
          publish(i);//����ǰ������Ϣ����chunks��
       }
       pw.close();
       return null;
    }

    /* EDT�߳�ִ�У����ݵ�ǰ���м��������½���������Ϣ */
    protected void process(List<Integer> chunks) {
      progressBar.setValue(chunks.get(chunks.size() - 1));
    }
    
    /* EDT�߳�ִ�У���ʾ������ɵ���Ϣ */
    protected void done() {
      JOptionPane.showMessageDialog(null, "������ɣ�");
    }
  }
}

/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̺��ļ������>>                       *
 * ����֧����ַ��www.javathinker.net                *
 ***************************************************/
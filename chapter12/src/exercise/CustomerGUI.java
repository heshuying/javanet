package exercise;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class CustomerGUI  extends JFrame{

    private int currentlyPage = 1; //当前的页数
    private JPanel center, bottom;
    private JButton previously, next, first, tail;
    private JTextArea ta;
    private DBService dbService = new DBService();
    ArrayList<Customer> arrayCustomers = null;

    public CustomerGUI(String title) {
        super(title);
        this.previously = new JButton("上一页");
        this.next = new JButton("下一页");
        this.first = new JButton("首页");
        this.tail = new JButton("尾页");
        this.ta = new JTextArea();
        ta.setEditable(false); 
        ta.setRows(5);
        ta.setBounds(0, 0, 40, 20);
        this.initialization();

        this.center = new JPanel(new GridLayout(1, 1));
        this.bottom = new JPanel(new GridLayout(1, 4));

        center.add(ta);

        bottom.add(previously);
        bottom.add(next);
        bottom.add(first);
        bottom.add(tail);

        /*对首页、下一页、上一页、尾页做监听*/
        MyListener ml = new MyListener();
        this.previously.addActionListener(ml);
        this.next.addActionListener(ml);
        this.first.addActionListener(ml);
        this.tail.addActionListener(ml);

        this.getContentPane().add(center, BorderLayout.CENTER);
        this.getContentPane().add(bottom, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setBounds(200, 200, 350, 180);
        this.setVisible(true);
    }

    /**
     * 初始化第一页
     * */
    void initialization() {
        showData();
    }

    /**
     * 处理按钮的翻页
     * */
    class MyListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            ta.setText("");
            if (e.getSource() == previously) {//上一页的按钮
                if (currentlyPage >= 2) {
                    currentlyPage--;
                } else {
                    ta.append("当前为第一页！\n");
                }
                //展示数据
                showData();


            } else if (e.getSource() == next) { //如果为下一页
                ta.setText("");
                int pagesNum = dbService.getPagesNum();
                if (currentlyPage < pagesNum) {//如果当前的页码小于总页数
                    currentlyPage++;
                } else {
                    ta.append("当前为最后一页！\n");
                }

                showData();

            } else if (e.getSource() == first) {//如果是首页
                ta.setText("");
                currentlyPage = 1;
                showData();


            } else if (e.getSource() == tail) {//如果是尾页
                ta.setText("");
                currentlyPage = dbService.getPagesNum();
                showData();
            }
        }
    }

    /**
     * 展示特定页面的所有Customer对象的数据
     */
    public void showData() {
        arrayCustomers = dbService.getPerPageData(currentlyPage);
        Iterator<Customer> i = arrayCustomers.iterator();
        while ( i.hasNext()) {
            Customer c = (Customer) i.next();
            ta.append(c.toString());
        }
    }
    public static void main(String[] args) {
        CustomerGUI gui = new CustomerGUI("客户信息");
    }
}
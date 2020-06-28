package exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class DBService {

    ArrayList<Customer> arrayCustomers = null;
    final int PER_PAGE_SIZE = 5;//每个页面的长度，即每个页面显示的记录数目
    ConnectionPool pool = new ConnectionPoolImpl2();

    public ResultSet getAllCustomers() {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            statement = con.createStatement();

            String sql = "select ID,NAME,AGE,ADDRESS from CUSTOMERS order by ID";
            rs = statement.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int getTotalSize() {  //获得CUSTOMERS表的总的记录数
        //得到查询的结果集
        ResultSet rs = getAllCustomers();
        if (rs == null) return 0;

        int totalsize = 0;//CUSTOMERS表的总的记录数
        try {
            rs.last();  // 将游标移动到此 ResultSet 对象的最后一行
            //  获取当前行编号(先将游标移到最后一行，
            // 然后再获取最后一行的下标，即可得到整个CUSTOMERS表的记录数)
            totalsize = rs.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalsize;
    }

    /**
     * 得到总共的页数
     */
    public int getPagesNum() {
        int totalsize = (Integer) getTotalSize() / PER_PAGE_SIZE;
        return (totalsize + 1);
    }

    /**
     * 将特定页的所有Customer数据装到ArrayList列表里
     * 参数pageNumber指定特定的页数
     */
    public ArrayList<Customer> getPerPageData(int pageNumber) {
        //得到所有客户的结果集
        ResultSet rs = getAllCustomers();
        //定义一个装客户对象的集合
        ArrayList<Customer> arrayCustomers = new ArrayList<Customer>();
        int totalsize = getTotalSize();

        int id = 0;//ID号
        String name = null;//姓名
        int age = 0; //年龄
        String address = null;//地址

        if (PER_PAGE_SIZE * (pageNumber - 1) < totalsize) {
            int start = PER_PAGE_SIZE * (pageNumber - 1) + 1;  //当前页的首条记录在结果集中的位置
            int end = 0;  // //当前页的最后一条记录在结果集中的位置
            if (PER_PAGE_SIZE * pageNumber > totalsize) {
                end = totalsize;
            } else {
                end = PER_PAGE_SIZE * pageNumber;
            }
            for (int i = start; i <= end; i++) {
                try {
                    rs.absolute(i);
                    id = rs.getInt(1);
                    name = rs.getString(2);
                    age = rs.getInt(3);
                    address = rs.getString(4);
                    Customer c = new Customer(id, name, address, age);
                    arrayCustomers.add(c);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("超出范围");
        }
        return arrayCustomers;
    }
}
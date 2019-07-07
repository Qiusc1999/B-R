package BANDR;

import java.security.interfaces.RSAKey;
import java.sql.*;
import javax.print.DocFlavor;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    public static Connection cn;
    public static Statement st;
    public static Statement st2;
    public static ResultSet rs;

    static {
        try {
            try {
                //注册驱动
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("无法找到驱动类");
                JOptionPane.showMessageDialog (null, "无法找到驱动类", "错误", JOptionPane.ERROR_MESSAGE);
            }
            //获得连接
            String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=BANDR;user=test;password=test";
            cn = DriverManager.getConnection(url);
            st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st2 = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            JOptionPane.showMessageDialog (null, "数据库连接失败", "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public static boolean query(String SQL){    //查询
        try{
            rs=null;
            rs=st.executeQuery(SQL);
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
            System.out.println(sqlE);
            return false;
        }
        return true;
    }

    public static int RecCount(ResultSet resultSet){    //计数
        int i=0;
        try{
            if(resultSet.getRow()!=0){
                resultSet.beforeFirst();
                while (resultSet.next()){
                    i++;
                }
                resultSet.beforeFirst();
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
            System.out.println(sqlE);
        }
        return i;
    }

    public static int executeUpdate(String SQL){        //执行更新
        int i=0;
        try{
            st2 = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); // 需要此句否则相互干扰
            i=st2.executeUpdate(SQL);
            cn.commit();
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
            System.out.println(sqlE);
        }
        return i;
    }

    public static ResultSet executeQuery(String SQL){       //执行查询
        ResultSet resultSet=null;
        try{
            st2 = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); // 需要此句否则相互干扰
            resultSet=st2.executeQuery(SQL);
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
            System.out.println(sqlE);
        }
        return resultSet;
    }


//    public static boolean joinDB(){
//        boolean whetherJoinFlag;
//        try{
//            whetherJoinFlag=true;
//            try {
//                //注册驱动
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            } catch (ClassNotFoundException e) {
//                System.out.println("无法找到驱动类");
//                JOptionPane.showMessageDialog (null, "无法找到驱动类", "错误", JOptionPane.ERROR_MESSAGE);
//            }
//            //获得连接
//            String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=BANDR;user=test;password=test";
//            cn = DriverManager.getConnection(url);
//            st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//            st2 = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//        }catch (SQLException sqlE){
//            sqlE.printStackTrace();
//            JOptionPane.showMessageDialog (null, "数据库连接失败...", "错误", JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
//            whetherJoinFlag=false;
//        }
//        return whetherJoinFlag;
//    }

//    public static boolean insert(String sqlString) {
//        boolean insertFlag;
//        try {
//            st2.executeUpdate(sqlString);
//            insertFlag = true;
//        }
//        catch (SQLException sqlE) {
//            insertFlag = false;
//            JOptionPane.showMessageDialog(null,sqlE);
//            System.out.println("sql exception:" + sqlE.getMessage());
//        }
//        return insertFlag;
//    }

}

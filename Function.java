package BANDR;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Function {
    //查询最长借阅天数1
    public static int getUAT(String UNO){
        int uat=0;
        String SQL_getUAT="SELECT UAT FROM TUSER,TGDINFO WHERE TUSER.UGD=TGDINFO.UGD AND TUSER.UNO = '"+UNO+"'";
        ResultSet rs_getUAT=Database.executeQuery(SQL_getUAT);
        try{
            while (rs_getUAT.next()){
                uat=rs_getUAT.getInt(1);
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        return uat;
    }

    //获取总罚金1
    public static double getTotalFine(String UNO){
        double total=0;
        String SQL="SELECT * FROM TBORROW WHERE UNO = '"+UNO+"'";
        ResultSet rs=Database.executeQuery(SQL);
        try {
            while (rs.next()){
                int WOM=rs.getInt(5);
                String RT=rs.getString(4);
                if(RT==null){   //未归还
                    String BNO=rs.getString(2);
                    String BT=rs.getString(3);
                    int time=Function.getTimeOfBorrow(UNO,BNO,BT);
                    int UAT=Function.getUAT(UNO);
                    double fine=(time-UAT)*0.02;
                    if(fine<=0){
                        fine=0;
                    }
                    total+=fine;
                }
                else {      //已归还未缴费
                    if(WOM==1){
                        String BNO=rs.getString(2);
                        String BT=rs.getString(3);
                        int time=Function.getTimeOfBorrow(UNO,BNO,BT);
                        int UAT=Function.getUAT(UNO);
                        double fine=(time-UAT)*0.02;
                        if(fine<=0){
                            fine=0;
                        }
                        total+=fine;
                    }
                }
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        total=(double)Math.round(total*100)/100;
        return total;
    }

    //检索是否有欠款1
    public static boolean FindOweMoney(String UNO){
        boolean ExistOweMoney=false;
        String SQL2="SELECT COUNT(*) FROM TBORROW,TUSER WHERE TBORROW.UNO=TUSER.UNO AND TBORROW.UNO = '"+UNO+"' AND WOM=1 GROUP BY TBORROW.UNO,BNO";
        ResultSet rs2=Database.executeQuery(SQL2);
        try {
            while (rs2.next()){
                if(rs2.getInt(1)!=0){
                    ExistOweMoney=true;
                }
                else ExistOweMoney=false;
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        return ExistOweMoney;
    }

    //检测已借阅数1
    public static int getNowNumofBorrow(String UNO){
        int NNB=0;
        //查找RT为空的数
        String SQL1="SELECT COUNT(*) FROM TUSER,TBORROW WHERE TUSER.UNO=TBORROW.UNO AND TUSER.UNO = '"+UNO+"' AND RT IS NULL GROUP BY TUSER.UNO";
        ResultSet rs1=Database.executeQuery(SQL1);
        try {
            while (rs1.next()){
                NNB=rs1.getInt(1);
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        return NNB;
    }

    //检测逾期书数（未归还）1
    public static int getNumOvertime(String UNO){
        int num=0;
        String SQL="SELECT COUNT(*) FROM TBORROW WHERE UNO = '"+UNO+"' AND RT IS NULL AND DATEDIFF(DAY,BT,GETDATE())> "+Account.UAT+" ";
        ResultSet rs=Database.executeQuery(SQL);
        try {
            while (rs.next()){
                num=rs.getInt(1);
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        return num;
    }

    //检测用户是否借阅此书1
    public static boolean isBorrowThisBook(String UNO,String BNO){
        Boolean p=false;
        String SQL_checkhave="SELECT COUNT(*) FROM TBORROW WHERE UNO = '"+UNO+"' AND BNO = '"+BNO+"' AND RT IS NULL";
        ResultSet rs_checkhave=Database.executeQuery(SQL_checkhave);
        try{
            while (rs_checkhave.next()){
                int i=rs_checkhave.getInt(1);
                if(i!=0){   //TBORROW已存在记录，不可借阅
                    p=true;
                }
                else {
                    p=false;
                }
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        return p;
    }

    //获取图书已借出数量1
    public static int getNum_haveBorrowed(String BNO){   //获取图书当前已借阅数量
        int num=0;
        String SQL="SELECT COUNT(*) FROM TBORROW WHERE BNO='"+BNO+"' AND RT IS NULL GROUP BY BNO";
        ResultSet resultSet=Database.executeQuery(SQL);
        try{
            while (resultSet.next()){
                num=resultSet.getInt(1);
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        return num;
    }

    public static int getBNU(String BNO){   //获取图书总数1
        int num=0;
        String SQL="SELECT BNU FROM TBOOK WHERE BNO='"+BNO+"' ";
        ResultSet resultSet=Database.executeQuery(SQL);
        try{
            while (resultSet.next()){
                num=resultSet.getInt(1);
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        return num;
    }

    public static int getWOM(String UNO,String BNO,String BT){
        int wom=0;
        String SQL="SELECT WOM FROM TBORROW WHERE UNO = '"+UNO+"' AND BNO = '"+BNO+"' AND BT = CONVERT(datetime,'"+BT+"')";
        ResultSet rs=Database.executeQuery(SQL);
        try {
            while (rs.next()){
                wom=rs.getInt(1);
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        return wom;
    }

    public static int getBTtoRT(String UNO,String BNO,String BT){     //获取BT to RT1
        int time1=0;
        String RT="";
        String SQL="SELECT DATEDIFF(DAY,BT,RT),RT FROM TBORROW WHERE UNO = '"+UNO+"' AND BNO = '"+BNO+"' AND BT = CONVERT(datetime,'"+BT+"')";
        ResultSet rs=Database.executeQuery(SQL);
        try {
            while (rs.next()){
                time1=rs.getInt(1);
                RT=rs.getString(2);
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        if(RT==null){  //未归还
            return -1;
        }else {
            return time1;
        }
    }

    public static int getBTtoRT_user(String UNO,String BNO,String BT){     //获取BT to RT
        // 由于用户界面中BT使用的是CONVERT(VARCHAR(100),BT,20),所以区别于管理员界面使用的getBTtoRT函数
        int time1=0;
        String RT="";
        String SQL="SELECT DATEDIFF(DAY,BT,RT),RT FROM TBORROW WHERE UNO = '"+UNO+"' AND BNO = '"+BNO+"' AND CONVERT(VARCHAR(100),BT,20) = '"+BT+"' ";
        ResultSet rs=Database.executeQuery(SQL);
        try {
            while (rs.next()){
                time1=rs.getInt(1);
                RT=rs.getString(2);
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        if(RT==null){  //未归还
            return -1;
        }else {
            return time1;
        }
    }


    public static int getTimeOfBorrow(String UNO,String BNO,String BT){     //获取已借阅时间或未归还截至当期的时长
        int time1=0;                                                        //可用于计算罚金
        int time2=0;
        String SQL="SELECT DATEDIFF(DAY,BT,RT),DATEDIFF(DAY,BT,GETDATE()) FROM TBORROW WHERE UNO = '"+UNO+"' AND BNO = '"+BNO+"' AND BT = CONVERT(datetime,'"+BT+"')";
        ResultSet rs=Database.executeQuery(SQL);
        try {
            while (rs.next()){
                time1=rs.getInt(1);
                time2=rs.getInt(2);
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        if(time1==0){   //尚未归还
            return time2;
        }
        else return time1;
    }

    //设置每页显示几个,pageNum*个数,得到子vector在总vector开始之处
    public static void showPage(DefaultTableModel model, int pageNum, int numOfEachPage,int totalnumofrow, Vector<Object[]> vector_total){
        model.setRowCount(0);   //清屏
        int startlocation=(pageNum-1)*numOfEachPage+1; //设置起始位置
        int restofrow=totalnumofrow-(pageNum-1)*numOfEachPage;
        int stoplocation=startlocation+numOfEachPage-1;
        if(restofrow<numOfEachPage){    //防止越界
            stoplocation=startlocation+restofrow-1;
        }
        for(int i=startlocation;i<=stoplocation;i++){
            Object[] rowdata=vector_total.get(i-1);
            model.addRow(rowdata);
        }
    }

//    public static double getFine(String UNO,String BNO,String BT){
//        double fine=0;
//        int time=Function.getTimeOfBorrow(UNO,BNO,BT);
//        if(time<Function.getUAT(UNO)){
//            fine=0;
//        }else {
//            fine=(time-Function.getUAT(UNO))*0.02;
//        }
//        return fine;
//    }

}

package BANDR;

import javax.swing.*;

public class Main{
    public static void main(String args[]){
//        for(int i=0;i<100000;i++){
//            String BNO="10"+i;
//            String BNA=BNO;
//            String BDA="2019";
//            String BPU="哈哈出版社";
//            String BPL="1楼A区";
//            String SQL="INSERT INTO TBOOK VALUES('"+BNO+"','"+BNA+"','"+BDA+"','"+BPU+"','"+BPL+"',5,GETDATE())";
//            int j=Database.executeUpdate(SQL);
//            if(j!=0) System.out.println("插入1行");
//        }

//        for(int i=0;i<1000000;i++){
//            String BNO="10"+i;
//            String SQL="DELETE FROM TBOOK WHERE BNO='"+BNO+"'";
//            int j=Database.executeUpdate(SQL);
//            if(j!=0) System.out.println("删除1行");
//        }

//        for(int i=0;i<10000;i++){
//            String UNO="10"+i;
//            String UNA="Bill"+i;
//            String UDE="计算机";
//            String USP="计算机";
//            String UGD="本科生";
//            String PWD=UNO;
//            String SQL="INSERT INTO TUSER VALUES('"+UNO+"','"+UNA+"','"+UDE+"','"+USP+"','"+UGD+"','"+PWD+"')";
//            int j=Database.executeUpdate(SQL);
//            if(j!=0) System.out.println("插入1行");
//        }

//        for(int i=0;i<10000;i++){
//            String UNO="10"+i;
//            String SQL="DELETE FROM TUSER WHERE UNO='"+UNO+"'";
//            int j=Database.executeUpdate(SQL);
//            if(j!=0) System.out.println("删除1行");
//        }

//        for(int i=0;i<100000;i++){
//            String BNO="10"+i;
//            String UNO="1";
//            String SQL="INSERT INTO TBORROW VALUES('"+UNO+"','"+BNO+"',GETDATE(),NULL,0)";
//            int j=Database.executeUpdate(SQL);
//            if(j!=0) System.out.println("插入1行");
//        }

        Form_login form_login=new Form_login();
        form_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
package BANDR;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    public static String UNO="";
    public static String ANO="";
    public static String ANA="";
    public static int UAT=0;    //最长时间
    public static int UUP=0;    //最多数量

    public static void setUserAccount(String s) {
        String SQL="SELECT UAT,UUP FROM TUSER,TGDINFO WHERE TUSER.UGD=TGDINFO.UGD AND TUSER.UNO = '"+s+"'";
        UNO=s;
        ResultSet rs=Database.executeQuery(SQL);
        try {
            while (rs.next()){
                UAT=rs.getInt(1);
                UUP=rs.getInt(2);
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }

    }
}

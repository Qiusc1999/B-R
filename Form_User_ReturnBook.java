package BANDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Form_User_ReturnBook extends JFrame {
    private JLabel ltitle, lbno, lbna,lbt, ltime,lpay, lbno1, lbna1,lbt1, ltime1,lpay1;
    private JButton button_cancel,button_OnlyReturn,button_return;
    private String BNO="";
    private String BT="";
    private double fine=0;

    public Form_User_ReturnBook(String Bno,String Bt) {
        super("归还图书");
        BNO=Bno;
        BT=Bt;
        ltitle = new JLabel("归还图书");
        ltitle.setFont(new Font("等线",1,17));
        lbno = new JLabel("图书编号");
        lbna = new JLabel("图书名称:");
        lbt=new JLabel("借书时间:");
        ltime =new JLabel("已借阅时间:");
        lpay=new JLabel("应缴罚金:");
        lbno1 = new JLabel("bno");
        lbna1 = new JLabel("bna");
        lbt1=new JLabel("bt");
        ltime1 =new JLabel("time");
        lpay1=new JLabel("pay");

        button_cancel = new JButton("取消");
        button_OnlyReturn=new JButton("暂缓缴费还书");
        button_return=new JButton("还书");


        int datediff=0;
//      01  String SQL = "SELECT TBORROW.BNO,BNA,BT,DATEDIFF(DAY,BT,GETDATE()) FROM TBORROW,TBOOK WHERE TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO = '"+Account.UNO+"' AND TBORROW.BNO = '" + Bno + "' AND BT='"+BT+"' ";


        String SQL = "SELECT TBORROW.BNO,BNA,BT,DATEDIFF(DAY,BT,GETDATE()) FROM TBORROW,TBOOK WHERE TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO = '"+Account.UNO+"' AND TBORROW.BNO = '" + Bno + "' AND CONVERT(VARCHAR(100),BT,20) = '"+BT+"' ";
        ResultSet rs = Database.executeQuery(SQL);
        try {
            while (rs.next()){
                lbno1.setText(rs.getString(1));
                lbna1.setText(rs.getString(2));
                lbt1.setText(rs.getString(3));
                ltime1.setText(rs.getString(4));
                datediff=rs.getInt(4);
            }
        } catch (SQLException sqlE) {
            JOptionPane.showMessageDialog(null, sqlE, "提示", JOptionPane.INFORMATION_MESSAGE);
        }

        fine=(datediff-Account.UAT)*0.02;
        if(fine<0) fine=0;
        fine=(double)Math.round(fine*100)/100;
        lpay1.setText(String.valueOf(fine));

        ltitle.setBounds(150, 20, 100, 20);
        lbno.setBounds(35, 55, 100, 35);
        lbno1.setBounds(165, 55, 200, 30);
        lbna.setBounds(35, 90, 100, 35);
        lbna1.setBounds(165, 90, 200, 30);
        lbt.setBounds(35, 125, 100, 35);
        lbt1.setBounds(165, 125, 200, 30);
        ltime.setBounds(35, 160, 100, 35);
        ltime1.setBounds(165, 160, 200, 30);
        lpay.setBounds(35, 195, 100, 35);
        lpay1.setBounds(165, 195, 200, 30);

        //设置字体
        lpay.setFont(new Font("等线",0,15));
        lpay1.setFont(new Font("等线",0,15));
        ltime.setFont(new Font("等线",0,15));
        ltime1.setFont(new Font("等线",0,15));
        lbt.setFont(new Font("等线",0,15));
        lbt1.setFont(new Font("等线",0,15));
        lbna.setFont(new Font("等线",0,15));
        lbna1.setFont(new Font("等线",0,15));
        lbno.setFont(new Font("等线",0,15));
        lbno1.setFont(new Font("等线",0,15));
        button_OnlyReturn.setFont(new Font("等线",0,15));
        button_return.setFont(new Font("等线",0,15));
        button_cancel.setFont(new Font("等线",0,15));

        Container container = new Container();
        container = getContentPane();
        container.setLayout(null);
        container.add(ltitle);
        container.add(lbno);
        container.add(lbno1);
        container.add(lbna);
        container.add(lbna1);
        container.add(lbt);
        container.add(lbt1);
        container.add(ltime);
        container.add(ltime1);
        container.add(lpay);
        container.add(lpay1);

        if(fine==0){
            button_return.setBounds(110,280,80,35);
            button_cancel.setBounds(210, 280, 80, 35);

            container.add(button_return);
        }
        else{
            button_return.setBounds(45,280,80,35);
            button_OnlyReturn.setBounds(130,280,140,35);
            button_cancel.setBounds(275, 280, 80, 35);

            container.add(button_return);
            container.add(button_OnlyReturn);
        }
        container.add(button_cancel);

        ActionEventHandler handler=new ActionEventHandler();
        button_cancel.addActionListener(handler);
        button_return.addActionListener(handler);
        button_OnlyReturn.addActionListener(handler);

        this.setBounds(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-200, ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-200,400,400);
        setSize(400,400);
        setResizable(false);
        setVisible(true);
    }

    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == button_cancel) {   //取消
                dispose();
            }
            else if(event.getSource()==button_return){  //还书
                if(fine==0){
//                  01  String SQL_return="UPDATE TBORROW SET RT = GETDATE() , WOM = 0  WHERE UNO = '"+Account.UNO+"' AND BNO = '"+BNO+"' AND BT = '"+BT+"' ";

                    String SQL_return="UPDATE TBORROW SET RT = GETDATE() , WOM = 0  WHERE UNO = '"+Account.UNO+"' AND BNO = '"+BNO+"' AND CONVERT(VARCHAR(100),BT,20) = '"+BT+"' ";

                    int i=Database.executeUpdate(SQL_return);
                    if(i!=0){
                        JOptionPane.showMessageDialog(null,"还书成功");
                        Panel_User_myBorrow.search();
                        Panel_UseBorrow.search();
                        dispose();
                    }
                }
                else{
//                  01  String SQL_return="UPDATE TBORROW SET RT = GETDATE() , WOM = 2  WHERE UNO = '"+Account.UNO+"' AND BNO = '"+BNO+"' AND BT = '"+BT+"'";

                    String SQL_return="UPDATE TBORROW SET RT = GETDATE() , WOM = 2  WHERE UNO = '"+Account.UNO+"' AND BNO = '"+BNO+"' AND CONVERT(VARCHAR(100),BT,20)  = '"+BT+"'";
                    int i=Database.executeUpdate(SQL_return);
                    if(i!=0){
                        JOptionPane.showMessageDialog(null,"还书成功");
                        Panel_User_myBorrow.search();
                        Panel_UseBorrow.search();
                        dispose();
                    }
                }
            }
            else if(event.getSource()==button_OnlyReturn){
                int isContinue=JOptionPane.showConfirmDialog(null,"仅还书，有罚金未缴时会影响你的借阅。","确定吗",JOptionPane.YES_NO_CANCEL_OPTION);
                if(isContinue==JOptionPane.YES_OPTION){
//                  01  String SQL_return="UPDATE TBORROW SET RT = GETDATE() , WOM = 1  WHERE UNO = '"+Account.UNO+"' AND BNO = '"+BNO+"' AND BT = '"+BT+"' ";

                    String SQL_return="UPDATE TBORROW SET RT = GETDATE() , WOM = 1  WHERE UNO = '"+Account.UNO+"' AND BNO = '"+BNO+"' AND CONVERT(VARCHAR(100),BT,20) = '"+BT+"' ";
                    int i=Database.executeUpdate(SQL_return);
                    if(i!=0){
                        JOptionPane.showMessageDialog(null,"还书成功，请及时缴费。");
                        Panel_User_myBorrow.search();
                        dispose();
                    }
                }
            }
        }
    }

}

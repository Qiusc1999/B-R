package BANDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class Form_User_infoSetting extends JFrame{
    private JPanel panel;
    private JLabel ltitle, luno, luna, lude, lusp, lugd, lpwd,luup,luat, luno1, luna1, lude1, lusp1, lugd1, lpwd1,luup1,luat1,lnnb,lnnb1;
    private JButton bclose,button_PWD,button_una,button_ude,button_usp;
    private String UNO="";
    private int NNB=0;

    public Form_User_infoSetting(String Uno) {
        super("信息修改");

        UNO=Uno;
        panel=new JPanel();
        
        ltitle = new JLabel("修改信息");
        ltitle.setFont(new Font("等线",1,17));
        luno = new JLabel("借书证号");
        luna = new JLabel("姓名:");
        lude = new JLabel("系别:");
        lusp = new JLabel("专业:");
        lugd = new JLabel("级别:");
        lpwd = new JLabel("密码:");
        luup=new JLabel("最多可借阅书数:");
        luat=new JLabel("最长可借阅天数:");
        lnnb=new JLabel("当前已借阅数:");
        button_PWD=new JButton("修改密码");
        button_una=new JButton("修改姓名");
        button_ude=new JButton("修改系别");
        button_usp=new JButton("修改专业");
        bclose = new JButton("关闭");
        //获取已借阅数
        String SQL1="SELECT COUNT(*) FROM TUSER,TBORROW WHERE TUSER.UNO=TBORROW.UNO AND TUSER.UNO = '"+UNO+"' AND RT IS NULL GROUP BY TUSER.UNO";
        ResultSet rs1=Database.executeQuery(SQL1);
        try {
            while (rs1.next()){
                NNB=rs1.getInt(1);
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null,sqlE);
        }
        //获取用户信息
        String SQL = "SELECT UNO,UNA,UDE,USP,TUSER.UGD,PWD,UUP,UAT FROM TUSER,TGDINFO WHERE TUSER.UGD=TGDINFO.UGD AND UNO = '" + Uno + "'";
        ResultSet rs = Database.executeQuery(SQL);

        try {
            while (rs.next()){
                luno1 = new JLabel(rs.getString(1));
                luna1 = new JLabel(rs.getString(2));
                lude1 = new JLabel(rs.getString(3));
                lusp1 = new JLabel(rs.getString(4));
                lugd1 = new JLabel(rs.getString(5));
                lpwd1 = new JLabel("***");
                luup1=new JLabel(rs.getString(7));
                luat1=new JLabel(rs.getString(8));
                lnnb1=new JLabel(String.valueOf(NNB));
            }
        } catch (SQLException sqlE) {
            JOptionPane.showMessageDialog(null, sqlE, "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        
        
        //组件放入Panel
        
        panel.setLayout(null);
        ltitle.setBounds(150, 15, 100, 20);
        luno.setBounds(35, 50, 100, 20);    luno1.setBounds(170, 50, 200, 20);
        luna.setBounds(35, 80, 100, 20);    luna1.setBounds(170, 80, 150, 20);   button_una.setBounds(270, 80, 100, 25);
        lpwd.setBounds(35, 110, 100, 20);   lpwd1.setBounds(170, 110, 150, 20);  button_PWD.setBounds(270, 110, 100, 25);
        lude.setBounds(35, 140, 100, 20);   lude1.setBounds(170, 140, 100, 20);  button_ude.setBounds(270, 140, 100, 25);
        lusp.setBounds(35, 170, 100, 20);   lusp1.setBounds(170, 170, 150, 20);  button_usp.setBounds(270, 170, 100, 25);
        bclose.setBounds(265, 295, 100, 35);

        //设置字体
        lpwd.setFont(new Font("等线",0,15));
        lude.setFont(new Font("等线",0,15));
        lugd.setFont(new Font("等线",0,15));
        luna.setFont(new Font("等线",0,15));
        luno.setFont(new Font("等线",0,15));
        lusp.setFont(new Font("等线",0,15));
        lpwd1.setFont(new Font("等线",0,15));
        lude1.setFont(new Font("等线",0,15));
        lugd1.setFont(new Font("等线",0,15));
        luna1.setFont(new Font("等线",0,15));
        luno1.setFont(new Font("等线",0,15));
        lusp1.setFont(new Font("等线",0,15));
        luat.setFont(new Font("等线",0,15));
        luat1.setFont(new Font("等线",0,15));
        luup.setFont(new Font("等线",0,15));
        luup1.setFont(new Font("等线",0,15));
        lnnb.setFont(new Font("等线",0,15));
        lnnb1.setFont(new Font("等线",0,15));
        button_PWD.setFont(new Font("等线",0,15));
        button_ude.setFont(new Font("等线",0,15));
        button_usp.setFont(new Font("等线",0,15));
        button_una.setFont(new Font("等线",0,15));
        bclose.setFont(new Font("等线",0,15));

        if(lugd1.getText().equals("教师")){
            lugd.setBounds(35, 165, 130, 35);
            lugd1.setBounds(170, 165, 150, 30);
            luup.setBounds(35, 205, 130, 35);
            luup1.setBounds(170, 205, 150, 30);
            luat.setBounds(35, 245, 130, 30);
            luat1.setBounds(170, 245, 150, 30);
            lnnb.setBounds(35,285,130,20);
            lnnb1.setBounds(170,285,150,20);
        }else {
            lugd.setBounds(35, 220, 130, 20);
            lugd1.setBounds(170, 220, 150, 20);
            luup.setBounds(35, 250, 130, 20);
            luup1.setBounds(170, 250, 150, 20);
            luat.setBounds(35, 280, 130, 20);
            luat1.setBounds(170, 280, 150, 20);
            lnnb.setBounds(35,310,130,20);
            lnnb1.setBounds(170,310,150,20);

            panel.add(lusp);
            panel.add(lusp1);
            panel.add(button_usp);
        }

        panel.add(ltitle);
        panel.add(luno);
        panel.add(luno1);
        panel.add(luna);
        panel.add(luna1);
        panel.add(button_una);
        panel.add(lude);
        panel.add(lude1);
        panel.add(button_ude);
        panel.add(lpwd);
        panel.add(lpwd1);
        panel.add(button_PWD);
        panel.add(lugd);
        panel.add(lugd1);
        panel.add(luup);
        panel.add(luup1);
        panel.add(luat);
        panel.add(luat1);
        panel.add(lnnb);
        panel.add(lnnb1);
        panel.add(bclose);

        this.add(panel);

        ActionEventHandler handler=new ActionEventHandler();
        bclose.addActionListener(handler);
        button_una.addActionListener(handler);
        button_PWD.addActionListener(handler);
        button_usp.addActionListener(handler);
        button_ude.addActionListener(handler);

        this.setBounds(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-200, ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-200,400,400);
        setSize(400,400);
        setResizable(false);
        setVisible(true);
    }

    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == bclose) {
                dispose();
            }
            else if(event.getSource()==button_PWD){     //修改密码
                //管理员验证密码
                String modify="";
                String nowPWD=JOptionPane.showInputDialog(null,"请输入当前密码进行验证");
                String SQL_checkNowPWD="SELECT PWD FROM TUSER WHERE UNO = '"+Account.UNO+"'";
                ResultSet rs_checkNowPWD=Database.executeQuery(SQL_checkNowPWD);
                try {
                    while (rs_checkNowPWD.next()) {
                        if (nowPWD.equals(rs_checkNowPWD.getString(1))) { //密码正确
                            modify=JOptionPane.showInputDialog(null,"输入修改后的密码").toString();
                            String SQL="UPDATE TUSER SET PWD = '"+modify+"' WHERE UNO= '"+UNO+"'";
                            int i=Database.executeUpdate(SQL);
                            if(i!=0){
                                JOptionPane.showMessageDialog(null,"修改成功","提醒",JOptionPane.INFORMATION_MESSAGE);
                            }
                        }else{   //密码错误
                            JOptionPane.showMessageDialog(null,"密码错误！","警告",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }catch (SQLException sqlE){
                    JOptionPane.showMessageDialog(null,sqlE);
                }
            }
            else if(event.getSource()==button_una){     //修改姓名
                //管理员验证密码
                String modify="";
                String nowPWD=JOptionPane.showInputDialog(null,"请输入当前密码进行验证");
                String SQL_checkNowPWD="SELECT PWD FROM TUSER WHERE UNO = '"+Account.UNO+"'";
                ResultSet rs_checkNowPWD=Database.executeQuery(SQL_checkNowPWD);
                try {
                    while (rs_checkNowPWD.next()) {
                        if (nowPWD.equals(rs_checkNowPWD.getString(1))) { //密码正确
                            modify=JOptionPane.showInputDialog(null,"输入修改后的姓名").toString();
                            if(!Pattern.matches("[a-zA-Z\\u4e00-\\u9fa5]+",modify)){
                                JOptionPane.showMessageDialog(null, "姓名不能为空，只能为中文或英文！", "警告", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                            String SQL="UPDATE TUSER SET UNA = '"+modify+"' WHERE UNO= '"+UNO+"'";
                            int i=Database.executeUpdate(SQL);
                            if(i!=0){
                                JOptionPane.showMessageDialog(null,"修改成功","提醒",JOptionPane.INFORMATION_MESSAGE);
                                luna1.setText(modify);
                            }
                        }else{   //密码错误
                            JOptionPane.showMessageDialog(null,"密码错误！","警告",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }catch (SQLException sqlE){
                    JOptionPane.showMessageDialog(null,sqlE);
                }
            }
            else if(event.getSource()==button_ude){     //修改系别
                //管理员验证密码
                String modify="";
                String nowPWD=JOptionPane.showInputDialog(null,"请输入当前密码进行验证");
                String SQL_checkNowPWD="SELECT PWD FROM TUSER WHERE UNO = '"+Account.UNO+"'";
                ResultSet rs_checkNowPWD=Database.executeQuery(SQL_checkNowPWD);
                try {
                    while (rs_checkNowPWD.next()) {
                        if (nowPWD.equals(rs_checkNowPWD.getString(1))) { //密码正确
                            modify=JOptionPane.showInputDialog(null,"输入修改后的系别").toString();
                            String SQL="UPDATE TUSER SET UDE = '"+modify+"' WHERE UNO= '"+UNO+"'";
                            int i=Database.executeUpdate(SQL);
                            if(i!=0){
                                JOptionPane.showMessageDialog(null,"修改成功","提醒",JOptionPane.INFORMATION_MESSAGE);
                                lude1.setText(modify);
                            }
                        }else{   //密码错误
                            JOptionPane.showMessageDialog(null,"密码错误！","警告",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }catch (SQLException sqlE){
                    JOptionPane.showMessageDialog(null,sqlE);
                }
            }
            else if(event.getSource()==button_usp){     //修改专业
                //管理员验证密码
                String modify="";
                String nowPWD=JOptionPane.showInputDialog(null,"请输入当前密码进行验证");
                String SQL_checkNowPWD="SELECT PWD FROM TUSER WHERE UNO = '"+Account.UNO+"'";
                ResultSet rs_checkNowPWD=Database.executeQuery(SQL_checkNowPWD);
                try {
                    while (rs_checkNowPWD.next()) {
                        if (nowPWD.equals(rs_checkNowPWD.getString(1))) { //密码正确
                            modify=JOptionPane.showInputDialog(null,"输入修改后的专业").toString();
                            String SQL="UPDATE TUSER SET USP = '"+modify+"' WHERE UNO= '"+UNO+"'";
                            int i=Database.executeUpdate(SQL);
                            if(i!=0){
                                JOptionPane.showMessageDialog(null,"修改成功","提醒",JOptionPane.INFORMATION_MESSAGE);
                                lusp1.setText(modify);
                            }
                        }else{   //密码错误
                            JOptionPane.showMessageDialog(null,"密码错误！","警告",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }catch (SQLException sqlE){
                    JOptionPane.showMessageDialog(null,sqlE);
                }
            }
        }
    }
}

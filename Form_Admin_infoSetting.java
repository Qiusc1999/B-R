package BANDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Form_Admin_infoSetting extends JFrame{
    private JPanel panel;
    private JLabel ltitle, lano, lana,lpwd, lano1, lana1, lpwd1;
    private JButton bclose,button_PWD, button_ana;
    private String ANO ="";

    public Form_Admin_infoSetting(String ANO) {
        super("修改信息");

        this.ANO =ANO;
        panel=new JPanel();
        
        ltitle = new JLabel("修改信息");
        ltitle.setFont(new Font("等线",1,17));
        lano = new JLabel("管理员编号:");
        lana = new JLabel("姓名:");
        lpwd = new JLabel("密码:");
        button_PWD=new JButton("修改密码");
        button_ana =new JButton("修改姓名");

        bclose = new JButton("关闭");
        //获取用户信息
        String SQL = "SELECT ANO,ANA,PWD FROM TADMIN WHERE ANO = '" + ANO + "'";
        ResultSet rs = Database.executeQuery(SQL);

        try {
            while (rs.next()){
                lano1 = new JLabel(rs.getString(1));
                lana1 = new JLabel(rs.getString(2));
                lpwd1 = new JLabel("*****");
            }
        } catch (SQLException sqlE) {
            JOptionPane.showMessageDialog(null, sqlE, "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        
        
        //组件放入Panel
        
        panel.setLayout(null);
        ltitle.setBounds(150, 15, 100, 20);
        lano.setBounds(35, 50, 100, 20);    lano1.setBounds(170, 50, 200, 20);
        lana.setBounds(35, 80, 100, 20);    lana1.setBounds(170, 80, 150, 20);   button_ana.setBounds(270, 80, 100, 25);
        lpwd.setBounds(35, 110, 100, 20);   lpwd1.setBounds(170, 110, 150, 20);  button_PWD.setBounds(270, 110, 100, 25);
        bclose.setBounds(265, 295, 100, 35);

        //设置字体
        lpwd.setFont(new Font("等线",0,15));
        lana.setFont(new Font("等线",0,15));
        lano.setFont(new Font("等线",0,15));
        lpwd1.setFont(new Font("等线",0,15));
        lana1.setFont(new Font("等线",0,15));
        lano1.setFont(new Font("等线",0,15));
        button_PWD.setFont(new Font("等线",0,15));
        button_ana.setFont(new Font("等线",0,15));
        bclose.setFont(new Font("等线",0,15));


        panel.add(ltitle);
        panel.add(lano);
        panel.add(lano1);
        panel.add(lana);
        panel.add(lana1);
        panel.add(button_ana);
        panel.add(lpwd);
        panel.add(lpwd1);
        panel.add(button_PWD);
        panel.add(bclose);

        this.add(panel);

        ActionEventHandler handler=new ActionEventHandler();
        bclose.addActionListener(handler);
        button_ana.addActionListener(handler);
        button_PWD.addActionListener(handler);

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
                String SQL_checkNowPWD="SELECT PWD FROM TADMIN WHERE ANO = '"+ANO+"'";
                ResultSet rs_checkNowPWD=Database.executeQuery(SQL_checkNowPWD);
                try {
                    while (rs_checkNowPWD.next()) {
                        if (nowPWD.equals(rs_checkNowPWD.getString(1))) { //密码正确
                            modify=JOptionPane.showInputDialog(null,"输入修改后的密码").toString();
                            String SQL="UPDATE TADMIN SET PWD = '"+modify+"' WHERE ANO= '"+ANO+"'";
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
            else if(event.getSource()== button_ana){     //修改姓名
                //管理员验证密码
                String modify="";
                String nowPWD=JOptionPane.showInputDialog(null,"请输入当前密码进行验证");
                String SQL_checkNowPWD="SELECT PWD FROM TADMIN WHERE ANO = '"+ANO+"'";
                ResultSet rs_checkNowPWD=Database.executeQuery(SQL_checkNowPWD);
                try {
                    while (rs_checkNowPWD.next()) {
                        if (nowPWD.equals(rs_checkNowPWD.getString(1))) { //密码正确
                            modify=JOptionPane.showInputDialog(null,"输入修改后的姓名").toString();
                            String SQL="UPDATE TADMIN SET ANA = '"+modify+"' WHERE ANO= '"+ANO+"'";
                            int i=Database.executeUpdate(SQL);
                            if(i!=0){
                                JOptionPane.showMessageDialog(null,"修改成功","提醒",JOptionPane.INFORMATION_MESSAGE);
                                lana1.setText(modify);
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

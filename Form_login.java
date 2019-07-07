package BANDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Form_login extends JFrame {
    private JTabbedPane tpLogin;
    private JPanel puser,padmin;
    private JLabel lu1,lu2,lu3,la1,la2,la3;
    private JTextField tuaccount,taaccount;
    private JPasswordField tupwd,tapwd;
    private JButton bulogin,balogin, buclose,baclose;
    private ImageIcon imageIcon_background,imageIcon_background1;
    private JLabel label_background,label_background1;

    public Form_login(){
        super("借阅管理子系统");
        tpLogin=new JTabbedPane();          //选项卡面板
        tpLogin.setBackground(Color.WHITE);

        //设置背景图片
        imageIcon_background=new ImageIcon("D:\\IdeaProjects\\LMS\\BG\\登录.png");
        imageIcon_background1=new ImageIcon("D:\\IdeaProjects\\LMS\\BG\\登录.png");
        label_background=new JLabel(imageIcon_background);
        label_background1=new JLabel(imageIcon_background1);
        label_background.setBounds(0,0,400,300);
        label_background1.setBounds(0,0,400,300);

        lu1=new JLabel("用户登录");
        lu2=new JLabel("借书证号:");
        lu3=new JLabel("密码:");
        la1=new JLabel("管理员登录");
        la2=new JLabel("管理员号:");
        la3=new JLabel("密码:");
        tuaccount=new JTextField(10);
//        tuaccount.setBackground(null);
        tuaccount.setOpaque(false);
        tupwd=new JPasswordField(10);
        tupwd.setOpaque(false);
        taaccount=new JTextField(10);
        taaccount.setOpaque(false);
        tapwd=new JPasswordField(10);
        tapwd.setOpaque(false);
        bulogin=new JButton("登录");
        balogin=new JButton("登录");
        buclose =new JButton("退出");
        baclose =new JButton("退出");

        puser=new JPanel();              //用户登录面板
        puser.setBackground(Color.WHITE);
        puser.setLayout(null);
        lu1.setBounds(145,25,100,40);
        lu2.setBounds(55,80,80,25);     tuaccount.setBounds(150,80,180,25);
        lu3.setBounds(55,120,80,25);    tupwd.setBounds(150,120,180,25);
        bulogin.setBounds(60,160,100,35);  buclose.setBounds(210,160,100,35);
        puser.add(lu1);
        puser.add(lu2);
        puser.add(lu3);
        puser.add(tuaccount);
        puser.add(tupwd);
        puser.add(bulogin);
        puser.add(buclose);
        puser.add(label_background,-1);

        padmin=new JPanel();              //管理员登录面板
        padmin.setBackground(Color.WHITE);
        padmin.setLayout(null);
        la1.setBounds(140,25,120,40);
        la2.setBounds(55,80,80,25);     taaccount.setBounds(150,80,180,25);
        la3.setBounds(55,120,80,25);    tapwd.setBounds(150,120,180,25);
        balogin.setBounds(60,160,100,35);  baclose.setBounds(210,160,100,35);
        padmin.add(la1);
        padmin.add(la2);
        padmin.add(la3);
        padmin.add(taaccount);
        padmin.add(tapwd);
        padmin.add(balogin);
        padmin.add(baclose);
        padmin.add(label_background1,-1);

        tpLogin.addTab("用户登录",puser);        //将用户登录添加到选项卡面板
        tpLogin.addTab("管理员登录",padmin);     //将管理员登录添加到选项卡面板

        //设置字体
        lu1.setFont(new Font("等线",1,20));
        lu2.setFont(new Font("等线",0,15));
        lu3.setFont(new Font("等线",0,15));
        la1.setFont(new Font("等线",1,20));
        la2.setFont(new Font("等线",0,15));
        la3.setFont(new Font("等线",0,15));
        tuaccount.setFont(new Font("等线",0,15));
        taaccount.setFont(new Font("等线",0,15));
        bulogin.setFont(new Font("等线",0,15));
        buclose.setFont(new Font("等线",0,15));
        balogin.setFont(new Font("等线",0,15));
        baclose.setFont(new Font("等线",0,15));
        tpLogin.setFont(new Font("等线",0,15));

        //添加监听器
        ActionEventHandler handler=new ActionEventHandler();
        bulogin.addActionListener(handler);
        balogin.addActionListener(handler);
        buclose.addActionListener(handler);
        baclose.addActionListener(handler);


        Container container=new Container();
        container=getContentPane();
        container.setBackground(Color.WHITE);
        ((JPanel)container).setOpaque(false);
        container.add(tpLogin);

        this.setBounds(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-200, ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-150,400,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setResizable(false);
        setVisible(true);
    }

    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource()==bulogin){
                String UNO=tuaccount.getText();
                String PWD=String.valueOf(tupwd.getPassword());
                if(UNO.isEmpty()||PWD.isEmpty()){
                    JOptionPane.showMessageDialog(null,"借书证号或密码不能为空！","提示",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String SQL="SELECT PWD ,COUNT(*) FROM TUSER WHERE UNO = '"+UNO+"' GROUP BY PWD";
                ResultSet rs=Database.executeQuery(SQL);
                try{
                    if(!rs.isBeforeFirst()){
                        JOptionPane.showMessageDialog(null, "借书证号或密码不正确！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    while (rs.next()) {
                        String getPWD = rs.getString(1);
                        if (!PWD.equals(getPWD)) {    //比对密码不正确
                            JOptionPane.showMessageDialog(null, "借书证号或密码不正确！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        } else {
                            JOptionPane.showMessageDialog(null, "登录成功");
                            Account.setUserAccount(UNO);
                            Form_user_Main form_user_main = new Form_user_Main();
                            dispose();
                        }
                    }
                }catch (SQLException sqlE){
                    JOptionPane.showMessageDialog(null,sqlE);
                }
            }
            else if(event.getSource()==balogin){    //管理员登录
                String ANO=taaccount.getText();
                String PWD=String.valueOf(tapwd.getPassword());
                if(ANO.isEmpty()||PWD.isEmpty()){
                    JOptionPane.showMessageDialog(null,"账号或密码不能为空！","提示",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                String SQL="SELECT PWD ,COUNT(*),ANA FROM TADMIN WHERE ANO = '"+ANO+"' GROUP BY PWD,ANA";
                ResultSet rs=Database.executeQuery(SQL);
                try{
                    if(!rs.isBeforeFirst()){
                        JOptionPane.showMessageDialog(null, "借书证号或密码不正确！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    while (rs.next()) {
                        String getPWD = rs.getString(1);
                        if (!PWD.equals(getPWD)) {    //比对密码不正确
                            JOptionPane.showMessageDialog(null, " 账号或密码不正确！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        } else {
                            JOptionPane.showMessageDialog(null, "登录成功");
                            Account.ANO=ANO;
                            Account.ANA=rs.getString(3);
                            Form_admin_Main form_admin_main = new Form_admin_Main();
                            dispose();
                        }
                    }
                }catch (SQLException sqlE){
                    JOptionPane.showMessageDialog(null,sqlE);
                }
            }
            else if(event.getSource()==buclose||event.getSource()==baclose){
                System.exit(0);
            }

        }
    }
}

//                        while (rs.next()){
////                        int i=rs.getInt(2);      //查找是否有该用户名
////                        if(i==0){
////                            JOptionPane.showMessageDialog(null,"借书证号或密码不正确！","提示",JOptionPane.INFORMATION_MESSAGE);
////                            return;
////                        }
//                        String getPWD=rs.getString(1);
//                        if(!PWD.equals(getPWD)){    //比对密码不正确
//                            JOptionPane.showMessageDialog(null,"借书证号或密码不正确！","提示",JOptionPane.INFORMATION_MESSAGE);
//                            return;
//                        }
//                        else {
//                            JOptionPane.showMessageDialog(null,"登录成功");
//                            Account.setUserAccount(UNO);
//                            Form_user_Main form_user_main=new Form_user_Main();
//                            form_user_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                            dispose();
//                        }

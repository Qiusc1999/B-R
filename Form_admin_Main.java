package BANDR;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form_admin_Main extends JFrame{
    private JTabbedPane tabbedPane;     //选项卡面板
    private Panel_ManageBook panel_manageBook;
    private Panel_ManageUser panel_manageUser;
    private Panel_ManageBorrow panel_manageBorrow;
    private Panel_ManageAdmin panel_manageAdmin;
    private JButton button_logout,button_exit,button_set,button_about;
    private JPanel panel_button;
    private String nowAccount ="";
    private ImageIcon imageIcon_background;
    private JLabel label_background;

    public Form_admin_Main(){
        super("借阅管理子系统(管理员版)");
        imageIcon_background=new ImageIcon("D:\\IdeaProjects\\LMS\\BG\\BG.png");
        label_background=new JLabel(imageIcon_background);
        label_background.setBounds(0,0,1200,735);

        UIManager.put("TabbedPane.contentOpaque", false);   //设置Tabbedpane透明
        nowAccount =Account.ANO;
        button_set=new JButton("修改信息");
        button_logout=new JButton("注销");
        button_about=new JButton("关于");
        button_exit=new JButton("退出");

        tabbedPane=new JTabbedPane();                   //选项卡Panel
        panel_manageBook=new Panel_ManageBook();        //图书管理Panel
        panel_manageBook.setOpaque(false);
        panel_manageUser=new Panel_ManageUser();        //用户管理Panel
        panel_manageUser.setOpaque(false);
        panel_manageBorrow=new Panel_ManageBorrow();    //借阅管理Panel
        panel_manageBorrow.setOpaque(false);

        //功能面板添加到选项卡面板
        tabbedPane.addTab("图书管理",panel_manageBook);
        tabbedPane.addTab("用户管理",panel_manageUser);
        tabbedPane.addTab("借阅管理",panel_manageBorrow);
        if(nowAccount.equals("1")){      //高级管理员
            panel_manageAdmin=new Panel_ManageAdmin();      //管理员管理Panel
            tabbedPane.addTab("管理员账户管理",panel_manageAdmin);
        }

        panel_button=new JPanel();
//        panel_button.setBackground(Color.WHITE);
        panel_button.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_button.add(button_about);
        panel_button.add(button_set);
        panel_button.add(button_logout);
        panel_button.add(button_exit);

        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setFont(new Font("等线",0,15));
        button_set.setFont(new Font("等线",0,15));
        button_about.setFont(new Font("等线",0,15));
        button_logout.setFont(new Font("等线",0,15));
        button_exit.setFont(new Font("等线",0,15));

        Container container=new Container();
        container=getContentPane();
        container.setLayout(null);
        container.setBackground(Color.WHITE);
        tabbedPane.setBounds(0,0,1200,665);
        container.add(tabbedPane);
        JLabel label_nowAccount=new JLabel("欢迎使用 当前管理员账户:"+ nowAccount+"  姓名:"+Account.ANA);
        label_nowAccount.setBounds(10,665,600,30);
        label_nowAccount.setFont(new Font("等线", 0, 13));
        container.add(label_nowAccount);
        panel_button.setBounds(600,662,580,35);
        panel_button.setOpaque(false);
        container.add(panel_button);
        container.add(label_background,-1);

        ActionEventHandler handler=new ActionEventHandler();
        button_set.addActionListener(handler);
        button_logout.addActionListener(handler);
        button_exit.addActionListener(handler);
        button_about.addActionListener(handler);

        this.setBounds(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-500, ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-400,1000,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200,735);
        setResizable(false);
        setVisible(true);
    }

    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource()==button_logout){
                int isLogout= JOptionPane.showConfirmDialog(null,"是否注销","是否注销",JOptionPane.YES_NO_OPTION);
                if(isLogout==JOptionPane.YES_OPTION){
                    Form_login form_login=new Form_login();
                    dispose();
                }
            }
            else if (event.getSource()==button_set){
                Form_Admin_infoSetting form_admin_infoSetting=new Form_Admin_infoSetting(Account.ANO);
            }
            else if(event.getSource()==button_exit){
                System.exit(0);
            }
            else if(event.getSource()==button_about){
                Form_about form_about=new Form_about();
            }
        }
    }

}

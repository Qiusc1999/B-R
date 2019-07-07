package BANDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form_user_Main extends JFrame{
    private JTabbedPane tabbedPane;     //选项卡面板
    private Panel_UseBorrow panel_useBorrow;
    private Panel_User_myBorrow panel_user_myBorrow;
    private JPanel panel_button,panelnowAccount;
    private JButton button_logout,button_exit,button_set,button_about;
    private ImageIcon imageIcon_background;
    private JLabel label_background;
    public static JLabel label_nowAccount;

    public Form_user_Main(){
        super("借阅管理子系统");
        imageIcon_background=new ImageIcon("D:\\IdeaProjects\\LMS\\BG\\BG.png");
        label_background=new JLabel(imageIcon_background);
        label_background.setBounds(0,0,1200,735);

        int n=Function.getNowNumofBorrow(Account.UNO);
        int m=Function.getNumOvertime(Account.UNO);
        label_nowAccount=new JLabel("欢迎使用 当前账户:"+Account.UNO+"/最多借阅数:"+Account.UUP+"/当前已借阅数:"+n+"/最长借阅时间:"+Account.UAT+"天/已有 "+m+" 本书逾期");
        label_nowAccount.setBounds(0,0,600,30);
        label_nowAccount.setFont(new Font("等线", 0, 13));

        UIManager.put("TabbedPane.contentOpaque", false);   //设置Tabbedpane透明
        tabbedPane=new JTabbedPane(); //选项卡Panel
        panel_useBorrow=new Panel_UseBorrow();
        panel_useBorrow.setOpaque(false);
        panel_user_myBorrow=new Panel_User_myBorrow();
        panel_user_myBorrow.setOpaque(false);

        //功能面板添加到选项卡面板
        tabbedPane.addTab("借阅",panel_useBorrow);
        tabbedPane.addTab("我的借阅",panel_user_myBorrow);

        panelnowAccount=new JPanel();
        panelnowAccount.setOpaque(false);
        panelnowAccount.add(label_nowAccount);
        panelnowAccount.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel_button=new JPanel();
        panel_button.setOpaque(false);
        button_logout=new JButton("注销");
        button_exit=new JButton("退出");
        button_set=new JButton("修改信息");
        button_about=new JButton("关于");
        panel_button.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_button.add(button_about);
        panel_button.add(button_set);
        panel_button.add(button_logout);
        panel_button.add(button_exit);

        //设置字体
        tabbedPane.setFont(new Font("等线",0,15));
        button_set.setFont(new Font("等线",0,15));
        button_about.setFont(new Font("等线",0,15));
        button_logout.setFont(new Font("等线",0,15));
        button_exit.setFont(new Font("等线",0,15));

        Container container=new Container();
        container=getContentPane();
        container.setLayout(null);
        container.setBackground(Color.WHITE);
        tabbedPane.setBounds(0,0,1200,660);
        container.add(tabbedPane);
        panelnowAccount.setBounds(10,660,600,40);
        container.add(panelnowAccount);
        panel_button.setBounds(560,660,600,40);
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
    public class ActionEventHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(event.getSource()==button_logout){
                int isLogout= JOptionPane.showConfirmDialog(null,"是否注销","是否注销",JOptionPane.YES_NO_OPTION);
                if(isLogout==JOptionPane.YES_OPTION){
                    Form_login form_login=new Form_login();
                    dispose();
                }
            }
            else if (event.getSource()==button_set){
                Form_User_infoSetting form_user_infoSetting=new Form_User_infoSetting(Account.UNO);
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

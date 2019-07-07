package BANDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.WeakHashMap;

public class Form_about extends JFrame{
    private JLabel ltitle, lname, lmakername, lsno, lclass,lname1, lmakername1, lsno1, lclass1,lcontent,lcontent1,lcontent2,lcontent3;
    private JButton bclose;

    public Form_about() {
        super("关于");
        ltitle = new JLabel("关于");
        lname=new JLabel("系统名称");
        lmakername=new JLabel("姓名");
        lclass=new JLabel("班级");
        lclass1=new JLabel("物联网工程学院 计科1702");
        lsno=new JLabel("学号");
        lname1=new JLabel("图书馆借阅及管理系统");
        lmakername1=new JLabel("邱思超");
        lsno1=new JLabel("1033170224");

        lcontent=new JLabel("本系统运用数据库相关知识进行数据存储");
        lcontent1=new JLabel("和处理，运用JAVA语言编写程序。");
        lcontent2=new JLabel("感谢钱瑛老师的指导和帮助。");
        lcontent3=new JLabel("2019年6月24日 1:10");
        bclose = new JButton("关闭");

        ltitle.setFont(new Font("等线",1,17));
        lname.setFont(new Font("等线",0,15));
        lname1.setFont(new Font("等线",0,15));
        lmakername.setFont(new Font("等线",0,15));
        lmakername1.setFont(new Font("等线",0,15));
        lclass.setFont(new Font("等线",0,15));
        lclass1.setFont(new Font("等线",0,15));
        lcontent.setFont(new Font("等线",0,15));
        lcontent1.setFont(new Font("等线",0,15));
        lcontent2.setFont(new Font("等线",0,15));
        lcontent3.setFont(new Font("等线",0,15));
        lsno.setFont(new Font("等线",0,15));
        lsno1.setFont(new Font("等线",0,15));
        bclose.setFont(new Font("等线",0,15));
        bclose.setFont(new Font("等线",0,15));

        Container container = new Container();
        container = getContentPane();
        container.setLayout(null);

        ltitle.setBounds(155, 20, 100, 20);
        lname.setBounds(35, 55, 100, 35);
        lname1.setBounds(165, 55, 200, 30);
        lmakername.setBounds(35, 90, 100, 35);
        lmakername1.setBounds(165, 90, 200, 30);
        lsno.setBounds(35, 125, 100, 35);
        lsno1.setBounds(165, 125, 200, 30);
        lclass.setBounds(35, 160, 100, 35);
        lclass1.setBounds(165, 160, 200, 30);
        lcontent.setBounds(35, 200, 300, 20);
        lcontent1.setBounds(35, 220, 300, 20);
        lcontent2.setBounds(35, 240, 300, 20);
        lcontent3.setBounds(35, 260, 300, 20);

        bclose.setBounds(275, 280, 65, 40);

        container.add(ltitle);
        container.add(lname);
        container.add(lname1);
        container.add(lmakername);
        container.add(lmakername1);
        container.add(lsno);
        container.add(lsno1);
        container.add(lclass);
        container.add(lclass1);
        container.add(lcontent);
        container.add(lcontent1);
        container.add(lcontent2);
        container.add(lcontent3);
        container.add(bclose);

        ActionEventHandler handler=new ActionEventHandler();
        bclose.addActionListener(handler);

        this.setBounds(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-200, ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-200,400,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380,400);
        setResizable(false);
        setVisible(true);
    }

    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == bclose) {
                dispose();
            }
        }
    }
}

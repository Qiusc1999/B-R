package BANDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.regex.Pattern;

public class Form_admin_addAdmin extends JFrame{
    private JPanel panel;
    private JLabel ltitle, lano,lpwd, lana;
    private JTextField tano, tana;
    private JPasswordField tpwd;
    private JButton bconfrim,breset, bclose;

    public Form_admin_addAdmin(){
        super("管理员注册");
        panel=new JPanel();

        ltitle=new JLabel("管理员注册");
        ltitle.setFont(new Font("等线",1,17));
        lano =new JLabel("管理员编号:");
        lpwd=new JLabel("密码:");
        lana =new JLabel("姓名:");
        tano =new JTextField(20);
        tpwd=new JPasswordField(20);
        tana =new JTextField(20);
        bconfrim=new JButton("确定");
        breset=new JButton("重输");
        bclose =new JButton("关闭");

        //设置字体
        lana.setFont(new Font("等线",0,15));
        lano.setFont(new Font("等线",0,15));
        lpwd.setFont(new Font("等线",0,15));
        tana.setFont(new Font("等线",0,15));
        tano.setFont(new Font("等线",0,15));
        bconfrim.setFont(new Font("等线",0,15));
        breset.setFont(new Font("等线",0,15));
        bconfrim.setFont(new Font("等线",0,15));


        Container container=new Container();
        container=getContentPane();
        container.setLayout(null);

        ltitle.setBounds(150,20,100,20);
        lano.setBounds(35,55,100,35);    tano.setBounds(165,55,200,30);
        lpwd.setBounds(35,90,100,35);    tpwd.setBounds(165,90,200,30);
        lana.setBounds(35,125,100,35);    tana.setBounds(165,125,200,30);
        bconfrim.setBounds(70,280,70,35);
        breset.setBounds(165,280,70,35);
        bclose.setBounds(265,280,70,35);

        container.add(ltitle);
        container.add(lano);
        container.add(tano);
        container.add(lpwd);
        container.add(tpwd);
        container.add(lana);
        container.add(tana);
        container.add(bconfrim);
        container.add(breset);
        container.add(bclose);

        //添加监听器
        ActionEventHandler handler=new ActionEventHandler();
        bconfrim.addActionListener(handler);
        breset.addActionListener(handler);
        bclose.addActionListener(handler);

        this.setBounds(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-200, ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-200,400,400);
        setSize(400,400);
        setResizable(false);
        setVisible(true);
    }

    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource()==bconfrim){
                String ANO=tano.getText();
                String ANA=tana.getText();
                String PWD=tpwd.getText();
                if(!Pattern.matches("[a-zA-Z0-9]{10}",ANO)){
                    JOptionPane.showMessageDialog(null, "管理员编号必须为10位的字母数字组合！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if(!Pattern.matches("[a-zA-Z0-9]{10,16}",PWD)){
                    JOptionPane.showMessageDialog(null, "密码必须为1到10位的字母数字组合！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if(!Pattern.matches("[a-zA-Z\\u4e00-\\u9fa5]+",ANA)){
                    JOptionPane.showMessageDialog(null, "姓名不能为空，只能为中文或英文！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String SQL="INSERT INTO TADMIN SELECT '"+ ANO+"','"+ANA+"','"+PWD+"'";
                int i=Database.executeUpdate(SQL);
                if(i!=0) {
                    JOptionPane.showMessageDialog(null, i + "个管理员添加成功");
                    Panel_ManageAdmin.search();
                    dispose();
                }
            }
            else if(event.getSource()==breset){     //重输
                tano.setText("");   //清空输入框
                tpwd.setText("");
                tana.setText("");
            }
            else if(event.getSource()==bclose){     //关闭
                dispose();
            }
        }
    }

}

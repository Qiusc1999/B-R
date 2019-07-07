package BANDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.regex.Pattern;

public class Form_admin_addUser extends JFrame {
    private JPanel panel;
    private JLabel ltitle, luno,lpwd,luna,lude,lusp,lugd;
    private JTextField tuno,tuna,tude,tusp;
    private JPasswordField tpwd;
    private JButton bconfrim,breset, bclose;
    private String string_UGD ="本科生";
    private JComboBox comboBox_UGD;

    public Form_admin_addUser(){
        super("用户注册");
        panel=new JPanel();
        panel.setLayout(null);

        ltitle=new JLabel("用户注册");
        ltitle.setFont(new Font("等线",1,17));
        luno=new JLabel("借书证号:");
        lpwd=new JLabel("密码:");
        luna=new JLabel("姓名:");
        lude=new JLabel("所在系别:");
        lusp=new JLabel("所在专业:");
        lugd=new JLabel("级别:");
        tuno =new JTextField(20);
        tpwd=new JPasswordField(20);
        tuna=new JTextField(20);
        tude=new JTextField(20);
        tusp=new JTextField(20);
        bconfrim=new JButton("确定");
        breset=new JButton("重输");
        bclose =new JButton("关闭");



        String[] Ugds={"本科生","硕士研究生","博士研究生","教师"};
        comboBox_UGD =new JComboBox(Ugds);
        comboBox_UGD.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    switch (comboBox_UGD.getSelectedIndex()+1){
                        case 1:{
                            string_UGD ="本科生";break;}
                        case 2:{
                            string_UGD ="硕士研究生";break;}
                        case 3:{
                            string_UGD ="博士研究生";break;}
                        case 4:{
                            string_UGD ="教师";break;}
                    }
                    if(comboBox_UGD.getSelectedIndex()+1==4||comboBox_UGD.getSelectedIndex()+1==5||comboBox_UGD.getSelectedIndex()+1==6){
                        panel.removeAll();
                        panel.repaint();
                        panel.add(ltitle);
                        panel.add(luno);
                        panel.add(tuno);
                        panel.add(lpwd);
                        panel.add(tpwd);
                        panel.add(luna);
                        panel.add(tuna);
                        panel.add(lude);
                        panel.add(tude);
                        panel.add(lugd);
                        panel.add(comboBox_UGD);
                        panel.add(bconfrim);
                        panel.add(breset);
                        panel.add(bclose);
                    }
                    else{
                        panel.removeAll();
                        panel.repaint();
                        panel.add(ltitle);
                        panel.add(luno);
                        panel.add(tuno);
                        panel.add(lpwd);
                        panel.add(tpwd);
                        panel.add(luna);
                        panel.add(tuna);
                        panel.add(lude);
                        panel.add(tude);
                        panel.add(lusp);
                        panel.add(tusp);
                        panel.add(lugd);
                        panel.add(comboBox_UGD);
                        panel.add(bconfrim);
                        panel.add(breset);
                        panel.add(bclose);
                    }
                }
            }
        });

        //设置字体
        lpwd.setFont(new Font("等线",0,15));
        lude.setFont(new Font("等线",0,15));
        lugd.setFont(new Font("等线",0,15));
        luna.setFont(new Font("等线",0,15));
        luno.setFont(new Font("等线",0,15));
        lusp.setFont(new Font("等线",0,15));
        tude.setFont(new Font("等线",0,15));
        tuna.setFont(new Font("等线",0,15));
        tuno.setFont(new Font("等线",0,15));
        tusp.setFont(new Font("等线",0,15));
        comboBox_UGD.setFont(new Font("等线",0,15));
        bconfrim.setFont(new Font("等线",0,15));
        breset.setFont(new Font("等线",0,15));
        bclose.setFont(new Font("等线",0,15));


        ltitle.setBounds(150,20,100,20);
        luno.setBounds(35,55,100,35);    tuno.setBounds(165,55,200,30);
        lpwd.setBounds(35,90,100,35);    tpwd.setBounds(165,90,200,30);
        luna.setBounds(35,125,100,35);    tuna.setBounds(165,125,200,30);
        lude.setBounds(35,160,100,35);    tude.setBounds(165,160,200,30);
        lusp.setBounds(35,195,100,35);    tusp.setBounds(165,195,200,30);
        lugd.setBounds(35,230,100,35);    comboBox_UGD.setBounds(165,230,200,30);
        bconfrim.setBounds(70,280,70,35);
        breset.setBounds(165,280,70,35);
        bclose.setBounds(265,280,70,35);

        panel.add(ltitle);
        panel.add(luno);
        panel.add(tuno);
        panel.add(lpwd);
        panel.add(tpwd);
        panel.add(luna);
        panel.add(tuna);
        panel.add(lude);
        panel.add(tude);
        panel.add(lusp);
        panel.add(tusp);
        panel.add(lugd);
        panel.add(comboBox_UGD);
        panel.add(bconfrim);
        panel.add(breset);
        panel.add(bclose);

        Container container=new Container();
        container=getContentPane();
        container.setLayout(null);
        panel.setBounds(0,0,400,400);
        container.add(panel);

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
                String UNO=tuno.getText();
                String UNA=tuna.getText();
                String PWD=tpwd.getText();
                String UDE=tude.getText();
                String USP=tusp.getText();
                if(!Pattern.matches("[a-zA-Z0-9]{10}",UNO)){
                    JOptionPane.showMessageDialog(null, "借书证号必须为10位的字母数字组合！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if(!Pattern.matches("[a-zA-Z0-9]{10,16}",PWD)){
                    JOptionPane.showMessageDialog(null, "密码必须为10到16位的字母数字组合！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if(!Pattern.matches("[a-zA-Z\\u4e00-\\u9fa5]+",UNA)){
                    JOptionPane.showMessageDialog(null, "姓名不能为空，只能为中文或英文！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if(UDE.isEmpty()){
                    JOptionPane.showMessageDialog(null, "系别不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String SQL="INSERT INTO TUSER SELECT '"+UNO+"','"+UNA+"','"+UDE+"','"+USP+"','"+ string_UGD +"','"+PWD+"'";
                int i=Database.executeUpdate(SQL);
                if(i!=0) {
                    JOptionPane.showMessageDialog(null, i + "个用户添加成功");
                    Panel_ManageUser.search();
                    dispose();
//                    tuno.setText("");   //清空输入框
//                    tpwd.setText("");
//                    tuna.setText("");
//                    tude.setText("");
//                    tusp.setText("");
//                    comboBox_UGD.setSelectedItem("本科生");
                }
            }
            else if(event.getSource()==breset){
                tuno.setText("");   //清空输入框
                tpwd.setText("");
                tuna.setText("");
                tude.setText("");
                tusp.setText("");
                comboBox_UGD.setSelectedItem("本科生");
            }
            else if(event.getSource()==bclose){
                dispose();
            }
        }
    }

}

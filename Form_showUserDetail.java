package BANDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Form_showUserDetail extends JFrame{
    private JPanel panel;
    private JLabel ltitle, luno, luna, lude, lusp, lugd, lpwd,luup,luat, luno1, luna1, lude1, lusp1, lugd1, lpwd1,luup1,luat1,lnnb,lnnb1;
    private JButton bclose;
    private String UNO="";
    private int NNB=0;

    public Form_showUserDetail(String Uno) {
        super("用户详情");

        UNO=Uno;
        panel=new JPanel();
        
        ltitle = new JLabel("用户详情");
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
        ltitle.setBounds(150, 20, 100, 20);
        luno.setBounds(35, 55, 100, 20);    luno1.setBounds(165, 55, 200, 20);
        luna.setBounds(35, 75, 100, 20);    luna1.setBounds(165, 75, 200, 20);
        lpwd.setBounds(35, 95, 100, 20);   lpwd1.setBounds(165, 95, 200, 20);
        lude.setBounds(35, 115, 100, 20);   lude1.setBounds(165, 115, 100, 20);
        lusp.setBounds(35, 135, 100, 20);   lusp1.setBounds(165, 135, 200, 20);
        bclose.setBounds(265, 285, 100, 35);

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
        bclose.setFont(new Font("等线",0,15));
        if(lugd1.getText().equals("教师")){
            lugd.setBounds(35, 135, 100, 20);
            lugd1.setBounds(165, 135, 200, 20);
            luup.setBounds(35, 160, 120, 30);
            luup1.setBounds(165, 160, 200, 30);
            luat.setBounds(35, 180, 200, 30);
            luat1.setBounds(165, 180, 200, 30);
            lnnb.setBounds(35,200,200,30);
            lnnb1.setBounds(165,200,200,30);

        }else {
            lugd.setBounds(35, 155, 100, 35);
            lugd1.setBounds(165, 155, 200, 30);
            luup.setBounds(35, 180, 120, 30);
            luup1.setBounds(165, 180, 200, 30);
            luat.setBounds(35, 200, 200, 30);
            luat1.setBounds(165, 200, 200, 30);
            lnnb.setBounds(35,220,200,30);
            lnnb1.setBounds(165,220,200,30);

            panel.add(lusp);
            panel.add(lusp1);

        }

        panel.add(ltitle);
        panel.add(luno);
        panel.add(luno1);
        panel.add(luna);
        panel.add(luna1);
        panel.add(lude);
        panel.add(lude1);
        panel.add(lpwd);
        panel.add(lpwd1);
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
        }
    }
}

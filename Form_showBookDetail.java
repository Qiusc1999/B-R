package BANDR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Form_showBookDetail extends JFrame {
    private JLabel ltitle, lbno, lbna, lbda, lbpu, lbpl, lbnu, lbno1, lbna1, lbda1, lbpu1, lbpl1, lbnu1;
    private JButton bclose;

    public Form_showBookDetail(String Bno) {
        super("图书详情");
        ltitle = new JLabel("图书详情");
        ltitle.setFont(new Font("等线",1,17));
        lbno = new JLabel("图书编号");
        lbna = new JLabel("图书名称:");
        lbda = new JLabel("出版日期:");
        lbpu = new JLabel("图书出版社:");
        lbpl = new JLabel("图书存放位置:");
        lbnu = new JLabel("图书总数量:");

        String SQL = "SELECT * FROM TBOOK WHERE BNO = '" + Bno + "'";
        ResultSet rs = Database.executeQuery(SQL);

        try {
            while (rs.next()){
                lbno1 = new JLabel(rs.getString(1));
                lbna1 = new JLabel(rs.getString(2));
                lbda1 = new JLabel(rs.getString(3));
                lbpu1 = new JLabel(rs.getString(4));
                lbpl1 = new JLabel(rs.getString(5));
                lbnu1 = new JLabel(rs.getString(6));
            }
        } catch (SQLException sqlE) {
            JOptionPane.showMessageDialog(null, sqlE, "提示", JOptionPane.INFORMATION_MESSAGE);
        }

        bclose = new JButton("关闭");

        lbno.setFont(new Font("等线",0,15));
        lbna.setFont(new Font("等线",0,15));
        lbnu.setFont(new Font("等线",0,15));
        lbda.setFont(new Font("等线",0,15));
        lbpl.setFont(new Font("等线",0,15));
        lbpu.setFont(new Font("等线",0,15));
        lbno1.setFont(new Font("等线",0,15));
        lbna1.setFont(new Font("等线",0,15));
        lbnu1.setFont(new Font("等线",0,15));
        lbda1.setFont(new Font("等线",0,15));
        lbpl1.setFont(new Font("等线",0,15));
        lbpu1.setFont(new Font("等线",0,15));
        bclose.setFont(new Font("等线",0,15));
        bclose.setFont(new Font("等线",0,15));


        Container container = new Container();
        container = getContentPane();
        container.setLayout(null);

        ltitle.setBounds(150, 20, 100, 20);
        lbno.setBounds(35, 55, 100, 35);
        lbno1.setBounds(165, 55, 200, 30);
        lbna.setBounds(35, 90, 100, 35);
        lbna1.setBounds(165, 90, 200, 30);
        lbda.setBounds(35, 125, 100, 35);
        lbda1.setBounds(165, 125, 200, 30);
        lbpu.setBounds(35, 160, 100, 35);
        lbpu1.setBounds(165, 160, 200, 30);
        lbpl.setBounds(35, 195, 100, 35);
        lbpl1.setBounds(165, 195, 200, 30);
        lbnu.setBounds(35, 230, 100, 35);
        lbnu1.setBounds(165, 230, 200, 30);
        bclose.setBounds(275, 280, 65, 40);

        container.add(ltitle);
        container.add(lbno);
        container.add(lbno1);
        container.add(lbna);
        container.add(lbna1);
        container.add(lbda);
        container.add(lbda1);
        container.add(lbpu);
        container.add(lbpu1);
        container.add(lbpl);
        container.add(lbpl1);
        container.add(lbnu);
        container.add(lbnu1);
        container.add(bclose);

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

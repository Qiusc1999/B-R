package BANDR;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class Form_admin_addRecord extends JFrame{
    private JPanel panel;
    private JLabel ltitle, luno,luna, lbno,lbna;
    private JTextField tuno, tbno;
    private JButton bconfrim,breset, bclose;
    private String suno=""; //tuno实时输入监听，用于匹配姓名

    public Form_admin_addRecord(){
        super("添加借阅记录");
        panel=new JPanel();

        ltitle=new JLabel("添加借阅记录");
        ltitle.setFont(new Font("等线",1,17));
        luno =new JLabel("借书证号:");
        luna=new JLabel("姓名:        ");
        lbno =new JLabel("图书编号:");
        lbna=new JLabel("图书名称:");
        tuno =new JTextField(20);
        tbno =new JTextField(20);
        bconfrim=new JButton("确定");
        breset=new JButton("重输");
        bclose =new JButton("关闭");

        //设置字体
        lbno.setFont(new Font("等线",0,15));
        luno.setFont(new Font("等线",0,15));
        luna.setFont(new Font("等线",0,15));
        lbna.setFont(new Font("等线",0,15));
        tbno.setFont(new Font("等线",0,15));
        tuno.setFont(new Font("等线",0,15));
        bconfrim.setFont(new Font("等线",0,15));
        breset.setFont(new Font("等线",0,15));
        bclose.setFont(new Font("等线",0,15));

        DocumentListenerHandler documentListenerHandler1=new DocumentListenerHandler();
        Document document1=tuno.getDocument();
        document1.addDocumentListener(documentListenerHandler1);
        Document document2=tbno.getDocument();
        document2.addDocumentListener(documentListenerHandler1);

        Container container=new Container();
        container=getContentPane();
        container.setLayout(null);

        ltitle.setBounds(140,20,120,20);
        luno.setBounds(35,55,100,35);    tuno.setBounds(165,55,200,30);
        luna.setBounds(35,90,350,35);
        lbno.setBounds(35,125,100,35);    tbno.setBounds(165,125,200,30);
        lbna.setBounds(35,160,350,35);
        bconfrim.setBounds(70,280,70,35);
        breset.setBounds(165,280,70,35);
        bclose.setBounds(265,280,70,35);

        container.add(ltitle);
        container.add(luno);
        container.add(tuno);
        container.add(luna);
        container.add(lbno);
        container.add(tbno);
        container.add(lbna);
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
            if (event.getSource() == bconfrim) {  //添加借阅
                try {
                    String UNO= tuno.getText();
                    String BNO= tbno.getText();

                    if(!Pattern.matches("[a-zA-Z0-9]{10}",UNO)){
                        JOptionPane.showMessageDialog(null, "借书证号必须为10位的字母数字组合！", "警告", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    else if(!Pattern.matches("[a-zA-Z0-9]{1,10}",BNO)){
                        JOptionPane.showMessageDialog(null, "图书编号必须为1到10位的字母数字组合！", "警告", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String SQL="SELECT * FROM TUSER WHERE UNO='"+UNO+"'";
                    ResultSet rs1=Database.executeQuery(SQL);
                    if(!rs1.isBeforeFirst()){
                        JOptionPane.showMessageDialog(null, "没有该用户信息", "警告", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String SQL1="SELECT * FROM TBOOK WHERE BNO= '"+BNO+"'";
                    ResultSet rs2=Database.executeQuery(SQL1);
                    if(!rs2.isBeforeFirst()){
                        JOptionPane.showMessageDialog(null, "没有图书信息", "警告", JOptionPane.WARNING_MESSAGE);
                        return;
                    }



                    Account.setUserAccount(tuno.getText());
                    //检测用户有无欠款
                    Boolean ExistOweMoney = Function.FindOweMoney(Account.UNO);
                    if (ExistOweMoney == true) {
                        JOptionPane.showMessageDialog(null, "该用户有罚金尚未缴纳，请缴费后再借阅。");
                        return;
                    }

                    //检测用户借书数是否达到上限
                    int NNB = 0;
                    NNB = Function.getNowNumofBorrow(Account.UNO);
                    if (NNB >= Account.UUP) {
                        JOptionPane.showMessageDialog(null, "该用户借阅的图书数已达上限，请还书后再借阅。");
                        return;
                    }

                    //检测是否有剩余图书

                    int num_haveBorrowed=Function.getNum_haveBorrowed(BNO);
                    int BNU=Function.getBNU(BNO);
                    if(num_haveBorrowed>=BNU){ //已借数量达到图书总数
                        JOptionPane.showMessageDialog(null, "该图书已全部借出，无法借阅。");
                        return;
                    }

                    //检测用户是否借阅此书
                    boolean isBorrowThisBook = false; //是否已借阅此书
                    isBorrowThisBook = Function.isBorrowThisBook(Account.UNO, BNO);
                    if (isBorrowThisBook == true) {  //TBORROW已存在记录，不可借阅
                        JOptionPane.showMessageDialog(null, "用户已借阅此书，不能再次借阅。");
                        return;
                    } else {  //可添加
                        String SQL_INSERT="INSERT INTO TBORROW SELECT '"+UNO+"','"+BNO+"',GETDATE(),NULL,0";
                        int j = Database.executeUpdate(SQL_INSERT);
                        if (j != 0) {
                            int residue=BNU-num_haveBorrowed-1;
                            JOptionPane.showMessageDialog(null, "借阅成功" + " 图书编号:" + BNO , "提示", JOptionPane.INFORMATION_MESSAGE);
                            //更新提示标签
                            int n=Function.getNumOvertime(Account.UNO);
//                            tableRefresh();     //刷新table
                            return;
                        }
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
            }
            else if(event.getSource()==breset){
                tuno.setText("");   //清空输入框
                tbno.setText("");
                luna.setText("姓名:      ");
                lbno.setText("图书名称:    ");
            }
            else if(event.getSource()==bclose){
                dispose();
            }
        }
    }

    public class DocumentListenerHandler implements DocumentListener {

        public void insertUpdate(DocumentEvent e){
            try{
                Document document=e.getDocument();
                suno=document.getText(0,document.getLength());
            }catch (BadLocationException e1){
                e1.printStackTrace();
            }

            if(tuno.getText().length()!=10) {
                luna.setText("姓名:    ");
            }

            if(tuno.getText().length()==10) {
                String SQL="SELECT UNA FROM TUSER WHERE UNO = '"+tuno.getText()+"'";
                ResultSet resultSet=Database.executeQuery(SQL);
                try {
                    resultSet.first();
                    String UNA=resultSet.getString(1);
                    luna.setText("姓名:                         "+UNA);
                }catch (SQLException sqlE){
                    sqlE.printStackTrace();
                }
            }


            if(tbno.getText().length()>=3){
                lbna.setText("图书名称:    ");
                String SQL="SELECT BNA FROM TBOOK WHERE BNO='"+tbno.getText()+"'";
                ResultSet resultSet=Database.executeQuery(SQL);
                try {
                    resultSet.first();
                    String BNA=resultSet.getString(1);
                    lbna.setText("图书名称:                 "+BNA);
                }catch (SQLException sqlE){
                    sqlE.printStackTrace();
                }
            }

            if(tbno.getText().length()<3){
                lbna.setText("图书名称:    ");
            }
        }

        public void removeUpdate(DocumentEvent e){
            if(tuno.getText().length()!=10) {
                luna.setText("姓名:    ");
            }

            if(tbno.getText().length()<3){
                lbna.setText("图书名称:    ");
            }

        }

        public void changedUpdate(DocumentEvent e){

        }
    }

}

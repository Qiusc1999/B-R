package BANDR;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.regex.Pattern;

public class Form_admin_addbook extends JFrame{
    private static JPanel panel;
    private static JLabel ltitle, lbno, lbna, lbda, lbpu, lbpl, lbnu;
    private static JTextField tbno, tbna, tbda, tbpu, tbpl, tbnu;
    private static JButton badd,breset, bclose;
    private static JComboBox comboBox_location;
    private String bpl ="";
    private String sbno=""; //tbno实时输入监听，用于匹配图书存放地点

    public Form_admin_addbook(){
        super("添加图书");
        panel=new JPanel();

        ltitle=new JLabel("添加图书");
        ltitle.setFont(new Font("等线",1,17));
        lbno =new JLabel("图书编号");
        lbna =new JLabel("图书名称:");
        lbda =new JLabel("出版日期:");
        lbpu =new JLabel("图书出版社:");
        lbpl =new JLabel("图书存放位置:");
        lbnu =new JLabel("图书总数量:");
        tbno =new JTextField(20);


        DocumentListenerHandler documentListenerHandler1=new DocumentListenerHandler();
        Document document1=tbno.getDocument();
        document1.addDocumentListener(documentListenerHandler1);

        tbna =new JTextField(20);
        tbda =new JTextField(20);
        tbpu =new JTextField(20);
        tbpl =new JTextField(20);
        tbnu =new JTextField(20);
        badd =new JButton("添加");
        breset=new JButton("重输");
        bclose =new JButton("关闭");

        String[] locations={"1楼A区","1楼B区","1楼C区","2楼A区","2楼B区","2楼C区","3楼A区","3楼B区","3楼C区"};
        comboBox_location=new JComboBox(locations);
        comboBox_location.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    int m=comboBox_location.getSelectedIndex()/3+1;
                    int n=comboBox_location.getSelectedIndex()%3;
                    if(n==0)
                        bpl=m+"楼A区";
                    else if(n==1)
                        bpl=m+"楼B区";
                    else bpl=m+"楼C区";
                }
            }
        });

        //设置字体
        lbno.setFont(new Font("等线",0,15));
        lbna.setFont(new Font("等线",0,15));
        lbnu.setFont(new Font("等线",0,15));
        lbda.setFont(new Font("等线",0,15));
        lbpl.setFont(new Font("等线",0,15));
        lbpu.setFont(new Font("等线",0,15));
        tbda.setFont(new Font("等线",0,15));
        tbna.setFont(new Font("等线",0,15));
        tbno.setFont(new Font("等线",0,15));
        tbnu.setFont(new Font("等线",0,15));
        comboBox_location.setFont(new Font("等线",0,15));
        tbpu.setFont(new Font("等线",0,15));
        badd.setFont(new Font("等线",0,15));
        breset.setFont(new Font("等线",0,15));
        bclose.setFont(new Font("等线",0,15));


        Container container=new Container();
        container=getContentPane();
        container.setLayout(null);

        ltitle.setBounds(150,20,100,20);
        lbno.setBounds(35,55,100,35);    tbno.setBounds(165,55,200,30);
        lbna.setBounds(35,90,100,35);    tbna.setBounds(165,90,200,30);
        lbda.setBounds(35,125,100,35);    tbda.setBounds(165,125,200,30);
        lbpu.setBounds(35,160,100,35);    tbpu.setBounds(165,160,200,30);
        lbpl.setBounds(35,195,100,35);    comboBox_location.setBounds(165,195,200,30);
        lbnu.setBounds(35,230,100,35);    tbnu.setBounds(165,230,200,30);
        badd.setBounds(70,280,70,35);
        breset.setBounds(165,280,70,35);
        bclose.setBounds(265,280,70,35);

        container.add(ltitle);
        container.add(lbno);
        container.add(tbno);
        container.add(lbna);
        container.add(tbna);
        container.add(lbda);
        container.add(tbda);
        container.add(lbpu);
        container.add(tbpu);
        container.add(lbpl);
        container.add(comboBox_location);
        container.add(lbnu);
        container.add(tbnu);
        container.add(badd);
        container.add(breset);
        container.add(bclose);

        //添加监听器
        ActionEventHandler handler=new ActionEventHandler();
        badd.addActionListener(handler);
        breset.addActionListener(handler);
        bclose.addActionListener(handler);

        this.setBounds(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-200, ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-200,400,400);
        setSize(400,400);
        setResizable(false);
        setVisible(true);
    }

    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource()== badd) {
                String Bno = tbno.getText();
                String Bna = tbna.getText();
                String Bda = tbda.getText();
                String Bpu = tbpu.getText();
                String Bpl = bpl;
                String Bnu = tbnu.getText();

                if(!Pattern.matches("[a-zA-Z0-9]{1,10}",Bno)){
                    JOptionPane.showMessageDialog(null, "图书编号必须为1到10位的字母数字组合！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if(Bna.isEmpty()){
                    JOptionPane.showMessageDialog(null, "图书名称不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if(!Pattern.matches("[0-9]{4,6}",Bda)){
                    JOptionPane.showMessageDialog(null, "出版日期必须为连续非空数字组合(例如201906或2019)！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if(Bpu.isEmpty()){
                    JOptionPane.showMessageDialog(null, "图书出版社不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if(!Pattern.matches("[0-9]{1,99999}",Bnu)){
                    JOptionPane.showMessageDialog(null, "图书总数量必须为数字！", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String SQL = "INSERT INTO TBOOK SELECT '" + Bno + "','" + Bna + "','" + Bda + "','" + Bpu + "','" + Bpl + "','" + Bnu + "',GETDATE()";
                int i = Database.executeUpdate(SQL);
                if (i != 0) {
                    JOptionPane.showMessageDialog(null, i + "本书添加成功!");
                    Panel_ManageBook.search();
                    dispose();
                }
            }
            else if(event.getSource()==breset){
                tbnu.setText("");   //清空
                tbpl.setText("");
                tbda.setText("");
                tbna.setText("");
                tbno.setText("");
                tbpu.setText("");
                comboBox_location.setSelectedIndex(0);
            }
            else if(event.getSource()==bclose){
                dispose();
            }

        }
    }
    public class DocumentListenerHandler implements DocumentListener{
        public void insertUpdate(DocumentEvent e){
            try{
                Document document=e.getDocument();
                sbno=document.getText(0,document.getLength());
            }catch (BadLocationException e1){
                e1.printStackTrace();
            }

            if(tbno.getText().length()<=2){
                if(sbno.indexOf("1")!=-1){
                    if(sbno.indexOf("1A")!=-1)
                        comboBox_location.setSelectedIndex(0);
                    else if(sbno.indexOf("1B")!=-1)
                        comboBox_location.setSelectedIndex(1);
                    else if(sbno.indexOf("1C")!=-1)
                        comboBox_location.setSelectedIndex(2);
                    else
                        comboBox_location.setSelectedIndex(0);
                }
                else if(sbno.indexOf("2")!=-1){
                    if(sbno.indexOf("2A")!=-1)
                        comboBox_location.setSelectedIndex(3);
                    else if(sbno.indexOf("2B")!=-1)
                        comboBox_location.setSelectedIndex(4);
                    else if(sbno.indexOf("2C")!=-1)
                        comboBox_location.setSelectedIndex(5);
                    else
                        comboBox_location.setSelectedIndex(3);
                }
                else if(sbno.indexOf("3")!=-1){
                    if(sbno.indexOf("3A")!=-1)
                        comboBox_location.setSelectedIndex(6);
                    else if(sbno.indexOf("3B")!=-1)
                        comboBox_location.setSelectedIndex(7);
                    else if(sbno.indexOf("3C")!=-1)
                        comboBox_location.setSelectedIndex(8);
                    else
                        comboBox_location.setSelectedIndex(6);
                }
            }
        }

        public void removeUpdate(DocumentEvent e){

        }

        public void changedUpdate(DocumentEvent e){

        }
    }

}

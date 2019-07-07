package BANDR;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.Pattern;

public class Panel_ManageUser extends JPanel{
    private static JButton button_manage_addUser, button_manage_delUser, button_manage_refresh,button_search,button_modify,button_showUserDetail;
    private static JTextField textField_search;
    private static JTable table_ManageUser;
    private static JScrollPane scrollPane_tableUser;
    private static DefaultTableModel model_ManageUser;
    private static JComboBox comboBox_searchtype,comboBox_grade;
    private static String searchtype="UNO";
    private static String gradetype="";
    private static int numOfRowInEachPage =25;
    private static int pageNum=1;
    private static int pageNumMax=1;
    private static int totalNumOfRow=0;
    private static JPanel panel_page;
    private static Vector<Object[]> vector_total;
    private static JLabel label_page, label_page1;
    private static JButton button_beforePage,button_afterPage,button_page;
    private static JTextField textField_page;

    public Panel_ManageUser(){

        //**********************************************************
        ////////////////////用户管理界面//////////////////////////
        /////////组件声明/////////
        //panel
        JPanel panel_button=new JPanel();
        JPanel panel_search =new JPanel();
        panel_page=new JPanel();     //翻页面板
        //button
        button_manage_addUser =new JButton("添加用户");
        button_manage_delUser =new JButton("删除用户");
        button_manage_refresh =new JButton("显示全部");
        button_modify=new JButton("修改用户信息");
        button_showUserDetail=new JButton("显示用户详情");
        button_search=new JButton("检索");
        button_beforePage=new JButton("上一页");
        button_afterPage=new JButton("下一页");
        button_page=new JButton("跳转");

        //label
        JLabel title=new JLabel("用户信息管理");
        JLabel label_search=new JLabel("按此类型搜索");
        JLabel label_search1=new JLabel("输入关键字:");
        label_page=new JLabel("当前页码:"+pageNum+" 共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
        //textfield
        textField_search=new JTextField(10);
        textField_page=new JTextField(3);
        //combobox
        String types[]={"借书证号","姓名","系别","专业","级别"};
        String grades[] = {"本科生", "硕士研究生","博士研究生","教师"};
        comboBox_searchtype = new JComboBox(types);
        comboBox_grade =new JComboBox(grades);
        comboBox_searchtype.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    switch (comboBox_searchtype.getSelectedIndex()+1){
                        case 1:searchtype="UNO";break;
                        case 2:searchtype="UNA";break;
                        case 3:searchtype="UDE";break;
                        case 4:searchtype="USP";break;
                        case 5: searchtype="UGD";break;
                    }
                    if(comboBox_searchtype.getSelectedIndex()+1==5){
                        gradetype="AND UGD='本科生'";
                        comboBox_grade.setSelectedIndex(0);
                        textField_search.setText("");
                        panel_search.remove(textField_search);
                        panel_search.remove(label_search1);
                        panel_search.remove(button_search);
                        panel_search.add(new JLabel("请选择"));
                        panel_search.add(comboBox_grade);
                        panel_search.add(button_search);
                        panel_search.repaint();
                    }
                    else {
                        gradetype="";
                        panel_search.removeAll();
                        panel_search.add(label_search);
                        panel_search.add(comboBox_searchtype);
                        panel_search.add(label_search1);
                        panel_search.add(textField_search);
                        panel_search.add(button_search);
                        panel_search.repaint();
                    }
                }
            }
        });
        comboBox_grade.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    switch (comboBox_grade.getSelectedIndex()+1){
                        case 1:gradetype="AND UGD='本科生'";break;
                        case 2:gradetype="AND UGD='硕士研究生'";break;
                        case 3:gradetype="AND UGD='博士研究生'";break;
                        case 4:gradetype="AND UGD='教师' ";break;
                    }
                }
            }
        });

        //vector
        vector_total =new Vector<Object[]>();
        //table
        Object[] columeName_ManageUser=new Object[]{"借书证号","姓名","系别","专业","级别","密码"}; //列名
        Object[][] context_ManageUser=new Object[][]{};
        model_ManageUser =new DefaultTableModel(context_ManageUser, columeName_ManageUser){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        table_ManageUser =new JTable(model_ManageUser);
        scrollPane_tableUser =new JScrollPane(table_ManageUser); //用ScrollPane包含Table
        /////////end.组件声明/////////

        /////////组件设置////////
        //设置layout
        this.setLayout(null);
        panel_page.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_button.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_search.setLayout(new FlowLayout(FlowLayout.LEFT));

        //设置字体
        title.setFont(new Font("等线",1,30));
        button_search.setFont(new Font("等线",0,15));
        button_showUserDetail.setFont(new Font("等线",0,15));
        button_manage_addUser.setFont(new Font("等线",0,15));
        button_manage_delUser.setFont(new Font("等线",0,15));
        button_manage_refresh.setFont(new Font("等线",0,15));
        button_modify.setFont(new Font("等线",0,15));
        label_search.setFont(new Font("等线",0,15));
        label_search1.setFont(new Font("等线",0,15));
        label_page.setFont(new Font("等线",0,15));
        textField_search.setFont(new Font("等线",0,15));
        comboBox_grade.setFont(new Font("等线",0,15));
        comboBox_searchtype.setFont(new Font("等线",0,13));
        button_beforePage.setFont(new Font("等线",0,15));
        button_afterPage.setFont(new Font("等线",0,15));
        button_page.setFont(new Font("等线", 0, 15));
        //设置透明
        this.setOpaque(false);
        table_ManageUser.setOpaque(false);
        scrollPane_tableUser.setOpaque(false);
        panel_button.setOpaque(false);
        panel_search.setOpaque(false);
        panel_page.setOpaque(false);
        textField_page.setOpaque(false);
        comboBox_grade.setBackground(Color.WHITE);
        comboBox_searchtype.setBackground(Color.WHITE);

        //setBounds
        title.setBounds(10,10,300,40);
        scrollPane_tableUser.setBounds(10,60,1160,425);
        panel_button.setBounds(10,550,1160,50);
        panel_search.setBounds(10,485,510,50);
        panel_page.setBounds(660, 500, 500, 50);
/////////end.组件设置////////

        /////面板添加组件///////
        panel_button.add(button_manage_refresh);
        panel_button.add(button_manage_addUser);
        panel_button.add(button_manage_delUser);
        panel_button.add(button_modify);
        panel_button.add(button_showUserDetail);
        label_page1=new JLabel("当前页码:");
        label_page1.setFont(new Font("等线",0,15));
        textField_page.setText(String.valueOf(pageNum));
        label_page=new JLabel("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");     //需放在search后面，防止pageNumMax更新不及时
        label_page.setFont(new Font("等线",0,15));
        panel_page.add(label_page1);
        panel_page.add(textField_page);
        panel_page.add(button_page);
        panel_page.add(label_page);
        panel_page.add(button_beforePage);
        panel_page.add(button_afterPage);
        panel_page.add(label_page);
        panel_page.add(button_beforePage);
        panel_page.add(button_afterPage);
        panel_search.add(label_search);
        panel_search.add(comboBox_searchtype);
        panel_search.add(label_search1);
        panel_search.add(textField_search);
        panel_search.add(button_search);
        //thisPanel
        this.add(title);
        this.add(scrollPane_tableUser);
        this.add(panel_button);
        this.add(panel_search);
        this.add(panel_page);
        /////end.面板添加组件///////

        ///////添加监听器///////
        ActionEventHandler handler=new ActionEventHandler();    //声明监听器
        button_manage_addUser.addActionListener(handler);
        button_manage_delUser.addActionListener(handler);
        button_manage_refresh.addActionListener(handler);
        button_search.addActionListener(handler);
        button_modify.addActionListener(handler);
        button_showUserDetail.addActionListener(handler);
        button_afterPage.addActionListener(handler);
        button_beforePage.addActionListener(handler);
        button_page.addActionListener(handler);
        ///////end.添加监听器///////
        //根据是否显示密码刷新
        search();
        ///////////////////end.用户管理界面////////////////////////
        //**********************************************************
    }

    public static int datetovector(ResultSet rs,Vector<Object[]> vector){      //将结果集数据存到vector，返回行数
        vector.removeAllElements();
        int rowCount=0;
        try {
            while (rs.next()){
                Object[] rowData={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),"***"};
                vector.addElement(rowData);
                rowCount++;
            }
        }catch (SQLException sqlE){
            JOptionPane.showMessageDialog(null, sqlE);
        }
        pageNumMax=rowCount/ numOfRowInEachPage+1;
        totalNumOfRow=rowCount;
        return rowCount;
    }

    public static void tableRefresh(ResultSet rs){     //table刷新1
        vector_total.removeAllElements();
        model_ManageUser.setRowCount(0);
        int totalNumofRow=datetovector(rs, vector_total);
        Function.showPage(model_ManageUser,pageNum, numOfRowInEachPage,totalNumofRow,vector_total);
        textField_page.setText(String.valueOf(pageNum));
        label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
        panel_page.repaint();
    }


    public static void search(){   //搜索
        String SQL="SELECT * FROM TUSER WHERE "+ searchtype +" LIKE '%"+textField_search.getText()+"%'" + gradetype ;
        ResultSet rs=Database.executeQuery(SQL);
        //根据是否显示密码刷新
        tableRefresh(rs);
    }

    ///////////////监听器/////////////////
    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource()== button_manage_addUser){  //添加用户
                Form_admin_addUser form_userRegister=new Form_admin_addUser();
                //根据是否显示密码刷新
                search();
            }
            else if(event.getSource()==button_manage_refresh){  //刷新
                //根据是否显示密码刷新
                comboBox_grade.setSelectedIndex(0);
                comboBox_searchtype.setSelectedIndex(0);
                textField_page.setText("1");
                textField_search.setText("");
                searchtype="UNO";
                gradetype="";
                pageNum=1;
                search();
                Function.showPage(model_ManageUser,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
            }
            else if(event.getSource()== button_manage_delUser){  //删除用户
                //验证管理员密码
                String nowPWD=JOptionPane.showInputDialog(null,"请输入当前管理员密码进行验证");
                String SQL_checkNowPWD="SELECT PWD FROM TADMIN WHERE ANO = '"+Account.ANO+"'";
                ResultSet rs_checkNowPWD=Database.executeQuery(SQL_checkNowPWD);
                try {
                    while (rs_checkNowPWD.next()) {
                        if (nowPWD.equals(rs_checkNowPWD.getString(1))) { //密码正确
                            try{
                                String Uno= model_ManageUser.getValueAt(table_ManageUser.getSelectedRow(),0).toString();
                                String Una= model_ManageUser.getValueAt(table_ManageUser.getSelectedRow(),1).toString();
                                int isDelete=JOptionPane.showConfirmDialog(null,"是否删除 “借书证号:"+Uno+" 姓名:"+Una+"” 用户？","是否删除",JOptionPane.YES_NO_CANCEL_OPTION);
                                if(isDelete==JOptionPane.YES_OPTION){       //确定删除
                                    String SQL="DELETE FROM TUSER WHERE UNO='"+Uno+"'";
                                    int i=Database.executeUpdate(SQL);
                                    if(i!=0){
                                        JOptionPane.showMessageDialog(null,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                            }catch (Exception e){
                                JOptionPane.showMessageDialog(null,"请在上方选中用户后删除","提示",JOptionPane.WARNING_MESSAGE);
                            }
                            //根据是否显示密码刷新
                            search();
                        }else{   //密码错误
                            JOptionPane.showMessageDialog(null,"密码错误！","警告",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }catch (SQLException sqlE){
                    JOptionPane.showMessageDialog(null,sqlE);
                }
            }
            else if(event.getSource()==button_search){      //查找
                search();
            }
            else if(event.getSource()==button_modify){
                //数据准备，由于不选择table内容
                // String UNO= model_ManageUser.getValueAt(table_ManageUser.getSelectedRow(),0).toString();
                // 会抛出异常，所以放在前面来防止不选择table内容时弹出输入密码框
                int row= table_ManageUser.getSelectedRow();  //获取table行
                int colume= table_ManageUser.getSelectedColumn();    //获取table列
                String columeName="";   //属性名
                String columeNameshow="";   //用于提示的属性名
                switch (colume+1) {
                    case 2:
                        columeName = "UNA";
                        columeNameshow="姓名";
                        break;
                    case 3:
                        columeName = "UDE";
                        columeNameshow="系别";
                        break;
                    case 4:
                        columeName = "USP";
                        columeNameshow="专业";
                        break;
                    case 5:
                        columeName="UGD";
                        columeNameshow="级别";
                        break;
                    case 6:
                        columeName="PWD";
                        columeNameshow="密码";
                        break;
                }
                String UNO= model_ManageUser.getValueAt(table_ManageUser.getSelectedRow(),0).toString();

                if(colume==0){  //不能修改借书证号
                    JOptionPane.showMessageDialog(null,"不能修改借书证号(BNO)","警告",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String modify="";
                if(colume+1==5){        //修改级别
                    String[] Ugds={"本科生","硕士研究生","博士研究生","教师"};
                    modify=JOptionPane.showInputDialog(null,"选择级别","选择级别",JOptionPane.QUESTION_MESSAGE,null,Ugds,Ugds[0]).toString();
                }
                else if(colume+1==6){   //修改密码
                    //管理员验证密码
                    String nowPWD=JOptionPane.showInputDialog(null,"请输入当前管理员密码进行验证");
                    String SQL_checkNowPWD="SELECT PWD FROM TADMIN WHERE ANO = '"+Account.ANO+"'";
                    ResultSet rs_checkNowPWD=Database.executeQuery(SQL_checkNowPWD);
                    try {
                        while (rs_checkNowPWD.next()) {
                            if (nowPWD.equals(rs_checkNowPWD.getString(1))) { //密码正确
                                modify=JOptionPane.showInputDialog(null,"输入修改后的"+columeNameshow).toString();
                            }else{   //密码错误
                                JOptionPane.showMessageDialog(null,"密码错误！","警告",JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                        }
                    }catch (SQLException sqlE){
                        JOptionPane.showMessageDialog(null,sqlE);
                    }
                }
                else {
                    modify=JOptionPane.showInputDialog(null,"输入修改后的"+columeNameshow).toString();
                }
                switch (colume+1){
                    case 2:
                        if(!Pattern.matches("[a-zA-Z\\u4e00-\\u9fa5]+",modify)){
                            JOptionPane.showMessageDialog(null, "姓名不能为空，只能为中文或英文！", "警告", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        break;
                    case 3:
                        if(modify.isEmpty()){
                            JOptionPane.showMessageDialog(null, "系别不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        break;
                    case 6:
                        if (!Pattern.matches("[a-zA-Z0-9]{10,16}",modify)) {
                            JOptionPane.showMessageDialog(null, "密码必须为10到16为英文和数字组合！", "警告", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                }

                String SQL="UPDATE TUSER SET "+columeName+" = '"+modify+"' WHERE UNO= '"+UNO+"'";
                int i=Database.executeUpdate(SQL);
                if(i!=0){
                    JOptionPane.showMessageDialog(null,"修改成功","提醒",JOptionPane.INFORMATION_MESSAGE);
                    model_ManageUser.setValueAt(modify,row,colume);
                }
            }
            else if(event.getSource()==button_showUserDetail){      //显示用户详情
                String UNO=model_ManageUser.getValueAt(table_ManageUser.getSelectedRow(),0).toString();
                Form_showUserDetail form_showUserDetail=new Form_showUserDetail(UNO);
            }
            else if(event.getSource()==button_afterPage){   //下一页
                if(pageNum>=pageNumMax){
                    JOptionPane.showMessageDialog(null,"已到最后一页");
                    return;
                }
                else {
                    pageNum++;
                    Function.showPage(model_ManageUser,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
                }
                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
                panel_page.repaint();
            }
            else if(event.getSource()==button_beforePage){      //上一页
                if(pageNum==1){
                    JOptionPane.showMessageDialog(null,"已到第一页");
                    return;
                }
                else {
                    pageNum--;
                    Function.showPage(model_ManageUser,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
                }
                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
                panel_page.repaint();
            }
            else if(event.getSource()==button_page){        //跳转
                int num=Integer.valueOf(textField_page.getText());
                if(num<=0||num>pageNumMax){
                    textField_page.setText(String.valueOf(pageNum));
                    JOptionPane.showMessageDialog(null,"页码有误。");
                    return;
                }
                else pageNum=num;
                Function.showPage(model_ManageUser,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
                panel_page.repaint();
            }
        }
    }
}

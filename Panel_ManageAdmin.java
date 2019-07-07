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

public class Panel_ManageAdmin extends JPanel{
    private static JButton button_manage_addAdmin, button_manage_delAdmin, button_manage_refresh,button_search,button_modify;
    private static JTable table_ManageAdmin;
    private static JTextField textField_search;
    private static JScrollPane scrollPane_tableAdmin;
    private static DefaultTableModel model_ManageAdmin;
    private static JComboBox comboBox_searchtype, comboBox_orderby;
    private static JRadioButton radioButton_ascorder,radioButton_descorder;
    private static String ordertype ="ASC";
    private static String searchtype="ANO";
    private static String orderby="ANO";
    private static int numOfRowInEachPage =20;
    private static int pageNum=1;
    private static int pageNumMax=1;
    private static int totalNumOfRow=0;
    private static JPanel panel_page;
    private static Vector<Object[]> vector_total;
    private static JLabel label_page, label_page1;
    private static JButton button_beforePage,button_afterPage,button_page;
    private static JTextField textField_page;


    public Panel_ManageAdmin(){
        //**********************************************************
        ////////////////////4管理员账户管理界面//////////////////////////
        /////////组件声明/////////
        //panel
        JPanel panel_button=new JPanel();
        JPanel panel_searchorder =new JPanel();
        JPanel panel_search=new JPanel();   panel_search.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel_order=new JPanel();    panel_order.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_page=new JPanel();     //翻页面板
        //button
        button_manage_addAdmin =new JButton("添加管理员账户");
        button_manage_delAdmin =new JButton("删除管理员账户");
        button_manage_refresh =new JButton("显示全部");
        button_beforePage=new JButton("上一页");
        button_afterPage=new JButton("下一页");
        button_page=new JButton("跳转");
        button_modify=new JButton("修改管理员账户信息");
        button_search=new JButton("检索");
        radioButton_ascorder=new JRadioButton("升序",true);
        radioButton_descorder=new JRadioButton("降序",false);
        ButtonGroup buttonGroup_order=new ButtonGroup();
        //label
        JLabel title=new JLabel("管理员账户管理");
        JLabel label_order=new JLabel("按此类型排序");
        JLabel label_search=new JLabel("按此类型搜索");
        JLabel label_search1=new JLabel("输入关键字:");
        label_page=new JLabel("当前页码:"+pageNum+" 共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
        //textField
        textField_page=new JTextField(3);
        textField_search=new JTextField(10);
        //combobox
        String types[]={"管理员编号","姓名"};
        String orders[]={"管理员编号","姓名"};
        comboBox_searchtype =new JComboBox(types);
        comboBox_orderby =new JComboBox(orders);
        comboBox_searchtype.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    switch (comboBox_searchtype.getSelectedIndex()+1){
                        case 1:{searchtype="ANO";break;}
                        case 2:{searchtype="ANA";break;}
                    }
                }
            }
        });
        comboBox_orderby.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    switch (comboBox_orderby.getSelectedIndex()+1){
                        case 1:{orderby="ANO";break;}
                        case 2:{orderby="ANA";break;}
                    }
                    //执行search，不用点击检索按钮便可出结果，方便操作
                    search();
                }
            }
        });
        //vector
        vector_total =new Vector<Object[]>();
        //table
        Object[] columeName_ManageAdmin=new Object[]{"管理员编号","管理员姓名","密码"}; //列名
        Object[][] context_ManageAdmin=new Object[][]{};
        model_ManageAdmin =new DefaultTableModel(context_ManageAdmin, columeName_ManageAdmin){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        table_ManageAdmin =new JTable(model_ManageAdmin);
        scrollPane_tableAdmin =new JScrollPane(table_ManageAdmin); //用ScrollPane包含Table
        /////////end.组件声明/////////

        /////////组件设置////////
        //设置layout
        this.setLayout(null);
        panel_page.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_button.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_searchorder.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel_searchorder.setLayout(new GridLayout(2,1));
        //设置字体
        title.setFont(new Font("等线",1,30));
        radioButton_ascorder.setFont(new Font("等线",0,15));
        radioButton_descorder.setFont(new Font("等线",0,15));
        button_search.setFont(new Font("等线",0,15));
        button_modify.setFont(new Font("等线",0,15));
        button_manage_addAdmin.setFont(new Font("等线",0,15));
        button_manage_delAdmin.setFont(new Font("等线",0,15));
        button_manage_refresh.setFont(new Font("等线",0,15));
        button_beforePage.setFont(new Font("等线",0,15));
        button_afterPage.setFont(new Font("等线",0,15));
        button_page.setFont(new Font("等线",0,15));
        label_order.setFont(new Font("等线",0,15));
        label_search.setFont(new Font("等线",0,15));
        label_search1.setFont(new Font("等线",0,15));
        label_page.setFont(new Font("等线",0,15));
        textField_search.setFont(new Font("等线",0,15));
        textField_page.setFont(new Font("等线",0,15));
        comboBox_searchtype.setFont(new Font("等线",0,13));
        comboBox_orderby.setFont(new Font("等线",0,13));
        table_ManageAdmin.setFont(new Font("宋体",0,13));
        //设置透明
        this.setOpaque(false);
        scrollPane_tableAdmin.setOpaque(false);
        table_ManageAdmin.setOpaque(false);
        panel_button.setOpaque(false);
        panel_order.setOpaque(false);
        panel_search.setOpaque(false);
        panel_searchorder.setOpaque(false);
        radioButton_descorder.setOpaque(false);
        radioButton_ascorder.setOpaque(false);
        textField_page.setOpaque(false);
        textField_search.setOpaque(false);
        panel_page.setOpaque(false);
        comboBox_searchtype.setBackground(Color.WHITE);
        comboBox_orderby.setBackground(Color.WHITE);

        //setBounds
        title.setBounds(10,10,300,40);
        scrollPane_tableAdmin.setBounds(10,60,1160,347);
        panel_button.setBounds(10,550,1160,50);
        panel_searchorder.setBounds(10,420,900,110);
        panel_page.setBounds(660,420,500,50);
        /////////end.组件设置////////

        /////面板添加组件///////
        panel_button.add(button_manage_refresh);
        panel_button.add(button_manage_addAdmin);
        panel_button.add(button_manage_delAdmin);
        panel_button.add(button_modify);
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
        buttonGroup_order.add(radioButton_ascorder);
        buttonGroup_order.add(radioButton_descorder);
        panel_search.add(label_search);
        panel_search.add(comboBox_searchtype);
        panel_search.add(label_search1);
        panel_search.add(textField_search);
        panel_search.add(button_search);
        panel_order.add(label_order);
        panel_order.add(comboBox_orderby);
        panel_order.add(radioButton_ascorder);
        panel_order.add(radioButton_descorder);
        panel_searchorder.add(panel_search);
        panel_searchorder.add(panel_order);
        //thisPanel
        this.add(scrollPane_tableAdmin);
        this.add(panel_button);
        this.add(panel_searchorder);
        this.add(panel_page);
        this.add(title);
        /////end.面板添加组件///////

        ///////添加监听器///////
        ActionEventHandler handler=new ActionEventHandler();    //声明监听器
        button_manage_addAdmin.addActionListener(handler);
        button_manage_delAdmin.addActionListener(handler);
        button_manage_refresh.addActionListener(handler);
        button_search.addActionListener(handler);
        button_modify.addActionListener(handler);
        button_afterPage.addActionListener(handler);
        button_beforePage.addActionListener(handler);
        button_page.addActionListener(handler);

        RadioButtonHandler radioButtonHandler=new RadioButtonHandler();
        radioButton_ascorder.addItemListener(radioButtonHandler);
        radioButton_descorder.addItemListener(radioButtonHandler);

        ///////end.添加监听器///////
        //根据是否显示密码刷新
//        if(boolen_showPWD ==true)   tableRefresh_showPWD();
//        else tableRefresh();
        search();
        ///////////////////end.管理员账户管理界面////////////////////////
        //**********************************************************
    }

    public static int datetovector(ResultSet rs,Vector<Object[]> vector){      //将结果集数据存到vector，返回行数
        vector.removeAllElements();
        int rowCount=0;
        try {
            while (rs.next()){
                Object[] rowData={rs.getString(1),rs.getString(2),"*****"};
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
        model_ManageAdmin.setRowCount(0);
        int totalNumofRow=datetovector(rs, vector_total);
        Function.showPage(model_ManageAdmin,pageNum, numOfRowInEachPage,totalNumofRow,vector_total);
        textField_page.setText(String.valueOf(pageNum));
        label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
        panel_page.repaint();
    }


//    旧版代码
/////////////////////////////
//    public static void tableRefresh(){     //table刷新2
//        model_ManageAdmin.setRowCount(0);
//        String SQL="SELECT * FROM TADMIN ORDER BY ANO ASC";
//        ResultSet rs=Database.executeQuery(SQL);
//        try{
//            while (rs.next()){
//                Object[] rowData={rs.getString(1),rs.getString(2),"*****"};
//                model_ManageAdmin.addRow(rowData);
//            }
//        }catch (SQLException sqlE){
//            JOptionPane.showMessageDialog(null,sqlE);
//        }
//    }
//
//    public static void tableRefresh_showPWD(){     //table刷新2  显示密码
//        model_ManageAdmin.setRowCount(0);
//        String SQL="SELECT * FROM TADMIN ORDER BY ANO ASC";
//        ResultSet rs=Database.executeQuery(SQL);
//        try{
//            while (rs.next()){
//                Object[] rowData={rs.getString(1),rs.getString(2),rs.getString(3)};
//                model_ManageAdmin.addRow(rowData);
//            }
//        }catch (SQLException sqlE){
//            JOptionPane.showMessageDialog(null,sqlE);
//        }
//    }
//////////////////////////////////////
//

    public static void search(){
        //执行search，不用点击检索按钮便可出结果，方便操作
        String SQL="SELECT * FROM TADMIN WHERE "+ searchtype +" LIKE '%"+textField_search.getText()+"%' ORDER BY "+orderby+" "+ordertype;
        ResultSet rs=Database.executeQuery(SQL);
        //根据是否显示密码刷新
        tableRefresh(rs);
    }

    ///////////////监听器/////////////////
    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource()== button_manage_addAdmin){  //添加管理员账户
                Form_admin_addAdmin form_admin_addAdmin=new Form_admin_addAdmin();
                //根据是否显示密码刷新
//                if(boolen_showPWD ==true)   tableRefresh_showPWD();
//                else tableRefresh();
                search();
            }
            else if(event.getSource()==button_manage_refresh){  //刷新
                //根据是否显示密码刷新
//                if(boolen_showPWD ==true)   tableRefresh_showPWD();
//                else tableRefresh();
                comboBox_orderby.setSelectedIndex(0);
                comboBox_searchtype.setSelectedIndex(0);
                textField_page.setText("1");
                textField_search.setText("");
                search();
            }
            else if(event.getSource()== button_manage_delAdmin){  //删除管理员账户
                if(model_ManageAdmin.getValueAt(table_ManageAdmin.getSelectedRow(),0).toString().equals(Account.ANO)){
                    JOptionPane.showMessageDialog(null,"不能删除自身账号！","警告",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String nowPWD=JOptionPane.showInputDialog(null,"请输入当前管理员密码进行验证");
                String SQL_checkNowPWD="SELECT PWD FROM TADMIN WHERE ANO = '"+Account.ANO+"'";
                ResultSet rs_checkNowPWD=Database.executeQuery(SQL_checkNowPWD);
                try {
                    while (rs_checkNowPWD.next()) {
                        if (nowPWD.equals(rs_checkNowPWD.getString(1))) { //密码正确
                            String Ano= model_ManageAdmin.getValueAt(table_ManageAdmin.getSelectedRow(),0).toString();
                            String Ana= model_ManageAdmin.getValueAt(table_ManageAdmin.getSelectedRow(),1).toString();
                            int isDelete=JOptionPane.showConfirmDialog(null,"是否删除 “管理员编号:"+Ano+" 管理员姓名:"+Ana+"” 管理员账户？","是否删除",JOptionPane.YES_NO_CANCEL_OPTION);
                            if(isDelete==JOptionPane.YES_OPTION){       //确定删除
                                String SQL="DELETE FROM TADMIN WHERE ANO='"+Ano+"'";
                                int i=Database.executeUpdate(SQL);
                                if(i!=0){
                                    JOptionPane.showMessageDialog(null,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
//                            tableRefresh();
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
                String SQL="SELECT * FROM TADMIN WHERE "+ searchtype +" LIKE '%"+textField_search.getText()+"%' ORDER BY "+orderby+" "+ordertype;
                ResultSet rs=Database.executeQuery(SQL);
                //根据是否显示密码刷新
                tableRefresh(rs);
            }
            else if(event.getSource()==button_modify){      //修改
                int row= table_ManageAdmin.getSelectedRow();  //获取table行
                int colume= table_ManageAdmin.getSelectedColumn();    //获取table列
                String columeName="";   //属性名
                String columeNameshow="";   //用于提示的属性名
                if(colume==0){  //不能修改管理员账户编号
                    JOptionPane.showMessageDialog(null,"不能修改管理员账户编号(ANO)","警告",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                switch (colume+1) {
                    case 2:
                        columeName = "ANA";
                        columeNameshow="管理员姓名";
                        break;
                    case 3:
                        columeName = "PWD";
                        columeNameshow="密码";
                        break;
                }

                String ANO= model_ManageAdmin.getValueAt(table_ManageAdmin.getSelectedRow(),0).toString();
                String modify=JOptionPane.showInputDialog(null,"输入修改后的"+columeNameshow).toString();

                switch (colume+1){ ///////////修改
                    case 2:
                        if(modify.isEmpty()){
                            JOptionPane.showMessageDialog(null, "姓名不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        break;
                    case 3:
                        if (!Pattern.matches("[a-zA-Z0-9]{10,16}",modify)) {
                            JOptionPane.showMessageDialog(null, "密码必须为10到16为英文和数字组合！", "警告", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                }
                String SQL="UPDATE TADMIN SET "+columeName+" = '"+modify+"' WHERE ANO= '"+ANO+"'";
                int i=Database.executeUpdate(SQL);
                if(i!=0){
                    JOptionPane.showMessageDialog(null,"修改成功","提醒",JOptionPane.INFORMATION_MESSAGE);
                    model_ManageAdmin.setValueAt(modify,row,colume);
                }
            }
            else if(event.getSource()==button_afterPage){   //下一页
                if(pageNum>=pageNumMax){
                    JOptionPane.showMessageDialog(null,"已到最后一页");
                    return;
                }
                else {
                    pageNum++;
                    Function.showPage(model_ManageAdmin,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
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
                    Function.showPage(model_ManageAdmin,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
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
                Function.showPage(model_ManageAdmin,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
                panel_page.repaint();
            }

        }
    }

    //radiobutton监听器
    public class RadioButtonHandler implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getItem()==radioButton_ascorder){
                ordertype="ASC";
                //执行search，不用点击检索按钮便可出结果，方便操作
                search();
            }
            else if(e.getItem()==radioButton_descorder){
                ordertype="DESC";
                //执行search，不用点击检索按钮便可出结果，方便操作
                search();
            }
        }
    }
}

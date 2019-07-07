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

public class Panel_UseBorrow extends JPanel {
    private static JButton button_manage_addBorrow, button_manage_refresh, button_search,button_search1;
    private static JButton button_screen, button_searchmore0, button_searchmore1;
    private static JTextField textField_search,textField_sreen_d;
    private static JTextField textField_BNO,textField_BNA,textField_BDA,textField_BPU;
    private static JLabel label_screen;
    private static JTable table_ManageBook;
    private static JScrollPane scrollPane_tableBook;
    private static DefaultTableModel modelbook;
    private static JComboBox comboBox_searchtype, comboBox_orderby,comboBox_screen;
    private static JRadioButton radioButton_ascorder, radioButton_descorder;
    private static String ordertype = " ASC ";
    private static String searchtype = " BNA LIKE ";
    private static String searchtype1 = "";
    private static String orderby = " ORDER BY BNO ";
    private static String screentype="";
    private static int int_screendaynum =0;
    private static JPanel panel_searchorder,panel_orderscreen,panel_search,panel_screen;
    private static JPanel panel_page,panel_searchmorebutton,panel_seachmore0,panel_seachmore1;
    private static int numOfRowInEachPage =20;
    private static int pageNum=1;
    private static int pageNumMax=1;
    private static int totalNumOfRow=0;

    private static JLabel label_page, label_page1;
    private static JButton button_beforePage,button_afterPage,button_page;
    private static JTextField textField_page;

    private static Vector<Object[]> vector_total;
    private static JLabel label_BNO,label_BNA,label_BDA,label_BPU;

    public Panel_UseBorrow() {

        //**********************************************************
        ////////////////////借阅功能界面//////////////////////////
        /////////组件声明/////////
        //panel
        JPanel panel_button = new JPanel();        //按钮面板
        panel_searchorder = new JPanel();        //借阅查询面板
        panel_page=new JPanel();     //翻页面板
        panel_search = new JPanel();
        panel_screen=new JPanel();
        JPanel panel_order = new JPanel();
        panel_orderscreen=new JPanel();
        panel_searchmorebutton=new JPanel();   //组合查询按钮
        panel_seachmore0 =new JPanel();   //组合查询
        panel_seachmore1=new JPanel();   //组合查询
        //button
        button_manage_addBorrow = new JButton("借阅");
        button_manage_refresh = new JButton("显示全部");
        button_searchmore0 =new JButton("组合查询");
        button_searchmore1 =new JButton("简单查询");
        button_screen=new JButton("筛选");
        button_search = new JButton("检索");
        button_search1 = new JButton("检索");
        button_beforePage=new JButton("上一页");
        button_afterPage=new JButton("下一页");
        button_page=new JButton("跳转");
        ButtonGroup buttonGroup_order = new ButtonGroup();
        radioButton_ascorder = new JRadioButton("升序", true);
        radioButton_descorder = new JRadioButton("降序", false);
        //textfield
        textField_page=new JTextField(3);
        textField_BNO=new JTextField(10);
        textField_BNA=new JTextField(10);
        textField_BDA=new JTextField(10);
        textField_BPU=new JTextField(10);
        textField_search = new JTextField(10);
        textField_sreen_d =new JTextField(5);
        //label
        JLabel title=new JLabel("借阅");
        JLabel label_order = new JLabel("按此类型排序");
        JLabel label_search = new JLabel("按此类型搜索");
        JLabel label_search1 = new JLabel("输入关键字:");
        label_BNO=new JLabel("图书编号:");
        label_BNA=new JLabel("图书名称:");
        label_BDA=new JLabel("出版日期:");
        label_BPU=new JLabel("出版社:    ");
        label_screen=new JLabel("筛选近期上架图书");
        //combobox
        String types[] = {"图书名称","图书编号", "出版日期", "图书出版社", "图书存放位置"};
        String orders[] = {"图书编号",  "出版日期","上架日期"};
        String screen[]={"无","1周内","1月内","3月内","1年内","自定义"};
        comboBox_searchtype = new JComboBox(types);
        comboBox_screen=new JComboBox(screen);
        comboBox_orderby = new JComboBox(orders);
        comboBox_searchtype.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch (comboBox_searchtype.getSelectedIndex() + 1) {
                        case 1:
                            searchtype = " BNA LIKE ";
                            break;
                        case 2:
                            searchtype = " BNO LIKE ";
                            break;
                        case 3:
                            searchtype = " BDA LIKE ";
                            break;
                        case 4:
                            searchtype = " BPU LIKE ";
                            break;
                        case 5:
                            searchtype = " BPL LIKE ";
                            break;
                    }
                }
            }
        });
        comboBox_screen.addItemListener(new ItemListener() {    //筛选
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch (comboBox_screen.getSelectedIndex() + 1) {
                        case 1:
                            screentype = "";
                            break;
                        case 2:
                            screentype = " AND DATEDIFF(DAY,BPD,GETDATE())<=7 ";
//                            search(screentype);
                            search();
                            break;

                        case 3:
                            screentype = " AND DATEDIFF(DAY,BPD,GETDATE())<=30 ";
//                            search(screentype);
                            search();
                            break;

                        case 4:
                            screentype = " AND DATEDIFF(DAY,BPD,GETDATE())<=120 ";
//                            search(screentype);
                            search();
                            break;

                        case 5:
                            screentype = " AND DATEDIFF(DAY,BPD,GETDATE())<=365 ";
//                            search(screentype);
                            search();
                            break;

                        case 6:
                            break;
                    }

                    if(comboBox_screen.getSelectedIndex()+1==6){
                        panel_screen.removeAll();
                        panel_screen.repaint();
                        panel_screen.add(label_screen);
                        panel_screen.add(comboBox_screen);
                        panel_screen.add(new JLabel("输入时段:"));
                        panel_screen.add(textField_sreen_d);
                        panel_screen.add(new JLabel("日内"),new Font("等线",0,15));
                        panel_screen.add(button_screen);
                        button_screen.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(e.getSource()==button_screen){
                                    if(textField_sreen_d.getText().equals("")){
                                        JOptionPane.showMessageDialog(null,"请输入天数");
                                        return;
                                    }
                                    int_screendaynum=Integer.parseInt(textField_sreen_d.getText());
                                    screentype = " AND DATEDIFF(DAY,BPD,GETDATE())<= "+ int_screendaynum;
                                    search(screentype);
                                }
                            }
                        });
                    }else {
                        panel_screen.removeAll();
                        panel_screen.repaint();
                        panel_screen.add(label_screen);
                        panel_screen.add(comboBox_screen);
                    }
                }
            }
        });
        comboBox_orderby.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch (comboBox_orderby.getSelectedIndex() + 1) {
                        case 1:
                            orderby = " ORDER BY BNO ";
                            break;
                        case 2:
                            orderby = " ORDER BY BDA ";
                            break;
                        case 3:
                            orderby = " ORDER BY BPD ";
                            break;
                    }
                    search();
                }
            }
        });
        //vector
        vector_total =new Vector<Object[]>();
        //table
        Object[] columeName_ManageBook = new Object[]{"图书编号", "图书名称", "出版日期", "图书出版社", "图书存放位置","剩余数量", "图书总数量", "上架时间"}; //列名
        Object[][] context_ManageBook = new Object[][]{};
        modelbook = new DefaultTableModel(context_ManageBook, columeName_ManageBook){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        table_ManageBook = new JTable(modelbook);
        scrollPane_tableBook = new JScrollPane(table_ManageBook); //用ScrollPane包含Table
        /////////end.组件声明/////////

        /////////组件设置////////
        //设置layout
        this.setLayout(null);
        panel_page.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_order.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_searchorder.setLayout(null);
        panel_orderscreen.setLayout(new GridLayout(2,1));
        panel_searchmorebutton.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_seachmore0.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_seachmore1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_search.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_screen.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_button.setLayout(new FlowLayout(FlowLayout.LEFT));
        //设置字体
        title.setFont(new Font("等线",1,30));
        radioButton_ascorder.setFont(new Font("等线",0,13));
        radioButton_descorder.setFont(new Font("等线",0,13));
        button_search.setFont(new Font("等线",0,15));
        button_search1.setFont(new Font("等线",0,15));
        button_manage_addBorrow.setFont(new Font("等线",0,15));
        button_manage_refresh.setFont(new Font("等线",0,15));
        button_beforePage.setFont(new Font("等线",0,15));
        button_afterPage.setFont(new Font("等线",0,15));
        button_page.setFont(new Font("等线", 0, 15));
        textField_BNO.setFont(new Font("等线",0,15));
        textField_BNA.setFont(new Font("等线",0,15));
        textField_BDA.setFont(new Font("等线",0,15));
        textField_BPU.setFont(new Font("等线",0,15));
        textField_page.setFont(new Font("等线",0,15));
        button_searchmore0.setFont(new Font("等线",0,15));
        button_searchmore1.setFont(new Font("等线",0,15));
        label_order.setFont(new Font("等线",0,15));
        label_search.setFont(new Font("等线",0,15));
        label_search1.setFont(new Font("等线",0,15));
        label_screen.setFont(new Font("等线",0,15));
        label_BNO.setFont(new Font("等线",0,15));
        label_BNA.setFont(new Font("等线",0,15));
        label_BDA.setFont(new Font("等线",0,15));
        label_BPU.setFont(new Font("等线",0,15));
        textField_search.setFont(new Font("等线",0,15));
        comboBox_searchtype.setFont(new Font("等线",0,13));
        comboBox_orderby.setFont(new Font("等线",0,13));
        comboBox_screen.setFont(new Font("等线",0,13));
        table_ManageBook.setFont(new Font("宋体",0,13));
        //设置透明
        this.setOpaque(false);
        scrollPane_tableBook.setOpaque(false);
        table_ManageBook.setOpaque(false);
        radioButton_ascorder.setOpaque(false);
        radioButton_descorder.setOpaque(false);
        panel_orderscreen.setOpaque(false);
        panel_searchmorebutton.setOpaque(false);
        panel_seachmore0.setOpaque(false);
        panel_seachmore1.setOpaque(false);
        panel_screen.setOpaque(false);
        panel_button.setOpaque(false);
        panel_order.setOpaque(false);
        panel_search.setOpaque(false);
        textField_page.setOpaque(false);
        panel_page.setOpaque(false);
        panel_searchorder.setOpaque(false);
        comboBox_searchtype.setBackground(Color.WHITE);
        comboBox_orderby.setBackground(Color.WHITE);
        comboBox_screen.setBackground(Color.WHITE);

        //setBounds
        title.setBounds(10,10,300,40);
        scrollPane_tableBook.setBounds(10, 60, 1160, 347);
        panel_button.setBounds(20,565,500,40);
        panel_searchmorebutton.setBounds(10,0,100,35);
        panel_seachmore0.setBounds(10,35,700,35);
        panel_seachmore1.setBounds(10,75,500,35);
        panel_search.setBounds(10,35,550,35);
        panel_orderscreen.setBounds(10,70,550,70);
        Form_user_Main.label_nowAccount.setBounds(6,105,650,35);
        panel_searchorder.setBounds(10, 420, 810, 175);
        panel_page.setBounds(660, 420, 500, 50);
        /////////end.组件设置////////

        /////面板添加组件///////
        panel_seachmore0.add(label_BNO);
        panel_seachmore0.add(textField_BNO);
        panel_seachmore0.add(label_BNA);
        panel_seachmore0.add(textField_BNA);
        panel_seachmore0.add(button_search1);
        panel_seachmore1.add(label_BDA);
        panel_seachmore1.add(textField_BDA);
        panel_seachmore1.add(label_BPU);
        panel_seachmore1.add(textField_BPU);
        panel_order.add(label_order);
        panel_order.add(comboBox_orderby);
        panel_order.add(radioButton_ascorder);
        panel_order.add(radioButton_descorder);
        panel_search.add(label_search);
        panel_search.add(comboBox_searchtype);
        panel_search.add(label_search1);
        panel_search.add(textField_search);
        panel_search.add(button_search);
        panel_screen.add(label_screen);
        panel_screen.add(comboBox_screen);
        panel_orderscreen.add(panel_order);
        panel_orderscreen.add(panel_screen);
        panel_searchmorebutton.add(button_searchmore0);
        panel_searchorder.add(panel_searchmorebutton);
        panel_searchorder.add(panel_search);
        panel_searchorder.add(panel_orderscreen);
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
        panel_button.add(button_manage_refresh);
        panel_button.add(button_manage_addBorrow);
        //thisPanel
        this.add(title);
        this.add(scrollPane_tableBook);
        this.add(panel_button);
        this.add(panel_page);
        this.add(panel_searchorder);
        /////end.面板添加组件///////

        ///////添加监听器///////
        ActionEventHandler handler = new ActionEventHandler();    //声明监听器
        button_manage_addBorrow.addActionListener(handler);
        button_manage_refresh.addActionListener(handler);
        button_searchmore0.addActionListener(handler);
        button_searchmore1.addActionListener(handler);
        button_search.addActionListener(handler);
        button_search1.addActionListener(handler);
        RadioButtonHandler radioButtonHandler = new RadioButtonHandler();
        radioButton_ascorder.addItemListener(radioButtonHandler);
        radioButton_descorder.addItemListener(radioButtonHandler);
        button_afterPage.addActionListener(handler);
        button_beforePage.addActionListener(handler);
        button_page.addActionListener(handler);
        ///////end.添加监听器///////
//        tableRefresh();     //刷新
        search();
        ///////////////////end.借阅功能界面////////////////////////
        //**********************************************************
    }

    public static int datetovector(ResultSet rs,Vector<Object[]> vector){      //将结果集数据存到vector，返回行数
        vector.removeAllElements();
        int rowCount=0;
        try {
            while (rs.next()){
//                int residuenum=0;
//                String BNO=rs.getString(1);
//                int n=Function.getNum_haveBorrowed(BNO);    //获取已借数量
//                residuenum=rs.getInt(6)-n;
//                Object[] rowData = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),residuenum, rs.getString(6), rs.getString(7)};
                Object[] rowData = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8)};
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


    public static void tableRefresh(ResultSet rs) {     //table刷新1
        vector_total.removeAllElements();
        modelbook.setRowCount(0);
        int totalNumofRow=datetovector(rs, vector_total);
        Function.showPage(modelbook,pageNum, numOfRowInEachPage,totalNumofRow,vector_total);

        int n=Function.getNowNumofBorrow(Account.UNO);
        int m=Function.getNumOvertime(Account.UNO);
        Form_user_Main.label_nowAccount.setText("欢迎使用 当前账户:"+Account.UNO+"  最多借阅数:"+Account.UUP+"   当前已借阅数:"+n+"   最长借阅时间:"+Account.UAT+"天   已有"+m+"本书逾期");
        panel_searchorder.repaint();
        textField_page.setText(String.valueOf(pageNum));
        label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
        panel_page.repaint();
    }

//旧版代码
//    public static void tableRefresh() {     //table刷新2
//        modelbook.setRowCount(0);
//        String SQL = "SELECT * FROM TBOOK ORDER BY BNO ASC";
//        ResultSet rs = Database.executeQuery(SQL);
//        try {
//            while (rs.next()) {
//                int residuenum=0;
//                String BNO=rs.getString(1);
//                int num_haveBorrowed=Function.getNum_haveBorrowed(BNO);    //获取已借数量
//                residuenum=rs.getInt(6)-num_haveBorrowed;
//                Object[] rowData = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),residuenum, rs.getString(6), rs.getString(7)};
//                modelbook.addRow(rowData);
//            }
//        } catch (SQLException sqlE) {
//            JOptionPane.showMessageDialog(null, sqlE);
//        }
//        int n=Function.getNowNumofBorrow(Account.UNO);
//        int m=Function.getNumOvertime(Account.UNO);
//        label_nowAccount.setText("欢迎使用 当前账户:"+Account.UNO+"  最多借阅数:"+Account.UUP+"   当前已借阅数:"+n+"   最长借阅时间:"+Account.UAT+"天   已有"+m+"本书逾期");
//        panel_searchorder.repaint();
//    }

    public static void search(){
//        String SQL = "SELECT * FROM TBOOK WHERE " + searchtype + " LIKE '%" + textField_search.getText() + "%' ORDER BY " + orderby + " " + ordertype;
        searchtype1="''%"+textField_search.getText()+"%''";
        String SQL="EXEC GetBookInfo '"+searchtype+"', '"+searchtype1+"','"+screentype+"', '"+orderby+"', '"+ordertype+"' ";
        ResultSet rs = Database.executeQuery(SQL);
        tableRefresh(rs);
    }

    public static void search(String SQL1){        //搜索2
//        String SQL = "SELECT * FROM TBOOK WHERE " + searchtype + " LIKE '%" + textField_search.getText() + "%' "+SQL1+"  ORDER BY " + orderby + " " + ordertype;
//        String SQL="EXEC GetBookInfo '"+searchtype+"', '"+searchtype1+"','"+screentype+"', '"+orderby+"', '"+ordertype+"' ";
        String SQL="EXEC GetBookInfo '"+searchtype+"', '"+searchtype1+"','"+SQL1+"', '"+orderby+"', '"+ordertype+"' ";
        ResultSet rs = Database.executeQuery(SQL);
        tableRefresh(rs);
    }

    public static void searchmore(String SQL){        //搜索3
        ResultSet rs = Database.executeQuery(SQL);
        tableRefresh(rs);
    }

    public static void refresh(){
        comboBox_orderby.setSelectedIndex(0);
        comboBox_screen.setSelectedIndex(0);
        comboBox_searchtype.setSelectedIndex(0);
        ordertype = " ASC ";
        searchtype = " BNA LIKE ";
        orderby = " ORDER BY BNO ";
        screentype="";
        int_screendaynum =0;
        pageNum=1;
        textField_page.setText("1");
        textField_BDA.setText("");
        textField_BNA.setText("");
        textField_BNO.setText("");
        textField_BPU.setText("");
        textField_search.setText("");
        textField_sreen_d.setText("");
        Function.showPage(modelbook,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
        search();
    }

    ///////////////监听器/////////////////
    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == button_manage_addBorrow) {  //添加借阅
                try {
                    //检测用户有无欠款
                    Boolean ExistOweMoney = Function.FindOweMoney(Account.UNO);
                    if (ExistOweMoney == true) {
                        JOptionPane.showMessageDialog(null, "你有罚金尚未缴纳，请缴费后再借阅。");
                        return;
                    }

                    //检测用户借书数是否达到上限
                    int NNB = 0;
                    NNB = Function.getNowNumofBorrow(Account.UNO);
                    if (NNB >= Account.UUP) {
                        JOptionPane.showMessageDialog(null, "你借阅的图书数已达上限，请还书后再借阅。");
                        return;
                    }

                    //检测是否有剩余图书
                    int row = table_ManageBook.getSelectedRow();  //获取table行
                    String BNO = modelbook.getValueAt(table_ManageBook.getSelectedRow(), 0).toString();
                    String BNA = modelbook.getValueAt(table_ManageBook.getSelectedRow(), 1).toString();
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
                        JOptionPane.showMessageDialog(null, "你已借阅此书，不能再次借阅。");
                        return;
                    } else {  //可添加
                        String SQL_INSERT = "INSERT INTO TBORROW SELECT '" + Account.UNO + "','" + BNO + "',GETDATE(),NULL,0";
                        int j = Database.executeUpdate(SQL_INSERT);
                        if (j != 0) {
                            int residue=BNU-num_haveBorrowed-1;
                            JOptionPane.showMessageDialog(null, "借阅成功" + " 图书编号:" + BNO + " 图书名称:" + BNA, "提示", JOptionPane.INFORMATION_MESSAGE);
                            modelbook.setValueAt(residue,row,6);
                            //更新提示标签
                            int n=Function.getNumOvertime(Account.UNO);
//                            tableRefresh();     //刷新table
                            search();
                            Panel_User_myBorrow.search();   //刷新myborrow面板
                            return;
                        }
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
            }
            else if (event.getSource() == button_manage_refresh) {  //刷新
//                tableRefresh();
                refresh();
            }
            else if (event.getSource() == button_search) {      //查找
                search();
            }
            else if(event.getSource()==button_afterPage){   //下一页
                if(pageNum>=pageNumMax){
                    JOptionPane.showMessageDialog(null,"已到最后一页");
                    return;
                }
                else {
                    pageNum++;
                    Function.showPage(modelbook,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
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
                    Function.showPage(modelbook,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
                }
                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
                panel_page.repaint();
            }
            else if(event.getSource()==button_page){        //跳转
                int num=Integer.valueOf(textField_page.getText());
                if(num<=0||num>pageNumMax){
                    JOptionPane.showMessageDialog(null,"页码有误。");
                    textField_page.setText(String.valueOf(pageNum));
                    return;
                }
                else pageNum=num;
                Function.showPage(modelbook,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
                panel_page.repaint();
            }
            else if(event.getSource()== button_searchmore0){     //组合查询
                panel_searchorder.removeAll();
                panel_searchmorebutton.removeAll();
                panel_searchmorebutton.add(button_searchmore1);
                panel_searchorder.add(panel_searchmorebutton);
                panel_searchorder.add(panel_seachmore0);
                panel_searchorder.add(panel_seachmore1);
                panel_searchorder.repaint();
                refresh();
            }
            else if(event.getSource()== button_searchmore1){     //简单查询
                panel_searchorder.removeAll();
                panel_searchmorebutton.removeAll();
                panel_searchmorebutton.add(button_searchmore0);
                panel_searchorder.add(panel_searchmorebutton);
                panel_searchorder.add(panel_search);
                panel_searchorder.add(panel_orderscreen);
                panel_searchorder.repaint();
                refresh();
            }
            else if(event.getSource() == button_search1){       //组合查询
                String search_bno=" BNO LIKE ";
                String search_bno1=" ''%"+textField_BNO.getText()+"%'' ";
                String search_bna=" AND BNA LIKE ";
                String search_bna1=" ''%"+textField_BNA.getText()+"%'' ";
                String search_bda=" AND BDA LIKE ";
                String search_bda1=" ''%"+textField_BDA.getText()+"%'' ";
                String search_bpu=" AND BPU LIKE ";
                String search_bpu1=" ''%"+textField_BPU.getText()+"%'' ";
                String SQL="EXEC CombinedSearch '"+search_bno+"', '"+search_bno1+"','"+search_bna+"', '"+search_bna1+"', '"+search_bda+"' , '"+search_bda1+"', '"+search_bpu+"', '"+search_bpu1+"'";
                searchmore(SQL);
                textField_page.setText(String.valueOf(1));
                label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
                panel_page.repaint();
            }

        }
    }

    //radiobutton监听器
    public class RadioButtonHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getItem() == radioButton_ascorder) {
                ordertype = " ASC ";
                search();
            } else if (e.getItem() == radioButton_descorder) {
                ordertype = " DESC ";
                search();
            }
        }

    }
}

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

public class Panel_User_myBorrow extends JPanel {
    private static JPanel panel_search, panel_order, panel_screen;
    private static JButton button_refresh, button_search, button_search1, button_return, button_pay, button_continueBorrow;
    private static JButton button_searchmore0, button_searchmore1;
    private static JLabel label_totalFine;
    private static JTextField textField_search, textField_search_y, textField_search_m, textField_search_d, textField_search_y1, textField_search_m1, textField_search_d1, textField_search_y2, textField_search_m2, textField_search_d2;
    private static JTextField textField_BNO, textField_BNA, textField_BT, textField_RT;
    private static JTable table_Record;
    private static JScrollPane scrollPane_tableRecord;
    private static DefaultTableModel model_Record;
    private static JComboBox comboBox_searchtype, comboBox_orderby, comboBox_screen;
    private static JRadioButton radioButton_ascorder, radioButton_descorder;
    private static String ordertype = "ASC";
    private static String searchtype = "TBOOK.BNA";
    private static String orderby = "TBORROW.RT";
    private static String screenSQL = "";
    private static String nowAccount = "";
    private static int UAT = 0;  //最长时间
    private static int UUP = 0;  //最多数量
    private static JPanel panel_searchorderscreen;
    private static int numOfRowInEachPage = 20;
    private static int pageNum = 1;
    private static int pageNumMax = 1;
    private static int totalNumOfRow = 0;
    private static JLabel label_page, label_page1;
    private static JButton button_beforePage,button_afterPage,button_page;
    private static JTextField textField_page;
    private static JPanel panel_page, panel_searchmorebutton, panel_seachmore0, panel_seachmore1, panel_orderscreen;
    private static Vector<Object[]> vector_total;
    private static JLabel label_BNO, label_BNA, label_BT, label_RT;
    //组合查询


    public Panel_User_myBorrow() {

        //**********************************************************
        ////////////////////图书管理界面//////////////////////////
        nowAccount = Account.UNO;
        UAT = Account.UAT;
        UUP = Account.UUP;

        /////////组件声明/////////
        //Panel
        JPanel panel_button = new JPanel();
        panel_searchorderscreen = new JPanel();
        panel_search = new JPanel();
        panel_search.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_order = new JPanel();
        panel_screen = new JPanel();
        panel_page = new JPanel();     //翻页面板
        panel_searchmorebutton = new JPanel();   //组合查询按钮panel
        panel_seachmore0 = new JPanel();   //组合查询panel
        panel_seachmore1 = new JPanel();   //组合查询panel
        panel_orderscreen = new JPanel();
        //button
        button_refresh = new JButton("刷新");
        button_return = new JButton("还书");
        button_pay = new JButton("缴费");
        button_continueBorrow = new JButton("续借");
        button_search = new JButton("检索");
        button_search1 = new JButton("检索");
        button_page=new JButton("跳转");
        button_beforePage = new JButton("上一页");
        button_afterPage = new JButton("下一页");
        radioButton_ascorder = new JRadioButton("升序", true);
        radioButton_descorder = new JRadioButton("降序", false);
        ButtonGroup buttonGroup_order = new ButtonGroup();
        button_searchmore0 = new JButton("组合查询");
        button_searchmore1 = new JButton("简单查询");
        //label
        JLabel title = new JLabel("我的借阅");
        JLabel label_order = new JLabel("按此类型排序");
        JLabel label_search = new JLabel("按此类型搜索");
        JLabel label_search1 = new JLabel("输入关键字:");
        label_BNO = new JLabel("图书编号:");
        label_BNA = new JLabel("图书名称:");
        label_BT = new JLabel("借书时间:");
        label_RT = new JLabel("还书时间:");
        double FINE = Function.getTotalFine(Account.UNO);
        label_totalFine = new JLabel("总罚金:" + FINE);
        JLabel label_screen = new JLabel("筛选");
        //textfield
        textField_page=new JTextField(3);
        textField_search = new JTextField(10);
        textField_search_y = new JTextField(6);
        textField_search_m = new JTextField(3);
        textField_search_d = new JTextField(3);
        textField_search_y1 = new JTextField(6);
        textField_search_m1 = new JTextField(3);
        textField_search_d1 = new JTextField(3);
        textField_search_y2 = new JTextField(6);
        textField_search_m2 = new JTextField(3);
        textField_search_d2 = new JTextField(3);
        textField_BNO = new JTextField(10);
        textField_BNA = new JTextField(10);
        textField_BT = new JTextField(10);
        textField_RT = new JTextField(10);
        //combobox
        String types[] = {"图书名称", "图书编号", "借书日期", "还书日期"};
        String orders[] = {"还书日期", "借书日期", "图书编号", "图书名称"};
        String screentypes[] = {"无", "未归还", "逾期未归还", "未缴费"};
        comboBox_searchtype = new JComboBox(types);  //选择列表
        comboBox_orderby = new JComboBox(orders);    //排序列表
        comboBox_screen = new JComboBox(screentypes);  //选择列表
        comboBox_searchtype.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch (comboBox_searchtype.getSelectedIndex() + 1) {
                        case 1:
                            searchtype = "TBOOK.BNA";
                            break;

                        case 2:
                            searchtype = "TBORROW.BNO";
                            break;

                        case 3:
                            searchtype = "BT";
                            break;

                        case 4:
                            searchtype = "RT";
                            break;

                    }
                    if (comboBox_searchtype.getSelectedIndex() + 1 == 3 || comboBox_searchtype.getSelectedIndex() + 1 == 4) {
                        panel_search.removeAll();
                        panel_search.repaint();
                        panel_search.add(label_search);
                        panel_search.add(comboBox_searchtype);
                        panel_search.add(new JLabel("输入日期(例如2019-06-24):"));
                        panel_search.add(textField_search_y);
                        panel_search.add(new JLabel("年"));
                        panel_search.add(textField_search_m);
                        panel_search.add(new JLabel("月"));
                        panel_search.add(textField_search_d);
                        panel_search.add(new JLabel("日"));
                        panel_search.add(button_search);
                    } else {
                        panel_search.removeAll();
                        panel_search.repaint();
                        panel_search.add(label_search);
                        panel_search.add(comboBox_searchtype);
                        panel_search.add(label_search1);
                        panel_search.add(textField_search);
                        panel_search.add(button_search);
                    }
                }
            }
        });
        comboBox_orderby.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch (comboBox_orderby.getSelectedIndex() + 1) {
                        case 1: {
                            orderby = "RT";
                            break;
                        }
                        case 2: {
                            orderby = "BT";
                            break;
                        }
                        case 3: {
                            orderby = "TBORROW.BNO";
                            break;
                        }
                        case 4: {
                            orderby = "TBOOK.BNA";
                            break;
                        }
                    }
                    search();
                }
            }
        });
        comboBox_screen.addItemListener(new ItemListener() {   //筛选列表
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch (comboBox_screen.getSelectedIndex() + 1) {
                        case 1:
                            screenSQL = "";
                            break;
                        case 2: {
                            screenSQL = "AND TBORROW.RT IS NULL ";    //未归还
                            break;
                        }
                        case 3: {
                            screenSQL = "AND TBORROW.RT IS NULL AND DATEDIFF(day,BT,GETDATE())> " + UAT;   //未归还且已逾期
                            break;
                        }
                        case 4: {
                            screenSQL = "AND TBORROW.WOM = 1 ";
                            break;
                        }
                    }
                    search();
                }
            }
        });
        //vector
        vector_total = new Vector<Object[]>();
        //table
        Object[] columeName_ManageRecord = new Object[]{"图书编号(BNO)", "图书名称(BNA)", "借书时间", "还书时间", "已过天数", "产生罚金", "是否欠费"}; //列名
        Object[][] context_ManageRecord = new Object[][]{};
        model_Record =new DefaultTableModel(context_ManageRecord, columeName_ManageRecord){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        table_Record = new JTable(model_Record);
        scrollPane_tableRecord = new JScrollPane(table_Record); //用ScrollPane包含Table

        ///////end.组件声明//////////

        /////////组件设置////////
        //设置layout
        this.setLayout(null);
        panel_button.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_page.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_order.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_screen.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_searchorderscreen.setLayout(null);
        panel_searchmorebutton.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_seachmore0.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_seachmore1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_orderscreen.setLayout(new FlowLayout(FlowLayout.LEFT));
        //设置字体
        title.setFont(new Font("等线", 1, 30));
        label_totalFine.setFont(new Font("等线", 0, 15));
        radioButton_ascorder.setFont(new Font("等线", 0, 13));
        radioButton_descorder.setFont(new Font("等线", 0, 13));
        button_pay.setFont(new Font("等线", 0, 15));
        button_continueBorrow.setFont(new Font("等线", 0, 15));
        button_return.setFont(new Font("等线", 0, 15));
        button_refresh.setFont(new Font("等线", 0, 15));
        button_beforePage.setFont(new Font("等线", 0, 15));
        button_afterPage.setFont(new Font("等线", 0, 15));
        button_page.setFont(new Font("等线", 0, 15));
        label_order.setFont(new Font("等线", 0, 15));
        label_search.setFont(new Font("等线", 0, 15));
        label_search1.setFont(new Font("等线", 0, 15));
        label_screen.setFont(new Font("等线", 0, 15));
        button_searchmore0.setFont(new Font("等线", 0, 15));
        button_searchmore1.setFont(new Font("等线", 0, 15));
        textField_BNO.setFont(new Font("等线", 0, 15));
        textField_BNA.setFont(new Font("等线", 0, 15));
        textField_BT.setFont(new Font("等线", 0, 15));
        textField_RT.setFont(new Font("等线", 0, 15));
        button_search.setFont(new Font("等线", 0, 15));
        button_search1.setFont(new Font("等线", 0, 15));
        label_BNO.setFont(new Font("等线", 0, 15));
        label_BNA.setFont(new Font("等线", 0, 15));
        label_BT.setFont(new Font("等线", 0, 15));
        label_RT.setFont(new Font("等线", 0, 15));
        textField_search.setFont(new Font("等线", 0, 15));
        textField_search_y.setFont(new Font("等线", 0, 15));
        textField_search_m.setFont(new Font("等线", 0, 15));
        textField_search_d.setFont(new Font("等线", 0, 15));
        textField_search_y1.setFont(new Font("等线", 0, 15));
        textField_search_m1.setFont(new Font("等线", 0, 15));
        textField_search_d1.setFont(new Font("等线", 0, 15));
        textField_search_y2.setFont(new Font("等线", 0, 15));
        textField_search_m2.setFont(new Font("等线", 0, 15));
        textField_search_d2.setFont(new Font("等线", 0, 15));
        comboBox_searchtype.setFont(new Font("等线", 0, 13));
        comboBox_orderby.setFont(new Font("等线", 0, 13));
        comboBox_screen.setFont(new Font("等线", 0, 13));
        table_Record.setFont(new Font("宋体", 0, 13));
        textField_page.setFont(new Font("宋体", 0, 13));

        //设置透明显示背景图片
        radioButton_ascorder.setOpaque(false);
        radioButton_descorder.setOpaque(false);
        panel_button.setOpaque(false);
        panel_page.setOpaque(false);
        panel_screen.setOpaque(false);
        panel_order.setOpaque(false);
        panel_search.setOpaque(false);
        panel_searchorderscreen.setOpaque(false);
        panel_searchmorebutton.setOpaque(false);
        panel_seachmore0.setOpaque(false);
        panel_seachmore1.setOpaque(false);
        panel_orderscreen.setOpaque(false);
        textField_page.setOpaque(false);
        comboBox_searchtype.setBackground(Color.WHITE);
        comboBox_orderby.setBackground(Color.WHITE);
        comboBox_screen.setBackground(Color.WHITE);

        //setbounds
        title.setBounds(10, 10, 300, 40);
        scrollPane_tableRecord.setBounds(10, 60, 1160, 347);
        label_totalFine.setBounds(10,105,100,35);
        panel_seachmore0.setBounds(10,35,500,35);
        panel_seachmore1.setBounds(10,75,600,35);
        panel_searchmorebutton.setBounds(10,0,100,35);
        panel_search.setBounds(10,35,550,35);
        panel_orderscreen.setBounds(6,70,500,35);
        panel_searchorderscreen.setBounds(10,420,800,135);
        label_totalFine.setBounds(15,105,100,35);

        panel_page.setBounds(660, 420, 500, 50);
        panel_button.setBounds(20, 565, 1160, 35);


        /////面板添加组件///////
        panel_searchmorebutton.add(button_searchmore0);
        panel_seachmore0.add(label_BNO);
        panel_seachmore0.add(textField_BNO);
        panel_seachmore0.add(label_BNA);
        panel_seachmore0.add(textField_BNA);
        panel_seachmore0.add(button_search1);
        panel_seachmore1.add(label_BT);
        panel_seachmore1.add(textField_search_y1);
        panel_seachmore1.add(new JLabel("年"));
        panel_seachmore1.add(textField_search_m1);
        panel_seachmore1.add(new JLabel("月"));
        panel_seachmore1.add(textField_search_d1);
        panel_seachmore1.add(new JLabel("日  "));
        panel_seachmore1.add(label_RT);
        panel_seachmore1.add(textField_search_y2);
        panel_seachmore1.add(new JLabel("年"));
        panel_seachmore1.add(textField_search_m2);
        panel_seachmore1.add(new JLabel("月"));
        panel_seachmore1.add(textField_search_d2);
        panel_seachmore1.add(new JLabel("日"));
        panel_button.add(button_refresh);
        panel_button.add(button_return);
        panel_button.add(button_continueBorrow);
        panel_button.add(button_pay);
        buttonGroup_order.add(radioButton_ascorder);
        buttonGroup_order.add(radioButton_descorder);
        label_page1=new JLabel("当前页码:");
        label_page1.setFont(new Font("等线",0,15));
        textField_page.setText(String.valueOf(pageNum));
        label_page = new JLabel("共" + pageNumMax + "页 每页"+numOfRowInEachPage+"行");     //需放在search后面，防止pageNumMax更新不及时
        label_page.setFont(new Font("等线", 0, 15));
        panel_page.add(label_page1);
        panel_page.add(textField_page);
        panel_page.add(button_page);
        panel_page.add(label_page);
        panel_page.add(button_beforePage);
        panel_page.add(button_afterPage);
        panel_search.add(label_search);
        panel_search.add(comboBox_searchtype);
        panel_search.add(label_search1);
        panel_search.add(textField_search);
        panel_search.add(button_search);
        panel_order.add(label_order);
        panel_order.add(comboBox_orderby);
        panel_order.add(radioButton_ascorder);
        panel_order.add(radioButton_descorder);
        panel_screen.add(label_screen);
        panel_screen.add(comboBox_screen);
        panel_orderscreen.add(panel_order);
        panel_orderscreen.add(panel_screen);
        panel_searchorderscreen.add(panel_searchmorebutton);
        panel_searchorderscreen.add(panel_search);
        panel_searchorderscreen.add(panel_orderscreen);
        panel_searchorderscreen.add(label_totalFine);

        this.add(title);
        this.add(panel_button);
        this.add(panel_page);
        this.add(panel_searchorderscreen);
        this.add(scrollPane_tableRecord);
        /////end.面板添加组件///////

        ///////添加监听器///////
        ActionEventHandler handler = new ActionEventHandler();    //声明监听器
        button_refresh.addActionListener(handler);
        button_search.addActionListener(handler);
        button_return.addActionListener(handler);
        button_pay.addActionListener(handler);
        button_continueBorrow.addActionListener(handler);
        button_afterPage.addActionListener(handler);
        button_beforePage.addActionListener(handler);
        RadioButtonHandler radioButtonHandler = new RadioButtonHandler();
        radioButton_ascorder.addItemListener(radioButtonHandler);
        radioButton_descorder.addItemListener(radioButtonHandler);
        button_searchmore0.addActionListener(handler);
        button_searchmore1.addActionListener(handler);
        button_search.addActionListener(handler);
        button_search1.addActionListener(handler);
        button_page.addActionListener(handler);
        ///////end.添加监听器///////

        ///////执行SQL///////////
//        tableRefresh();
        search();
        ///////end.执行SQL///////////
        ///////////////////end.借阅管理界面////////////////////////
        //**********************************************************
    }

    public static int datatovector(ResultSet rs, Vector<Object[]> vector) {      //将结果集数据存到vector，返回行数
        vector.removeAllElements();
        int rowCount = 0;
        try {
            while (rs.next()) {
                int isOverdue = 0;    //是否逾期
                int uat = Account.UAT;   //获取可借阅最大天数
                double fine = 0;  //罚金
                int BTtoRT = rs.getInt(7);
                int wom = rs.getInt(5);   //获取缴费情况
                String RT = rs.getString(4);  //获取归还日期
                String datediff = rs.getString(6);  //获取时间差

                //罚金的计算有 未归还、已归还、仅归还 三种情况
                if ((wom == 0) && RT == null) {  //未归还
                    fine = (rs.getInt(6) - uat) * 0.02;
                } else if ((wom == 0) && RT != null) {
                    datediff = "已归还";
                } else if (wom == 1) {    //仅归还
                    fine = (BTtoRT - uat) * 0.02;
                    datediff = String.valueOf(BTtoRT);
                } else if (wom == 2) {    //已缴费
                    datediff = "已缴费";
                }

                int whetherOvemoney = rs.getInt(5);
                String swhetherOvemoney = "";
                switch (whetherOvemoney) {    //将是否欠费的数字显示为文字，便于查看
                    case 0:
                        swhetherOvemoney = "无欠款";
                        break;
                    case 1:
                        swhetherOvemoney = "未缴费";
                        break;
                    case 2:
                        swhetherOvemoney = "已缴费";
                        break;
                }
                if (fine <= 0) fine = 0;

                if (RT == null) {
                    RT = "未归还";
                } else {
                    RT = rs.getString(4);
                }

                fine = (double) Math.round(fine * 100) / 100;
                Object[] rowData = {rs.getString(1), rs.getString(2), rs.getString(3), RT, datediff, fine, swhetherOvemoney};
                vector.addElement(rowData);
                rowCount++;
            }
        } catch (SQLException sqlE) {
            JOptionPane.showMessageDialog(null, sqlE);
        }
        pageNumMax = rowCount / numOfRowInEachPage + 1;
        totalNumOfRow = rowCount;
        return rowCount;
    }


    public static void tableRefresh(ResultSet rs) {     //table刷新1
        vector_total.removeAllElements();
        model_Record.setRowCount(0);
        int totalNumofRow = datatovector(rs, vector_total);
        Function.showPage(model_Record, pageNum, numOfRowInEachPage, totalNumofRow, vector_total);

        int n = Function.getNowNumofBorrow(Account.UNO);
        int m = Function.getNumOvertime(Account.UNO);
        Form_user_Main.label_nowAccount.setText("欢迎使用 当前账户:" + Account.UNO + "  最多借阅数:" + Account.UUP + "   当前已借阅数:" + n + "   最长借阅时间:" + Account.UAT + "天   已有" + m + "本书逾期");
        double FINE = Function.getTotalFine(Account.UNO);
        label_totalFine.setText("总罚金:" + FINE);
        panel_searchorderscreen.repaint();
        textField_page.setText(String.valueOf(pageNum));
        label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
        panel_page.repaint();
    }

//旧版代码
//    public void tableRefresh(){     //table刷新2
//        model_Record.setRowCount(0);
//        //DATEDIFF(DAY,BT,GETDATE()) 天数差
////      01  String SQL="SELECT TBORROW.BNO,TBOOK.BNA,TBORROW.BT,TBORROW.RT,TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE()) ,DATEDIFF(DAY,BT,RT) FROM TBORROW,TUSER,TBOOK  WHERE TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO AND TUSER.UNO = '"+nowAccount+"' ";
//
//        String SQL="SELECT TBORROW.BNO,TBOOK.BNA,CONVERT(VARCHAR(100),TBORROW.BT,20),CONVERT(VARCHAR(100),RT,20),TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE()) ,DATEDIFF(DAY,BT,RT) FROM TBORROW,TUSER,TBOOK  WHERE TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO AND TUSER.UNO = '"+nowAccount+"'  ORDER BY "+orderby+"  "+ordertype+" ";
//
//        ResultSet rs=Database.executeQuery(SQL);
//        try{
//            while (rs.next()){
//                int isOverdue=0;    //是否逾期
//                int uat=Account.UAT;   //获取可借阅最大天数
//                double fine=0;  //罚金
//                int BTtoRT=rs.getInt(7);    //借书到还书时间差
//                int wom=rs.getInt(5);   //获取缴费情况
//                String RT=rs.getString(4);  //获取归还日期
//                String datediff=rs.getString(6);  //获取时间差
//
//                //罚金的计算有 未归还、已归还、仅归还 三种情况
//                if((wom == 0) && RT==null){  //未归还
//                    fine=(rs.getInt(6)-uat)*0.02;
//                }
//                else if((wom == 0) && RT!=null){
//                    datediff="已归还";
//                }
//                else if(wom==1){    //仅归还
//                    fine=(BTtoRT-uat)*0.02;
//                    datediff=String.valueOf(BTtoRT);
//                }
//                else if(wom==2){    //已缴费
//                    datediff="已缴费";
//                }
//
//                int whetherOvemoney=rs.getInt(5);
//                String swhetherOvemoney="";
//                switch (whetherOvemoney){    //将是否欠费的数字显示为文字，便于查看
//                    case 0:
//                        swhetherOvemoney="无欠款";
//                        break;
//                    case 1:
//                        swhetherOvemoney="未缴费";
//                        break;
//                    case 2:
//                        swhetherOvemoney="已缴费";
//                        break;
//                }
//                if(fine<=0) fine=0;
//
//                if(RT==null){
//                    RT="未归还";
//                }
//                else {
//                    RT=rs.getString(4);
//                }
//
//                fine=(double)Math.round(fine*100)/100;
//                Object[] rowData={rs.getString(1),rs.getString(2),rs.getString(3),RT,datediff,fine,swhetherOvemoney};
//                model_Record.addRow(rowData);
//            }
//        }catch (SQLException sqlE){
//            JOptionPane.showMessageDialog(null,sqlE);
//        }
//
//        int n=Function.getNowNumofBorrow(Account.UNO);
//        int m=Function.getNumOvertime(Account.UNO);
//        label_nowAccount.setText("欢迎使用 当前账户:"+Account.UNO+"  最多借阅数:"+Account.UUP+"   当前已借阅数:"+n+"   最长借阅时间:"+Account.UAT+"天   已有"+m+"本书逾期");
//        double FINE=Function.getTotalFine(Account.UNO);
//        label_totalFine.setText("总罚金:"+FINE);
//        panel_searchorderscreen.repaint();
//    }

    public static void search() {
        if (comboBox_searchtype.getSelectedIndex() + 1 == 3) {   //借书时间模糊搜素
            String year = textField_search_y.getText();
            String month = textField_search_m.getText();
            String day = textField_search_d.getText();
            if (!month.isEmpty()) {
                if (Integer.parseInt(month) <= 0 || Integer.parseInt(month) > 12) {
                    JOptionPane.showMessageDialog(null, "请输入正确的日期格式");
                    return;
                }
            }
            if (!day.isEmpty()) {
                if (Integer.parseInt(day) <= 0 || Integer.parseInt(day) > 31) {
                    JOptionPane.showMessageDialog(null, "请输入正确的日期格式");
                    return;
                }
            }
            if (month.length() == 1) {
                month = "0" + month;
            }
            String date =  year + "_" + month + "_" + day + "%";
//          01  String SQL="SELECT TBORROW.BNO,TBOOK.BNA,TBORROW.BT,TBORROW.RT,TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE())  ,DATEDIFF(DAY,BT,RT)  FROM TBORROW,TUSER,TBOOK WHERE TUSER.UNO = '"+nowAccount+"' AND TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO "+screenSQL+" AND CONVERT(varchar,BT,120) LIKE '"+date+"' ORDER BY "+orderby+" "+ordertype;

            String SQL = "SELECT TBORROW.BNO,TBOOK.BNA,CONVERT(VARCHAR(100),BT,20),CONVERT(VARCHAR(100),RT,20),TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE())  ,DATEDIFF(DAY,BT,RT)  FROM TBORROW,TUSER,TBOOK WHERE TUSER.UNO = '" + nowAccount + "' AND TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO " + screenSQL + " AND CONVERT(varchar,BT,120) LIKE '" + date + "' ORDER BY " + orderby + " " + ordertype;
            ResultSet rs = Database.executeQuery(SQL);
            tableRefresh(rs);
        }
        else if (comboBox_searchtype.getSelectedIndex() + 1 == 4) {   //还书时间模糊搜素
            String year = textField_search_y.getText();
            String month = textField_search_m.getText();
            String day = textField_search_d.getText();
//            String date=year+"_"+month+"_"+day+"%";
            String date =  year + "_" + month + "_" + day + "%";

//          01  String SQL="SELECT TBORROW.BNO,TBOOK.BNA,TBORROW.BT,TBORROW.RT,TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE()) ,DATEDIFF(DAY,BT,RT)  FROM TBORROW,TUSER,TBOOK WHERE TUSER.UNO = '"+nowAccount+"' AND TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO "+screenSQL+" AND CONVERT(varchar(100),RT,120) LIKE '"+date+"' ORDER BY "+orderby+" "+ordertype;

            String SQL = "SELECT TBORROW.BNO,TBOOK.BNA,CONVERT(VARCHAR(100),BT,20),CONVERT(VARCHAR(100),RT,20),TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE()) ,DATEDIFF(DAY,BT,RT)  FROM TBORROW,TUSER,TBOOK WHERE TUSER.UNO = '" + nowAccount + "' AND TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO " + screenSQL + " AND CONVERT(varchar,RT,120) LIKE '" + date + "' ORDER BY " + orderby + " " + ordertype;
            ResultSet rs = Database.executeQuery(SQL);
            tableRefresh(rs);
        }
        else {
//          01  String SQL="SELECT TBORROW.BNO,TBOOK.BNA,TBORROW.BT,TBORROW.RT,TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE()) ,DATEDIFF(DAY,BT,RT)  FROM TBORROW,TUSER,TBOOK WHERE TUSER.UNO = '"+nowAccount+"' AND TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO  "+screenSQL+" AND "+ searchtype +" LIKE '%"+textField_search.getText()+"%' ORDER BY "+orderby+" "+ordertype;

            String SQL = "SELECT TBORROW.BNO,TBOOK.BNA,CONVERT(VARCHAR(100),BT,20),CONVERT(VARCHAR(100),RT,20),TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE()) ,DATEDIFF(DAY,BT,RT)  FROM TBORROW,TUSER,TBOOK WHERE TUSER.UNO = '" + nowAccount + "' AND TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO  " + screenSQL + " AND " + searchtype + " LIKE '%" + textField_search.getText() + "%' ORDER BY " + orderby + " " + ordertype;
            ResultSet rs = Database.executeQuery(SQL);
            tableRefresh(rs);
        }
    }

    public static void searchmore(String SQL) {        //搜索3
        ResultSet rs = Database.executeQuery(SQL);
        tableRefresh(rs);
    }

    public static void refresh(){
        comboBox_orderby.setSelectedIndex(0);
        comboBox_screen.setSelectedIndex(0);
        comboBox_searchtype.setSelectedIndex(0);
        textField_page.setText("1");
        textField_BNA.setText("");
        textField_BNO.setText("");
        textField_BT.setText("");
        textField_RT.setText("");
        textField_search.setText("");
        textField_search_d.setText("");
        textField_search_m.setText("");
        textField_search_y.setText("");
        textField_search_d1.setText("");
        textField_search_m1.setText("");
        textField_search_y1.setText("");
        textField_search_d2.setText("");
        textField_search_m2.setText("");
        textField_search_y2.setText("");
        pageNum=1;
        ordertype = "ASC";
        searchtype = "TBOOK.BNA";
        orderby = "TBORROW.RT";
        screenSQL = "";
        search();
    }

    ///////////////监听器/////////////////
    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == button_refresh) {  //刷新
//                tableRefresh();
                refresh();
            }
            else if (event.getSource() == button_search) {      //查找
                search();
            }
            else if (event.getSource() == button_return) {   //还书
                int row = table_Record.getSelectedRow();  //获取table行
                String BNO = model_Record.getValueAt(table_Record.getSelectedRow(), 0).toString();
                String BNA = model_Record.getValueAt(table_Record.getSelectedRow(), 1).toString();
                String BT = model_Record.getValueAt(table_Record.getSelectedRow(), 2).toString();
                int BTtoRT = Function.getBTtoRT_user(Account.UNO, BNO, BT);
                if (BTtoRT == -1) {  //未归还
                    Form_User_ReturnBook form_user_returnBook = new Form_User_ReturnBook(BNO, BT);
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "图书已归还，无需还书！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

            }
            else if (event.getSource() == button_pay) {     //缴费
                int row = table_Record.getSelectedRow();  //获取table行
                String UNO = Account.UNO;
                String BNO = model_Record.getValueAt(table_Record.getSelectedRow(), 0).toString();
                String BT = model_Record.getValueAt(table_Record.getSelectedRow(), 2).toString();
                int BTtoRT = Function.getBTtoRT_user(UNO, BNO, BT);
                if (BTtoRT == -1) {  //未归还
                    JOptionPane.showMessageDialog(null, "未归还，无法缴费！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {    //已归还
                    int uat = Function.getUAT(UNO);   //最长借阅时长
                    if (BTtoRT <= uat) {  //未逾期
                        JOptionPane.showMessageDialog(null, "未逾期，无法缴费！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    } else {  //已逾期，可修改
                        int isModify = JOptionPane.showConfirmDialog(null, "是否缴费", "设置", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (isModify == JOptionPane.YES_OPTION) {   //点击是,已缴费
//                          01  String SQL = "UPDATE TBORROW SET WOM=2 WHERE BNO= '" + BNO + "' AND UNO = '" + UNO + "'  AND BT = CONVERT(datetime,'"+BT+"') ";

                            String SQL = "UPDATE TBORROW SET WOM=2 WHERE BNO= '" + BNO + "' AND UNO = '" + UNO + "'  AND CONVERT(VARCHAR(100),BT,20) = '" + BT + "' ";
                            //执行更新操作
                            int i = Database.executeUpdate(SQL);
                            if (i != 0) {   //执行成功
                                JOptionPane.showMessageDialog(null, "已缴费", "提示", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        search();
                    }
                }
            }
            else if (event.getSource() == button_continueBorrow) {
                int row = table_Record.getSelectedRow();  //获取table行
                String UNO = Account.UNO;
                String BNO = model_Record.getValueAt(table_Record.getSelectedRow(), 0).toString();
                String BT = model_Record.getValueAt(table_Record.getSelectedRow(), 2).toString();
                int time = 0;
                String SQL_whetherReturn = "SELECT RT,DATEDIFF(DAY,BT,GETDATE()) FROM TBORROW WHERE BNO= '" + BNO + "' AND UNO = '" + UNO + "'  AND CONVERT(VARCHAR(100),BT,20) = '" + BT + "' ";
                ResultSet resultSet_whetherReturn = Database.executeQuery(SQL_whetherReturn);
                try {
                    while (resultSet_whetherReturn.next()) {
                        String RT = resultSet_whetherReturn.getString(1);
                        time = resultSet_whetherReturn.getInt(2);
                        if (RT != null) {
                            JOptionPane.showMessageDialog(null, "已归还，无法续借！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    }
                } catch (SQLException sqlE) {
                    JOptionPane.showMessageDialog(null, sqlE);
                }
//                int time=Function.getTimeOfBorrow(UNO,BNO,BT);
                int uat = Function.getUAT(UNO);   //最长借阅时长
                if (time > uat) {  //未逾期
                    JOptionPane.showMessageDialog(null, "已逾期，无法续借！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {  //已逾期，可修改
                    int isModify = JOptionPane.showConfirmDialog(null, "是否续借", "设置", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (isModify == JOptionPane.YES_OPTION) {   //点击是,已缴费
//                          01  String SQL = "UPDATE TBORROW SET WOM=2 WHERE BNO= '" + BNO + "' AND UNO = '" + UNO + "'  AND BT = CONVERT(datetime,'"+BT+"') ";

                        String SQL = "UPDATE TBORROW SET BT=GETDATE() WHERE BNO= '" + BNO + "' AND UNO = '" + UNO + "'  AND CONVERT(VARCHAR(100),BT,20) = '" + BT + "' ";
                        //执行更新操作
                        int i = Database.executeUpdate(SQL);
                        if (i != 0) {   //执行成功
                            JOptionPane.showMessageDialog(null, "已续借", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    search();
                }
            }
            else if (event.getSource() == button_afterPage) {   //下一页
                if (pageNum >= pageNumMax) {
                    JOptionPane.showMessageDialog(null, "已到最后一页");
                    return;
                } else {
                    pageNum++;
                    Function.showPage(model_Record, pageNum, numOfRowInEachPage, totalNumOfRow, vector_total);
                }
                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("当前页码:" + pageNum + " 共" + pageNumMax + "页");
                panel_page.repaint();
            }
            else if (event.getSource() == button_beforePage) {      //上一页
                if (pageNum == 1) {
                    JOptionPane.showMessageDialog(null, "已到第一页");
                    return;
                } else {
                    pageNum--;
                    Function.showPage(model_Record, pageNum, numOfRowInEachPage, totalNumOfRow, vector_total);
                }
                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("当前页码:" + pageNum + " 共" + pageNumMax + "页");
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
                Function.showPage(model_Record,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
                panel_page.repaint();
            }
            else if (event.getSource() == button_searchmore0) {     //组合查询
                panel_searchorderscreen.removeAll();
                panel_searchmorebutton.removeAll();
                panel_searchmorebutton.add(button_searchmore1);
                panel_searchorderscreen.add(panel_searchmorebutton);
                panel_searchorderscreen.add(panel_seachmore0);
                panel_searchorderscreen.add(panel_seachmore1);
                panel_searchorderscreen.repaint();
                refresh();
            }
            else if (event.getSource() == button_searchmore1) {     //简单查询
                panel_searchorderscreen.removeAll();
                panel_searchmorebutton.removeAll();
                panel_searchmorebutton.add(button_searchmore0);
                panel_searchorderscreen.add(panel_searchmorebutton);
                panel_searchorderscreen.add(panel_search);
                panel_searchorderscreen.add(panel_orderscreen);
                panel_searchorderscreen.add(label_totalFine);
                panel_searchorderscreen.repaint();
                refresh();
            }
            else if (event.getSource() == button_search1) {       //组合查询
                //借书时间模糊搜索
                String year1 = textField_search_y1.getText();
                String month1 = textField_search_m1.getText();
                String day1 = textField_search_d1.getText();
                if (!month1.isEmpty()) {
                    if (Integer.parseInt(month1) <= 0 || Integer.parseInt(month1) > 12) {
                        JOptionPane.showMessageDialog(null, "请输入正确的日期格式");
                        return;
                    }
                }
                if (!day1.isEmpty()) {
                    if (Integer.parseInt(day1) <= 0 || Integer.parseInt(day1) > 31) {
                        JOptionPane.showMessageDialog(null, "请输入正确的日期格式");
                        return;
                    }
                }
                if (month1.length() == 1) {
                    month1 = "0" + month1;
                }
                String date1 =  year1 + "_" + month1 + "_" + day1 + "%";

                String SQL_BT = "  AND CONVERT(varchar,BT,120) LIKE '" + date1 + "'";

                //还书时间模糊搜索
                String year2 = textField_search_y2.getText();
                String month2 = textField_search_m2.getText();
                String day2 = textField_search_d2.getText();
                if (!month2.isEmpty()) {
                    if (Integer.parseInt(month2) <= 0 || Integer.parseInt(month2) > 12) {
                        JOptionPane.showMessageDialog(null, "请输入正确的日期格式");
                        return;
                    }
                }
                if (!day2.isEmpty()) {
                    if (Integer.parseInt(day2) <= 0 || Integer.parseInt(day2) > 31) {
                        JOptionPane.showMessageDialog(null, "请输入正确的日期格式");
                        return;
                    }
                }
                if (month2.length() == 1) {
                    month2 = "0" + month2;
                }
                String date2 =  year2 + "_" + month2 + "_" + day2 + "%";

                String SQL_RT = "  AND CONVERT(varchar,RT,120) LIKE '" + date2 + "'";
                if (month2.isEmpty() && year2.isEmpty() && day2.isEmpty()) {
                    SQL_RT = "";
                }

                String SQL = "SELECT TBORROW.BNO,TBOOK.BNA,CONVERT(VARCHAR(100),BT,20),CONVERT(VARCHAR(100),RT,20),TBORROW.WOM, DATEDIFF(DAY,BT,GETDATE()) ,DATEDIFF(DAY,BT,RT) FROM TBORROW,TUSER,TBOOK WHERE TBORROW.UNO=TUSER.UNO AND TBORROW.BNO=TBOOK.BNO AND TUSER.UNO = '" + nowAccount + "' AND TBORROW.BNO LIKE '%" + textField_BNO.getText() + "%'  AND TBOOK.BNA LIKE '%" + textField_BNA.getText() + "%' " + SQL_BT + SQL_RT + "  ORDER BY " + orderby + " " + ordertype;

                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("当前页码:" + pageNum + " 共" + pageNumMax + "页");
                panel_page.repaint();
                ResultSet rs = Database.executeQuery(SQL);
                tableRefresh(rs);

            }
        }
    }

    //radiobutton监听器
    public class RadioButtonHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getItem() == radioButton_ascorder) {
                ordertype = "ASC";
                search();
            } else if (e.getItem() == radioButton_descorder) {
                ordertype = "DESC";
                search();
            }
        }
    }
}
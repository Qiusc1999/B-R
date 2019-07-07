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

//借阅表的优化在监听器
public class Panel_ManageBorrow extends JPanel{
    private static JPanel panel_search,panel_order,panel_screen, panel_searchorderscreen,panel_seachmore0,panel_seachmore1,panel_searchmorebutton,panel_orderscreen;
    private static JButton button_manage_addRecord, button_manage_delRecord, button_manage_refresh,button_search, button_search1,button_setReturn,button_setPay,button_showBookDetail,button_showUserDetail;
    private static JButton button_searchmore0, button_searchmore1;
    private static JTextField textField_search, textField_search_y, textField_search_m, textField_search_d, textField_search_y1, textField_search_m1, textField_search_d1, textField_search_y2, textField_search_m2, textField_search_d2,textField_UNO,textField_UNA;
    private static JTextField textField_BNO, textField_BNA, textField_BT, textField_RT;
    private static JTable table_ManageRecord;
    private static JScrollPane scrollPane_tableRecord;
    private static DefaultTableModel model_ManageRecord;
    private static JComboBox comboBox_searchtype, comboBox_orderby,comboBox_screen;
    private static JRadioButton radioButton_ascorder,radioButton_descorder;
    private static String ordertype ="ASC";
    private static String searchtype="TUSER.UNO";
    private static String orderby="TUSER.UNO";
    private static String screenSQL="";
    private static int numOfRowInEachPage =20;
    private static int pageNum=1;
    private static int pageNumMax=1;
    private static int totalNumOfRow=0;
    private static JPanel panel_page;
    private static Vector<Object[]> vector_total;
    private static JLabel  label_BNO,label_UNO,label_UNA, label_BNA, label_BT, label_RT;
    private static JLabel label_page, label_page1;
    private static JButton button_beforePage,button_afterPage,button_page;
    private static JTextField textField_page;


    public Panel_ManageBorrow(){
        //**********************************************************
        ////////////////////借阅管理界面//////////////////////////
        /////////组件声明/////////
        //panel
        JPanel panel_button=new JPanel();
        JPanel panel_set=new JPanel();
        panel_searchorderscreen =new JPanel();
        panel_page=new JPanel();     //翻页面板
        panel_search=new JPanel();
        panel_order=new JPanel();
        panel_searchmorebutton = new JPanel();   //组合查询按钮panel
        panel_seachmore0 = new JPanel();   //组合查询panel
        panel_seachmore1 = new JPanel();   //组合查询panel
        panel_screen=new JPanel();
        panel_orderscreen = new JPanel();
        //button
        button_manage_refresh =new JButton("显示全部");
        button_showBookDetail=new JButton("显示图书详情");
        button_showUserDetail=new JButton("显示用户详情");
        button_manage_addRecord =new JButton("添加记录");
        button_manage_delRecord =new JButton("删除记录");
        button_setReturn =new JButton("设置是否归还");
        button_setPay=new JButton("设置是否缴费");
        button_search=new JButton("检索");
        button_search1=new JButton("检索");
        button_beforePage=new JButton("上一页");
        button_afterPage=new JButton("下一页");
        button_page=new JButton("跳转");
        radioButton_ascorder=new JRadioButton("升序",true);
        radioButton_descorder=new JRadioButton("降序",false);
        ButtonGroup buttonGroup_order=new ButtonGroup();
        button_searchmore0 = new JButton("组合查询");
        button_searchmore1 = new JButton("简单查询");
        //label
        JLabel title=new JLabel("借阅记录管理");
        JLabel label_order=new JLabel("按此类型排序");
        JLabel label_search=new JLabel("按此类型搜索");
        JLabel label_search1=new JLabel("输入关键字:");
        JLabel label_screen=new JLabel("筛选");
        label_page=new JLabel("当前页码:"+pageNum+" 共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
        label_UNO=new JLabel(" 借书证号:");
        label_UNA=new JLabel("姓名:");
        label_BNO = new JLabel("图书编号:");
        label_BNA = new JLabel("图书名称:");
        label_BT = new JLabel("借书时间:");
        label_RT = new JLabel("还书时间:");
        //textfield
        textField_page=new JTextField(3);
        textField_search = new JTextField(10);
        textField_search_y = new JTextField(2);
        textField_search_m = new JTextField(2);
        textField_search_d = new JTextField(2);
        textField_search_y1 = new JTextField(3);
        textField_search_m1 = new JTextField(2);
        textField_search_d1 = new JTextField(2);
        textField_search_y2 = new JTextField(3);
        textField_search_m2 = new JTextField(2);
        textField_search_d2 = new JTextField(2);
        textField_UNO = new JTextField(7);
        textField_UNA = new JTextField(5);
        textField_BNO = new JTextField(5);
        textField_BNA = new JTextField(5);
        textField_BT = new JTextField(7);
        textField_RT = new JTextField(7);
        //combobox
        String types[]={"借书证号","用户姓名","图书编号","图书名称","借书日期","还书日期"};
        String orders[]={"借书证号","用户姓名","图书编号","图书名称","借书日期","还书日期"};
        String screentypes[]={"无","未归还","逾期未归还","未缴费"};
        comboBox_searchtype =new JComboBox(types);  //选择列表
        comboBox_orderby =new JComboBox(orders);    //排序列表
        comboBox_screen =new JComboBox(screentypes);  //选择列表
        comboBox_searchtype.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    switch (comboBox_searchtype.getSelectedIndex()+1){
                        case 1:searchtype="TUSER.UNO";break;
                        case 2:searchtype="TUSER.UNA";break;
                        case 3:searchtype="TBOOK.BNO";break;
                        case 4:searchtype="TBOOK.BNA";break;
                        case 5:searchtype="BT";break;
                        case 6:searchtype="RT";break;
                    }
                    if(comboBox_searchtype.getSelectedIndex()+1==5||comboBox_searchtype.getSelectedIndex()+1==6){
                        panel_search.removeAll();
                        panel_search.repaint();
                        panel_search.add(label_search);
                        panel_search.add(comboBox_searchtype);
                        panel_search.add(new JLabel("输入日期:"));
                        panel_search.add(textField_search_y);
                        panel_search.add(new JLabel("年"));
                        panel_search.add(textField_search_m);
                        panel_search.add(new JLabel("月"));
                        panel_search.add(textField_search_d);
                        panel_search.add(new JLabel("日"));
                        panel_search.add(button_search);
                    }else {
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
                if(e.getStateChange()==ItemEvent.SELECTED){
                    switch (comboBox_orderby.getSelectedIndex()+1){
                        case 1:orderby="TUSER.UNO";break;
                        case 2:orderby="TUSER.UNA";break;
                        case 3:orderby="TBOOK.BNO";break;
                        case 4:orderby="TBOOK.BNA";break;
                        case 5:orderby="BT";break;
                        case 6:orderby="RT";break;
                    }
                    //执行search，不用点击检索按钮便可出结果，方便操作
                    search();
                }
            }
        });
        comboBox_screen.addItemListener(new ItemListener() {   //筛选列表
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    switch (comboBox_screen.getSelectedIndex()+1){
                        case 1:
                            screenSQL=""; break;
                        case 2:{
                            screenSQL="AND TBORROW.RT IS NULL ";    //未归还
                            break;}
                        case 3:{
                            screenSQL="AND TBORROW.RT IS NULL AND DATEDIFF(day,BT,GETDATE())> UAT ";   //未归还且已逾期
                            break;}
                        case 4:{
                            screenSQL="AND TBORROW.WOM = 1 ";
                            break;}
                    }
                    //执行search，不用点击检索按钮便可出结果，方便操作
                    search();
                }
            }
        });
        //vector
        vector_total =new Vector<Object[]>();
        //table
        Object[] columeName_ManageRecord=new Object[]{"借书证号","用户姓名","图书编号","图书名称","借书时间","还书时间","已过天数","产生罚金","是否欠费"}; //列名
        Object[][] context_ManageRecord=new Object[][]{};
        model_ManageRecord =new DefaultTableModel(context_ManageRecord, columeName_ManageRecord){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        table_ManageRecord =new JTable(model_ManageRecord);
        scrollPane_tableRecord =new JScrollPane(table_ManageRecord); //用ScrollPane包含Table
        /////////end.组件声明/////////

        /////////组件设置////////
        //设置layout
        this.setLayout(null);
        panel_page.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_button.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_search.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_order.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_orderscreen.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_searchorderscreen.setLayout(null);
        panel_seachmore0.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_seachmore1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_screen.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_searchmorebutton.setLayout(new FlowLayout(FlowLayout.LEFT));
        //设置字体
        title.setFont(new Font("等线",1,30));
        radioButton_ascorder.setFont(new Font("等线",0,13));
        radioButton_descorder.setFont(new Font("等线",0,13));
        button_search.setFont(new Font("等线",0,15));
        button_manage_addRecord.setFont(new Font("等线",0,15));
        button_setPay.setFont(new Font("等线",0,15));
        button_showUserDetail.setFont(new Font("等线",0,15));
        button_showBookDetail.setFont(new Font("等线",0,15));
        button_manage_delRecord.setFont(new Font("等线",0,15));
        button_manage_refresh.setFont(new Font("等线",0,15));
        button_setReturn.setFont(new Font("等线",0,15));
        button_searchmore0.setFont(new Font("等线", 0, 15));
        button_searchmore1.setFont(new Font("等线", 0, 15));
        label_order.setFont(new Font("等线",0,15));
        label_search.setFont(new Font("等线",0,15));
        label_search1.setFont(new Font("等线",0,15));
        label_screen.setFont(new Font("等线",0,15));
        label_page.setFont(new Font("等线",0,15));
        button_afterPage.setFont(new Font("等线",0,15));
        button_beforePage.setFont(new Font("等线",0,15));
        button_page.setFont(new Font("等线",0,15));
        textField_search.setFont(new Font("等线",0,15));
        textField_search_d.setFont(new Font("等线",0,15));
        textField_search_m.setFont(new Font("等线",0,15));
        textField_search_y.setFont(new Font("等线",0,15));
        textField_BNO.setFont(new Font("等线", 0, 15));
        textField_BNA.setFont(new Font("等线", 0, 15));
        textField_BT.setFont(new Font("等线", 0, 15));
        textField_RT.setFont(new Font("等线", 0, 15));
        textField_UNA.setFont(new Font("等线", 0, 15));
        textField_UNO.setFont(new Font("等线", 0, 15));
        label_BNO.setFont(new Font("等线", 0, 15));
        label_BNA.setFont(new Font("等线", 0, 15));
        label_BT.setFont(new Font("等线", 0, 15));
        label_RT.setFont(new Font("等线", 0, 15));
        label_UNA.setFont(new Font("等线", 0, 15));
        label_UNO.setFont(new Font("等线", 0, 15));
        button_search.setFont(new Font("等线", 0, 15));
        button_search1.setFont(new Font("等线", 0, 15));
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
        comboBox_searchtype.setFont(new Font("等线",0,13));
        comboBox_orderby.setFont(new Font("等线",0,13));
        comboBox_screen.setFont(new Font("等线",0,13));
        table_ManageRecord.setFont(new Font("宋体",0,13));
        //设置透明
        this.setOpaque(false);
        textField_page.setOpaque(false);
        scrollPane_tableRecord.setOpaque(false);
        table_ManageRecord.setOpaque(false);
        panel_screen.setOpaque(false);
        panel_order.setOpaque(false);
        radioButton_descorder.setOpaque(false);
        radioButton_ascorder.setOpaque(false);
        textField_search_m.setOpaque(false);
        textField_search.setOpaque(false);
        textField_search_y.setOpaque(false);
        textField_search_d.setOpaque(false);
        panel_seachmore0.setOpaque(false);
        panel_seachmore1.setOpaque(false);
        panel_searchmorebutton.setOpaque(false);
        panel_search.setOpaque(false);
        panel_orderscreen.setOpaque(false);
        panel_searchorderscreen.setOpaque(false);
        panel_page.setOpaque(false);
        panel_button.setOpaque(false);
        panel_set.setOpaque(false);
        comboBox_searchtype.setBackground(Color.WHITE);
        comboBox_orderby.setBackground(Color.WHITE);
        comboBox_screen.setBackground(Color.WHITE);

        //setBounds
        title.setBounds(10,10,300,40);
        scrollPane_tableRecord.setBounds(10,60,1160,347);
        panel_seachmore0.setBounds(6,35,800,35);
        panel_seachmore1.setBounds(10,75,800,35);
        panel_searchmorebutton.setBounds(0,0,100,35);
        panel_search.setBounds(10,35,800,35);
        panel_orderscreen.setBounds(6,70,700,35);
        panel_searchorderscreen.setBounds(10,410,800,105);
        panel_page.setBounds(660, 420, 500, 50);
//        panel_button.setBounds(380,525,380,35);
//        panel_set.setBounds(330,560,480,35);
        panel_button.setBounds(10,525,380,35);
        panel_set.setBounds(5,560,480,35);

        /////////end.组件设置////////

        /////面板添加组件///////
        panel_searchmorebutton.add(button_searchmore0);
        panel_seachmore0.add(label_UNO);
        panel_seachmore0.add(textField_UNO);
        panel_seachmore0.add(label_UNA);
        panel_seachmore0.add(textField_UNA);
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
        panel_button.add(button_manage_refresh);
        panel_button.add(button_showBookDetail);
        panel_button.add(button_showUserDetail);
        panel_set.add(button_manage_addRecord);
        panel_set.add(button_manage_delRecord);
        panel_set.add(button_setReturn);
        panel_set.add(button_setPay);
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
        panel_screen.add(label_screen);
        panel_screen.add(comboBox_screen);
        panel_orderscreen.add(panel_order);
        panel_orderscreen.add(panel_screen);
        panel_searchorderscreen.add(panel_searchmorebutton);
        panel_searchorderscreen.add(panel_search);
        panel_searchorderscreen.add(panel_orderscreen);
        //thisPanel
        this.add(scrollPane_tableRecord);
        this.add(panel_searchorderscreen);
//        this.add(panel_screen);
        this.add(panel_set);
        this.add(panel_button);
        this.add(panel_page);
        this.add(title);
        /////end.面板添加组件///////

        ///////添加监听器///////
        ActionEventHandler handler=new ActionEventHandler();    //声明监听器
        button_manage_addRecord.addActionListener(handler);
        button_manage_delRecord.addActionListener(handler);
        button_manage_refresh.addActionListener(handler);
        button_searchmore0.addActionListener(handler);
        button_searchmore1.addActionListener(handler);
        button_search.addActionListener(handler);
        button_search1.addActionListener(handler);
        button_setReturn.addActionListener(handler);
        button_setPay.addActionListener(handler);
        button_showBookDetail.addActionListener(handler);
        button_showUserDetail.addActionListener(handler);
        RadioButtonHandler radioButtonHandler=new RadioButtonHandler();
        radioButton_ascorder.addItemListener(radioButtonHandler);
        radioButton_descorder.addItemListener(radioButtonHandler);
        button_afterPage.addActionListener(handler);
        button_beforePage.addActionListener(handler);
        button_page.addActionListener(handler);
        ///////end.添加监听器///////
        search();
        ///////////////////end.借阅管理界面////////////////////////
        //**********************************************************
    }

    public static int datetovector(ResultSet rs,Vector<Object[]> vector){      //将结果集数据存到vector，返回行数
        vector.removeAllElements();
        int rowCount=0;
        try {
            while (rs.next()){
                int isOverdue=0;    //是否逾期
                int uat=rs.getInt(9);   //获取可借阅最大天数
                double fine=0;  //罚金
                int BTtoRT=rs.getInt(10);
                int wom=rs.getInt(7);   //获取缴费情况
                String RT=rs.getString(6);  //获取归还日期
                String datediff=rs.getString(8);  //获取时间差

                //罚金的计算有 未归还、已归还、仅归还 三种情况
                if((wom == 0) && RT==null){  //未归还
                    fine=(rs.getInt(8)-uat)*0.02;
                }
                else if((wom == 0) && RT!=null){
                    datediff="已归还";
                }
                else if(wom==1){    //仅归还
                    fine=(BTtoRT-uat)*0.02;
                    datediff=String.valueOf(fine);
                }
                else if(wom==2){    //已缴费
                    datediff="已缴费";
                }
                int whetherOvemoney=rs.getInt(7);
                String swhetherOvemoney="";
                switch (whetherOvemoney){    //将是否欠费的数字显示为文字，便于查看
                    case 0:
                        swhetherOvemoney="无欠款";
                        break;
                    case 1:
                        swhetherOvemoney="未缴费";
                        break;
                    case 2:
                        swhetherOvemoney="已缴费";
                        break;
                }
                if(fine<=0) fine=0;

                if(RT==null){
                    RT="未归还";
                }
                else {
                    RT=rs.getString(6);
                }
                fine=(double)Math.round(fine*100)/100;
                Object[] rowData={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),RT,datediff,fine,swhetherOvemoney};
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
        model_ManageRecord.setRowCount(0);
        int totalNumofRow=datetovector(rs, vector_total);
//        pageNum=1;
        Function.showPage(model_ManageRecord,pageNum, numOfRowInEachPage,totalNumofRow,vector_total);
        textField_page.setText(String.valueOf(pageNum));
        label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
        panel_page.repaint();
    }

//    public static void tableRefresh(){     //table刷新2
//        model_ManageRecord.setRowCount(0);
//        //DATEDIFF(DAY,BT,GETDATE()) 天数差 ,DATEDIFF(DAY,BT,RT)计算罚金
//        String SQL="SELECT TBORROW.UNO,TUSER.UNA,TBORROW.BNO,TBOOK.BNA,TBORROW.BT,TBORROW.RT,TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE()) ,UAT ,DATEDIFF(DAY,BT,RT) FROM TBORROW,TUSER,TBOOK,TGDINFO  WHERE TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO AND TUSER.UGD=TGDINFO.UGD ";
//        ResultSet rs=Database.executeQuery(SQL);
//        try{
//            while (rs.next()){
//                int isOverdue=0;    //是否逾期
//                int uat=rs.getInt(9);   //获取可借阅最大天数
//                double fine=0;  //罚金
//                int BTtoRT=rs.getInt(10);
//                int wom=rs.getInt(7);   //获取缴费情况
//                String RT=rs.getString(6);  //获取归还日期
//                String datediff=rs.getString(8);  //获取时间差
//
//                //罚金的计算有 未归还、已归还、仅归还 三种情况
//                if((wom == 0) && RT==null){  //未归还
//                    fine=(rs.getInt(8)-uat)*0.02;
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
//                int whetherOvemoney=rs.getInt(7);
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
//                //归还时间列视觉优化
//                if(RT==null){
//                    RT="未归还";
//                }
//                else {
//                    RT=rs.getString(6);
//                }
//
//                Object[] rowData={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),RT,datediff,fine,swhetherOvemoney};
//                model_ManageRecord.addRow(rowData);
//            }
//        }catch (SQLException sqlE){
//            JOptionPane.showMessageDialog(null,sqlE);
//        }
//    }

    public static void search(){       //搜索
        if(comboBox_searchtype.getSelectedIndex()+1==5) {   //借书时间模糊搜素
            String year=textField_search_y.getText();
            String month=textField_search_m.getText();
            String day=textField_search_d.getText();

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

            String date=year+"_"+month+"_"+day+"%";
            String SQL="SELECT TBORROW.UNO,TUSER.UNA,TBORROW.BNO,TBOOK.BNA,TBORROW.BT,TBORROW.RT,TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE()) ,UAT ,DATEDIFF(DAY,BT,RT)  FROM TBORROW,TUSER,TBOOK,TGDINFO WHERE TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO AND TUSER.UGD=TGDINFO.UGD  "+screenSQL+" AND CONVERT(varchar,BT,120) LIKE '"+date+"' ORDER BY "+orderby+" "+ordertype;
            ResultSet rs=Database.executeQuery(SQL);
            tableRefresh(rs);
        }else if(comboBox_searchtype.getSelectedIndex()+1==6){   //还书时间模糊搜素
            String year=textField_search_y.getText();
            String month=textField_search_m.getText();
            String day=textField_search_d.getText();

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

            String date=year+"_"+month+"_"+day+"%";
            String SQL="SELECT TBORROW.UNO,TUSER.UNA,TBORROW.BNO,TBOOK.BNA,TBORROW.BT,TBORROW.RT,TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE()) ,UAT ,DATEDIFF(DAY,BT,RT)  FROM TBORROW,TUSER,TBOOK,TGDINFO WHERE TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO AND TUSER.UGD=TGDINFO.UGD  "+screenSQL+" AND CONVERT(varchar,RT,120) LIKE '"+date+"' ORDER BY "+orderby+" "+ordertype;
            ResultSet rs=Database.executeQuery(SQL);
            tableRefresh(rs);
        }else {
            String SQL="SELECT TBORROW.UNO,TUSER.UNA,TBORROW.BNO,TBOOK.BNA,TBORROW.BT,TBORROW.RT,TBORROW.WOM , DATEDIFF(DAY,BT,GETDATE()) ,UAT ,DATEDIFF(DAY,BT,RT)  FROM TBORROW,TUSER,TBOOK,TGDINFO WHERE TBORROW.BNO=TBOOK.BNO AND TBORROW.UNO=TUSER.UNO AND TUSER.UGD=TGDINFO.UGD  "+screenSQL+" AND "+ searchtype +" LIKE '%"+textField_search.getText()+"%' ORDER BY "+orderby+" "+ordertype;
            ResultSet rs=Database.executeQuery(SQL);
            tableRefresh(rs);
        }
    }

    public static void searchmore(String SQL) {        //搜索3
        ResultSet rs = Database.executeQuery(SQL);
        tableRefresh(rs);
    }

    public static void refresh(){
        pageNum=1;
        ordertype ="ASC";
        searchtype="TUSER.UNO";
        orderby="TUSER.UNO";
        screenSQL="";
        comboBox_orderby.setSelectedIndex(0);
        comboBox_screen.setSelectedIndex(0);
        comboBox_searchtype.setSelectedIndex(0);
        textField_page.setText("1");
        textField_UNA.setText("");
        textField_UNO.setText("");
        textField_BNA.setText("");
        textField_BNO.setText("");
        textField_BT.setText("");
        textField_RT.setText("");
        textField_search_d.setText("");
        textField_search_m.setText("");
        textField_search_y.setText("");
        textField_search_d1.setText("");
        textField_search_m1.setText("");
        textField_search_y1.setText("");
        textField_search_d2.setText("");
        textField_search_m2.setText("");
        textField_search_y2.setText("");
        textField_search.setText("");
        ordertype ="ASC";
        searchtype="TUSER.UNO";
        orderby="TUSER.UNO";
        screenSQL="";
        Function.showPage(model_ManageRecord,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
        search();
    }

    ///////////////监听器/////////////////
    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource()== button_manage_addRecord){  //添加记录
                Form_admin_addRecord form_admin_addRecord=new Form_admin_addRecord();
            }
            else if(event.getSource()==button_manage_refresh){  //刷新
                refresh();
            }
            else if(event.getSource()== button_manage_delRecord){  //删除记录
                try{
                    String Uno= model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(),0).toString();
                    String Bno= model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(),2).toString();
                    String BT=model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(),4).toString();
                    int isDelete=JOptionPane.showConfirmDialog(null,"是否删除 “借书证号:"+Uno+" 图书编号:"+Bno+"” 记录？","是否删除",JOptionPane.YES_NO_CANCEL_OPTION);
                    if(isDelete==JOptionPane.YES_OPTION){       //确定删除
                        String SQL="DELETE FROM TBORROW WHERE UNO='"+Uno+"' AND BNO = '"+Bno+"' AND BT = CONVERT(datetime,'"+BT+"') ";
                        int i=Database.executeUpdate(SQL);
                        if(i!=0){
                            JOptionPane.showMessageDialog(null,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
                            search();
                        }
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"请在上方选中记录后删除","提示",JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(event.getSource()==button_search){      //查找
                search();
            }
            else if(event.getSource()== button_setReturn){      //设置是否归还
                String RT=model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(),5).toString();
                int row= table_ManageRecord.getSelectedRow();  //获取table行
                String UNO=model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(),0).toString();
                String BNO= model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(),2).toString();
                String BT=model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(),4).toString();
                //获取最多可借阅天数
                int UAT=0;  //最多可借阅天数
                UAT=Function.getUAT(UNO);
                int isModify=JOptionPane.showConfirmDialog(null,"设置 是否归还","设置",JOptionPane.YES_NO_CANCEL_OPTION);
                if(isModify==JOptionPane.YES_OPTION){   //点击是
                    String SQL="SELECT GETDATE() ,DATEDIFF(DAY,BT,GETDATE()) FROM TBORROW WHERE BNO= '"+BNO+"' AND UNO = '"+UNO+"' AND BT= CONVERT(DATETIME,'"+BT+"') ";//获取间隔时间
                    String SQL1=""; //用于更新操作
                    String nowTime="";  //当前时间用于更新
                    int datediff=0;
                    String sOweMoney="无欠款";
                    ResultSet rs=Database.executeQuery(SQL);
                    try{
                        while (rs.next()){
                            nowTime=rs.getString(1);
                            datediff=rs.getInt(2);
                            if(datediff>UAT)    //已逾期，设置是否欠款为是
                            {
                                sOweMoney="未缴费";
                                SQL1="UPDATE TBORROW SET RT=CONVERT(datetime,'"+nowTime+"'),WOM=1 WHERE BNO= '"+BNO+"' AND UNO = '"+UNO+"' AND  BT = CONVERT(datetime,'"+BT+"') ";
                            }
                            else {  //未逾期，设置是否欠款为否
                                SQL1="UPDATE TBORROW SET RT=CONVERT(datetime,'"+nowTime+"'),WOM=0 WHERE BNO= '"+BNO+"' AND UNO = '"+UNO+"' AND BT = CONVERT(datetime,'"+BT+"') ";
                            }
                        }
                    }catch (SQLException sqlE){
                        JOptionPane.showMessageDialog(null,sqlE);
                    }
                    //执行更新操作
                    int i=Database.executeUpdate(SQL1);
                    if(i!=0){
                    //获取归还时间，用于更新table
                        JOptionPane.showMessageDialog(null,"已设置：已归还","提示",JOptionPane.INFORMATION_MESSAGE);
                        model_ManageRecord.setValueAt(sOweMoney,row,8);
                        model_ManageRecord.setValueAt(nowTime,row,5);
                        search();
                    }
                }
                else if(isModify==JOptionPane.NO_OPTION){   //点击否
                    String SQL="UPDATE TBORROW SET WOM=0,RT=null WHERE BNO= '"+BNO+"' AND UNO = '"+UNO+"'  AND BT = CONVERT(datetime,'"+BT+"') ";
                    int i=Database.executeUpdate(SQL);
                    if(i!=0){
                        JOptionPane.showMessageDialog(null,"已设置：未归还","提示",JOptionPane.INFORMATION_MESSAGE);
                        model_ManageRecord.setValueAt("无欠款",row,8);
                        model_ManageRecord.setValueAt("未归还",row,5);
                        search();
                    }
                }
            }
            else if(event.getSource()==button_setPay) {     //设置是否缴费
                int row = table_ManageRecord.getSelectedRow();  //获取table行
                String UNO = model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(), 0).toString();
                String BNO = model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(), 2).toString();
                String BT=model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(),4).toString();
                int BTtoRT=Function.getBTtoRT(UNO,BNO,BT);
                if(BTtoRT==-1){  //未归还
                    JOptionPane.showMessageDialog(null,"未归还，无法更改！","提示",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                else {    //已归还
                    int uat=Function.getUAT(UNO);   //最长借阅时长
                    if(BTtoRT<=uat){  //未逾期
                        JOptionPane.showMessageDialog(null,"未逾期，无法更改！","提示",JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    else {  //已逾期，可修改
                        String sOweMoney="未缴费";
                        int isModify = JOptionPane.showConfirmDialog(null, "设置 是否缴费", "设置", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (isModify == JOptionPane.YES_OPTION) {   //点击是,已缴费
                            String SQL = "UPDATE TBORROW SET WOM=2 WHERE BNO= '" + BNO + "' AND UNO = '" + UNO + "'  AND BT = CONVERT(datetime,'"+BT+"') ";
                            //执行更新操作
                            int i = Database.executeUpdate(SQL);
                            if (i != 0) {   //执行成功
                                sOweMoney="已缴费";
                                JOptionPane.showMessageDialog(null, "已设置：已缴费", "提示", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }else if(isModify==JOptionPane.NO_OPTION) {   //点击否，未缴费
                            sOweMoney="未缴费";
                            String SQL = "UPDATE TBORROW SET WOM=1 WHERE BNO= '" + BNO + "' AND UNO = '" + UNO + "' AND BT = CONVERT(datetime,'"+BT+"') ";
                            //执行更新操作
                            int i = Database.executeUpdate(SQL);
                            if (i != 0) {   //执行成功
                                sOweMoney="未缴费";
                                JOptionPane.showMessageDialog(null, "已设置：未缴费", "提示", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        model_ManageRecord.setValueAt(sOweMoney, row, 8);
                        search();
                    }
                }
            }
            else if(event.getSource()==button_showBookDetail){      //显示图书详情
                String BNO= model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(),2).toString();
                Form_showBookDetail form_showBookDetail=new Form_showBookDetail(BNO);
            }
            else if(event.getSource()==button_showUserDetail){      //显示用户详情
                String UNO=model_ManageRecord.getValueAt(table_ManageRecord.getSelectedRow(),0).toString();
                Form_showUserDetail form_showUserDetail=new Form_showUserDetail(UNO);
            }
            else if(event.getSource()==button_afterPage){   //下一页
                if(pageNum>=pageNumMax){
                    JOptionPane.showMessageDialog(null,"已到最后一页");
                    return;
                }
                else {
                    pageNum++;
                    Function.showPage(model_ManageRecord,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
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
                    Function.showPage(model_ManageRecord,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
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
                Function.showPage(model_ManageRecord,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
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
//                panel_searchorderscreen.add(panel_orderscreen);
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

                String SQL = "SELECT TBORROW.UNO,TUSER.UNA,TBORROW.BNO,TBOOK.BNA,TBORROW.BT,TBORROW.RT,TBORROW.WOM, DATEDIFF(DAY,BT,GETDATE()),UAT ,DATEDIFF(DAY,BT,RT) FROM TBORROW,TUSER,TBOOK ,TGDINFO WHERE TBORROW.UNO=TUSER.UNO AND TBORROW.BNO=TBOOK.BNO AND TUSER.UGD=TGDINFO.UGD  AND TUSER.UNO LIKE '%" + textField_UNO.getText() + "%' AND TUSER.UNA LIKE '%"+textField_UNA.getText()+"%' AND TBORROW.BNO LIKE '%" + textField_BNO.getText() + "%'  AND TBOOK.BNA LIKE '%" + textField_BNA.getText() + "%' " + SQL_BT + SQL_RT + "  ORDER BY " + orderby + " " + ordertype;
                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("共" + pageNumMax + "页");
                panel_page.repaint();
                ResultSet rs = Database.executeQuery(SQL);
                tableRefresh(rs);

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

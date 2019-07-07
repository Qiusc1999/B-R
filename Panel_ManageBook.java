package BANDR;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.Pattern;

public class Panel_ManageBook extends JPanel {
    private static JButton button_manage_addBook, button_manage_delBook, button_manage_refresh, button_search,button_search1, button_modify;
    private static JTextField textField_search, textField_sreen_d;
    private static JTextField textField_BNO,textField_BNA,textField_BDA,textField_BPU;
    private static JLabel label_screen;
    private static JTable table_ManageBook;
    private static JScrollPane scrollPane_tableBook;
    private static DefaultTableModel model_ManageBook;
    private static JComboBox comboBox_searchtype, comboBox_orderby,comboBox_screen;
    private static JRadioButton radioButton_ascorder, radioButton_descorder;
    private static String ordertype = " ASC ";
    private static String searchtype = " BNA LIKE ";
    private static String searchtype1 = "";
    private static String orderby = " ORDER BY BNO ";
    private static String screentype="";
    private static int int_screendaynum =0;
    private static JButton button_screen, button_searchmore0, button_searchmore1;
    private static int numOfRowInEachPage =20;
    private static int pageNum=1;
    private static int pageNumMax=1;
    private static int totalNumOfRow=0;
    private static JPanel panel_page, panel_searchmorebutton,panel_seachmore0,panel_seachmore1, panel_searchorderscreen,panel_orderscreen,panel_search,panel_screen,panel_order;
    private static Vector<Object[]> vector_total;
    private static JLabel label_BNO,label_BNA,label_BDA,label_BPU;
    private static JLabel label_page, label_page1;
    private static JButton button_beforePage,button_afterPage,button_page;
    private static JTextField textField_page;

    public Panel_ManageBook() {
        //**********************************************************
        ////////////////////图书管理界面//////////////////////////
        /////////组件声明/////////
        //panel
        JLabel title=new JLabel("图书信息管理");
        JLabel label_order = new JLabel("按此类型排序");
        JPanel panel_button = new JPanel();
        panel_searchmorebutton=new JPanel();   //组合查询按钮
        panel_seachmore0 =new JPanel();   //组合查询
        panel_seachmore1=new JPanel();   //组合查询
        panel_page=new JPanel();     //翻页面板
        panel_searchorderscreen = new JPanel();
        panel_orderscreen=new JPanel();
        panel_search = new JPanel();
        panel_screen=new JPanel();
        panel_order = new JPanel();
        //button
        button_manage_addBook = new JButton("添加图书");
        button_manage_delBook = new JButton("删除图书");
        button_manage_refresh = new JButton("显示全部");
        button_screen=new JButton("筛选");
        button_searchmore0 =new JButton("组合查询");
        button_searchmore1 =new JButton("简单查询");
        button_modify = new JButton("修改图书信息");
        button_search = new JButton("检索");
        button_search1 = new JButton("检索");
        button_page=new JButton("跳转");
        button_beforePage=new JButton("上一页");
        button_afterPage=new JButton("下一页");
        radioButton_ascorder = new JRadioButton("升序", true);
        radioButton_descorder = new JRadioButton("降序", false);
        ButtonGroup buttonGroup_order = new ButtonGroup();
        //label
        JLabel label_search = new JLabel("按此类型搜索");
        JLabel label_search1 = new JLabel("输入关键字:");
        label_screen=new JLabel("筛选近期上架图书");
        label_BNO=new JLabel("图书编号:");
        label_BNA=new JLabel("图书名称:");
        label_BDA=new JLabel("出版日期:");
        label_BPU=new JLabel("出版社:   ");
        //textField
        textField_page=new JTextField(3);
        textField_search = new JTextField(10);
        textField_sreen_d =new JTextField(5);
        textField_BNO=new JTextField(10);
        textField_BNA=new JTextField(10);
        textField_BDA=new JTextField(10);
        textField_BPU=new JTextField(10);
        //combobox
        String types[] = {"图书名称","图书编号", "出版日期", "图书出版社", "图书存放位置"};
        String screen[]={"无","1周内","1月内","3月内","1年内","自定义"};
        String orders[] = {"图书编号", "出版日期","上架日期"};
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
                    //执行search，不用点击检索按钮便可出结果，方便操作
                    search();
                }
            }
        });
        //vector
        vector_total =new Vector<Object[]>();
        //table
        Object[] columeName_ManageBook = new Object[]{"图书编号", "图书名称", "出版日期", "图书出版社", "图书存放位置","剩余数量", "图书总数量", "上架时间"}; //列名
        Object[][] context_ManageBook = new Object[][]{};
        model_ManageBook = new DefaultTableModel(context_ManageBook, columeName_ManageBook){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        table_ManageBook = new JTable(model_ManageBook);
        scrollPane_tableBook = new JScrollPane(table_ManageBook); //用ScrollPane包含Table
        /////////end.组件声明/////////

        /////////组件设置////////
        //设置layout
        this.setLayout(null);
        panel_page.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_button.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_searchmorebutton.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_seachmore0.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_seachmore1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_search.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_order.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_screen.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_orderscreen.setLayout(new GridLayout(2,1));
        panel_searchorderscreen.setLayout(null);
        //设置字体
        title.setFont(new Font("等线",1,30));
        radioButton_ascorder.setFont(new Font("等线",0,13));
        radioButton_descorder.setFont(new Font("等线",0,13));
        button_searchmore0.setFont(new Font("等线",0,15));
        button_searchmore1.setFont(new Font("等线",0,15));
        textField_BNO.setFont(new Font("等线",0,15));
        textField_BNA.setFont(new Font("等线",0,15));
        textField_BDA.setFont(new Font("等线",0,15));
        textField_BPU.setFont(new Font("等线",0,15));
        button_search.setFont(new Font("等线",0,15));
        button_search1.setFont(new Font("等线",0,15));
        button_screen.setFont(new Font("等线",0,15));
        button_modify.setFont(new Font("等线",0,15));
        button_manage_refresh.setFont(new Font("等线",0,15));
        button_manage_addBook.setFont(new Font("等线",0,15));
        button_manage_delBook.setFont(new Font("等线",0,15));
        button_beforePage.setFont(new Font("等线",0,15));
        button_afterPage.setFont(new Font("等线",0,15));
        button_page.setFont(new Font("等线",0,15));
        label_order.setFont(new Font("等线",0,15));
        label_search.setFont(new Font("等线",0,15));
        label_search1.setFont(new Font("等线",0,15));
        label_BNO.setFont(new Font("等线",0,15));
        label_BNA.setFont(new Font("等线",0,15));
        label_BDA.setFont(new Font("等线",0,15));
        label_BPU.setFont(new Font("等线",0,15));
        label_screen.setFont(new Font("等线",0,15));
        textField_search.setFont(new Font("等线",0,15));
        textField_sreen_d.setFont(new Font("等线",0,15));
        comboBox_searchtype.setFont(new Font("等线",0,13));
        comboBox_orderby.setFont(new Font("等线",0,13));
        comboBox_screen.setFont(new Font("等线",0,13));
        table_ManageBook.setFont(new Font("宋体",0,13));

        //设置透明
        this.setOpaque(false);
        scrollPane_tableBook.setOpaque(false);
        panel_button.setOpaque(false);
        panel_page.setOpaque(false);
        panel_searchmorebutton.setOpaque(false);
        panel_seachmore0.setOpaque(false);
        panel_seachmore1.setOpaque(false);
        panel_order.setOpaque(false);
        panel_screen.setOpaque(false);
        panel_search.setOpaque(false);
        panel_orderscreen.setOpaque(false);
        panel_searchorderscreen.setOpaque(false);
        table_ManageBook.setOpaque(false);
        textField_sreen_d.setOpaque(false);
        textField_search.setOpaque(false);
        textField_page.setOpaque(false);
        radioButton_ascorder.setOpaque(false);
        radioButton_descorder.setOpaque(false);
        panel_searchmorebutton.setOpaque(false);
        comboBox_orderby.setBackground(Color.WHITE);
        comboBox_screen.setBackground(Color.WHITE);
        comboBox_searchtype.setBackground(Color.WHITE);


        //setBounds
        title.setBounds(10,10,300,40);
        panel_searchmorebutton.setBounds(10,0,100,35);
        panel_seachmore0.setBounds(10,35,800,35);
        panel_seachmore1.setBounds(10,75,800,35);
        panel_search.setBounds(10,35,800,35);
        panel_orderscreen.setBounds(10,70,800,70);
        panel_searchorderscreen.setBounds(10,410,550,155);

        scrollPane_tableBook.setBounds(10, 60, 1160, 347);
        panel_button.setBounds(20, 550, 600, 50);
        panel_page.setBounds(660, 420, 500, 50);
        /////////end.组件设置////////


        /////面板添加组件///////
        panel_searchmorebutton.add(button_searchmore0);
        panel_seachmore0.add(label_BNO);
        panel_seachmore0.add(textField_BNO);
        panel_seachmore0.add(label_BNA);
        panel_seachmore0.add(textField_BNA);
        panel_seachmore0.add(button_search1);
        panel_seachmore1.add(label_BDA);
        panel_seachmore1.add(textField_BDA);
        panel_seachmore1.add(label_BPU);
        panel_seachmore1.add(textField_BPU);
        panel_button.add(button_manage_refresh);
        panel_button.add(button_manage_addBook);
        panel_button.add(button_manage_delBook);
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

        panel_search.add(label_search);
        panel_search.add(comboBox_searchtype);
        panel_search.add(label_search1);
        panel_search.add(textField_search);
        panel_search.add(button_search);
        panel_screen.add(label_screen);
        panel_screen.add(comboBox_screen);
        buttonGroup_order.add(radioButton_ascorder);
        buttonGroup_order.add(radioButton_descorder);
        panel_order.add(label_order);
        panel_order.add(comboBox_orderby);
        panel_order.add(radioButton_ascorder);
        panel_order.add(radioButton_descorder);
        panel_searchorderscreen.add(panel_searchmorebutton);
        panel_searchorderscreen.add(panel_search);
        panel_searchorderscreen.add(panel_orderscreen);
        panel_orderscreen.add(panel_order);
        panel_orderscreen.add(panel_screen);
        //thisPanel
        this.add(scrollPane_tableBook);     //Table子面板
        this.add(panel_button);     //按钮子面板
        this.add(panel_searchorderscreen);    //查询子面板
        this.add(panel_page);
        this.add(title);
        /////end.面板添加组件///////

        ///////添加监听器///////
        ActionEventHandler handler = new ActionEventHandler();    //声明监听器
        button_manage_addBook.addActionListener(handler);
        button_manage_delBook.addActionListener(handler);
        button_manage_refresh.addActionListener(handler);
        button_searchmore0.addActionListener(handler);
        button_searchmore1.addActionListener(handler);
        button_search.addActionListener(handler);
        button_search1.addActionListener(handler);
        button_modify.addActionListener(handler);
        RadioButtonHandler radioButtonHandler = new RadioButtonHandler();
        radioButton_ascorder.addItemListener(radioButtonHandler);
        radioButton_descorder.addItemListener(radioButtonHandler);
        button_afterPage.addActionListener(handler);
        button_beforePage.addActionListener(handler);
        button_page.addActionListener(handler);
        ///////end.添加监听器///////
        search();
        ///////////////////end.图书管理界面////////////////////////
        //**********************************************************
    }

    public static int datetovector(ResultSet rs,Vector<Object[]> vector){      //将结果集数据存到vector，返回行数
        vector.removeAllElements();
        int rowCount=0;
        try {
            while (rs.next()){
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
        model_ManageBook.setRowCount(0);
        int totalNumofRow=datetovector(rs, vector_total);
        Function.showPage(model_ManageBook,pageNum, numOfRowInEachPage,totalNumofRow,vector_total);
        textField_page.setText(String.valueOf(pageNum));
        label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
        panel_page.repaint();
    }

    //旧版代码
//    public static void tableRefresh() {     //table刷新2
//        vector_total.removeAllElements();
//        model_ManageBook.setRowCount(0);
//        String SQL = "SELECT * FROM TBOOK ORDER BY BNO ASC";
//        ResultSet rs = Database.executeQuery(SQL);
//        try {
//            while (rs.next()) {
//                Object[] rowData = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)};
//                model_ManageBook.addRow(rowData);
//                vector_total.addElement(rowData);
//            }
//        } catch (SQLException sqlE) {
//            JOptionPane.showMessageDialog(null, sqlE);
//        }
//    }

    public static void search(){       //搜索1
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
        textField_search.setText("");
        textField_BNO.setText("");
        textField_BDA.setText("");
        textField_BPU.setText("");
        textField_BNA.setText("");
        textField_page.setText("1");
        textField_sreen_d.setText("");
        comboBox_orderby.setSelectedIndex(0);
        comboBox_screen.setSelectedIndex(0);
        comboBox_searchtype.setSelectedIndex(0);
        ordertype = " ASC ";
        searchtype = " BNA LIKE ";
        orderby = " ORDER BY BNO ";
        screentype="";
        int_screendaynum =0;
        pageNum=1;
        Function.showPage(model_ManageBook,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
        search();
    }

    ///////////////监听器/////////////////
    public class ActionEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == button_manage_addBook) {  //添加图书
                Form_admin_addbook form_admin_addbook = new Form_admin_addbook();
                search();
            } else if (event.getSource() == button_manage_refresh) {  //刷新
                refresh();
            } else if (event.getSource() == button_manage_delBook) {  //删除图书
                //验证管理员密码
                try {
                    String Bno = model_ManageBook.getValueAt(table_ManageBook.getSelectedRow(), 0).toString();
                    String Bna = model_ManageBook.getValueAt(table_ManageBook.getSelectedRow(), 1).toString();
                    int isDelete = JOptionPane.showConfirmDialog(null, "是否删除 “编号:" + Bno + " 图书名称:" + Bna + "” 图书？", "是否删除", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (isDelete == JOptionPane.YES_OPTION) {       //确定删除
                        String SQL = "DELETE FROM TBOOK WHERE BNO='" + Bno + "'";
                        int i = Database.executeUpdate(SQL);
                        if (i != 0) {
                            JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "请在上方选中图书后删除", "提示", JOptionPane.WARNING_MESSAGE);
                }
                search();
            } else if (event.getSource() == button_search) {      //查找
//                tableRefresh();
                search();
            } else if (event.getSource() == button_modify) {
                //数据准备，由于不选择table内容
                //String BNO=model_ManageBook.getValueAt(table_ManageBook.getSelectedRow(),0).toString();
                // 会抛出异常，所以放在前面来防止不选择table内容时弹出输入密码框
                int row = table_ManageBook.getSelectedRow();  //获取table行
                int colume = table_ManageBook.getSelectedColumn();    //获取table列
                String columeName = "";   //属性名
                String columeNameshow = "";   //用于提示的属性名
                switch (colume + 1) {
                    case 2:
                        columeName = "BNA";
                        columeNameshow = "图书名称";
                        break;
                    case 3:
                        columeName = "BDA";
                        columeNameshow = "出版日期";
                        break;
                    case 4:
                        columeName = "BPU";
                        columeNameshow = "图书出版社";
                        break;
                    case 5:
                        columeName = "BPL";
                        columeNameshow = "图书摆放位置";
                        break;
                    case 7:
                        columeName = "BNU";
                        columeNameshow = "图书总数量";
                        break;
                }
                String BNO = model_ManageBook.getValueAt(table_ManageBook.getSelectedRow(), 0).toString();
                String modify ="";
                if (colume == 0) {  //不能修改图书编号
                    JOptionPane.showMessageDialog(null, "不能修改图书编号", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (colume == 5) {
                    JOptionPane.showMessageDialog(null, "不能修改剩余数量", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (colume == 7) {
                    JOptionPane.showMessageDialog(null, "不能修改上架时间", "警告", JOptionPane.WARNING_MESSAGE);
                    return;
                }else if(colume==4){
                    String[] locations={"1楼A区","1楼B区","1楼C区","2楼A区","2楼B区","2楼C区","3楼A区","3楼B区","3楼C区"};
                    modify=JOptionPane.showInputDialog(null,"选择地点","选择地点",JOptionPane.QUESTION_MESSAGE,null,locations,locations[0]).toString();
                }else {
                    modify=JOptionPane.showInputDialog(null, "输入修改后的" + columeNameshow).toString();
                }

                if (modify == null) {
                    return;
                }
                switch (colume + 1) {
                    case 2:
                        if (modify.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "图书名称不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        break;
                    case 3:
                        if (!Pattern.matches("[0-9]{4,6}", modify)) {
                            JOptionPane.showMessageDialog(null, "出版日期必须为连续非空数字组合(例如20190620或2019)！", "警告", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        break;
                    case 4:
                        if (modify.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "图书出版社不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        break;
//                    case 5:
//                        if (modify.isEmpty()) {
//                            JOptionPane.showMessageDialog(null, "图书摆放位置不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
//                            return;
//                        }
//                        break;
                    case 6:
                        if (!Pattern.matches("[0-9]{0,99999}", modify)) {
                            JOptionPane.showMessageDialog(null, "图书总数量必须为数字！", "警告", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                }
                String SQL = "UPDATE TBOOK SET " + columeName + " = '" + modify + "' WHERE BNO= '" + BNO + "'";
                int i = Database.executeUpdate(SQL);
                if (i != 0) {
                    JOptionPane.showMessageDialog(null, "修改成功", "提醒", JOptionPane.INFORMATION_MESSAGE);
                    model_ManageBook.setValueAt(modify, row, colume);
                }
            }
            else if(event.getSource()==button_afterPage){   //下一页
                if(pageNum>=pageNumMax){
                    JOptionPane.showMessageDialog(null,"已到最后一页");
                    return;
                }
                else {
                    pageNum++;
                    Function.showPage(model_ManageBook,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
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
                    Function.showPage(model_ManageBook,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
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
                Function.showPage(model_ManageBook,pageNum,numOfRowInEachPage,totalNumOfRow,vector_total);
                textField_page.setText(String.valueOf(pageNum));
                label_page.setText("共"+pageNumMax+"页  每页"+numOfRowInEachPage+"行");
                panel_page.repaint();
            }
            else if(event.getSource()== button_searchmore0){     //组合查询
                panel_searchorderscreen.removeAll();
                panel_searchmorebutton.removeAll();
                panel_searchmorebutton.add(button_searchmore1);
                panel_searchorderscreen.add(panel_searchmorebutton);
                panel_searchorderscreen.add(panel_seachmore0);
                panel_searchorderscreen.add(panel_seachmore1);
                panel_searchorderscreen.repaint();
                refresh();
            }
            else if(event.getSource()== button_searchmore1){     //简单查询
                panel_searchorderscreen.removeAll();
                panel_searchmorebutton.removeAll();
                panel_searchmorebutton.add(button_searchmore0);
                panel_searchorderscreen.add(panel_searchmorebutton);
                panel_searchorderscreen.add(panel_search);
                panel_searchorderscreen.add(panel_orderscreen);
                panel_searchorderscreen.repaint();
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
                ordertype=" ASC ";
                //执行search，不用点击检索按钮便可出结果，方便操作
                search();
            }
            else if(e.getItem()==radioButton_descorder){
                ordertype=" DESC ";
                //执行search，不用点击检索按钮便可出结果，方便操作
                search();
            }
        }
    }

}

//笔记
//管理员验证密码
//String nowPWD=JOptionPane.showInputDialog(null,"请输入当前管理员密码进行验证");
//                String SQL_checkNowPWD="SELECT PWD FROM TADMIN WHERE ANO = '"+Account.ANO+"'";
//                ResultSet rs_checkNowPWD=Database.executeQuery(SQL_checkNowPWD);
//                try {
//                    while (rs_checkNowPWD.next()) {
//                        if (nowPWD.equals(rs_checkNowPWD.getString(1))) { //密码正确
//
//                        }else{   //密码错误
//                            JOptionPane.showMessageDialog(null,"密码错误！","警告",JOptionPane.WARNING_MESSAGE);
//                            return;
//                        }
//                    }
//                }catch (SQLException sqlE){
//                    JOptionPane.showMessageDialog(null,sqlE);
//                }

//笔记
//    以下为Jtable翻页版处理
//    public static int datatovector(ResultSet resultSet,Vector<Object[]> vector){      //将结果集数据存到vector，返回行数
//        vector.removeAllElements();
//        int rowCount=0;
//        try {
//            while (resultSet.next()){
//                 适配：在此处rowdata加入各个界面所用rowdata
//                修改：Object[] rowData = {resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7)};
//                vector.addElement(rowData);
//                rowCount++;
//            }
//        }catch (SQLException sqlE){
//            JOptionPane.showMessageDialog(null, sqlE);
//        }
//        pageNumMax=rowCount/ numOfRowInEachPage+1;
//        totalNumOfRow=rowCount;
//        return rowCount;
//    }
//
//    public static void tableRefresh(ResultSet rs) {     //table刷新1
//        vector_total.removeAllElements();
//        model_ManageBook.setRowCount(0);
//        int totalNumofRow=datatovector(rs, vector_total);
//        Function.showPage(model_ManageBook,pageNum, numOfRowInEachPage,totalNumofRow,vector_total);
//    }
//
//
//翻页使用的页码变量范围为整个该部分代码，所以刷新后还会保留当前页码
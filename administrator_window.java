import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class administrator_window implements ActionListener {

    JFrame JF_pur, del_pur;
    JFrame del_sup,del_con,del_sit;
    JFrame InsertPur_jFrame, InsertSup_jFrame,InsertSit_jFrame;
    JFrame InsertCon_jFrame;

    JTable global_table,table_pur,table_sup,table_con,table_sit;
    JTable table_toInsertPur, table_toInsertSup;

    DefaultTableModel tableModel;

  ;
    JButton serch_users, serch_contract, serch_supplier;
    JButton amend_users, button_amendpur, amend_supplier, button_amendcontract, button_confirm_sup, button_exit_sup;
    JButton insert_users, insert_contract, insert_supplier, jb_delpur, jb_delpur_confirm, jb_delpur_cancel;
    JButton button_confirm,jb_delsit_confirm,jb_delsit_cancel;
    JButton jb_delsup, jb_delsup_confirm, jb_delsup_cancel,button_confirm_sit,button_exit_sit;
    JButton jb_delsit,ShowAllPur,ShowAllSup,ShowAllCon,ShowAllSit;
    JButton search_contract,jb_delcon,jb_delcon_confirm,jb_delcon_cancel,search_situation,insert_situation,button_amendsituation;
    JButton button_confirm_con,button_exit_con;
    JButton button_exit;

    JPanel panel_update = new JPanel();
    JPanel panel_update_con=new JPanel();
    JPanel panel_update_sup = new JPanel();
    JPanel panel_update_sit=new JPanel();
    JScrollPane scroll_con,scroll_sit;
    JScrollPane scroll ;
    JScrollPane scroll_sup;

    JTextField del_id, del_supid,del_sitid;
    JTextField jTextField, jt_sup,del_conid,jTsituation;
    JTextField jTcontract;

    DB_opt admin = new DB_opt();
    Connection c = admin.getConnection();
    User_opt admin_user = new User_opt();

    public void WindowView() {
        JF_pur = new JFrame();
        final JTabbedPane JTP = new JTabbedPane();
        final JTabbedPane SubJTP_table = new JTabbedPane();
        final JTabbedPane SubJTP_serching = new JTabbedPane();
        //JF_pur.setLocationRelativeTo(null);

        SubJTP_serching.addTab("业务员信息查询", user_MSG());
        SubJTP_serching.addTab("供应商信息查询", supplier_MSG());
        SubJTP_serching.addTab("合同信息查询", contract_MSG());
        SubJTP_serching.addTab("合同执行情况查询",situation_MSG());

        JTP.addTab("管理员界面", SubJTP_serching);

        JTP.setSelectedIndex(0);
        JF_pur.setContentPane(JTP);
        JF_pur.pack();
        JF_pur.setVisible(true);
    }
    public Component situation_MSG(){
        Box boxV = Box.createVerticalBox();
        JPanel panel_1 = new JPanel();
        JPanel panel_2 = new JPanel();

        this.jTsituation = new JTextField(30);
        jTsituation.addFocusListener(new JTextFieldHintListener(jTsituation, "请输入执行合同ID"));
        ShowAllSit =new JButton("查看列表");
        search_situation = new JButton("搜索");

        button_amendsituation= new JButton("修改信息");
        this.jb_delsit = new JButton("删除信息");

        search_situation.addActionListener(this);

        button_amendsituation.addActionListener(this);
        jb_delsit.addActionListener(this);
        ShowAllSit.addActionListener(this);

        panel_1.add(jTsituation);
        panel_1.add(search_situation);
        panel_2.add(ShowAllSit);
        panel_2.add(button_amendsituation);
        panel_2.add(jb_delsit);

        boxV.add(panel_1);
        table_sit = new JTable();
        this.scroll_sit= new JScrollPane(table_sit);
        panel_update_sit.add(scroll_sit);

        boxV.add(this.panel_update_sit);
        boxV.add(panel_2);
        return boxV;

    }

    public Component contract_MSG() {
        Box boxV = Box.createVerticalBox();
        JPanel panel_1 = new JPanel();
        JPanel panel_2 = new JPanel();

        this.jTcontract = new JTextField(30);
        jTcontract.addFocusListener(new JTextFieldHintListener(jTcontract, "请输入合同ID"));
        search_contract = new JButton("搜索");
        ShowAllCon=new JButton("查看列表");
        insert_contract = new JButton("插入信息");
        button_amendcontract= new JButton("修改信息");
        this.jb_delcon = new JButton("删除信息");

        search_contract.addActionListener(this);
        insert_contract.addActionListener(this);
        button_amendcontract.addActionListener(this);
        ShowAllCon.addActionListener(this);
        jb_delcon.addActionListener(this);

        panel_1.add(jTcontract);
        panel_1.add(search_contract);
        panel_2.add(ShowAllCon);
        panel_2.add(insert_contract);
        panel_2.add(button_amendcontract);
        panel_2.add(jb_delcon);

        boxV.add(panel_1);
        table_con= new JTable();
        this.scroll_con= new JScrollPane(table_con);
        panel_update_con.add(scroll_con);

        boxV.add(this.panel_update_con);
        boxV.add(panel_2);
        return boxV;
    }

    public Component supplier_MSG() {
        Box boxV = Box.createVerticalBox();

        JPanel All_panel = new JPanel();
        JPanel panel_1 = new JPanel();
        JPanel panel_2 = new JPanel();

        this.jt_sup = new JTextField(30);
        jt_sup.addFocusListener(new JTextFieldHintListener(jt_sup, "请输入供应商ID"));
        serch_supplier = new JButton("搜索");
        amend_supplier = new JButton("修改信息");
        insert_supplier = new JButton("插入信息");
        ShowAllSup=new JButton("查看列表");
        jb_delsup = new JButton("删除信息");

        panel_1.add(jt_sup);
        panel_1.add(serch_supplier);
        panel_2.add(ShowAllSup);
        panel_2.add(insert_supplier);
        panel_2.add(amend_supplier);
        panel_2.add(jb_delsup);

        serch_supplier.addActionListener(this);
        ShowAllSup.addActionListener(this);
        amend_supplier.addActionListener(this);
        insert_supplier.addActionListener(this);
        jb_delsup.addActionListener(this);

        boxV.add(panel_1);
        table_sup = new JTable();
        this.scroll_sup = new JScrollPane(table_sup);
        panel_update_sup.add(scroll_sup);
        boxV.add(panel_update_sup);
        boxV.add(panel_2);
        return boxV;
    }

    public Component user_MSG() {
        Box boxV = Box.createVerticalBox();
        JPanel panel_1 = new JPanel();
        JPanel panel_2 = new JPanel();

        this.jTextField = new JTextField(30);
        jTextField.addFocusListener(new JTextFieldHintListener(jTextField, "请输入业务员ID"));
        serch_users = new JButton("搜索");
        ShowAllPur =new JButton("查看列表");
        insert_users = new JButton("插入信息");
        button_amendpur = new JButton("修改信息");
        this.jb_delpur = new JButton("删除信息");

        serch_users.addActionListener(this);
        insert_users.addActionListener(this);
        button_amendpur.addActionListener(this);
        ShowAllPur.addActionListener(this);
        jb_delpur.addActionListener(this);

        panel_1.add(jTextField);
        panel_1.add(serch_users);

        panel_2.add(ShowAllPur);
        panel_2.add(insert_users);
        panel_2.add(button_amendpur);
        panel_2.add(jb_delpur);

        boxV.add(panel_1);
        table_pur = new JTable();
        this.scroll = new JScrollPane(table_pur);
       // JScrollPane jScrollPane=new JScrollPane(this.global_table);
        panel_update.add(scroll);

        boxV.add(this.panel_update);
       // boxV.add(jScrollPane);
        boxV.add(panel_2);
        return boxV;

    }


    public static void main(String[] args) {
        administrator_window admin = new administrator_window();
        admin.WindowView();
    }

    public void CreateInsertpur() {
        this.InsertPur_jFrame = new JFrame("新建业务员信息");
        JPanel panel = new JPanel();
        Box boxv = Box.createVerticalBox();
        /*******************************************/
        String DataTitle[] = {"工号", "姓名", "性别", "年龄", "联系方式", "业绩"};
        Vector title = new Vector();
        for (int i = 0; i < DataTitle.length; i++) {
            title.add(DataTitle[i]);
        }
        Vector<Vector<String>> TABLEValue = new Vector<>();
        Vector<String> ROWV = new Vector<>();
        ROWV.add("");
        ROWV.add("");
        ROWV.add("");
        ROWV.add("");
        ROWV.add("");
        ROWV.add("");
        TABLEValue.add(ROWV);

        this.table_toInsertPur = new JTable(TABLEValue, title);
        JTableHeader header = table_toInsertPur.getTableHeader();
        header.setReorderingAllowed(false);
        this.button_confirm = new JButton("确定");
        button_confirm.addActionListener(this);
        this.button_exit = new JButton("取消");
        button_exit.addActionListener(this);

        boxv.add(header);
        boxv.add(table_toInsertPur);
        panel.add(button_confirm);
        panel.add(button_exit);
        boxv.add(panel);

        InsertPur_jFrame.setContentPane(boxv);
        InsertPur_jFrame.pack();
        InsertPur_jFrame.setVisible(true);

    }

    public void CreateInsertSup() {
        this.InsertSup_jFrame = new JFrame("新建供应商信息");
        JPanel panel = new JPanel();
        Box boxv = Box.createVerticalBox();
        /*******************************************/
        String DataTitle[] = {"供应商ID", "供应商名", "供应商地址", "供应商电话", "供应商类型"};
        Vector title = new Vector();
        for (int i = 0; i < DataTitle.length; i++) {
            title.add(DataTitle[i]);
        }
        Vector<Vector<String>> TABLEValue = new Vector<>();
        Vector<String> ROWV = new Vector<>();
        ROWV.add("");
        ROWV.add("");
        ROWV.add("");
        ROWV.add("");
        ROWV.add("");
        TABLEValue.add(ROWV);
        this.table_toInsertSup = new JTable(TABLEValue, title);
        JTableHeader header = table_toInsertSup.getTableHeader();
        header.setReorderingAllowed(false);
        /*************************************************************/
        this.button_confirm_sup = new JButton("确定");
        button_confirm_sup.addActionListener(this);
        this.button_exit_sup = new JButton("取消");
        button_exit_sup.addActionListener(this);

        boxv.add(header);
        boxv.add(table_toInsertSup);
        panel.add(button_confirm_sup);
        panel.add(button_exit_sup);
        boxv.add(panel);

        InsertSup_jFrame.setContentPane(boxv);
        InsertSup_jFrame.pack();
        InsertSup_jFrame.setVisible(true);
    }

    public void CreateDelpur() {

        this.del_pur = new JFrame("删除业务员信息");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        Box boxv = Box.createVerticalBox();


        JLabel word = new JLabel("输入业务员ID：");
        del_id = new JTextField(30);
        this.jb_delpur_confirm = new JButton("确认删除");
        this.jb_delpur_cancel = new JButton("取消删除");
        jb_delpur_cancel.addActionListener(this);
        jb_delpur_confirm.addActionListener(this);
        panel1.add(word);
        panel1.add(del_id);
        panel2.add(jb_delpur_confirm);
        panel2.add(jb_delpur_cancel);

        boxv.add(panel1);
        boxv.add(panel2);

        del_pur.setContentPane(boxv);
        del_pur.pack();
        del_pur.setVisible(true);

    }

    public void CreateDelsup() {
        this.del_sup = new JFrame("删除供应商信息");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        Box boxv = Box.createVerticalBox();

        JLabel word = new JLabel("输入供应商ID：");
        del_supid = new JTextField(30);
        this.jb_delsup_confirm = new JButton("确认删除");
        this.jb_delsup_cancel = new JButton("取消删除");
        jb_delsup_cancel.addActionListener(this);
        jb_delsup_confirm.addActionListener(this);
        panel1.add(word);
        panel1.add(del_supid);
        panel2.add(jb_delsup_confirm);
        panel2.add(jb_delsup_cancel);

        boxv.add(panel1);
        boxv.add(panel2);

        del_sup.setContentPane(boxv);
        del_sup.pack();
        del_sup.setVisible(true);

    }
    public void  CreateInsertCon(){
       this.InsertCon_jFrame = new JFrame("新建合同信息");
       JPanel panel = new JPanel();
       Box boxv = Box.createVerticalBox();
       /*******************************************/
       String DataTitle[] = {"合同ID", "业务员ID", "供应商ID", "原材料ID", "价格","数量"};
       Vector title = new Vector();
       for (int i = 0; i < DataTitle.length; i++) {
           title.add(DataTitle[i]);
       }
       Vector<Vector<String>> TABLEValue = new Vector<>();
       Vector<String> ROWV = new Vector<>();
       ROWV.add("");
       ROWV.add("");
       ROWV.add("");
       ROWV.add("");
       ROWV.add("");
       ROWV.add("");
       TABLEValue.add(ROWV);
       this.global_table = new JTable(TABLEValue, title);
       JTableHeader header = global_table.getTableHeader();
       header.setReorderingAllowed(false);
       /*************************************************************/
       this.button_confirm_con = new JButton("确定");
       button_confirm_con.addActionListener(this);
       this.button_exit_con = new JButton("取消");
       button_exit_con.addActionListener(this);

       boxv.add(header);
       boxv.add(global_table);
       panel.add(button_confirm_con);
       panel.add(button_exit_con);
       boxv.add(panel);

       InsertCon_jFrame.setContentPane(boxv);
       InsertCon_jFrame.pack();
       InsertCon_jFrame.setVisible(true);

   }
    public void CreateDelcon(){
        this.del_con = new JFrame("删除合同信息");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        Box boxv = Box.createVerticalBox();

        JLabel word = new JLabel("输入合同ID：");
        del_conid = new JTextField(30);
        this.jb_delcon_confirm = new JButton("确认删除");
        this.jb_delcon_cancel = new JButton("取消删除");
        jb_delcon_cancel.addActionListener(this);
        jb_delcon_confirm.addActionListener(this);
        panel1.add(word);
        panel1.add(del_conid);
        panel2.add(jb_delcon_confirm);
        panel2.add(jb_delcon_cancel);

        boxv.add(panel1);
        boxv.add(panel2);

        del_con.setContentPane(boxv);
        del_con.pack();
        del_con.setVisible(true);

    }
    public  void CreateDelSit(){
        this.del_sit = new JFrame("删除执行合同信息");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        Box boxv = Box.createVerticalBox();

        JLabel word = new JLabel("输入执行合同ID：");
        del_sitid = new JTextField(30);
        this.jb_delsit_confirm = new JButton("确认删除");
        this.jb_delsit_cancel = new JButton("取消删除");
        jb_delsit_cancel.addActionListener(this);
        jb_delsit_confirm.addActionListener(this);
        panel1.add(word);
        panel1.add(del_sitid);
        panel2.add(jb_delsit_confirm);
        panel2.add(jb_delsit_cancel);

        boxv.add(panel1);
        boxv.add(panel2);

        del_sit.setContentPane(boxv);
        del_sit.pack();
        del_sit.setVisible(true);

    }

    public class JTextFieldHintListener implements FocusListener {
        private String hintText;
        private JTextField textField;
        public JTextFieldHintListener(JTextField jTextField,String hintText) {
            this.textField = jTextField;
            this.hintText = hintText;
            jTextField.setText(hintText);  //默认直接显示
            jTextField.setForeground(Color.GRAY);
        }

        @Override
        public void focusGained(FocusEvent e) {
            //获取焦点时，清空提示内容
            String temp = textField.getText();
            if(temp.equals(hintText)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }

        }

        @Override
        public void focusLost(FocusEvent e) {
            //失去焦点时，没有输入内容，显示提示内容
            String temp = textField.getText();
            if(temp.equals("")) {
                textField.setForeground(Color.GRAY);
                textField.setText(hintText);
            }

        }

    }
    public class MyTableModel extends DefaultTableModel{

        @Override
        public boolean isCellEditable(int row, int column) {
            //第一列不可编译
            if(0 <= row && row < getRowCount() && column == 0){
                return false;
            }else{
                return true;
            }

        }


        public MyTableModel(Vector data,Vector columns){
            super(data, columns);
        }

    }
    public void Show_pur(String sql){

          panel_update.remove(scroll);

          Vector<String> columnNameV = new Vector<>();
          columnNameV.add("业务员ID");
          columnNameV.add("姓名");
          columnNameV.add("性别");
          columnNameV.add("年龄");
          columnNameV.add("电话");
          columnNameV.add("业绩");

          Vector<Vector<String>> tableValue = new Vector<>();

          List a = new ArrayList();
          a = admin.select(c, sql);
          System.out.println(a.size());
          for (int i = 1; i < a.size(); i++) {
              String detail = a.get(i).toString().substring(1, a.get(i).toString().length() - 1);
              String[] s = detail.split(",");
              Vector<String> rowV = new Vector<>();//   作为局部变量。
              for (int j = 0; j < s.length; j++) {
                  rowV.add(s[j]);
              }
              tableValue.add(rowV);
          }
          System.out.println(tableValue);
         MyTableModel model =new MyTableModel(tableValue, columnNameV);
         table_pur.setModel(model);

          JTableHeader tableHeader = table_pur.getTableHeader();
          tableHeader.setReorderingAllowed(false);
          this.scroll = new JScrollPane(table_pur);


          panel_update.add(scroll);
          panel_update.updateUI();

    }
    public void Show_Sup(String sql){
        panel_update_sup.remove(scroll_sup);
        Vector<String> columnNameV = new Vector<>();
        columnNameV.add("供应商ID");
        columnNameV.add("供应商名");
        columnNameV.add("地址");
        columnNameV.add("电话");
        columnNameV.add("类型");
        Vector<Vector<String>> tableValue = new Vector<>();
        List a = new ArrayList();
        a = admin.select(c, sql);
        for (int i = 1; i < a.size(); i++) {
            String detail = a.get(i).toString().substring(1, a.get(i).toString().length() - 1);
            String[] s = detail.split(",");
            Vector<String> rowV = new Vector<>();//   作为局部变量。
            for (int j = 0; j < s.length; j++) {
                rowV.add(s[j]);
            }
            tableValue.add(rowV);
        }
        System.out.println(tableValue);
        MyTableModel model =new MyTableModel(tableValue, columnNameV);
        table_sup.setModel(model);
        JTableHeader tableHeader = table_sup.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        this.scroll_sup = new JScrollPane(table_sup);
        panel_update_sup.add(scroll_sup);
        panel_update_sup.updateUI();

    }
    public void Show_Con(String sql){
        panel_update_con.remove(scroll_con);
        Vector<String> columnNameV = new Vector<>();
        columnNameV.add("合同ID");
        columnNameV.add("业务员ID");
        columnNameV.add("供应商ID");
        columnNameV.add("原材料ID");
        columnNameV.add("价格");
        columnNameV.add("数量");

        Vector<Vector<String>> tableValue = new Vector<>();
        List a = new ArrayList();
        a = admin.select(c, sql);
        for (int i = 1; i < a.size(); i++) {
            String detail = a.get(i).toString().substring(1, a.get(i).toString().length() - 1);
            String[] s = detail.split(",");
            Vector<String> rowV = new Vector<>();//   作为局部变量。
            for (int j = 0; j < s.length; j++) {
                rowV.add(s[j]);
            }
            tableValue.add(rowV);
        }
        System.out.println(tableValue);
        MyTableModel model =new MyTableModel(tableValue, columnNameV);
        table_con.setModel(model);
        JTableHeader tableHeader = table_con.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        this.scroll_con = new JScrollPane(table_con);
        panel_update_con.add(scroll_con);
        panel_update_con.updateUI();
    }
    public void Show_Sit(String sql){
        panel_update_sit.remove(scroll_sit);
        Vector<String> columnNameV = new Vector<>();
        columnNameV.add("合同ID");
        columnNameV.add("生效时间");
        columnNameV.add("原材料ID");
        columnNameV.add("更新时间");
        columnNameV.add("到货数量");
        columnNameV.add("到货率");

        Vector<Vector<String>> tableValue = new Vector<>();
        List a = new ArrayList();
        a = admin.select(c, sql);
        for (int i = 1; i < a.size(); i++) {
            String detail = a.get(i).toString().substring(1, a.get(i).toString().length() - 1);
            String[] s = detail.split(",");
            Vector<String> rowV = new Vector<>();//   作为局部变量。
            for (int j = 0; j < s.length; j++) {
                rowV.add(s[j]);
            }
            tableValue.add(rowV);
        }
        System.out.println(tableValue);
        MyTableModel model =new MyTableModel(tableValue, columnNameV);
        table_sit.setModel(model);
        JTableHeader tableHeader = table_sit.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        this.scroll_sit = new JScrollPane(table_sit);
        panel_update_sit.add(scroll_sit);
        panel_update_sit.updateUI();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object Command = e.getSource();

        if (Command.equals(serch_users)) {

            String sql="select * from purchaser where PID="+jTextField.getText();
            Show_pur(sql);

        }//业务员界面 搜索 按钮、
        else if(Command.equals(ShowAllPur)){
            String sql="select * from purchaser";
            Show_pur(sql);
        }
        else if (Command.equals(insert_users)) {

            CreateInsertpur();

        } else if (Command.equals(button_confirm)) {

            TableCellEditor cellEditor = table_toInsertPur.getCellEditor();
            cellEditor.stopCellEditing();

            System.out.println(table_toInsertPur.getColumnCount());
            List<String> values = new ArrayList<>();
            String getitem;
            for (int i = 0; i < table_toInsertPur.getColumnCount(); i++) {
                getitem = table_toInsertPur.getValueAt(0, i).toString();
                System.out.println(getitem);
                values.add(getitem);
            }
            boolean status = admin_user.insert(admin, c, "purchaser", values);
            if (status) {
                JOptionPane.showMessageDialog(null, "添加信息成功！");
                InsertPur_jFrame.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "添加信息失败，请确认数据项和表名后重试！");
            }

            //插入信息确认界面
        }//业务员 插入信息 按钮
        else if(Command.equals(button_exit)) {
            InsertPur_jFrame.dispose();
        }

        else if (Command.equals(button_amendpur)) {

            TableCellEditor cellEditor = table_pur.getCellEditor();
            cellEditor.stopCellEditing();

            List<String> values = new ArrayList<>();
            String getitem;
            for (int i = 0; i < table_pur.getColumnCount(); i++) {

                getitem = table_pur.getValueAt(table_pur.getSelectedRow(), i).toString();
                getitem = getitem.replaceAll(" ", "");//去掉所得字符串空格
                values.add(getitem);
            }
            List<String> column_name = new ArrayList<>();
            column_name.add("PID");
            column_name.add("PName");
            column_name.add("PGender");
            column_name.add("PAge");
            column_name.add("PPhone");
            column_name.add("PAchievement");

            boolean status = admin_user.update(admin, c, "purchaser", column_name, values, "PID", values.get(0));
            if (status) {
                JOptionPane.showMessageDialog(null, "修改信息成功！");

            } else {
                JOptionPane.showMessageDialog(null, "修改信息失败，请确认数据项和表名后重试！");
            }

        }//业务员 修改信息 按钮

        else if (Command.equals(insert_supplier)) {
            CreateInsertSup();
        }//合同界面 搜索 按钮

        else if (Command.equals(button_confirm_sup)) {

            TableCellEditor cellEditor = table_toInsertSup.getCellEditor();
            cellEditor.stopCellEditing();

            // System.out.println(table_toInsertPur.getColumnCount());
            List<String> values = new ArrayList<>();
            String getitem;
            for (int i = 0; i < table_toInsertSup.getColumnCount(); i++) {
                getitem = table_toInsertSup.getValueAt(0, i).toString();
                System.out.println(getitem);
                values.add(getitem);
            }
            boolean status = admin_user.insert(admin, c, "supplier", values);
            if (status) {
                JOptionPane.showMessageDialog(null, "添加信息成功！");
                InsertSup_jFrame.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "添加信息失败，请确认数据项和表名后重试！");
            }


        }//合同界面 插入信息 按钮
        else if(Command.equals(button_exit_sup)) {
            InsertSup_jFrame.dispose();
        }

        else if (Command.equals(jb_delpur)) {

            CreateDelpur();
        } else if (Command.equals(jb_delpur_confirm)) {

            int status = admin_user.delete(admin, c, "purchaser", "PID", del_id.getText());
            switch (status) {
                case (0):
                    JOptionPane.showMessageDialog(null, "约束条件有误，请确认后更改");
                    break;
                case (1):
                    JOptionPane.showMessageDialog(null, " 删除完成");
                    del_pur.dispose();
                    break;
                case (2):
                    JOptionPane.showMessageDialog(null, " 另一个表中含有依赖删除项的数据，请确认后处理;");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, " 出错，请稍后再试");

            }
        } else if (Command.equals(jb_delpur_cancel)) {
            del_pur.dispose();
        }//合同界面 修改信息 按钮
        else if (Command.equals(serch_supplier)) {
           String sql="select * from supplier where SID="+jt_sup.getText();
           Show_Sup(sql);
        }//供应商界面 搜索 按钮
        else if(Command.equals(ShowAllSup)){
            String sql="select * from supplier";
            Show_Sup(sql);
        }
        else if (Command.equals(amend_supplier)) {

            TableCellEditor cellEditor1 = table_sup.getCellEditor();
            cellEditor1.stopCellEditing();

            List<String> values = new ArrayList<>();
            String getitem;
            for (int i = 0; i < table_sup.getColumnCount(); i++) {
                getitem = table_sup.getValueAt(table_sup.getSelectedRow(), i).toString();
                getitem = getitem.replaceAll(" ", "");//去掉所得字符串空格
                values.add(getitem);
            }
            List<String> column_name = new ArrayList<>();
            column_name.add("SID");
            column_name.add("SName");
            column_name.add("SAddress");
            column_name.add("SPhone");
            column_name.add("SCategory");

            boolean status = admin_user.update(admin, c, "supplier", column_name, values, "SID", values.get(0));
            if (status) {
                JOptionPane.showMessageDialog(null, "修改信息成功！");

            } else {
                JOptionPane.showMessageDialog(null, "修改信息失败，请确认数据项和表名后重试！");
            }
        }//供应商 修改 按钮
        else if (Command.equals(jb_delsup)) {
            CreateDelsup();
        }
        else if (Command.equals(jb_delsup_confirm)) {
            int status = admin_user.delete(admin, c, "supplier", "SID", del_supid.getText());
            switch (status) {
                case (0):
                    JOptionPane.showMessageDialog(null, "约束条件有误，请确认后更改");
                    break;
                case (1):
                    JOptionPane.showMessageDialog(null, " 删除完成");
                    del_sup.dispose();
                    break;
                case (2):
                    JOptionPane.showMessageDialog(null, " 另一个表中含有依赖删除项的数据，请确认后处理;");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, " 出错，请稍后再试");
            }
        }
        else if (Command.equals(jb_delsup_cancel)) {
            del_sup.dispose();
        }//合同界面 修
        else if(Command.equals(search_contract)){
          String sql="select * from contract where CID="+jTcontract.getText();
          Show_Con(sql);
        }
        else if(Command.equals(ShowAllCon)){
            String sql="select * from contract";
            Show_Con(sql);
        }
        else if(Command.equals(button_amendcontract)){

            TableCellEditor cellEditor1 = table_con.getCellEditor();
            cellEditor1.stopCellEditing();

            List<String> values = new ArrayList<>();
            String getitem;
            for (int i = 0; i < table_con.getColumnCount(); i++) {
                getitem = table_con.getValueAt(table_con.getSelectedRow(), i).toString();
                getitem = getitem.replaceAll(" ", "");//去掉所得字符串空格
                values.add(getitem);
            }
            List<String> column_name = new ArrayList<>();
            column_name.add("CID");
            column_name.add("PID");
            column_name.add("SID");
            column_name.add("MID");
            column_name.add("Price");
            column_name.add("Num");

            boolean status = admin_user.update(admin, c, "contract", column_name, values, "CID", values.get(0));
            if (status) {
                JOptionPane.showMessageDialog(null, "修改信息成功！");

            } else {
                JOptionPane.showMessageDialog(null, "修改信息失败，请确认数据项和表名后重试！");
            }
        }
        else if(Command.equals(insert_contract)){
            CreateInsertCon();
        }
        else if(Command.equals(button_confirm_con)){

            TableCellEditor cellEditor = global_table.getCellEditor();
            cellEditor.stopCellEditing();


            List<String> values = new ArrayList<>();
            String getitem;
            for (int i = 0; i <global_table.getColumnCount(); i++) {
                getitem = global_table.getValueAt(0, i).toString();
                System.out.println(getitem);
                values.add(getitem);
            }
            boolean status = admin_user.insert(admin, c, "contract_temp", values);
            if (status) {
                JOptionPane.showMessageDialog(null, "添加信息成功！");
                InsertCon_jFrame.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "添加信息失败，请确认数据项和表名后重试！");
            }
        }
        else if(Command.equals(button_exit_con)) {
            InsertCon_jFrame.dispose();
        }

        else if(Command.equals(jb_delcon)){
            CreateDelcon();
        }
        else if(Command.equals(jb_delcon_confirm)) {
            int status = admin_user.delete(admin, c, "contract", "CID", del_conid.getText());
            switch (status) {
                case (0):
                    JOptionPane.showMessageDialog(null, "约束条件有误，请确认后更改");
                    break;
                case (1):
                    JOptionPane.showMessageDialog(null, " 删除完成");
                    del_con.dispose();
                    break;
                case (2):
                    JOptionPane.showMessageDialog(null, " 另一个表中含有依赖删除项的数据，请确认后处理;");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, " 出错，请稍后再试");
            }
        }
             else if(Command.equals(jb_delcon_cancel)){
                 del_con.dispose();
            }
             else if(Command.equals(search_situation)){

            String sql="select * from situation  where CID=" + jTsituation.getText();
            Show_Sit(sql);

        }
             else if(Command.equals(ShowAllSit)){
                 String sql="select * from situation";
                 Show_Sit(sql);
        }

             else if(Command.equals(button_amendsituation)){

            TableCellEditor cellEditor = table_sit.getCellEditor();
            cellEditor.stopCellEditing();

            List<String> values = new ArrayList<>();
            String getitem;
            for (int i = 0; i <table_sit.getColumnCount(); i++) {
                getitem = table_sit.getValueAt(table_sit.getSelectedRow(), i).toString();
                getitem = getitem.replaceAll(" ", "");//去掉所得字符串空格
                values.add(getitem);
            }
            List<String> column_name = new ArrayList<>();
            column_name.add("CID");
            column_name.add("MID");
            column_name.add("Arrival_num");//0 2 4

            System.out.println(values);
            List<String> values_temp=new ArrayList<>();
            values_temp.add(values.get(0));
            values_temp.add(values.get(2));
            values_temp.add(values.get(4));

             System.out.println(values_temp);
            System.out.println(jTsituation.getText());
            boolean status = admin_user.insert(admin, c, "situation_temp",values_temp);
            if (status) {
                JOptionPane.showMessageDialog(null, "修改信息成功！");

            } else {
                JOptionPane.showMessageDialog(null, "修改信息失败，请确认数据项和表名后重试！");
            }

        }
             else if(Command.equals(jb_delsit)){

                 CreateDelSit();
        }
             else if(Command.equals(jb_delsit_confirm)){
            int status = admin_user.delete(admin, c, "situation", "CID", del_sitid.getText());
            switch (status) {
                case (0):
                    JOptionPane.showMessageDialog(null, "约束条件有误，请确认后更改");
                    break;
                case (1):
                    JOptionPane.showMessageDialog(null, " 删除完成");
                    del_sit.dispose();
                    break;
                case (2):
                    JOptionPane.showMessageDialog(null, " 另一个表中含有依赖删除项的数据，请确认后处理;");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, " 出错，请稍后再试");
            }
     }
             else if(Command.equals(jb_delsit_cancel)){
                 del_sit.dispose();
        }

        }
    }



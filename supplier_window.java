import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class supplier_window extends JFrame implements ActionListener {
    JFrame JF_sup;
    String user_id;
    JButton jb_contract;
    JButton jb_material,button_amendsup,update_sup;
    JButton jb_supplier;
    JScrollPane scrollPane,scroll;
    JPanel panel_update=new JPanel();
    JPanel panel_Top=new JPanel();
    JTable table=new JTable();
    JTable table_1=new JTable();
    JTable table_2=new JTable();
    JTable table_3=new JTable();
    String sup_type="";

    Box vBox;
    User_opt sup_user=new User_opt();
    DB_opt sup_db = new DB_opt();
    Connection c = sup_db.getConnection();


    public void Createselectbox() {

        this.vBox = Box.createVerticalBox();
        JPanel panel01 = new JPanel();
        jb_contract = new JButton("已签合同");
        jb_material = new JButton("所售原材料信息");
        jb_supplier = new JButton("已执行合同情况");
        jb_contract.addActionListener(this);
        jb_material.addActionListener(this);
        jb_supplier.addActionListener(this);
        panel01.add(jb_contract);
        panel01.add(jb_material);
        panel01.add(jb_supplier);
        this.scrollPane = new JScrollPane(table);
        vBox.add(panel01);
        panel_update.add(scrollPane);
        vBox.add(panel_update);

    }

    public void actionPerformed(ActionEvent e1) {
        String command= e1.getActionCommand();

        if("已签合同".equals(command)){
            panel_update.remove(scrollPane);
            Vector<String> columnNameV=new Vector<>();
            columnNameV.add("合同ID");
            columnNameV.add("业务员ID");
            columnNameV.add("供应商ID");
            columnNameV.add("原材料ID");
            columnNameV.add("单价");
            columnNameV.add("签订数量");

            Vector<Vector<String>> tableValue=new Vector<>();
            String sql="select * from contract_temp  where SID="+user_id;
            List a=new ArrayList();
            a = sup_db.select(c, sql);
            for (int i=1; i<a.size(); i++){
                String detail = a.get(i).toString().substring(1,a.get(i).toString().length()-1);
                String[] s = detail.split(",");
                Vector<String> rowV=new Vector<>();//   作为局部变量。
                for (int j = 0; j<s.length;j++){
                    rowV.add(s[j]);
                }
                tableValue.add(rowV);
            }
            MyTableModel1 model=new MyTableModel1(tableValue,columnNameV);
            table_1.setModel(model);
            JTableHeader tableHeader=table_1.getTableHeader();
            this.scrollPane=new JScrollPane(table_1);
            scrollPane.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            panel_update.add(scrollPane);
            panel_update.updateUI();

        }
        else if("所售原材料信息".equals(command)){

            panel_update.remove(scrollPane);
            Vector<String> columnNameV=new Vector<>();
            columnNameV.add("原材料ID");
            columnNameV.add("原材料名");
            columnNameV.add("原材料类型");

            Vector <Vector<String>> tableValue=new Vector<>();

            String sql="select * from material where MCategory="+sup_type;
            List a=new ArrayList();
            a = sup_db.select(c, sql);
            for (int i=1; i<a.size(); i++){
                String detail = a.get(i).toString().substring(1,a.get(i).toString().length()-1);
                String[] s = detail.split(",");
                Vector<String> rowV=new Vector<>();
                for (int j = 0; j<s.length;j++){
                    rowV.add(s[j]);

                }
                tableValue.add(rowV);
            }

            MyTableModel1 model=new MyTableModel1(tableValue,columnNameV);
            table_2.setModel(model);
            JTableHeader tableHeader=table_2.getTableHeader();
            this.scrollPane=new JScrollPane(table_2);
            scrollPane.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            panel_update.add(scrollPane);
            panel_update.updateUI();


        }
        else  if("已执行合同情况".equals(command)){

            panel_update.remove(scrollPane);
            Vector<String> columnNameV=new Vector<>();
            columnNameV.add("合同ID");
            columnNameV.add("合同生效时间");//合同ID 合同生效时间 原材料ID 更新时间 到货数量  到货率  供应商ID
            columnNameV.add("原材料ID");
            columnNameV.add("更新时间");
            columnNameV.add("到货数量");
            columnNameV.add("到货率");;
            Vector <Vector<String>> tableValue=new Vector<>();

            String sql="select * from situation where CID in(select CID from contract where SID="+user_id+")";
            List a=new ArrayList();
            a = sup_db.select(c, sql);
            for (int i=1; i<a.size(); i++){
                String detail = a.get(i).toString().substring(1,a.get(i).toString().length()-1);
                String[] s = detail.split(",");
                Vector<String> rowV=new Vector<>();
                for (int j = 0; j<s.length;j++){
                    rowV.add(s[j]);

                }
                tableValue.add(rowV);
            }
            MyTableModel1 model=new MyTableModel1(tableValue,columnNameV);
            table_3.setModel(model);
            JTableHeader tableHeader=table_3.getTableHeader();
            this.scrollPane=new JScrollPane(table_3);
            scrollPane.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            panel_update.add(scrollPane);
            panel_update.updateUI();
        }
        else if("确认修改".equals(command)){
            TableCellEditor cellEditor = table.getCellEditor();
            cellEditor.stopCellEditing();

            List<String> values = new ArrayList<>();
            String getitem;
            for (int i = 0; i < table.getColumnCount(); i++) {

                getitem = table.getValueAt(table.getSelectedRow(), i).toString();
                getitem = getitem.replaceAll(" ", "");//去掉所得字符串空格
                values.add(getitem);
            }
            List<String> column_name = new ArrayList<>();
            column_name.add("SID");
            column_name.add("SName");
            column_name.add("SAddress");
            column_name.add("SPhone");
            column_name.add("SCategory");

            boolean status = sup_user.update(sup_db, c, "supplier", column_name, values, "SID", values.get(0));
            if (status) {
                JOptionPane.showMessageDialog(null, "修改信息成功！");

            } else {
                JOptionPane.showMessageDialog(null, "修改信息失败，请确认数据项和表名后重试！");
            }
        }
        else if("更新信息".equals(command)){

            panel_Top.remove(scroll);
            Vector<String> columnNameV=new Vector<>();
            columnNameV.add("供应商ID");
            columnNameV.add("供应商名");//合同ID 合同生效时间 原材料ID 更新时间 到货数量  到货率  供应商ID
            columnNameV.add("地址");
            columnNameV.add("电话");
            columnNameV.add("类型");
            Vector <Vector<String>> tableValue=new Vector<>();

            String sql="select * from supplier where SID="+user_id;
            List a=new ArrayList();
            a = sup_db.select(c, sql);
            for (int i=1; i<a.size(); i++){
                String detail = a.get(i).toString().substring(1,a.get(i).toString().length()-1);
                String[] s = detail.split(",");
                Vector<String> rowV=new Vector<>();
                for (int j = 0; j<s.length;j++){
                    rowV.add(s[j]);

                }
                tableValue.add(rowV);
            }
            MyTableModel model=new MyTableModel(tableValue,columnNameV);
            table.setModel(model);
            JTableHeader tableHeader=table.getTableHeader();
              scroll=new JScrollPane(table);
            scroll.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            panel_Top.add(scroll);
            panel_Top.updateUI();



        }

    }
    public class MyTableModel extends DefaultTableModel {

        @Override
        public boolean isCellEditable(int row, int column) {
            //第一列不可编译
            if(0 <= row && row < getRowCount() && column == 0)
                return false;
           else if(0 <= row && row < getRowCount() && column ==getColumnCount()-1)

                    return false;
           else
                    return true;

        }

        public MyTableModel(Vector data,Vector columns){
            super(data, columns);
        }

    }
    public class MyTableModel1 extends DefaultTableModel {

        @Override
        public boolean isCellEditable(int row, int column) {

                return false;

        }
        public MyTableModel1(Vector data,Vector columns){
            super(data, columns);
        }
    }

    public JComponent CreateSupMessage() {

        Box vbox= Box.createVerticalBox();

        Vector<String> columnNameV = new Vector<>();
        columnNameV.add("供应商ID");
        columnNameV.add("供应商名");
        columnNameV.add("地址");
        columnNameV.add("电话");
        columnNameV.add("类型");

        Vector<Vector<String>> tableValue = new Vector<>();

        String sql = "select * from supplier where SID= " + user_id;/**********/
        List a = new ArrayList();
        a = sup_db.select(c, sql);
        for (int i = 1; i < a.size(); i++) {
            String detail = a.get(i).toString().substring(1, a.get(i).toString().length() - 1);
            String[] s = detail.split(",");
            Vector<String> rowV = new Vector<>();

            for (int j = 0; j < s.length; j++) {
                rowV.add(s[j]);
                if(j==s.length-1)
                {
                    sup_type=s[j];
                }
            }
            tableValue.add(rowV);
            System.out.println(sup_type);
        }
        MyTableModel model=new MyTableModel(tableValue,columnNameV);
        table.setModel(model);
        JTableHeader tableHeader = table.getTableHeader();
        scroll=new JScrollPane(table);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel_Top.add(tableHeader);
        panel_Top.add(scroll);
        JPanel panel_bottom=new JPanel();
        button_amendsup=new JButton("确认修改");
        update_sup=new JButton("更新信息");
        button_amendsup.addActionListener(this);
        update_sup.addActionListener(this);
        panel_bottom.add(update_sup);
        panel_bottom.add(button_amendsup);
        vbox.add(panel_Top);
        vbox.add(panel_bottom);
        return vbox;
    }
    public void Window_view(String user) {
    this.user_id = user;
    JF_sup = new JFrame("供应商界面");
    final JTabbedPane JTP = new JTabbedPane();
    JF_sup.setLocationRelativeTo(null);
    this.Createselectbox();

    JTP.addTab("供应商信息", CreateSupMessage());
    JTP.addTab("信息查询", vBox);
    JTP.setSelectedIndex(0);

    JF_sup.setResizable(false);
    JF_sup.setContentPane(JTP);
    JF_sup.setSize(500, 400);
    JF_sup.setVisible(true);
}
    public static void main(String[] args) {

        supplier_window sup=new supplier_window();
       sup.Window_view("00105");
    }

}
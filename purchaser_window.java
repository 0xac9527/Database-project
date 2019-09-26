import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Vector;

public class purchaser_window extends JPanel implements ActionListener{
    JFrame JF_pur;
    String user_id;
    JButton jb_contract;
    JButton  jb_material,amend_pur,update_pur;//n
    JButton   jb_supplier;
    JScrollPane scrollPane,scroll;//n
    JTable table=new JTable();
    JTable table_showone=new JTable();
    JTable table_showtwo=new JTable();
    JTable table_showthree=new JTable();
    JPanel panel_update=new JPanel();
    JPanel panel_Top;
       Box vBox,boxv;

       DB_opt purchaser=new DB_opt();
    Connection c=purchaser.getConnection();
    User_opt pur_user = new User_opt();


    public class MyTableModel extends DefaultTableModel {

        @Override
        public boolean isCellEditable(int row, int column) {

                return false;
        }

        public MyTableModel(Vector data,Vector columns){
            super(data, columns);
        }

    }
    public class MyTableModel1 extends DefaultTableModel {

        @Override
        public boolean isCellEditable(int row, int column) {
            //第一列不可编译
            if(0 <= row && row < getRowCount() && column == 0){
                return false;
            }
            else if(0 <= row && row < getRowCount() && column ==getColumnCount()-1)
                {
                    return false;
                }
            else
                return true;

        }


        public MyTableModel1(Vector data,Vector columns){
            super(data, columns);
        }

    }




    public  JComponent creatuserMassagePanel(){
        boxv=Box.createVerticalBox();

         panel_Top=new JPanel();
        JPanel panel_bottom=new JPanel();
        Vector<String> columnNameV=new Vector<>();
        columnNameV.add("业务员ID");
        columnNameV.add("姓名");
        columnNameV.add("性别");
        columnNameV.add("年龄");
        columnNameV.add("电话");
        columnNameV.add("业绩");

        Vector<Vector<String>> tableValue=new Vector<>();

        String sql="select * from purchaser where PID= "+user_id;/**********/
        List a=new ArrayList();
        a = purchaser.select(c, sql);
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
        table.setModel(model);
        JTableHeader tableHeader=table.getTableHeader();
        scroll=new JScrollPane(table);//n
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//n


        update_pur=new JButton("更新个人信息");//n
        amend_pur=new JButton("确认修改");
        amend_pur.addActionListener(this);//n
        update_pur.addActionListener(this);//n
        panel_Top.add(tableHeader);
        panel_Top.add(scroll);//

        panel_bottom.add(update_pur);//new
        panel_bottom.add(amend_pur);

        boxv.add(panel_Top);
        boxv.add(panel_bottom);

        return boxv;
    }
    public void Createselectbox(){

        this.vBox = Box.createVerticalBox();
         JPanel panel01= new JPanel();
         jb_contract=new JButton("我的已签合同");
         jb_material=new JButton("查看原材料信息");
         jb_supplier=new JButton("查看厂商信息");
         jb_contract.addActionListener(this);
         jb_material.addActionListener(this);
         jb_supplier.addActionListener(this);
         panel01.add(jb_contract);
         panel01.add(jb_material);
         panel01.add(jb_supplier);
         this.scrollPane=new JScrollPane(table);
         panel_update.add(scrollPane);
        vBox.add(panel01);
        vBox.add(panel_update);


    }

    public void actionPerformed(ActionEvent e1) {
          String command= e1.getActionCommand();

          if("我的已签合同".equals(command)){
              panel_update.remove(scrollPane);
              Vector<String> columnNameV=new Vector<>();
              columnNameV.add("合同ID");
              columnNameV.add("业务员ID");
              columnNameV.add("供应商ID");
              columnNameV.add("原材料ID");
              columnNameV.add("单价");
              columnNameV.add("签订数量");

              Vector<Vector<String>> tableValue=new Vector<>();

              String sql="select * from contract where PID="+user_id;
              List a=new ArrayList();
              a = purchaser.select(c, sql);

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
              table_showone.setModel(model);
              JTableHeader tableHeader=table_showone.getTableHeader();
              this.scrollPane=new JScrollPane(table_showone);
              scrollPane.setVerticalScrollBarPolicy(
                      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                panel_update.add(scrollPane);
                panel_update.updateUI();

          }
          else if("查看原材料信息".equals(command)){

              panel_update.remove(scrollPane);
              Vector<String> columnNameV=new Vector<>();
              columnNameV.add("原材料ID");
              columnNameV.add("原材料名");
              columnNameV.add("原材料类型");

              Vector <Vector<String>> tableValue=new Vector<>();
              String sql="select * from material ";
              List a=new ArrayList();
              a = purchaser.select(c, sql);

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
              table_showtwo.setModel(model);
              JTableHeader tableHeader=table_showtwo.getTableHeader();
              this.scrollPane=new JScrollPane(table_showtwo);
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
              column_name.add("PID");
              column_name.add("PName");
              column_name.add("PGender");
              column_name.add("PAge");
              column_name.add("PPhone");
              column_name.add("PAchievement");

              boolean status = pur_user.update(purchaser, c, "purchaser", column_name, values, "PID", values.get(0));
              if (status) {
                  JOptionPane.showMessageDialog(null, "修改信息成功！");

              } else {
                  JOptionPane.showMessageDialog(null, "修改信息失败，请确认数据项和表名后重试！");
              }
          }
          else if("更新个人信息".equals(command)) {

              panel_Top.remove(scroll);
              Vector<String> columnNameV=new Vector<>();
              columnNameV.add("业务员ID");
              columnNameV.add("姓名");
              columnNameV.add("性别");
              columnNameV.add("年龄");
              columnNameV.add("电话");
              columnNameV.add("业绩");

              Vector<Vector<String>> tableValue=new Vector<>();
              Vector<String> rowV=new Vector<>();//   作为局部变量。
              String sql="select * from purchaser where PID="+user_id;
              List a=new ArrayList();
              a = purchaser.select(c, sql);

              for (int i=1; i<a.size(); i++){
                  String detail = a.get(i).toString().substring(1,a.get(i).toString().length()-1);
                  String[] s = detail.split(",");
                  for (int j = 0; j<s.length;j++){
                      rowV.add(s[j]);
                  }
                  tableValue.add(rowV);
              }
              MyTableModel1 model=new MyTableModel1(tableValue,columnNameV);
              table.setModel(model);
              JTableHeader tableHeader=table.getTableHeader();
              scroll=new JScrollPane(table);
              scroll.setVerticalScrollBarPolicy(
                      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
              panel_Top.add(scroll);
              panel_Top.updateUI();

          }


          else{
              panel_update.remove(scrollPane);
              Vector<String> columnNameV=new Vector<>();
              columnNameV.add("厂商ID");
              columnNameV.add("厂商名");
              columnNameV.add("厂商地址");
              columnNameV.add("厂商电话");
              columnNameV.add("厂商类型");

              Vector <Vector<String>> tableValue=new Vector<>();
              String sql="select * from supplier ";
              List a=new ArrayList();
              a = purchaser.select(c, sql);

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
              table_showthree.setModel(model);
              JTableHeader tableHeader= table_showthree.getTableHeader();
              this.scrollPane=new JScrollPane( table_showthree);
              scrollPane.setVerticalScrollBarPolicy(
                      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
              panel_update.add(scrollPane);
              panel_update.updateUI();
          }

      }

    public void Window_view(String user){
        this.user_id=user;
        JF_pur=new JFrame("业务员界面");
        final JTabbedPane JTP=new JTabbedPane();
        JF_pur.setLocationRelativeTo(null);
        this.Createselectbox();

        JTP.addTab("个人信息",creatuserMassagePanel());
        JTP.addTab("信息查询",vBox);;

        JTP.setSelectedIndex(0);

        //JF_pur.add(JTP);
        JF_pur.setResizable(false);
        JF_pur.setContentPane(JTP);
        JF_pur.setSize(500,400);
        JF_pur.setVisible(true);
    }
    public static void main(String[] args){

        purchaser_window PU=new purchaser_window();
       PU.Window_view("2019001006");
    }
}

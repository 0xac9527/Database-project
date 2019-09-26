import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Register_Window extends JFrame implements ActionListener {

    JFrame Reg;
    Box boxv,vbox;
    JTextField jtname;
    JTextField jtgender;
    JTextField jtage;
    JTextField jtpphone;
    JTextField jtsname;
    JTextField jtsaddress;
    JTextField jtasphone;
    JTextField jtsc;

    JPasswordField jtpass;
    JPasswordField jtpass_1;
    JPasswordField jtspassword;
    JPasswordField jtspassword_confirm;

    JButton pbutton_rs;
    JButton pbutton_exit;
    JButton sbutton_confirm;
    JButton sbutton_exit;


    DB_opt reg_db=new DB_opt();
    User_opt reg_user=new User_opt();
    Connection c=reg_db.getConnection();

    public JComponent CreateRegPur() {


        Box boxv = Box.createVerticalBox();

        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        JPanel panel4=new JPanel();
        JPanel panel5=new JPanel();
        JPanel panel6=new JPanel();
        JPanel panel7=new JPanel();


          //构造控件
        JLabel name=new JLabel("   姓     名");
        JLabel gender=new JLabel("   性     别");
        JLabel age=new JLabel("   年     龄");
        JLabel pphone=new JLabel("   电     话");
        JLabel password=new JLabel("   密     码");
        JLabel password_confirm=new JLabel("密码确认");

          jtname=new JTextField(20);
         jtgender=new JTextField(20);
         jtage=new JTextField(20);
         jtpphone=new JTextField(20);
         jtpass =new JPasswordField(20);
        jtpass_1=new JPasswordField(20);


         pbutton_rs=new JButton("确认注册");
        pbutton_exit=new JButton("取消注册");
         pbutton_rs.addActionListener(this);
        pbutton_exit.addActionListener(this);

        panel1.add(name);
        panel1.add(jtname);
        panel2.add(gender);
        panel2.add(jtgender);
        panel3.add(age);
        panel3.add(jtage);
        panel4.add(pphone);
        panel4.add(jtpphone);
        panel5.add(password);
        panel5.add(jtpass);
        panel6.add(password_confirm);
        panel6.add(jtpass_1);

        panel7.add(pbutton_rs);
        panel7.add(pbutton_exit);
        boxv.add(panel1);
        boxv.add(panel2);
        boxv.add(panel3);
        boxv.add(panel4);
        boxv.add(panel5);
        boxv.add(panel6);
        boxv.add(panel7);
        return  boxv;
    }

    public JComponent CreateRegSup() {
        boxv = Box.createVerticalBox();

        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        JPanel panel4=new JPanel();
        JPanel panel5=new JPanel();
        JPanel panel6=new JPanel();
        JPanel panel7=new JPanel();

        JLabel sname=new JLabel("供应商名");
        JLabel saddress=new JLabel("   地     址");
        JLabel sphone=new JLabel("   电     话");
        JLabel  sc=new JLabel("   类     型");
        JLabel spassword=new JLabel("   密     码");
        JLabel spassword_confirm=new JLabel("密码确认");

         jtsname=new JTextField(20);
         jtsaddress=new JTextField(20);
         jtasphone=new JTextField(20);
         jtsc=new JTextField(20);
         jtspassword=new JPasswordField(20);
        jtspassword_confirm=new JPasswordField(20);

         sbutton_confirm=new JButton("确认注册");
        sbutton_exit=new JButton("取消注册");
        sbutton_confirm.addActionListener(this);
        sbutton_exit.addActionListener(this);

        panel1.add(sname);
        panel1.add(jtsname);
        panel2.add(saddress);
        panel2.add(jtsaddress);
        panel3.add(sphone);
        panel3.add(jtasphone);
        panel4.add(sc);
        panel4.add(jtsc);
        panel5.add(spassword);
        panel5.add(jtspassword);
        panel6.add(spassword_confirm);
        panel6.add(jtspassword_confirm);
        panel7.add(sbutton_confirm);
        panel7.add(sbutton_exit);

        boxv.add(panel1);
        boxv.add(panel2);
        boxv.add(panel3);
        boxv.add(panel4);
        boxv.add(panel5);
        boxv.add(panel6);
        boxv.add(panel7);

        return boxv;
    }


    public void Window_view() {


        Reg = new JFrame("用户注册");
        final JTabbedPane JTP = new JTabbedPane();
        Reg.setLocationRelativeTo(null);


        JTP.addTab("业务员注册", CreateRegPur());
        JTP.addTab("供应商注册", CreateRegSup());
        JTP.setSelectedIndex(0);

        Reg.setResizable(false);
        Reg.setContentPane(JTP);
        Reg.setSize(500, 400);
        Reg.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object Command = e.getSource();
       if(Command.equals(pbutton_rs)) {
           char[] password_char = jtpass.getPassword();
           String password_str_1 = String.valueOf(password_char);
           char[] password_char_1 = jtpass_1.getPassword();
           String password_str_2 = String.valueOf(password_char_1);
           if (password_str_1.equals(password_str_2)) {

               String pid = reg_user.PAccount(reg_db, c);
               String pname = jtname.getText();
               String pgender = jtgender.getText();
               String page = jtage.getText();
               String pphone = jtpphone.getText();
               String pachieve = "0";

               List<String> pvalues = new ArrayList<>();
               List<String> pusers = new ArrayList<>();

               pvalues.add(pid);
               pvalues.add(pname);
               pvalues.add(pgender);
               pvalues.add(page);
               pvalues.add(pphone);
               pvalues.add(pachieve);

               pusers.add(pid);
               pusers.add(password_str_1);
               pusers.add("业务员");

               boolean status_1 = reg_user.insert(reg_db, c, "purchaser", pvalues);
               boolean status_2 = reg_user.insert(reg_db, c, "user", pusers);

               if (status_1 && status_2) {
                   JOptionPane.showMessageDialog(null, "注册成功！您的登录用户名为：" + pid);
                   Reg.dispose();
                   loginWindow reg_login = new loginWindow();
                   reg_login.CreateJFrame();

               } else {
                   JOptionPane.showMessageDialog(null, "注册失败！请重试");

               }

           } else {
               JOptionPane.showMessageDialog(null, "请确认两次密码输入相同");
           }
       }
           else if(Command.equals(pbutton_exit)){
               Reg.dispose();
           loginWindow reg_login = new loginWindow();
           reg_login.CreateJFrame();

           }
           else if(Command.equals(sbutton_confirm)){

           char[] password_char = jtspassword.getPassword();
           String password_str_1 = String.valueOf(password_char);
           char[] password_char_1 = jtspassword_confirm.getPassword();
           String password_str_2 = String.valueOf(password_char_1);
           if (password_str_1.equals(password_str_2)) {

               String sid = reg_user.SAccount(reg_db, c);
               String sname = jtsname.getText();
               String saddress = jtsaddress.getText();
               String sphone = jtasphone.getText();
               String sc=jtsc.getText();


               List<String> svalues = new ArrayList<>();
               List<String> susers = new ArrayList<>();

               svalues.add(sid);
               svalues.add(sname);
               svalues.add(saddress);
               svalues.add(sphone);
               svalues.add(sc);


               susers.add(sid);
               susers.add(password_str_1);
               susers.add("供应商");

               boolean status_1 = reg_user.insert(reg_db, c, "supplier", svalues);
               boolean status_2 = reg_user.insert(reg_db, c, "user", susers);

               if (status_1 && status_2) {
                   JOptionPane.showMessageDialog(null, "注册成功！您的登录用户名为：" + sid);
                   Reg.dispose();
                   loginWindow reg_login = new loginWindow();
                   reg_login.CreateJFrame();

               } else {
                   JOptionPane.showMessageDialog(null, "注册失败！请重试");

               }

           } else {
               JOptionPane.showMessageDialog(null, "请确认两次密码输入相同");
           }

       }
           else {
           Reg.dispose();
           loginWindow reg_login = new loginWindow();
           reg_login.CreateJFrame();

       }


    }
    public static void main(String[] args) {

        Register_Window reg = new Register_Window();
        reg.Window_view();

    }
}













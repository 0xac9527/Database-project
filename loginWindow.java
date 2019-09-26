import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class loginWindow  extends JFrame implements  ActionListener{
    JFrame jf;
    JPanel panel01;
    JPanel panel02;
    JPanel panel03;
    Box vBox;
    JLabel user_name;
    JLabel password;
    JTextField jtuser;
    JPasswordField jpassword;
    JButton jblogin;
    JButton jbexit;
    JButton jbregister;


    public void CreateJFrame () {

         jf = new JFrame("用户登录");
         jf.setSize(1000,600);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 第 1 个 JPanel, 使用默认的浮动布局
         panel01 = new JPanel();
        panel01.add(new JLabel("用户名"));
        jtuser=new JTextField(10);
        panel01.add(jtuser);

        // 第 2 个 JPanel, 使用默认的浮动布局
         panel02 = new JPanel();
        panel02.add(new JLabel("密   码"));
        jpassword=new JPasswordField(10);
        panel02.add(jpassword);

        // 第 3 个 JPanel, 使用浮动布局, 并且容器内组件居中显示
         panel03 = new JPanel(new FlowLayout(FlowLayout.CENTER));
         jblogin=new JButton("登录");
        jbexit=new JButton("退出");
        jbregister=new JButton("注册");
        jblogin.addActionListener(this);
        jbregister.addActionListener(this);
        jbexit.addActionListener(this);

        panel03.add(jblogin);
        panel03.add(jbregister);
        panel03.add(jbexit);

        // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
         vBox = Box.createVerticalBox();
        vBox.add(panel01);
        vBox.add(panel02);
        vBox.add(panel03);
        jf.setContentPane(vBox);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setSize(350,180);
        jf.setVisible(true);
    }
    public void actionPerformed(ActionEvent e1) {
        String command= e1.getActionCommand();
        if("登录".equals(command)){
            String driver ="com.mysql.jdbc.Driver";//驱动程序名
            String url ="jdbc:MySQL://localhost:3306/materials_supply?serverTimezone=CTT";//url指向要访问的数据库study
            String user ="root";//MySQL配置时的用户名
            String password ="zxr122089";//MySQL配置时的密码
            try {
                Class.forName(driver);//加载驱动程序
                Connection c =DriverManager.getConnection(url,user,password);//连接数据库
                if(!c.isClosed()){
                    System.out.println("Succeeded connecting to the Database!");
                }
                Statement statement =c.createStatement();//操作数据库
                String user_str= jtuser.getText();
                char[] password_char= jpassword.getPassword();
                String password_str=String.valueOf(password_char);
                String sql ="select * from user where user="+user_str;//要执行的sql语句/***************/
                ResultSet rs=statement.executeQuery(sql);
                String password_db="";
                String identity_db="11";
                while(rs.next()) {
                    password_db = rs.getString("password");
                    identity_db=rs.getString("identity");

                }
                if(!password_db.equals(password_str)) {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误请重新登录", "提示", JOptionPane.ERROR_MESSAGE);
                }
                else {

                    if(identity_db.equals("业务员"))
                    {
                        jf.dispose();
                        purchaser_window p_w=new purchaser_window();
                        p_w.Window_view(user_str);
                    }

                    else if(identity_db.equals("供应商"))
                    {
                         jf.dispose();
                         supplier_window s_w=new supplier_window();
                         s_w.Window_view(user_str);

                    }
                    else if(identity_db.equals("管理员"))
                    {
                        jf.dispose();
                        administrator_window admin_w=new administrator_window();
                        admin_w.WindowView();

                     }
                }
            }
            catch(ClassNotFoundException e) {
                System.out.println("Sorry,can not find the Driver!");
                e.printStackTrace();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        else if("退出".equals(command))
        {
            System.exit(0);
        }
        else{
            jf.dispose();
            Register_Window reg=new Register_Window();
            reg.Window_view();
        }
    }
    public static void main(String[] args) {
        loginWindow win=new loginWindow();
        win.CreateJFrame();

    }
}

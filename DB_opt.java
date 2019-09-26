import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB_opt {

    Connection c = null;

//    public DB_opt() {
//        getConnection();//建立mysql连接
//        select(String opt);//查询功能
//    }

    //***********************连接mysql****************************************
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:MySQL://localhost:3306/materials_supply?serverTimezone=CTT";
            String user = "root";
            String pass = "zxr122089";
            c = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
    //*************************************************************************

    //************************查询*********************************************
    public List select(Connection c, String opt) {//查询, opt为传入的select语句
        List<String> colName = new ArrayList<>();//存字段名
        List<List> table = new ArrayList<>();//存factor
        PreparedStatement stmt;
        try {
            stmt = c.prepareStatement(opt);//预编译sql语句
            ResultSet rs = stmt.executeQuery(opt);//sql操作
            ResultSetMetaData data = rs.getMetaData();//获得查询表结构
            for (int i = 1; i <= data.getColumnCount(); i++) {//获取字段名
                String ColumnName = data.getColumnName(i);
                colName.add(ColumnName);
            }
            table.add(colName);//加入字段名list
            while (rs.next()) {
                List<String> factor = new ArrayList<>();//存一个数据元素
                for (int i = 0; i < colName.size(); i++) {//获取一个数据元素
                    String clo = colName.get(i);
                    factor.add(rs.getString(clo));
                }
                table.add(factor);
            }
        } catch (SQLException e) {
            return null;
        }
        return table;
    }
    //**********************************************************************

    //*********************************添加***********************************
    public int insert(Connection c, String tablename, List<String> values) {//添加，在tablename上添加values
        StringBuffer temp = new StringBuffer();
        PreparedStatement stmt;
        String sql = "select * from " + tablename;
        List columnTypes = new ArrayList();//存字段类型

        //创建opt指令格式,opt = "insert into tablename "+"values(?,?,?,?,?)"  ?根据value长度
        temp.append("(");
        for (int i = 0; i < values.size() - 1; i++) {
            temp.append("?,");
        }
        temp.append("?)");
        String opt = "insert into " + tablename + " values" + temp.toString();//insert指令

        try {
            //获取表字段类型
            stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            int size = data.getColumnCount();
            for (int i = 1; i <= size; i++) {
                columnTypes.add(data.getColumnTypeName(i));
            }

            //字段类型与values值进行匹配
            if (columnTypes.size() == values.size()) {
                stmt = c.prepareStatement(opt);//预编译sql语句
                for (int i = 0; i < values.size(); i++) {//给表中指定字段赋值
                    if (columnTypes.get(i) == "VARCHAR" || columnTypes.get(i) == "CHAR") {
                        stmt.setString(i + 1, values.get(i).toString());
                    } else if (columnTypes.get(i) == "DECIMAL") {
                        String reg = "^[0-9]+(.[0-9]+)?$";
                        if (values.get(i).matches(reg)) {//若插入的该字段元素为数字
                            stmt.setBigDecimal(i + 1, new BigDecimal(values.get(i)));
                        } else {
                            return 0;
                        }
                    } else {
                        return 0;
                    }
                }
                stmt.executeUpdate();
            }
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    //******************************修改*************************************
    public int update(Connection c, String tablename, List<String> update_colname, List<String> update_values, String flag_col, String flag_value) {
        //在tablename表上修改update_colname字段（可同时修改多个），将对应的update_values写入，要求update_colname与mysql中对应表的字段名完全相同
        //flag_col与flag_value为约束条件
        PreparedStatement stmt;
        StringBuffer update_temp = new StringBuffer();
        StringBuffer col_temp = new StringBuffer();
        List columnTypes = new ArrayList();//存字段类型
        try {
            if (update_colname.size() == update_values.size()) {
                //创建opt命令格式
                for (int i = 0; i < update_colname.size(); i++) {
                    update_temp.append(update_colname.get(i) + " = ? ,");
                }
                update_temp.delete(update_temp.length() - 1, update_temp.length());//去掉最后一个“，”
                String opt = null;
                if (flag_col != null){
                    opt = "update " + tablename + " set " + update_temp.toString() + "where " + flag_col + " = ?";//opt = "update tablename set col1 = ? , col2 = ? where flag_col = ?"
                }
                else{
                    opt = "update " + tablename + " set " + update_temp.toString();
                }
                //获取字段类型
                for (int i = 0; i < update_colname.size(); i++) {
                    col_temp.append(update_colname.get(i) + ",");
                }
                if (flag_col != null){
                    col_temp.append(flag_col);
                }
                else{
                    col_temp.delete(col_temp.length()-1, col_temp.length());
                }
                String sql = "select " + col_temp.toString() + " from " + tablename;
                stmt = c.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData data = rs.getMetaData();
                int size = data.getColumnCount();
                for (int i = 1; i <= size; i++) {
                    columnTypes.add(data.getColumnTypeName(i));
                }

                //字段类型匹配value值
                stmt = c.prepareStatement(opt);//预编译sql语句
                for (int i = 0; i < update_colname.size(); i++) {//给表中指定字段赋值
                    if (columnTypes.get(i) == "VARCHAR"|| columnTypes.get(i) == "CHAR") {
                        stmt.setString(i + 1, update_values.get(i).toString());
                    } else if (columnTypes.get(i) == "DECIMAL") {
                        String reg = "^[0-9]+(.[0-9]+)?$";
                        if (update_values.get(i).matches(reg)) {//若插入的该字段元素为数字
                            stmt.setBigDecimal(i + 1, new BigDecimal(update_values.get(i)));
                        } else {
                            return 0;
                        }
                    } else {
                        return 0;
                    }
                }
                if(flag_col != null) {
                    stmt.setString(columnTypes.size(), flag_value);
                }
                stmt.executeUpdate();
                return 1;
            }
            else{
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    //******************************删除****************************
    public int delete(Connection c, String tablename, String flag_col, String flag_value){
        //flag_col 与 flag_value为约束条件List，col与value内的值一一对应
        PreparedStatement stmt;
        StringBuffer del_temp = new StringBuffer();
        String opt = null;
        String sql = null;
        try{
            if (flag_col != null && flag_value != null){//若约束不为空
                //获取flag_col在tablename表中的类型
                sql = "select " + flag_col + " from " + tablename;
                stmt = c.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData data = rs.getMetaData();
                String colType = data.getColumnTypeName(1);
                //字段匹配
                opt = "delete from " + tablename + " where " + flag_col + " = ?";
                stmt = c.prepareStatement(opt);
                if( colType == "VARCHAR"|| colType == "CHAR"){
                    stmt.setString(1, flag_value);
                }
                else if(colType == "DECIMAL"){
                    String reg = "^[0-9]+(.[0-9]+)?$";
                    if (flag_value.matches(reg)) {//若插入的该字段元素为数字
                        stmt.setBigDecimal(1, new BigDecimal(flag_value));
                    }
                    else {
                        return 0;
                    }
                }
                else{
                    return 0;
                }
            }
            else if(flag_col == null && flag_value == null){//若约束为空
                //字段匹配
                opt = "delete from " + tablename;
                stmt = c.prepareStatement(opt);
            }
            else {
                return 0;
            }
            stmt.executeUpdate();
            return 1;
        }catch(SQLException e){
            if (e.toString().contains("Unknown column")){
                return 0;
            }
            else{
                return 2;
            }
        }
    }
}

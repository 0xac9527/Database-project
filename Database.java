import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class User_opt{

    //********************查询**************************
    void select(DB_opt opt, Connection c, String sql){
        List<List> result = opt.select(c,sql);
        if (result == null){
            System.out.println("查询信息或范围有误，请确认后更正");
        }else{
            for(int i = 0; i < result.size(); i++){
                for(int j=0 ;j< result.get(i).size(); j++){
                    System.out.print(result.get(i).get(j)+ "\t");
                }
                System.out.println();
            }
        }
    }

    //*******************添加信息**************************
    boolean insert(DB_opt opt, Connection c, String tablename, List values){//添加表名为tablename，添加信息为List values
        int i = opt.insert(c, tablename, values);
        if (i == 0){
            System.out.println("添加信息失败，请确认数据项和表名后重试");
            return  false;
        }
        else{
            System.out.println("添加信息成功");
            return true;
        }
    }

    //******************修改信息***************************
    public boolean update(DB_opt opt, Connection c, String tablename, List<String> update_colname, List<String> update_values, String flag_col, String flag_value){
        //修改表名为tablename， 修改列与条件在list update_colname与list update_values对应，要求update_colname中字段名与mysql中tablename表字段名完全相同约束条件为flag_col = flag_value
        int i = opt.update(c, tablename, update_colname,update_values, flag_col, flag_value);
        if (i == 0){
            System.out.println("修改值或约束条件有误，请确认数据项和表名后重试");
            return  false;
        }
        else{
            System.out.println("修改信息成功");
            return  true;
        }
    }

    //*****************删除信息*****************************
    public int delete(DB_opt opt, Connection c, String tablename, String flag_col, String flag_value){
        int i = opt.delete(c, tablename, flag_col, flag_value);
        if (i == 0){
            System.out.println("约束条件有误，请确认后更改");
            return 0;
        }
        else if (i == 1){
            System.out.println("删除完成");
            return  1;
        }
        else if ( i == 2){
            System.out.println("另一个表中含有依赖删除项的数据，请确认后处理");
            return 2;
        }
        return  3;
    }

    //生成业务员账号
    public String PAccount(DB_opt opt, Connection c){
        String sql = "select random_num FROM ( SELECT FLOOR(RAND() * 9999999999) AS random_num FROM purchaser UNION SELECT FLOOR(RAND() * 9999999999) AS random_num ) AS ss WHERE \"random_num\" NOT IN (SELECT PID FROM purchaser) LIMIT 1";
        List<List> result = opt.select(c,sql);
        String account = null;
        if (result == null){
            System.out.println("查询信息或范围有误，请确认后更正");
        }else{
            account = result.get(1).get(0).toString().replace(".","");
        }
        return account.substring(0,account.length()-2);
    }

    //生成供应商账号
    public String SAccount(DB_opt opt, Connection c){
        String sql = "select random_num FROM ( SELECT FLOOR(RAND() * 99999) AS random_num FROM purchaser UNION SELECT FLOOR(RAND() * 99999) AS random_num ) AS ss WHERE \"random_num\" NOT IN (SELECT PID FROM purchaser) LIMIT 1";
        List<List> result = opt.select(c,sql);
        String account = null;
        if (result == null){
            System.out.println("查询信息或范围有误，请确认后更正");
        }else{
            account = result.get(1).get(0).toString();
        }
        return account.substring(0,account.length()-2);
    }
}

public class  Database {
    public static void main(String[] args) {
        DB_opt opt = new DB_opt();
        User_opt user = new User_opt();
        Connection c = opt.getConnection();//连接数据库
        List<String> L = new ArrayList<>();
        List<String> col = new ArrayList<>();
        List<String> val = new ArrayList<>();
        StringBuffer t = new StringBuffer();

        L.add("00101");
        L.add("2019001006");
        L.add("00103");
        L.add("001006");
        L.add("435.32");
        L.add("10000");

        col.add("SName");
        col.add("SPhone");
        val.add("耐克鞋业");
        val.add("12334523412");
        String tablename = "contract";
        String flag_col = "CID";
        String flag_val = "00101";

        //String sql = "insert ";  //insert into Situation_temp values("00101","001006",2400);
        String sql = "select * from contract";
        //String sql = "update";//update purchaser set PAchievement = 0 where PID = "2019001001";
        //String sql = "delete"; // delete from contract ;

        //生成业务员id
        System.out.println("业务员id: "+user.PAccount(opt,c));

        //生成供应商id
        System.out.println("供应商id: "+user.SAccount(opt,c));

        if (sql.contains("select")){//查询操作
            user.select(opt, c, sql);
        }
        else if(sql.contains("insert")){//插入操作
            user.insert(opt, c ,tablename, L);
        }
        else if (sql.contains("update")){
            user.update(opt, c, tablename, col, val, flag_col, flag_val);
        }
        else if (sql.contains("delete")){
            user.delete(opt, c, tablename, flag_col, flag_val);
        }
    }
}
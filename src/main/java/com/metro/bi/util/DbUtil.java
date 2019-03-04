package com.metro.bi.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtil {
    public static Connection getConnection(){
        Connection conn=null;
        try {
            Class.forName(ConfigUtil.getString("third_jdbc_driverClassName"));
            String url=ConfigUtil.getString("third_jdbc_url"); //URL地址
            String username=ConfigUtil.getString("third_jdbc_username");
            String password=ConfigUtil.getString("third_jdbc_password");
            conn= DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    public  void invokeStoredProcedure() throws SQLException{
        String str="{call SMSBUSINESS.deleteZhZMember(?,?,?)}";
        Connection conn=null;
        conn=getConnection();
        CallableStatement cs=conn.prepareCall(str);
        cs.setInt(1,1);
        cs.setInt(2,-2);
        cs.registerOutParameter(3,Types.NUMERIC);
        cs.execute();
        int flag=cs.getInt(3);
        conn.close();

    }

    public static List<String> getListFromStoredProcedure(){
        List<String> retList=new ArrayList<String>(20);
        Connection conn=null;ResultSet rs = null;int counter=0;
        conn=getConnection();
        CallableStatement proc = null;
        try {
            proc = conn.prepareCall("{ call hyq.testc(?) }"); //
            proc.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);//
            proc.execute();//执行
            rs = (ResultSet)proc.getObject(1); //from cursor to resultset
            while(rs.next()) //iterate all data
            {
                System.out.println("<tr><td>" + rs.getString(1) + "</td><td>"+rs.getString(2)+"</td></tr>");
                retList.add(rs.getString(1));
                if(counter++>=20)break;
            }
            rs.close();
            proc.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retList;

    }

    public static void close(PreparedStatement pstmt){
        if(pstmt !=null){
            try {
                pstmt.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs){
        if(rs !=null){
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

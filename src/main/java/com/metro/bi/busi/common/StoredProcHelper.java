package com.metro.bi.busi.common;

import com.metro.bi.busi.entity.MethodParameterDef;
import com.metro.bi.util.DbUtil;
import com.metro.bi.util.StringUtil;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoredProcHelper {

    private static final Logger log = LoggerFactory.getLogger(StoredProcHelper.class);

    public static List<Map<String,Object>> getListFromStoredProcedure(String procedureName, List<MethodParameterDef> paramDefIn,List<MethodParameterDef> paramDefOut, Map<String,Object> param){
        List<Map<String,Object>> retList=new ArrayList<Map<String,Object>>(20);
        Connection conn=null;
        ResultSet rs = null;int counter=0;
        conn= DbUtil.getConnection();
        CallableStatement proc = null;
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<(paramDefIn.size()+1);i++){
            sb.append("?,");
        }
        for(String s:param.keySet()){
            log.info(s+":"+param.get(s).toString());
        }
        try {
            proc = conn.prepareCall("{ call "+procedureName+"("+sb.substring(0,sb.length()-1)+") }"); //
//            proc.registerOutParameter(1,OracleTypes.NVARCHAR);
//            proc.registerOutParameter(2,OracleTypes.NVARCHAR);
            counter=1;
            for(MethodParameterDef p:paramDefIn){
//                1-int,2-float,3-double,4-string,5-short,6-long,7-boolean,8-byte
                switch (p.getFormalParamType()){
                    case "1":
                        proc.setInt(counter++,Integer.parseInt(param.get(p.getFormalParamName()).toString()));
                        break;
                    case "2":
                        proc.setFloat(counter++,Float.parseFloat(param.get(p.getFormalParamName()).toString()));
                        break;
                    case "3":
                        proc.setDouble(counter++,Double.parseDouble(param.get(p.getFormalParamName()).toString()));
                        break;
                    case "4":
                        proc.setString(counter++,param.get(p.getFormalParamName()).toString());
                        break;
                    case "5":
                        proc.setShort(counter++,Short.parseShort(param.get(p.getFormalParamName()).toString()));
                        break;
                    case "6":
                        proc.setLong(counter++,Long.parseLong(param.get(p.getFormalParamName()).toString()));
                        break;
                    case "7":
                        proc.setBoolean(counter++,Boolean.getBoolean(param.get(p.getFormalParamName()).toString()));
                        break;
//                    case "8":
//                        proc.setString(counter++,param.get(p.getFormalParamName()).toString());
//                        break;
                        default:break;
                }

            }
//            for(String s:param.keySet()){
//                proc.setString(counter++,param.get(s).toString());
//            }
//            if(paramDefOut.size()>0){
//                MethodParameterDef outPara=paramDefOut.get(0);
//                if(StringUtil.isEmpty(outPara.getFormalParamParent())){
//
//                }else{
//
//                }
//            }
            proc.registerOutParameter(paramDefIn.size()+1, OracleTypes.CURSOR);//
//            for(int i=1;i<=paramDefOut.size();i++){
//
//            }

            proc.execute();//执行
            rs = (ResultSet)proc.getObject(paramDefIn.size()+1); //from cursor to resultset
            ResultSetMetaData md = rs.getMetaData();
            int count = md.getColumnCount();
            while(rs.next()) //iterate all data无效的列索引
            {
                Map<String,Object> m = new HashMap<String,Object>();
                for(int i=1;i<=count;i++){
                    m.put(md.getColumnName(i),typeParse(md.getColumnType(i),md.getScale(i),i,rs));
                }
//                System.out.println("<tr><td>" + rs.getString(1) + "</td><td>"+rs.getString(2)+"</td></tr>");
                retList.add(m);
            }
            rs.close();
            proc.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retList;

    }

    public static int getAmountFromStoredProcedure(String procedureName, List<MethodParameterDef> paramDefIn,List<MethodParameterDef> paramDefOut, Map<String,Object> param){
        int ret=0;
        Connection conn=null;
        int counter=0;
        conn= DbUtil.getConnection();
        CallableStatement proc = null;
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<(paramDefIn.size()+1);i++){
            sb.append("?,");
        }
        for(String s:param.keySet()){
            log.info(s+":"+param.get(s).toString());
        }
        try {
            proc = conn.prepareCall("{ call "+procedureName+"("+sb.substring(0,sb.length()-1)+") }"); //
//            proc.registerOutParameter(1,OracleTypes.NVARCHAR);
//            proc.registerOutParameter(2,OracleTypes.NVARCHAR);
            counter=1;
            for(MethodParameterDef p:paramDefIn){
//                1-int,2-float,3-double,4-string,5-short,6-long,7-boolean,8-byte
                switch (p.getFormalParamType()){
                    case "1":
                        proc.setInt(counter++,Integer.parseInt(param.get(p.getFormalParamName()).toString()));
                        break;
                    case "2":
                        proc.setFloat(counter++,Float.parseFloat(param.get(p.getFormalParamName()).toString()));
                        break;
                    case "3":
                        proc.setDouble(counter++,Double.parseDouble(param.get(p.getFormalParamName()).toString()));
                        break;
                    case "4":
                        proc.setString(counter++,param.get(p.getFormalParamName()).toString());
                        break;
                    case "5":
                        proc.setShort(counter++,Short.parseShort(param.get(p.getFormalParamName()).toString()));
                        break;
                    case "6":
                        proc.setLong(counter++,Long.parseLong(param.get(p.getFormalParamName()).toString()));
                        break;
                    case "7":
                        proc.setBoolean(counter++,Boolean.getBoolean(param.get(p.getFormalParamName()).toString()));
                        break;
//                    case "8":
//                        proc.setString(counter++,param.get(p.getFormalParamName()).toString());
//                        break;
                    default:break;
                }

            }
//            for(String s:param.keySet()){
//                proc.setString(counter++,param.get(s).toString());
//            }
//            if(paramDefOut.size()>0){
//                MethodParameterDef outPara=paramDefOut.get(0);
//                if(StringUtil.isEmpty(outPara.getFormalParamParent())){
//
//                }else{
//
//                }
//            }
            proc.registerOutParameter(paramDefIn.size()+1, OracleTypes.INTEGER);//
//            for(int i=1;i<=paramDefOut.size();i++){
//
//            }

            proc.execute();//执行
            ret = proc.getInt(paramDefIn.size()+1); //from cursor to resultset
            proc.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;

    }

    private static Object typeParse(int columnType,int scale,int columnIndex,ResultSet rs) throws SQLException{
        Object value=null;
        switch(columnType){
            case Types.LONGVARCHAR: //-1
//                dataType="Long";
                value = rs.getString(columnIndex);
                break;
            case Types.CHAR:    //1
//                dataType="Character";
                value = rs.getString(columnIndex);
                break;
            case Types.NUMERIC: //2
                switch(scale){
                    case 0:
//                        dataType="Number";
                        value =  rs.getInt(columnIndex);
                        break;
                    case -127:
//                        dataType="Float";
                        value =  rs.getFloat(columnIndex);
                        break;
                    default:
//                        dataType="double";
                        value =  rs.getDouble(columnIndex);
                }
                break;
            case Types.VARCHAR:  //12
//                dataType="String";
                value = rs.getString(columnIndex);
                break;
            case Types.DATE:  //91
//                dataType="Date";
                value = rs.getDate(columnIndex);
                break;
            case Types.TIMESTAMP: //93
//                dataType="Date";
                value = rs.getDate(columnIndex);
                break;
            case Types.BLOB :
//                dataType="Blob";

                break;
            default:
//                dataType="String";
                value = rs.getString(columnIndex);
        }
        return value;

    }
    private static String getDataType(int type,int scale){
        String dataType="";

        switch(type){
            case Types.LONGVARCHAR: //-1
                dataType="Long";
                break;
            case Types.CHAR:    //1
                dataType="Character";
                break;
            case Types.NUMERIC: //2
                switch(scale){
                    case 0:
                        dataType="Number";
                        break;
                    case -127:
                        dataType="Float";
                        break;
                    default:
                        dataType="Number";
                }
                break;
            case Types.VARCHAR:  //12
                dataType="String";
                break;
            case Types.DATE:  //91
                dataType="Date";
                break;
            case Types.TIMESTAMP: //93
                dataType="Date";
                break;
            case Types.BLOB :
                dataType="Blob";
                break;
            default:
                dataType="String";
        }
        return dataType;
    }

}

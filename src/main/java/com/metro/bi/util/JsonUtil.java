package com.metro.bi.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metro.bi.busi.response.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Title:
 *
 * @author lvbq
 * @Date 2018/11/11 13:40:50
 * @Description
 */
public class JsonUtil {

    public static void toJson(Response responseMi, HttpServletResponse response) {
        response.setContentType("text/json");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer=null;

        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(writer != null){

            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
            try {
                writer.write(mapper.writeValueAsString(responseMi));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            writer.close();
        }
    }
    public static Map<String,Object> fromJson2Map(String json) {
        Map<String,Object> ret=new HashMap<String,Object>(8);
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = json.replaceAll("\\\\","");
            ret = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }

//    public <T> toObject(String jsonStr,Class<?> cls){
//        ObjectMapper objectMapper = new ObjectMapper();
//        T t = objectMapper.readValue(jsonStr, cls);
//        return t;
//    }
    public static void main(String[] ar){

    }


}

package com.metro.bi.busi.response;


/**
 * Title: 统一响应结构
 * Description:每个REST请求将返回相同结构的JSON响应结构。定义一个通用的JSON响应结构，其
 * 中包含两部分：状态与数据，其中，状态包含返回码与返回值消息，数据对应响应的业务数据。
 * { "code": 0,"message": "success", "data": {......}}
 *
 * @author admin
 * @date
 */

public class Response {

//    private static final String MSG_SUCCESS="success";
//    private static final String MSG_FAIL="fail";

    private int code;
    private String message;
    private Object data;

    public Response(){
    }

    public Response(int code){
        this.code=code;
    }

    public Response(int code, String message){
        this.code=code;
        this.message=message;
    }

    public Response(int code, String message, Object data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

    public int getCode(){return this.code;}
    public String getMessage(){return this.message;}
    public Object getData(){return this.data;}




}

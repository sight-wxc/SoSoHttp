package com.sight.sosohttp.network;

/**
 * 创建人Created by Sight-WXC on 2016/9/20 0020.
 * 这里注释说明 *
 */
public class User {


    private  String cust_id;

    private  String cust_realname;

    private  String  return_code;


    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_realname() {
        return cust_realname;
    }

    public void setCust_realname(String cust_realname) {
        this.cust_realname = cust_realname;
    }
}

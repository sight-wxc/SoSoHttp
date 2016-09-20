package com.sight.sosohttp.network;

import android.text.TextUtils;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;

/**
 * 创建人Created by Sight-WXC on 2016/9/20 0020.
 * 这里注释说明 *
 */
public class Requestor {

    /**
     * builder
     */
    private Request.Builder builder;
    /**
     * 表单参数的提交
     */
    private FormEncodingBuilder formEncodingBuilder;
    /**
     * 请求方法
     */
    private String method = Constant.POST;

    public Requestor() {
        builder = new Request.Builder();
        builder.url(Constant.ServerUrl);
        formEncodingBuilder = new FormEncodingBuilder();
    }

    /**
     * 设置请求的 服务器 url
     *
     * @param url
     */
    public void setServerUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            builder.url(url);
        }
    }

    /**
     * 请求方式
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }


    /**
     * 添加 key-value 键值对参数
     *
     * @param key
     * @param value
     */
    public void add(String key, String value) {
        if (builder != null) {
            formEncodingBuilder.add(key, value);
        }
    }


    /**
     * post的参数到body里
     */
    private void post() {
        builder.post(formEncodingBuilder.build());
    }



    /**
     * 返回OkHttpRequst
     *
     * @return
     */
    public Request getOkHttpRequest() {
        if (method.equals(Constant.POST)){
            post();
        }else if(method.equals(Constant.GET)){
            builder.get();
        }
        return builder.build();
    }


}

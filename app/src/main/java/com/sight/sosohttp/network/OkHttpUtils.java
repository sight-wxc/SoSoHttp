package com.sight.sosohttp.network;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

/**
 * 创建人Created by Sight-WXC on 2016/9/20 0020.
 * 基于Okhttp简单的封装  对外部暴露简单的接口
 */
public class OkHttpUtils {


    /**
     * 单例形式
     */
    private static OkHttpUtils instance;

    /**
     * OKhtttp的客户端
     */
    private OkHttpClient mOkHttpClient;
    /**
     * 请求队列
     */
    private Call call;

    /**
     * GSON 解析工具
     */
    private Gson mGson;
    /**
     * CallBack回调都是在子线程里操作
     * 需要发送回主线程 进行ui操作
     */
    private Handler mPostHandler;


    public OkHttpUtils() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mGson = new Gson();
        mPostHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取单例
     */
    public static OkHttpUtils getInstance() {
        synchronized (OkHttpUtils.class) {
            if (instance == null) {
                instance = new OkHttpUtils();
            }
            return instance;
        }
    }

    /**
     * 添加一个请求并执行回调
     *
     * @param requestor
     * @param callback
     * @param isShowDailog
     */
    public void addRequest(Requestor requestor, final HttpResponseCallback callback, boolean isShowDailog) {
        mOkHttpClient.newCall(requestor.getOkHttpRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {

                mPostHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailed(request.urlString(), e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String body = response.body().string();
                Class aclass = callback.parseObject();

                if (!TextUtils.isEmpty(body) && aclass != null && aclass instanceof Class) {
                    Object object = mGson.fromJson(body, aclass);
                    sendUIThread(callback, object);
                } else {
                    Object  object =body;
                    sendUIThread(callback,body);
                }
            }
        });
    }

    /**
     * 发送到主线程 方便操作ui
     * @param callback
     * @param object
     */
    private void sendUIThread(final HttpResponseCallback callback, final Object object) {
        mPostHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccessed(object);
            }
        });
    }

}

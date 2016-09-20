package com.sight.sosohttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sight.sosohttp.network.HttpResponseCallback;
import com.sight.sosohttp.network.OkHttpUtils;
import com.sight.sosohttp.network.Requestor;
import com.sight.sosohttp.network.User;

import java.io.IOException;

public class DemoActivity extends AppCompatActivity {


    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mResult = (TextView) findViewById(R.id.tv_result);

        findViewById(R.id.btn_base).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               get();
            }
        });
        findViewById(R.id.btn_javaben).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestJavaBeen();
            }
        });

    }


    public void post() {
        requestServer("POST");
    }

    public void get() {
        requestServer("GET");
    }

    public void requestServer(String Method) {
        Requestor requestor = new Requestor();
        requestor.setMethod(Method);
        requestor.add("cust_id","1");
        OkHttpUtils.getInstance().addRequest(requestor, new HttpResponseCallback<String>() {
            @Override
            public void onFailed(String url, IOException e) {
                mResult.setText("服务器发送失败 返回" + mResult);
            }

            @Override
            public void onSuccessed(String str) {
                mResult.setText("服务器发送成功返回:" + str);
            }

            @Override
            public Class parseObject() {
                return null;
            }
        }, false);
    }


    public void requestJavaBeen() {

        Requestor requestor = new Requestor();
        requestor.add("cust_id","2");
        OkHttpUtils.getInstance().addRequest(requestor, new HttpResponseCallback<User>() {
            @Override
            public void onFailed(String url, IOException e) {
                mResult.setText("s失败的url  :" +url );
            }

            @Override
            public void onSuccessed(User bean) {
                mResult.setText("服务器返回的javaben :" +bean +"\n"+"服务器 return code 状态:"+bean.getReturn_code());
            }

            @Override
            public Class parseObject() {
                return User.class;
            }
        }, false);

    }


}

package com.sight.sosohttp.network;

import java.io.IOException;

/**
 * 创建人Created by Sight-WXC on 2016/9/20 0020.
 * 这里注释说明 *
 */
public interface HttpResponseCallback<T> {


     void onFailed(String url, IOException e);

     void onSuccessed(T bean);

     Class<T> parseObject();
}

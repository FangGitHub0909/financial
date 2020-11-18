package com.fang.util;

import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


/**
 * 发送Rest请求
 */
public class RestUtil{

    public static Object postJson(RestTemplate r,String url,Object param, Class<? extends Object> responseType) {
        Object result = r.postForObject(url, param, responseType);
        return result;
    }
}

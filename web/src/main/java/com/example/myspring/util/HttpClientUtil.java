package com.example.myspring.util;

import com.google.common.collect.ImmutableMap;
import io.netty.util.CharsetUtil;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Map;

public class HttpClientUtil {

    public static String get(String url, Map<String, String> requestParam ) {
        CloseableHttpClient client = HttpClients.createDefault();
        if(url.startsWith("https")) {
            client = createSSLClient();
        }
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if(requestParam != null && !requestParam.isEmpty()) {
                requestParam.forEach((k,v)-> uriBuilder.addParameter(k, v));
            }
            HttpGet get = new HttpGet(uriBuilder.build());
            CloseableHttpResponse response = null;
            response = client.execute(get);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postJson(String url, String body){
        return post(url, ImmutableMap.of("Content-Type", "application/json;charset=utf-8"), null, body);
    }

    public static String postForm(String url, String body){
        return post(url, ImmutableMap.of("Content-Type", "application/x-www-form-urlencoded"), null, body);
    }

    public static String post(String url, Map<String, String> headers, Map<String, Object> urlParams, String body) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost();
        CloseableHttpResponse response = null;
        try {
            if (body != null) {
                httpPost.setEntity(new StringEntity(body, CharsetUtil.UTF_8));
            }
            if (urlParams != null && urlParams.size() > 0) {
                StringBuffer sb = new StringBuffer("?");
                urlParams.forEach((k,v)-> sb.append(k).append("=").append(v).append("&"));
                url += sb.substring(0, sb.length()-1);
                System.out.println("[URL=]" + url);
            }
            httpPost.setURI(URI.create(url));
            if(url.startsWith("https")) {
                client = createSSLClient();
            }
            if (headers != null && headers.size() > 0) {
                headers.forEach((k,v) -> {
                    httpPost.addHeader(k, v);
                });
            }
            response = client.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CloseableHttpClient createSSLClient()
    {
        try
        {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager()
            {
                public X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] xcs, String str)
                {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String str)
                {
                }
            };

            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[] { trustManager }, null);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);

            // 创建Registry
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(Boolean.TRUE)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();

            // 创建ConnectionManager，添加Connection配置信息
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(requestConfig).build();

            return closeableHttpClient;
        } catch (KeyManagementException ex)
        {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex)
        {
            throw new RuntimeException(ex);
        }
    }



}

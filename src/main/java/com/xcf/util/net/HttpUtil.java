package com.xcf.util.net;

import com.xcf.util.lang.CollectionUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpUtil {

    public static HttpResponse get(String url, Map<String, String> param) throws UnsupportedEncodingException {
        if (!CollectionUtil.isEmpty(param)) {
            return get(url, null, param, null, null, null);
        }
        return get(url, null, null, null, null, null);
    }

    public static HttpResponse get(String url, Map<String, String> headers, Map<String, String> param) throws UnsupportedEncodingException {
        if (!CollectionUtil.isEmpty(param)) {
            return get(url, headers, param, null, null, null);
        }
        return get(url, headers, null, null, null, null);
    }

    public static HttpResponse postForm(String url, Map<String, String> body) throws UnsupportedEncodingException {
        if (!CollectionUtil.isEmpty(body)) {
            List<BasicNameValuePair> valuePairs = body.entrySet().stream().map(kv -> new BasicNameValuePair(kv.getKey(), kv.getValue())).collect(Collectors.toList());
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, "utf-8");
            return post(url, null, formEntity, null, null, null);
        }
        return post(url, null, null, null, null, null);
    }

    public static HttpResponse postForm(String url, Map<String, String> headers, Map<String, String> body) throws UnsupportedEncodingException {
        if (!CollectionUtil.isEmpty(body)) {
            List<BasicNameValuePair> valuePairs = body.entrySet().stream().map(kv -> new BasicNameValuePair(kv.getKey(), kv.getValue())).collect(Collectors.toList());
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, "utf-8");
            return post(url, headers, formEntity, null, null, null);
        }
        return post(url, headers, null, null, null, null);
    }

    public static HttpResponse postJson(String url, String body) throws UnsupportedEncodingException {
        if (null == body || "".equals(body)) {
            return post(url, null, null, null, null, null);
        }
        StringEntity jsonEntity = new StringEntity(body, "utf-8");
        return post(url, null, jsonEntity, null, null, null);
    }

    public static HttpResponse postJson(String url, Map<String, String> headers, String body) throws UnsupportedEncodingException {
        if (null == body || "".equals(body)) {
            return post(url, headers, null, null, null, null);
        }
        StringEntity jsonEntity = new StringEntity(body, "utf-8");
        return post(url, headers, jsonEntity, null, null, null);
    }

    private static class DefaultX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static SSLContext defaultSSL() throws NoSuchProviderException, NoSuchAlgorithmException, KeyManagementException {
        // HTTPS处理
        TrustManager[] tm = {new DefaultX509TrustManager()};
        SSLContext defaultSSL = SSLContext.getInstance("SSL", "SunJSSE");
        defaultSSL.init(null, tm, new java.security.SecureRandom());
        return defaultSSL;
    }

    private static RequestConfig defaultConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(1000)
                .build();
    }

    private static HttpResponse post(String url, Map<String, String> headers, HttpEntity body, RequestConfig config
            , String encode, SSLContext sslContext) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse;
        try {
            if (url.startsWith("https")) {
                // SSL
                httpClient = HttpClients.custom().setSSLContext(sslContext == null ? defaultSSL() : sslContext).build();
            }
            if (!CollectionUtil.isEmpty(headers)) {
                // 设置请求头
                headers.forEach(httpPost::setHeader);
            }
            if (null != body) {
                // 设置请求体
                httpPost.setEntity(body);
            }
            // 设置请求参数
            httpPost.setConfig(config == null ? defaultConfig() : config);

            response = httpClient.execute(httpPost);
            return getResponse(encode, response);
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException e) {
            httpResponse = new HttpResponse().setStatus(-1)
                    .setMsg("Exception")
                    .setBody(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                httpPost.abort();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return httpResponse;
    }

    public static HttpResponse get(String url, Map<String, String> headers, Map<String, String> params, RequestConfig config
            , String encode, SSLContext sslContext) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = null;
        HttpResponse httpResponse;
        try {
            if (url.startsWith("https")) {
                // SSL
                httpClient = HttpClients.custom().setSSLContext(sslContext == null ? defaultSSL() : sslContext).build();
            }

            if (!CollectionUtil.isEmpty(params)) {
                StringBuilder builder = new StringBuilder();
                builder.append("?");
                params.forEach((k, v) -> builder.append(k).append("=").append(v).append("&"));
                builder.delete(builder.length() - 1, builder.length());
                if (builder.length() > 1) {
                    url = url + builder.toString();
                }
            }

            httpGet = new HttpGet(url);
            if (!CollectionUtil.isEmpty(headers)) {
                // 设置请求头
                headers.forEach(httpGet::setHeader);
            }
            // 设置请求参数
            httpGet.setConfig(config == null ? defaultConfig() : config);

            response = httpClient.execute(httpGet);
            return getResponse(encode, response);
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException e) {
            httpResponse = new HttpResponse().setStatus(-1)
                    .setMsg("Exception")
                    .setBody(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                httpGet.abort();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return httpResponse;
    }

    private static HttpResponse getResponse(String encode, CloseableHttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        HttpResponse httpResponse = new HttpResponse().setStatus(statusCode);
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String rev = "";
            if (entity != null) {
                rev = EntityUtils.toString(entity, encode == null || "".equals(encode) ? Consts.UTF_8.name() : encode);
            }
            httpResponse.setBody(rev);
            httpResponse.setMsg("success");
            EntityUtils.consume(entity);
        } else if (statusCode == 302) {
            String locationUrl = response.getLastHeader("Location").getValue();
            httpResponse.setMsg("redirect");
            httpResponse.setBody(locationUrl);
        } else {
            httpResponse.setMsg("fail");
        }
        return httpResponse;
    }
}

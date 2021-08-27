//package com.doopp.youlin.util;
//
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.*;
//import org.jetbrains.annotations.NotNull;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.*;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//@Slf4j
//public class AliyunSMS {
//
//    private String accessKeyId;
//
//    private String accessKeySecret;
//
//    private String smsApiHost;
//
//    private OkHttpClient okHttpClient;
//
//    private static final String ALGORITHM_NAME = "HmacSHA1";
//
//    private static final String ENCODING = "UTF-8";
//
//    public AliyunSMS(OkHttpClient okHttpClient, String smsApiHost, String accessKeyId, String accessKeySecret) {
//        this.accessKeyId = accessKeyId;
//        this.accessKeySecret = accessKeySecret;
//        this.okHttpClient = okHttpClient;
//        this.smsApiHost = smsApiHost;
//    }
//
//    private String getSortQueryString(Map<String, String> params) throws UnsupportedEncodingException {
//        // 排序
//        TreeMap<String, String> sortParams = new TreeMap<>(params);
//        // 构建参数
//        Iterator<String> it = sortParams.keySet().iterator();
//        StringBuilder sortQueryStringTmp = new StringBuilder();
//        while (it.hasNext()) {
//            String key = it.next();
//            sortQueryStringTmp
//                    .append("&")
//                    .append(specialUrlEncode(key))
//                    .append("=")
//                    .append(specialUrlEncode(params.get(key)));
//        }
//        return sortQueryStringTmp.toString();
//    }
//
//    private String specialUrlEncode(String value) throws UnsupportedEncodingException {
//        return java.net.URLEncoder
//                .encode(value, ENCODING)
//                .replace("+", "%20")
//                .replace("*", "%2A")
//                .replace("%7E", "~");
//    }
//
//    private String signSortQueryString(String readySignString) throws UnsupportedEncodingException {
//        //String stringToSign = "GET&%2F&" + specialUrlEncode(sortQueryStringTmp.substring(1));
//        // log.info(readySignString);
//        // 构造待签名字符串
//        // String stringToSign = "GET&%2F&" + specialUrlEncode(sortedQueryString);
//        // 签名
//        try {
//            Mac mac = Mac.getInstance(ALGORITHM_NAME);
//            mac.init(new SecretKeySpec((accessKeySecret+"&").getBytes(ENCODING), ALGORITHM_NAME));
//            byte[] signData = mac.doFinal(readySignString.getBytes(ENCODING));
//            return Base64.getEncoder().encodeToString(signData);
//            // return DatatypeConverter.printBase64Binary(signData);
//        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//            throw new IllegalArgumentException(e.toString());
//        }
//    }
//
//    public String sendSms(String signName, String TemplateCode, String phone, String code) throws UnsupportedEncodingException {
//
//        // time
//        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
//
//        // params
//        Map<String, String> params = new HashMap<>();
//        params.put("SignatureMethod", "HMAC-SHA1");
//        params.put("AccessKeyId", accessKeyId);
//        params.put("SignatureVersion", "1.0");
//        params.put("Format", "JSON");
//        params.put("Action", "SendSms");
//        params.put("Version", "2017-05-25");
//        params.put("RegionId", "cn-hangzhou");
//        params.put("SignatureNonce", UUID.randomUUID().toString());
//        params.put("Timestamp", df.format(new Date()));
//        params.put("SignName", signName);
//        params.put("PhoneNumbers", phone);
//
//        params.put("TemplateParam", "{\"code\":\""+code+"\"}");
//        params.put("TemplateCode", TemplateCode);
//        // query string
//        String sortQueryString = getSortQueryString(params);
//        String readySignString = "GET&%2F&" + specialUrlEncode(sortQueryString.substring(1));
//        String signature = specialUrlEncode(signSortQueryString(readySignString));
//
//        // url query String
//        String url = smsApiHost + "/?Signature=" + signature + sortQueryString;
//
//        // log.info(url);
//        Request request = new Request.Builder().url(url).get().build();
//        return okHttpClientRequest(request);
//    }
//
//    private String okHttpClientRequest(Request request) {
//
//        // Future
//        final CompletableFuture<String> future = new CompletableFuture<>();
//
//        // 异步请求
//        okHttpClient.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                future.completeExceptionally(e);
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                ResponseBody responseBody = response.body();
//                if (responseBody==null) {
//                    response.close();
//                    future.complete(null);
//                }
//                else {
//                    String responseContent = responseBody.string();
//                    response.close();
//                    future.complete(responseContent);
//                }
//            }
//        });
//
//        try {
//            return future.get();
//        }
//        catch (InterruptedException | ExecutionException e) {
//            throw new IllegalArgumentException(e.toString());
//        }
//    }
//}

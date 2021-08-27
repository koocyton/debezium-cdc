package com.doopp.youlin.component;

import com.doopp.youlin.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class HttpClient {

    private final OkHttpClient okHttpClient;

    private final JsonUtil jsonUtil;

    public <T> T restGet(String url, Map<String, String> headerMap, Class<T> resultClass) {
        if (headerMap==null) {
            headerMap = new HashMap<>();
        }
        headerMap.put("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes =  request(url, "GET", headerMap, null);
        return jsonUtil.toObject(Arrays.toString(bytes), resultClass);
    }

    public <T> T restPost(String url, Map<String, String> headerMap, Object bodyContent, Class<T> resultClass) {
        if (headerMap==null) {
            headerMap = new HashMap<>();
        }
        headerMap.put("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = request(url, "POST", headerMap, jsonUtil.toJsonBytes(bodyContent));
        return jsonUtil.toObject(Arrays.toString(bytes), resultClass);
    }

    public <T> T restPut(String url, Map<String, String> headerMap, Object bodyContent, Class<T> resultClass) {
        if (headerMap==null) {
            headerMap = new HashMap<>();
        }
        headerMap.put("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = request(url, "PUT", headerMap, jsonUtil.toJsonBytes(bodyContent));
        return jsonUtil.toObject(Arrays.toString(bytes), resultClass);
    }

    public <T> T restDelete(String url, Map<String, String> headerMap, Object bodyContent, Class<T> resultClass) {
        if (headerMap==null) {
            headerMap = new HashMap<>();
        }
        headerMap.put("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = request(url, "DELETE", headerMap, jsonUtil.toJsonBytes(bodyContent));
        return jsonUtil.toObject(Arrays.toString(bytes), resultClass);
    }

    private byte[] request(String url, String method, Map<String, String> headerMap,  byte[] bodyContent) {
        // request builder
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        // request header
        if (headerMap!=null && headerMap.size()>0) {
            headerMap.forEach(requestBuilder::header);
        }
        // request method
        if (!method.equals("GET")) {
            RequestBody requestBody = bodyContent==null ? null : RequestBody.create(bodyContent);
            requestBuilder.method(method, requestBody);
        }
        // CompletableFuture
        final CompletableFuture<byte[]> future = new CompletableFuture<>();
        // Async request
        okHttpClient.newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody==null) {
                    response.close();
                    future.complete(null);
                }
                else {
                    byte[] responseContent = responseBody.bytes();
                    response.close();
                    future.complete(responseContent);
                }
            }
        });

        try {
            return future.get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

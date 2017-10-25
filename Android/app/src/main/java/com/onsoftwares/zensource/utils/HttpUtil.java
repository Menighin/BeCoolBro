package com.onsoftwares.zensource.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    private OkHttpClient client;
    private Callback successCallback;
    private Callback failCallback;
    private HttpUrl.Builder url;
    private Headers.Builder headers;

    private HttpUtil() {
        this.client = new OkHttpClient();
        this.headers = new Headers.Builder();
    }

    public OkHttpClient getClient() {
        return client;
    }

    private Headers.Builder getHeaders() {
        return this.headers;
    }

    private HttpUrl.Builder getUrl() {
        return url;
    }

    private void setUrl(HttpUrl.Builder url) {
        this.url = url;
    }

    private void setSuccessCallback(Callback successCallback) {
        this.successCallback = successCallback;
    }

    private void setFailCallback(Callback failCallback) {
        this.failCallback = failCallback;
    }

    public void makeGet() {
        Request request = new Request.Builder()
            .url(this.url.build())
            .headers(this.headers.build())
            .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("WHAT", e.getStackTrace().toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();

                    if (successCallback != null && successCallback instanceof CallbackString)
                        ((CallbackString) successCallback).callback(responseStr);
                    else if (successCallback != null && successCallback instanceof CallbackJson) {
                        try {
                            JSONObject json = new JSONObject(responseStr);
                            ((CallbackJson) successCallback).callback(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Log.e("WHAT", response.message());
                }
            }
        });
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {

        private HttpUtil httpUtil;

        public Builder() {
            this.httpUtil = new HttpUtil();
        }

        @SuppressWarnings("ConstantConditions")
        public Builder withUrl(String url) {
            this.httpUtil.setUrl(HttpUrl.parse(url).newBuilder());
            return this;
        }

        public Builder addQueryParameter(String name, String value) {
            this.httpUtil.getUrl().addQueryParameter(name, value);
            return this;
        }

        public Builder addHeader(String name, String value) {
            this.httpUtil.getHeaders().add(name, value);
            return this;
        }

        public Builder ifSuccess(Callback c) {
            this.httpUtil.setSuccessCallback(c);
            return this;
        }

        public Builder ifFail(Callback c) {
            this.httpUtil.setFailCallback(c);
            return this;
        }

        public HttpUtil build() {
            return httpUtil;
        }

        public void makeGet() {
            this.httpUtil.makeGet();
        }

    }

    public interface Callback {
    }

    public interface CallbackString extends Callback {
        void callback(String response);
    }

    public interface CallbackJson extends Callback {
        void callback(JSONObject json);
    }
}

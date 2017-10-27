package com.onsoftwares.zensource.utils.httputil;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

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
    private IHttpResponseConverter<?> converter;

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

    private void setConverter(IHttpResponseConverter<?> converter) {
        this.converter = converter;
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
                    try {
                        if (successCallback != null && successCallback instanceof CallbackString)
                            ((CallbackString) successCallback).callback(responseStr);
                        else if (successCallback != null && successCallback instanceof CallbackJsonObject) {
                            JSONObject json = new JSONObject(responseStr);
                            ((CallbackJsonObject) successCallback).callback(json);
                        }
                        else if (successCallback != null && successCallback instanceof CallbackJsonArray) {
                            JSONArray json = new JSONArray(responseStr);
                            ((CallbackJsonArray) successCallback).callback(json);
                        }
                        else if (successCallback != null && successCallback instanceof CallbackConverted<?> && converter != null) {
                            ((CallbackConverted) successCallback).callback(converter.convertHttpResponse(responseStr));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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

        public Builder withConverter(IHttpResponseConverter converter) {
            this.httpUtil.setConverter(converter);
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

    public interface CallbackJsonObject extends Callback {
        void callback(JSONObject json);
    }

    public interface CallbackJsonArray extends Callback {
        void callback(JSONArray json);
    }

    public interface CallbackConverted<T> extends Callback {
        void callback(T response);
    }
}

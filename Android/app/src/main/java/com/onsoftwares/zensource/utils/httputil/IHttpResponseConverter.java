package com.onsoftwares.zensource.utils.httputil;

import org.json.JSONException;

public interface IHttpResponseConverter<T> {
    T convertHttpResponse(String response) throws JSONException;
}

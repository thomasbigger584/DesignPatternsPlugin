package com.twb.designpatternsplugin.services.github.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GitHubAPI {
    @GET("/iluwatar/java-design-patterns/zip/refs/heads/master")
    Call<ResponseBody> downloadLatestZip();
}

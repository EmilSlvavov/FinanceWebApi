package com.finances.service;

import com.google.gson.Gson;
import okhttp3.*;
import java.io.IOException;

public class ApiClient {
    private final String baseUrl;
    private final OkHttpClient httpClient;
    private final Gson gson;
    private String authToken;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
        this.authToken = null;
    }

    public void setAuthToken(String token) {
        this.authToken = token;
    }

    public void clearAuthToken() {
        this.authToken = null;
    }

    public String getAuthToken() {
        return authToken;
    }

    public <T> T post(String endpoint, Object body, Class<T> responseClass) throws IOException {
        String jsonBody = gson.toJson(body);
        RequestBody requestBody = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request.Builder requestBuilder = new Request.Builder()
                .url(baseUrl + endpoint)
                .post(requestBody);

        if (authToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + authToken);
        }

        Request request = requestBuilder.build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API Error: " + response.code() + " " + response.message());
            }
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, responseClass);
        }
    }

    public <T> T get(String endpoint, Class<T> responseClass) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(baseUrl + endpoint)
                .get();

        if (authToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + authToken);
        }

        Request request = requestBuilder.build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API Error: " + response.code() + " " + response.message());
            }
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, responseClass);
        }
    }

    public <T> T put(String endpoint, Object body, Class<T> responseClass) throws IOException {
        String jsonBody = gson.toJson(body);
        RequestBody requestBody = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request.Builder requestBuilder = new Request.Builder()
                .url(baseUrl + endpoint)
                .put(requestBody);

        if (authToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + authToken);
        }

        Request request = requestBuilder.build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API Error: " + response.code() + " " + response.message());
            }
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, responseClass);
        }
    }

    public void delete(String endpoint) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(baseUrl + endpoint)
                .delete();

        if (authToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + authToken);
        }

        Request request = requestBuilder.build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API Error: " + response.code() + " " + response.message());
            }
        }
    }

    public Gson getGson() {
        return gson;
    }
}

package com.theofficialdarac.task_manager.serviceapi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    static ApiManager instance;
    private final Services services;
    private static final String BASE_URL = "http://192.168.0.5/PIN-3/PMA/";

    //  string with localhost and api call folder location
    private ApiManager() {
        //  singleton constructor
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        services = retrofit.create(Services.class);
    }

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    public Services services() {
        return services;
    }

}


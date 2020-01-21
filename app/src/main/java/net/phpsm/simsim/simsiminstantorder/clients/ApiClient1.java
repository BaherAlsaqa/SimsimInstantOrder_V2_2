package net.phpsm.simsim.simsiminstantorder.clients;

import net.phpsm.simsim.simsiminstantorder.utils.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by baher on 11/26/2017.
 */

public class ApiClient1 {
    public static Retrofit RETROFIT     = null;
    public static String base_url = "https://maps.googleapis.com/maps/api/";
    public static final String GOOGLE_PLACE_API_KEY = "AIzaSyC4CzfdbGLfvqZMMpaXINtxLXbDLEmN1Vs";

    public static Retrofit getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor).build();


        RETROFIT = null;

        RETROFIT = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return RETROFIT;
    }
}

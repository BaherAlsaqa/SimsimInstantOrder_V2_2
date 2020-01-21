package net.phpsm.simsim.simsiminstantorder.clients;

import net.phpsm.simsim.simsiminstantorder.utils.LoggingInterceptor;
import net.phpsm.simsim.simsiminstantorder.utils.RestrictedSocketFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by baher on 11/26/2017.
 */

public class ApiClient {
    public static final String URL = "http://simsim.phpsm.net/simsimlaravel/";
    public static final String URL_IMAGE = URL+"public/uploads/";
    public static Retrofit RETROFIT     = null;
    public static String base_url = "https://maps.googleapis.com/maps/api/";
    public static final String GOOGLE_PLACE_API_KEY = "AIzaSyC4CzfdbGLfvqZMMpaXINtxLXbDLEmN1Vs";

    public static Retrofit getClient(){
        if(RETROFIT==null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                   // .connectTimeout(15, TimeUnit.SECONDS)
                    //.readTimeout(15L, TimeUnit.SECONDS)
                    //.writeTimeout(15L, TimeUnit.SECONDS)
                   // .socketFactory(new RestrictedSocketFactory(256*1024))
                    .build();
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT;
    }

    public static Retrofit getClientMap(){
        if(RETROFIT==null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptor())
                    .build();
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT;
    }
}

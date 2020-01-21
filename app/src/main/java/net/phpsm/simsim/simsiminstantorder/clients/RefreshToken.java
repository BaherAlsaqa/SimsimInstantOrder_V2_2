package net.phpsm.simsim.simsiminstantorder.clients;

import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.LoginResponse;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by baher on 14/01/2018.
 */

public interface RefreshToken {

  /*  public static String[] get_refresh_token_result(String refresh_token){

        final String[] result = {"","","",""};
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = apiService.refreshToken(appSharedPreferences.readString("lang"),"application/json","password", 3, "pzavbkx5WTC9HaEoKWEhY31xXqgXFNBzAYGzMUXb", refresh_token);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    String access_token = response.body().getAccess_token();
                    String refresh_token = response.body().getRefresh_token();
                    result[0] = "true";
                    result[1] = access_token;
                    result[2] = refresh_token;
                } else{
                    APIError apiError = ErrorUtils.parseError(response);
                    if (apiError.getMessage().equals("Unauthenticated."))
                    {
                        result[0] = "false";
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

        return result;
    }*/
}

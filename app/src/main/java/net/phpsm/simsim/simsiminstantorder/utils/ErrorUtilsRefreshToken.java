package net.phpsm.simsim.simsiminstantorder.utils;

import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIErrorRefreshToken;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by baher on 03/01/2018.
 */

public class ErrorUtilsRefreshToken {

    public static APIErrorRefreshToken parseError(Response<?> response) {
        Converter<ResponseBody, APIErrorRefreshToken> converter =
                        ApiClient.getClient()
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIErrorRefreshToken error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIErrorRefreshToken();
        }

        return error;
    }
}

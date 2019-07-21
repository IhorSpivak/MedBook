package ua.profarma.medbook.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ua.profarma.medbook.App;

public class AuthorizationInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer " + App.getAccessToken())
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build();
        return chain.proceed(request);
    }
}

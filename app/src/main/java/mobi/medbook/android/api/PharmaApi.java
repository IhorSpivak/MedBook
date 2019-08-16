package mobi.medbook.android.api;

import java.io.IOException;

import mobi.medbook.android.App;
import mobi.medbook.android.BuildConfig;
import mobi.medbook.android.R;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PharmaApi {
    public final static int CODE_401 = 401;
    public final static int CODE_521 = 521;

    private static PharmaApiModule apiRestSecondModule = null;

    public static PharmaApiModule PointAccess() {
        if (apiRestSecondModule == null && !App.getAppContext().getString(R.string.url_api_rest_second).isEmpty()) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    Request request = response.request();
                    if (request.header("Authorization") != null)
                        // Логин и пароль неверны
                        return null;
                    return request.newBuilder()
                            .header("Authorization", Credentials.basic("Mobile", "12312312"))
                            .build();
                }
            });

            if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(logging);
            }
            //httpClient.addInterceptor(new AuthorizationInterceptor());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(App.getAppContext().getString(R.string.url_api_rest_second))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            apiRestSecondModule = retrofit.create(PharmaApiModule.class);
        }
        return apiRestSecondModule;
    }
}

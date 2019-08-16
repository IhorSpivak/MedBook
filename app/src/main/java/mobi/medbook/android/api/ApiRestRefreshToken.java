package mobi.medbook.android.api;

import java.util.concurrent.TimeUnit;

import mobi.medbook.android.App;
import mobi.medbook.android.BuildConfig;
import mobi.medbook.android.R;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiRestRefreshToken {
    public final static int CODE_401 = 401;
    public final static int CODE_521 = 521;

    private static ApiRestModule apiRestModule = null;

    public static ApiRestModule PointAccess() {
        if (apiRestModule == null && !App.getAppContext().getString(R.string.url_api_rest).isEmpty()) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.connectTimeout(11, TimeUnit.SECONDS);
            httpClient.readTimeout(11, TimeUnit.SECONDS);


            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(logging);
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(App.getAppContext().getString(R.string.url_api_rest))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            apiRestModule = retrofit.create(ApiRestModule.class);
        }
        return apiRestModule;
    }

}

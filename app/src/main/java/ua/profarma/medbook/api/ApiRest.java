package ua.profarma.medbook.api;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;
import ua.profarma.medbook.App;
import ua.profarma.medbook.BuildConfig;
import ua.profarma.medbook.R;
import ua.profarma.medbook.utils.LogUtils;

public class ApiRest {
    public final static int CODE_401 = 401;
    public final static int CODE_521 = 521;

    private static ApiRestModule apiRestModule = null;

    public static ApiRestModule PointAccess() {
        if (apiRestModule == null && !App.getAppContext().getString(R.string.url_api_rest).isEmpty()) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5,TimeUnit.MINUTES);



            if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(logging);
            }
            httpClient.addInterceptor(new AuthorizationInterceptor());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(App.getAppContext().getString(R.string.url_api_rest))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            apiRestModule = retrofit.create(ApiRestModule.class);
        }
        return apiRestModule;
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            //OkHttpClient okHttpClient = builder.build();
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

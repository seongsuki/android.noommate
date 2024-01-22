package noommate.android.models.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class BaseRouter {
  public static String BASE_URL = "http://noom.api.hollysome.com/";

  protected static Object retrofit(Class<?> className) {
    HttpLoggingInterceptor.Logger networkLayerLogger = new HttpLoggingInterceptor.Logger() {
      @Override
      public void log(String message) {
        try {
          Gson gson = new GsonBuilder().setPrettyPrinting().create();
          JsonParser jp = new JsonParser();
          Timber.d("" + gson.toJson(jp.parse(message)));
        } catch (JsonParseException e) {
          Timber.d(message);
        }
      }
    };

    HttpLoggingInterceptor.Logger appLayerLogger = new HttpLoggingInterceptor.Logger() {

      @Override
      public void log(String message) {
        try {
          Gson gson = new GsonBuilder().setPrettyPrinting().create();
          JsonParser jp = new JsonParser();
          Timber.d("" + gson.toJson(jp.parse(message)));
        } catch (JsonParseException e) {
          Timber.d(message);
        }

      }
    };
    HttpLoggingInterceptor networkLogging = new HttpLoggingInterceptor(networkLayerLogger);
    HttpLoggingInterceptor appLogging = new HttpLoggingInterceptor(appLayerLogger);

    networkLogging.setLevel(HttpLoggingInterceptor.Level.BODY);
    appLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES);
//    httpClient.addNetworkInterceptor(networkLogging);
    httpClient.addInterceptor(appLogging);
    String host = BASE_URL;
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build();

    return retrofit.create(className);
  }

}


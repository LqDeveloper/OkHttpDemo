package com.example.okhttpdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.getSync)
    TextView getSync;
    @BindView(R.id.getAsync)
    TextView getAsync;
    @BindView(R.id.postSync)
    TextView postSync;
    @BindView(R.id.postASync)
    TextView postASync;


    String urlStr = "https://api.apiopen.top/getSongPoetry?page=1&count=20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.getSync, R.id.getAsync, R.id.postSync, R.id.postASync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getSync:
                getSyncFun(urlStr);
                break;
            case R.id.getAsync:
                getAsyncFun(urlStr);
                break;
            case R.id.postSync:
                postSyncFun("https://api.apiopen.top/getSongPoetry");
                break;
            case R.id.postASync:
                postASyncFun("https://api.apiopen.top/getSongPoetry");
                break;
        }
    }

    private void getSyncFun(String urlStr) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(urlStr)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.d("MainActivity", "response.code()==" + response.code());
                        Log.d("MainActivity", "response.message()==" + response.message());
                        Log.d("MainActivity", "res==" + response.body().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getAsyncFun(String urlStr) {

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlStr)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("MainActivity", "response.code()==" + response.code());
                    Log.d("MainActivity", "response.message()==" + response.message());
                    Log.d("MainActivity", "res==" + response.body().string());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postSyncFun(String urlStr) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
                    builder.add("page", "1");
                    builder.add("count", "20");
                    Request request = new Request.Builder()
                            .url(urlStr)
                            .post(builder.build())
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.d("MainActivity", "response.code()==" + response.code());
                        Log.d("MainActivity", "response.message()==" + response.message());
                        Log.d("MainActivity", "res==" + response.body().string());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void postASyncFun(String urlStr) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("page", "1");
            builder.add("count", "20");
            Request request = new Request.Builder()
                    .url(urlStr)
                    .post(builder.build())
                    .build();
           okHttpClient.newCall(request).enqueue(new Callback() {
               @Override
               public void onFailure(Call call, IOException e) {

               }

               @Override
               public void onResponse(Call call, Response response) throws IOException {
                   Log.d("MainActivity", "response.code()==" + response.code());
                   Log.d("MainActivity", "response.message()==" + response.message());
                   Log.d("MainActivity", "res==" + response.body().string());
               }
           });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

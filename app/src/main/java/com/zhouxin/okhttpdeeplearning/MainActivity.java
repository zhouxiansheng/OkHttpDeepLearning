package com.zhouxin.okhttpdeeplearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        okHttpClient = new OkHttpClient.Builder().readTimeout(5,TimeUnit.SECONDS).build();
        okHttpGet();
        okHttpPost();
    }

    private void okHttpGet() {
        try {
            Request request = new Request.Builder().url("http://www.baidu.com").build();
            Response response = null;
            //同步Get请求
            response = okHttpClient.newCall(request).execute();
            //异步Get请求
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //回调方法在子线程中只执行
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void okHttpPost() {
        FormBody.Builder fromBody = new FormBody.Builder();
        fromBody.add("name","value");
        Request request1 = new Request.Builder().url("http://www.baidu.com").post(fromBody.build()).build();
        //异步Post请求,传递键值对
        okHttpClient.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        //异步Post请求，传递json
        //数据为json格式
        MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
        //json数据
        String jsonStr = "{\"username\":\"zhouxin\",\"sex\":\"男\"}";
        RequestBody requestBody = RequestBody.create(jsonMediaType,jsonStr);
        Request request2 = new Request.Builder().url("http://www.baidu.com").post(requestBody).build();
        okHttpClient.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


        //异步Post请求，传递文件
        MediaType fileMediaType = MediaType.parse("File/*");
        File file = new File("path");
        RequestBody requestBody1 = RequestBody.create(fileMediaType,file);
        Request request3 = new Request.Builder().url("http://www.baidu.com").post(requestBody1).build();
        okHttpClient.newCall(request3).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        //使用MultipartBody混合上传
        MultipartBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("name","zhouxin")
                .addFormDataPart("age","20")
                .addFormDataPart("file",file.getName(),RequestBody.create(MediaType.parse("File/*"),file))
                .build();
        Request request4 = new Request.Builder().url("http://www.baidu.com").post(multipartBody).build();
        okHttpClient.newCall(request4).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        //设置请求头
        Request request5 = new Request.Builder().url("http://www.baidu.com")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("token","myToken")
                .build();

    }
}

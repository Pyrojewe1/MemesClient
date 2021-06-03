package com.example.memes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;
    private Button forget_pwd;
    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
        okHttpClient = new OkHttpClient.Builder().build();
        setButtonOnclickListener();

    }

    public void initView(){
        this.username = findViewById(R.id.username);
        this.password = findViewById(R.id.password);
        this.login = findViewById(R.id.login);
        this.register = findViewById(R.id.register);
        this.forget_pwd = findViewById(R.id.forget_pwd);
    }

    public void setButtonOnclickListener(){
        this.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_username = username.getText().toString();
                String str_pwd = password.getText().toString();
                RequestBody requestBody = new FormBody.Builder()
                        .add("username",str_username)
                        .add("password",str_pwd)
                        .build();
                Request request = new Request.Builder()
                        .url("http://"+StaticVar.currentIP + ":8080/loginController/login")
                        .post(requestBody)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"check your internet",Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                        if(res!=null&&res!=""){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"login success",Toast.LENGTH_LONG);
                                }
                            });
                            StaticVar.currentUserID = Long.valueOf(res);
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });

            }
        });
    }

}

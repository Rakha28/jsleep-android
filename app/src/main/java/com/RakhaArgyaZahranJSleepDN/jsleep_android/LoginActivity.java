package com.RakhaArgyaZahranJSleepDN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Account;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.BaseApiService;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.UtilsApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Rakha Argya
 * @version 1.0.0
 * activity untuk login
 */
public class LoginActivity extends AppCompatActivity {

    BaseApiService mApiService;
    EditText username, password;
    Context mContext;
    static Account MainAccount;

    /**
     * method untuk exit app
     */
    @Override
    public void onBackPressed()
    {
        finishAffinity();
    }
    /**
     * method untuk menginisialisasi activity
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        username = findViewById(R.id.username);
        password = findViewById(R.id.Password);


        Button login = findViewById(R.id.buttonLogin);
        TextView register = findViewById(R.id.registerNow);

        login.setOnClickListener(new View.OnClickListener() {

            /**
             * method untuk menghandle event saat button login diklik
             * @param view view
             */
            @Override
            public void onClick(View view) {
                Account account = requestLogin();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {

            /**
             * method untuk menghandle event saat textview registerNow diklik
             * @param view view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * method untuk melakukan request login
     * @return account
     */
    protected Account requestLogin(){
        mApiService.login(username.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {

            /**
             * method untuk menghandle response dari server
             * @param call call
             * @param response response
             */
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()||response.code()==200) {
                    MainAccount = response.body();
                    Toast.makeText(mContext, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "username atau password salah", Toast.LENGTH_SHORT).show();
                }
            }

            /**
             * method untuk menghandle error dari server
             * @param call call
             * @param t throwable
             */
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Login Failed Check Internet Settings", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
        return null;
    }
}
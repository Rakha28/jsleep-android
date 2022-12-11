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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Rakha Argya
 * @version 1.0.0
 * activity untuk register
 */
public class RegisterActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText email, password, name;
    Context mContext;

    /**
     * method untuk menginisialisasi activity
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        name = findViewById(R.id.Nama);
        email = findViewById(R.id.EmailRegister);
        password = findViewById(R.id.PasswordRegister);


        Button register = findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestRegister();
            }
        });
    }

    /**
     * method untuk menghandle event saat button register diklik
     * @return account
     */
    protected Account requestRegister(){
        mApiService.register(name.getText().toString(), email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {

            /**
             * method untuk menghandle response dari server
             * @param call call
             * @param response response
             */
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "Register Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            /**
             * method untuk menghandle error dari server
             * @param call call
             * @param t throwable
             */
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Register Gagal", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }


}
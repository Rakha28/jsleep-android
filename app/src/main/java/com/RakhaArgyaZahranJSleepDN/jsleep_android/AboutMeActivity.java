package com.RakhaArgyaZahranJSleepDN.jsleep_android;


import androidx.annotation.NonNull;
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
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Renter;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.BaseApiService;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Rakha Argya
 * @version 1.0.0
 * activity untuk About Me (PROFIL)
 */
public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;

    TextView nameRenter, addressRenter, phoneRenter, DisplayNamaRenter, DisplayAddressRenter, DisplayPhoneRenter;

    Button RegRenter, RegAcc, Cancel, TopUp;
    TextView name, email, balance;

    EditText namaRenter, alamatRenter, NomorRenter, BalanceTopUp;

    Context mContext;


    /**
     * method untuk menginisialisasi activity
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_me);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        RegRenter = findViewById(R.id.RegisterRenter);
        RegAcc = findViewById(R.id.RegisterAcc);
        Cancel = findViewById(R.id.CancelReg);

        name = findViewById(R.id.IsiName);
        email = findViewById(R.id.IsiEmail);
        balance = findViewById(R.id.IsiBalance);
        name.setText(LoginActivity.MainAccount.name);
        email.setText(LoginActivity.MainAccount.email);
        balance.setText(Double.toString(LoginActivity.MainAccount.balance));

        DisplayNamaRenter = findViewById(R.id.NamaRenterDisplay);
        DisplayAddressRenter = findViewById(R.id.AddressDisplay);
        DisplayPhoneRenter = findViewById(R.id.PhoneNumDisplay);

        namaRenter = findViewById(R.id.NamaRenter);
        alamatRenter = findViewById(R.id.AddressRenter);
        NomorRenter = findViewById(R.id.PhoneRenter);

        nameRenter = findViewById(R.id.IsiNamaRenter);
        phoneRenter = findViewById(R.id.IsiAddress);
        addressRenter = findViewById(R.id.IsiPhoneRenter);

        DisplayAddressRenter.setVisibility(View.GONE);
        DisplayPhoneRenter.setVisibility(View.GONE);
        DisplayNamaRenter.setVisibility(View.GONE);
        RegRenter.setVisibility(View.GONE);
        nameRenter.setVisibility(View.GONE);
        addressRenter.setVisibility(View.GONE);
        phoneRenter.setVisibility(View.GONE);
        namaRenter.setVisibility(View.GONE);
        alamatRenter.setVisibility(View.GONE);
        NomorRenter.setVisibility(View.GONE);

        BalanceTopUp = findViewById(R.id.etTopUp);
        TopUp = findViewById(R.id.button5);
        TopUp.setOnClickListener(new View.OnClickListener() {
            /**
             * method untuk menginisialisasi button Top Up
             * @param view view
             */
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(BalanceTopUp.getText().toString());
                if(amount < 0 || amount > 1000000){
                    Toast.makeText(mContext, "Top Up Gagal", Toast.LENGTH_SHORT).show();
                }else{
                    requestTopUp(amount);
                }
            }
        });


        if (LoginActivity.MainAccount.renter != null) {

            DisplayAddressRenter.setVisibility(View.VISIBLE);
            DisplayPhoneRenter.setVisibility(View.VISIBLE);
            DisplayNamaRenter.setVisibility(View.VISIBLE);
            RegRenter.setVisibility(View.GONE);
            nameRenter.setVisibility(View.VISIBLE);
            addressRenter.setVisibility(View.VISIBLE);
            phoneRenter.setVisibility(View.VISIBLE);
            namaRenter.setVisibility(View.GONE);
            alamatRenter.setVisibility(View.GONE);
            NomorRenter.setVisibility(View.GONE);

            nameRenter.setText(LoginActivity.MainAccount.renter.username);
            phoneRenter.setText(LoginActivity.MainAccount.renter.phoneNumber);
            addressRenter.setText(LoginActivity.MainAccount.renter.address);

        } else {
            RegRenter.setVisibility(View.VISIBLE);
            RegRenter.setOnClickListener(new View.OnClickListener() {
                /**
                 * method untuk menginisialisasi button Register Renter
                 * @param view view
                 */
                @Override
                public void onClick(View view) {
                    namaRenter.setVisibility(View.VISIBLE);
                    alamatRenter.setVisibility(View.VISIBLE);
                    NomorRenter.setVisibility(View.VISIBLE);
                    RegAcc.setVisibility(View.VISIBLE);
                    Cancel.setVisibility(View.VISIBLE);
                    RegRenter.setVisibility(View.GONE);
                }

            });
            RegAcc.setOnClickListener(new View.OnClickListener() {
                /**
                 * method untuk menginisialisasi button Register Account
                 * @param view view
                 */
                @Override
                public void onClick(View view) {
                    Renter renter = requestRegisterRenter();
                }
            });

            Cancel.setOnClickListener(new View.OnClickListener() {
                /**
                 * method untuk menginisialisasi button Cancel
                 * @param view view
                 */
                @Override
                public void onClick(View view) {
                    nameRenter.setVisibility(View.GONE);
                    alamatRenter.setVisibility(View.GONE);
                    NomorRenter.setVisibility(View.GONE);
                    RegAcc.setVisibility(View.GONE);
                    Cancel.setVisibility(View.GONE);
                    RegRenter.setVisibility(View.VISIBLE);
                }
            });
        }

    }

    /**
     * method untuk register renter
     * @return renter
     */
    protected Renter requestRegisterRenter() {
        mApiService.registerRenter(LoginActivity.MainAccount.id, namaRenter.getText().toString(), alamatRenter.getText().toString(), NomorRenter.getText().toString()).enqueue(new Callback<Renter>() {
            /**
             * method untuk menginisialisasi register renter
             * @param call call
             * @param response response
             */
            @Override
            public void onResponse(@NonNull Call<Renter> call, @NonNull Response<Renter> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "Register Renter Success", Toast.LENGTH_SHORT).show();
                    LoginActivity.MainAccount.renter = response.body();
                    DisplayAddressRenter.setVisibility(View.VISIBLE);
                    DisplayNamaRenter.setVisibility(View.VISIBLE);
                    DisplayPhoneRenter.setVisibility(View.VISIBLE);
                    nameRenter.setVisibility(View.VISIBLE);
                    addressRenter.setVisibility(View.VISIBLE);
                    phoneRenter.setVisibility(View.VISIBLE);
                    namaRenter.setVisibility(View.GONE);
                    alamatRenter.setVisibility(View.GONE);
                    NomorRenter.setVisibility(View.GONE);
                    RegAcc.setVisibility(View.GONE);
                    Cancel.setVisibility(View.GONE);
                    RegRenter.setVisibility(View.GONE);
                    nameRenter.setText(LoginActivity.MainAccount.renter.username);
                    phoneRenter.setText(LoginActivity.MainAccount.renter.phoneNumber);
                    addressRenter.setText(LoginActivity.MainAccount.renter.address);

                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(mContext, "Register Renter Failed", Toast.LENGTH_SHORT).show();

                }
            }

            /**
             * method untuk menginisialisasi register renter
             * @param call call
             * @param t t
             */
            @Override
            public void onFailure(@NonNull Call<Renter> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Register Renter Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    /**
     * method untuk top up
     * @param amount amount
     */
    protected void requestTopUp(int amount) {
        mApiService.topUp(LoginActivity.MainAccount.id, amount).enqueue(new Callback<Account>() {
            /**
             * method untuk jika top up berhasil
             * @param call call
             * @param response response
             */
            @Override
            public void onResponse(@NonNull Call<Account> call, @NonNull Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account = response.body();
                    LoginActivity.MainAccount.balance = account.balance;
                    Toast.makeText(mContext, "Top Up Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AboutMeActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Fail to Top Up", Toast.LENGTH_SHORT).show();
                }
            }

            /**
             * method untuk jika top up gagal
             * @param call call
             * @param t t
             */
            @Override
            public void onFailure(@NonNull Call<Account> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Fail to Top Up", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });

    }
}
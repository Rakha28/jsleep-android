package com.RakhaArgyaZahranJSleepDN.jsleep_android;

import static com.RakhaArgyaZahranJSleepDN.jsleep_android.MainActivity.accountObject;

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

public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;

    TextView nameRenter, addressRenter,phoneRenter, DisplayNamaRenter, DisplayAddressRenter, DisplayPhoneRenter;

    Button RegRenter,RegAcc,Cancel;
    TextView name, email, balance;

    EditText namaRenter, alamatRenter, NomorRenter;

    Context mContext;


    Account account = MainActivity.accountObject;





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
        name.setText(account.name);
        email.setText(account.email);
        balance.setText(Double.toString(account.balance));

        DisplayNamaRenter = findViewById(R.id.NamaRenterDisplay);
        DisplayAddressRenter= findViewById(R.id.AddressDisplay);
        DisplayPhoneRenter= findViewById(R.id.PhoneNumDisplay);

        namaRenter = findViewById(R.id.NamaRenter);
        alamatRenter = findViewById(R.id.AddressRenter);
        NomorRenter = findViewById(R.id.PhoneRenter);

        nameRenter = findViewById(R.id.IsiNamaRenter);
        phoneRenter = findViewById(R.id.IsiPhoneNum);
        addressRenter = findViewById(R.id.IsiAddressRenter);

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




        if(account.renter != null){

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

            nameRenter.setText(account.renter.username);
            phoneRenter.setText(account.renter.phoneNumber);
            addressRenter.setText(account.renter.address);

        } else{
            RegRenter.setVisibility(View.VISIBLE);
            RegRenter.setOnClickListener(new View.OnClickListener() {
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
                @Override
                public void onClick(View view) {
                    Renter renter = requestRegisterRenter();
                }
            });

            Cancel.setOnClickListener(new View.OnClickListener() {
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

    protected Renter requestRegisterRenter(){


        mApiService.registerRenter(accountObject.id, namaRenter.getText().toString(), alamatRenter.getText().toString(), NomorRenter.getText().toString()).enqueue(new Callback<Renter>() {



            @Override

            public void onResponse(@NonNull Call<Renter> call, @NonNull Response<Renter> response) {
                if (response.isSuccessful()) {



                    Renter renter = response.body();
                    accountObject.renter = renter;
                    Toast.makeText(mContext, "Register Renter Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AboutMeActivity.this, MainActivity.class);
                    startActivity(intent);


                } else {



                    Toast.makeText(mContext, "Register Renter Failed", Toast.LENGTH_SHORT).show();
                }
            }




            @Override
            public void onFailure(@NonNull Call<Renter> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Register Renter Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}
package com.RakhaArgyaZahranJSleepDN.jsleep_android;

import static com.RakhaArgyaZahranJSleepDN.jsleep_android.MainActivity.accountObject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Account;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        Account account = MainActivity.accountObject;

        TextView name = findViewById(R.id.IsiName);
        TextView email = findViewById(R.id.IsiEmail);
        TextView balance = findViewById(R.id.IsiBalance);

        name.setText(account.name);
        email.setText(account.email);
        balance.setText(Double.toString(account.balance));
    }
}
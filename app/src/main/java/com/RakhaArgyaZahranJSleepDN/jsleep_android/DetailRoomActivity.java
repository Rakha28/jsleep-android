package com.RakhaArgyaZahranJSleepDN.jsleep_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Facility;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Invoice;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Payment;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Room;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.BaseApiService;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.UtilsApi;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Rakha Argya
 * @version 1.0.0
 * activity untuk menampilkan detail room
 */
public class DetailRoomActivity extends AppCompatActivity {

    protected static Room currentRoom;
    protected Payment currentPayment;
    CheckBox cbWiFi, cbBathtub, cbBalcony, cbAC, cbFitnessCenter, cbRefrigerator, cbRestaurant, cbSwimmingPool;
    ArrayList<Facility> facilityList;
    BaseApiService mApiService;
    Context mContext;
    LinearLayout startLayout, payLayout, buttonLayout, textLayout;


    @SuppressLint("MissingInflatedId")

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(DetailRoomActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * method untuk menginisialisasi activity
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        currentRoom = MainActivity.allRooms.get(MainActivity.selectedPos);
        facilityList = currentRoom.facility;


        TextView nameText = findViewById(R.id.nameAns_detail);
        TextView bedText = findViewById(R.id.bedAns_detail);
        TextView sizeText = findViewById(R.id.sizeAns_detail);
        TextView priceText = findViewById(R.id.priceAns_detail);
        TextView addressText = findViewById(R.id.addressAns_detail);


        String price = NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(currentRoom.price.price);
        String address = currentRoom.address + ", " + currentRoom.city;


        nameText.setText(currentRoom.name);
        bedText.setText(currentRoom.bedType.toString());
        sizeText.setText(currentRoom.size + " m\u00B2");
        priceText.setText(price);
        addressText.setText(address);
        findIdAllCb();
        settingCheckbox();
        requestGetPayment(LoginActivity.MainAccount.id, LoginActivity.MainAccount.renter.id, currentRoom.id);
    }


    /**
     * method untuk inisialisasi checkbox yang sesuai dengan fasilitas yang ada di room
     */
    public void findIdAllCb(){
        cbAC = (CheckBox) findViewById(R.id.AC_detail);
        cbBalcony = (CheckBox) findViewById(R.id.Balcony_detail);
        cbBathtub = (CheckBox) findViewById(R.id.Bathtub_detail);
        cbFitnessCenter = (CheckBox) findViewById(R.id.Fitness_detail);
        cbRefrigerator = (CheckBox) findViewById(R.id.Refrigerator_detail);
        cbRestaurant = (CheckBox) findViewById(R.id.Restaurant_detail);
        cbSwimmingPool = (CheckBox) findViewById(R.id.SwimmingPool_detail);
        cbWiFi = (CheckBox) findViewById(R.id.Wifi_detail);
    }


    /**
     * method untuk mengecek fasilitas yang ada di room
     */
    public void settingCheckbox(){
        System.out.println(facilityList);

        for(Facility facility: facilityList){
            if (facility == Facility.AC){
                cbAC.setChecked(true);
            }else if(facility == Facility.Balcony){
                cbBalcony.setChecked(true);
            }else if(facility == Facility.Bathtub) {
                cbBathtub.setChecked(true);
            }else if(facility == Facility.FitnessCenter){
                cbFitnessCenter.setChecked(true);
            }else if(facility == Facility.Refrigerator){
                cbRefrigerator.setChecked(true);
            }else if(facility == Facility.Restaurant){
                cbRestaurant.setChecked(true);
            }else if(facility == Facility.SwimmingPool){
                cbSwimmingPool.setChecked(true);
            }else if(facility == Facility.WiFi){
                cbWiFi.setChecked(true);
            }
        }
    }


    /**
     * method sebagai algoritm calculate booking
     */
    protected void mainLogic(){
        startLayout = findViewById(R.id.linearStart_detail);
        payLayout = findViewById(R.id.linearPayment_detail);
        buttonLayout = findViewById(R.id.linearButton_detail);
        textLayout = findViewById(R.id.linearText_detail);


        TextView infoText = findViewById(R.id.infoText_detail);
        TextView statusText = findViewById(R.id.statusText_detail);


        startLayout.setVisibility(View.GONE);
        payLayout.setVisibility(View.GONE);
        if(currentPayment != null && currentPayment.status == Invoice.PaymentStatus.WAITING){
            startLayout.setVisibility(View.INVISIBLE);
            payLayout.setVisibility(View.VISIBLE);
            buttonLayout.setVisibility(View.VISIBLE);
            textLayout.setVisibility(View.INVISIBLE);
            Button payButton = findViewById(R.id.payButton_detail);
            Button cancelButton = findViewById(R.id.cancelButton_detail);
            payButton.setOnClickListener(view -> {
                requestAcceptPayment(currentPayment.id);
            });
            cancelButton.setOnClickListener(view -> {
                requestCancelPayment(currentPayment.id);
            });
            String info = "You have booked this room for " +
                    simpleCalcDays(currentPayment.from, currentPayment.to) +
                    " Day(s).\nfrom: " +
                    simpleDateFormat(currentPayment.from) +
                    " to: " +
                    simpleDateFormat(currentPayment.to);
            infoText.setText(info);

        }else if (currentPayment != null && currentPayment.status == Invoice.PaymentStatus.SUCCESS){
            startLayout.setVisibility(View.GONE);
            payLayout.setVisibility(View.VISIBLE);
            buttonLayout.setVisibility(View.GONE);
            textLayout.setVisibility(View.VISIBLE);


            String info = "You have booked this room for " +
                    simpleCalcDays(currentPayment.from, currentPayment.to) +
                    " Day(s).\nfrom: " +
                    simpleDateFormat(currentPayment.from) +
                    " to: " +
                    simpleDateFormat(currentPayment.to);
            infoText.setText(info);
            String status = "Payment status: " + currentPayment.status;
            statusText.setText(status);

        }else{

            startLayout.setVisibility(View.VISIBLE);
            payLayout.setVisibility(View.INVISIBLE);
            buttonLayout.setVisibility(View.INVISIBLE);
            textLayout.setVisibility(View.INVISIBLE);
            Button backButtton = findViewById(R.id.backButton_detail);
            backButtton.setOnClickListener(view -> {
                Intent mainIntent = new Intent(DetailRoomActivity.this, MainActivity.class);
                startActivity(mainIntent);
            });


            Button bookButtton = findViewById(R.id.bookButton_detail);
            bookButtton.setOnClickListener(view -> {
                Intent bookIntent = new Intent(DetailRoomActivity.this, BookActivity.class);
                startActivity(bookIntent);
            });
        }
    }


    /**
     * method untuk menghitung selisih hari
     */
    public long simpleCalcDays(Date before, Date after){
        long timeDiff = Math.abs(after.getTime() - before.getTime());
        return TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
    }


    /**
     * method untuk mengubah format tanggal
     */
    public String simpleDateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        assert date != null;
        return sdf.format(date);
    }


    /**
     * method untuk request accept payment
     */
    protected Boolean requestAcceptPayment(int id){
        mApiService.confirmPayment(id).enqueue(new Callback<Boolean>() {

            /**
             * method untuk mengecek apakah request berhasil
             */
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Payment Successful", Toast.LENGTH_LONG).show();
                    Intent move = new Intent(DetailRoomActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            /**
             * method untuk mengecek apakah request gagal
             */
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(mContext, "Payment Failed", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }


    /**
     * method untuk request cancel payment
     */
    protected Boolean requestCancelPayment(int id){
        mApiService.cancelPayment(id).enqueue(new Callback<Boolean>() {

            /**
             * method untuk mengecek apakah request berhasil
             */
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Booking Canceled", Toast.LENGTH_LONG).show();
                    Intent move = new Intent(DetailRoomActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            /**
             * method untuk mengecek apakah request gagal
             */
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(mContext, "Error can't cancel booking", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }


    /**
     * method untuk request get payment
     */
    protected Payment requestGetPayment(int buyerId, int renterId, int roomId){
        mApiService.getPayment(buyerId, renterId, roomId).enqueue(new Callback<Payment>() {

            /**
             * method untuk mengecek apakah request berhasil
             */
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful()){
                    currentPayment = response.body();
                    mainLogic();
                }
            }

            /**
             * method untuk mengecek apakah request gagal
             */
            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                System.out.println(t);
                mainLogic();
            }
        });
        return null;
    }
}
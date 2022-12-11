package com.RakhaArgyaZahranJSleepDN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.BedType;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.City;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Facility;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Room;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.BaseApiService;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Rakha Argya
 * @version 1.0.0
 * activity untuk membuat room baru
 */
public class CreateRoomActivity extends AppCompatActivity {


    BaseApiService mApiService;
    Context mContext;
    EditText namaKamar, hargaKamar, kasurKamar, alamatKamar, ukuranKamar;
    CheckBox AC, Wifi, Refrigerator, Bathtub, Balcony, Restaurant, Swim, Fitness;
    Spinner bedType, city;
    Button createRoom, cancelCreateRoom;
    ArrayList<Facility> facility = new ArrayList<>();


    /**
     * method untuk menginisialisasi activity
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        Spinner citySpinner = (Spinner) findViewById(R.id.city_spinner);
        citySpinner.setAdapter(new ArrayAdapter<City>(this, R.layout.spinner , City.values()));
        Spinner bedTypeSpinner = (Spinner) findViewById(R.id.bedType_spinner);

        bedTypeSpinner.setAdapter(new ArrayAdapter<BedType>(this, R.layout.spinner , BedType.values()));
        mApiService = UtilsApi.getApiService();
        mContext = this;

        createRoom = findViewById(R.id.button_createRoom);
        cancelCreateRoom = findViewById(R.id.button_cancelCreateRoom);
        createRoom.setOnClickListener(a -> {
                createRoomRequest();
                finish();
        }
        );

        cancelCreateRoom.setOnClickListener(new View.OnClickListener() {
            /**
             * method untuk menghandle event click
             * @param view view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * method untuk membuat room baru
     */
    protected Room createRoomRequest(){
        namaKamar = findViewById(R.id.room_name_et);
        hargaKamar = findViewById(R.id.room_price_et);
        double price = Double.parseDouble(hargaKamar.getText().toString());
        alamatKamar = findViewById(R.id.room_address_et);
        ukuranKamar = findViewById(R.id.room_size_et);
        int size = Integer.parseInt(ukuranKamar.getText().toString());
        bedType = findViewById(R.id.bedType_spinner);
        BedType bedType1 = BedType.valueOf(bedType.getSelectedItem().toString());
        city = findViewById(R.id.city_spinner);
        City city1 = City.valueOf(city.getSelectedItem().toString());
        AC = findViewById(R.id.checkBox_AC);
        Wifi = findViewById(R.id.checkBox_Wifi);
        Refrigerator = findViewById(R.id.checkBox_Refri);
        Bathtub = findViewById(R.id.checkBox_bathtub);
        Balcony = findViewById(R.id.checkBox_balcony);
        Restaurant = findViewById(R.id.checkBox_restaurant);
        Swim = findViewById(R.id.checkBox_swimpool);
        Fitness = findViewById(R.id.checkBox_fitnessC);
        if(AC.isChecked()){
            facility.add(Facility.AC);
        }
        if(Wifi.isChecked()){
            facility.add(Facility.WiFi);
        }
        if(Refrigerator.isChecked()){
            facility.add(Facility.Refrigerator);
        }
        if(Bathtub.isChecked()){
            facility.add(Facility.Bathtub);
        }
        if(Balcony.isChecked()){
            facility.add(Facility.Balcony);
        }
        if(Restaurant.isChecked()){
            facility.add(Facility.Restaurant);
        }
        if(Swim.isChecked()){
            facility.add(Facility.SwimmingPool);
        }
        if(Fitness.isChecked()){
            facility.add(Facility.FitnessCenter);
        }


        mApiService.createRoom(LoginActivity.MainAccount.id, namaKamar.getText().toString(), size, price, facility,city1,alamatKamar.getText().toString(), bedType1).enqueue(new Callback<Room>() {
            /**
             * method untuk menghandle response dari server
             * @param call call
             * @param response response
             */
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Room Created", Toast.LENGTH_SHORT).show();
                }
            }

            /**
             * method untuk menghandle error dari server
             * @param call call
             * @param t throwable
             */
            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(mContext, "Room Creation Failed", Toast.LENGTH_SHORT).show();
            }
        });

        return null;
    }
}
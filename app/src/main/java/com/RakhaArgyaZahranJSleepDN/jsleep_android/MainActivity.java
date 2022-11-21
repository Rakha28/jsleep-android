package com.RakhaArgyaZahranJSleepDN.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Room;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        ArrayList<String> list = new ArrayList<>();

        String myList = null;


        try
        {
            InputStream is = getAssets().open("randomRoomList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            myList = new String(buffer, "UTF-8");

        }

        catch (IOException e)

        {
            e.printStackTrace();
        }



        Type listType = new TypeToken<ArrayList<Room>>(){}.getType();



        ArrayList<Room> rooms = new Gson().fromJson(myList, listType);

        for (Room room :    rooms)
        {
            list.add(room.name);
        }


        ListView listView = findViewById(R.id.listView);




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);


        listView.setAdapter(adapter);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.top_menu, menu);

        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_button:


                Intent aboutMeIntent = new Intent(MainActivity.this, AboutMeActivity.class);

                startActivity(aboutMeIntent);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }


    }

}
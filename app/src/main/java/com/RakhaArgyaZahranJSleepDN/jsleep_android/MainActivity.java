package com.RakhaArgyaZahranJSleepDN.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Room;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.BaseApiService;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Rakha Argya
 * @version 1.0.0
 * activity untuk menampilkan list room
 */
public class MainActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    EditText pageSize_et;
    ListView listView;
    Button nextBtn, prevBtn, goBtn;
    int currPage = 0;
    static List<Room> allRooms = new ArrayList<Room>();
    public static int selectedPos;

    @Override
    public void onBackPressed()
    {

    }

    /**
     * method untuk menginisialisasi activity
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        if(LoginActivity.MainAccount.renter == null){
            Toast.makeText(mContext, "Welcome, Please Add Renter", Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, "By Pressing the profile button", Toast.LENGTH_SHORT).show();
        } else {
            getAllRooms();
        }

        nextBtn = findViewById(R.id.next_button);
        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(currPage < allRooms.size()/10){
                    currPage++;
                    getAllRooms();
                } else{
                    Toast.makeText(mContext, "No more pages", Toast.LENGTH_SHORT).show();
                }
            }

        });

        prevBtn = findViewById(R.id.previous_button);
        prevBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(currPage > 0){
                    currPage--;
                    getAllRooms();
                } else {
                    Toast.makeText(mContext, "No more pages", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goBtn = findViewById(R.id.Go_Button);
        goBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                pageSize_et = findViewById(R.id.Page_list);
                int pageSize = Integer.parseInt(pageSize_et.getText().toString());
                if(pageSize > 0 && pageSize <= allRooms.size()/10){
                    currPage = pageSize - 1;
                    getAllRooms();
                } else {
                    Toast.makeText(mContext, "Page doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPos = position;
            Intent intent = new Intent(mContext, DetailRoomActivity.class);
            startActivity(intent);
        });
    }

    /**
     * method untuk mengambil semua room
     */
    protected ArrayList<Room> getAllRooms() {

        pageSize_et = findViewById(R.id.Page_list);

        pageSize_et.setText(String.valueOf(currPage+1));

        listView = findViewById(R.id.listView);


        String pageSizeStr = pageSize_et.getText().toString();
        int pageSize = 0;
        if (pageSizeStr.equals("1")) {
            pageSize = 10;
        } else {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        /**
         * method untuk mengambil semua room
         */
        mApiService.getAllRoom(currPage, pageSize).enqueue(new Callback<List<Room>>() {
            /**
             * method untuk menampilkan response
             * @param call call
             * @param response response
             */
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                allRooms = response.body();
                if(allRooms == null) {
                    Toast.makeText(mContext, "No Room Found", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayAdapter<Room> adapter = new ArrayAdapter<Room>(mContext, R.layout.roomlist, allRooms);
                    listView.invalidateViews();
                    listView.setAdapter(adapter);
                }
            }

            /**
             * method untuk menampilkan error
             * @param call call
             * @param t error
             */
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to load the room", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    /**
     * method untuk membuat menu
     * @param menu menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        if(LoginActivity.MainAccount.renter != null){
            menu.findItem(R.id.add_button).setVisible(true);
        } else {
            menu.findItem(R.id.add_button).setVisible(false);
        }
        return true;
    }

    /**
     * method untuk menghandle menu
     * @param item item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_button:
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.search_button:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_button:
                Intent intent1 = new Intent(this, CreateRoomActivity.class);
                startActivity(intent1);
                return true;
            case R.id.person_button:
                Intent intent2 = new Intent(this, AboutMeActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


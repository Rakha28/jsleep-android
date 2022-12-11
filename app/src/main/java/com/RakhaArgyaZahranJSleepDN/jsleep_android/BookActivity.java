package com.RakhaArgyaZahranJSleepDN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Payment;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.BaseApiService;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.request.UtilsApi;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Rakha Argya
 * @version 1.0.0
 * activity untuk melakukan booking
 */
public class BookActivity extends AppCompatActivity {



    private DatePickerDialog datePickerDialog;
    private Button dateButtonFrom, dateButtonTo;
    private int index = 0;
    protected static String from, to;
    BaseApiService mApiService;
    Context mContext;
    double roomPrice = DetailRoomActivity.currentRoom.price.price;
    double accountBalance = LoginActivity.MainAccount.balance;
    long numDays = 0;
    protected static ArrayList<Payment> paymentList = null;



    /**
     * method untuk menginisialisasi activity
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        if(paymentList == null){
            paymentList = new ArrayList<>();
        }


        dateButtonFrom = findViewById(R.id.fromPicker);
        dateButtonTo = findViewById(R.id.toPicker);
        initDatePicker();
        from = getTodaysDate(0);
        to = getTodaysDate(1);
        dateButtonFrom.setText(from);
        dateButtonTo.setText(to);


        dateButtonFrom.setOnClickListener(view -> {
            index = 1;
            datePickerDialog.show();
        });


        dateButtonTo.setOnClickListener(view -> {
            index = 2;
            datePickerDialog.show();
        });


        Button saveButton = findViewById(R.id.saveButton_date);
        saveButton.setOnClickListener(view -> {
            numDays = calcDays(from, to);
            requestBooking(LoginActivity.MainAccount.id,
                    LoginActivity.MainAccount.renter.id,
                    DetailRoomActivity.currentRoom.id,
                    formatDate(from),
                    formatDate(to));
        });


        updatePrice();
        TextView balanceText = findViewById(R.id.tvBalance);
        String balanceCurrency = NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(accountBalance);
        balanceText.setText(balanceCurrency);
    }


    /**
     * method untuk menginisialisasi date hari ini
     */
    private String getTodaysDate(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, offset);
        int year =cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH);
        month = month + 1;
        int day =cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    /**
     * method untuk menginisialisasi date picker
     */
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                if(index == 1){
                    from = date;
                    dateButtonFrom.setText(from);
                }else if(index == 2){
                    String tempTo = to;
                    to = date;
                    if(calcDays(from, to) >= 1 ){
                        dateButtonTo.setText(to);
                        updatePrice();
                    }else{
                        to = tempTo;
                        Toast.makeText(mContext, "Min. 1 day of stay", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };


        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 30L *24*60*60*1000);
    }


    /**
     * method untuk membuat string dari date
     * @param day hari
     * @param month bulan
     * @param year tahun
     * @return string dari date
     */
    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }


    /**
     * method untuk mengubah format bulan
     * @param month bulan
     * @return string dari bulan
     */
    private String getMonthFormat(int month) {
        switch (month){
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
        }
        return null;
    }


    /**
     * method untuk mengubah format date
     * @param date date
     * @return string dari date
     */
    public String formatDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        Date fDate = null;
        try {
            fDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd");
        assert fDate != null;
        return sdfFormat.format(fDate);
    }


    /**
     * method untuk menghitung jumlah hari
     * @param before tanggal awal
     * @param after tanggal akhir
     * @return jumlah hari
     */
    public long calcDays(String before, String after){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        Date dateBefore = null;
        Date dateAfter = null;
        try {
            dateBefore = sdf.parse(before);
            dateAfter = sdf.parse(after);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeDiff = Math.abs(dateAfter.getTime() - dateBefore.getTime());
        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        return daysDiff;
    }


    /**
     * method untuk mengupdate harga
     */
    public void updatePrice(){
        TextView priceText = findViewById(R.id.tvPrice);
        String priceCurrency = NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(roomPrice * calcDays(from, to));
        priceText.setText(priceCurrency);
    }


    /**
     * method untuk request booking
     * @param renterId id penyewa
     * @param roomId id kamar
     * @param from tanggal check in
     * @param to tanggal check out
     */
    protected Payment requestBooking(int buyerId, int renterId, int roomId, String from, String to){
        mApiService.createPayment(buyerId, renterId, roomId,from, to).enqueue(new Callback<Payment>() {
            /**
             * method untuk menangani response dari server
             * @param call request
             * @param response response dari server
             */
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful()){
                    Payment payment;
                    payment = response.body();
                    System.out.println(payment.toString());
                    paymentList.add(payment);
                    Toast.makeText(mContext, "Booking Successful", Toast.LENGTH_LONG).show();
                    Intent move = new Intent(BookActivity.this, DetailRoomActivity.class);
                    startActivity(move);
                }
            }


            /**
             * method untuk menangani error dari server
             * @param call request
             * @param t error dari server
             */
            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                if(roomPrice * numDays  > accountBalance){
                    Toast.makeText(mContext, "Please top up first", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mContext, "Please book another date", Toast.LENGTH_LONG).show();
                }
            }
        });
        return null;
    }

}
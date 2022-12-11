package com.RakhaArgyaZahranJSleepDN.jsleep_android.request;

import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Account;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.BedType;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.City;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Facility;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Payment;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Renter;
import com.RakhaArgyaZahranJSleepDN.jsleep_android.model.Room;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Rakha Argya
 * @version 1.0.0
 * API untuk request data dari database atau backend
 */

public interface BaseApiService {

    /**
     * request untuk login
     * @param email email dari user
     * @param password password dari user
     */
    @POST("account/login")
    Call<Account> login (@Query("email") String email, @Query("password") String password);

    /**
     * request untuk register
     * @param email email dari user
     * @param password password dari user
     * @param name nama dari user
     */
    @POST("account/register")
    Call<Account> register  (@Query("name") String name,
                             @Query("email") String email,
                             @Query("password") String password);

    /**
     * request untuk mendaftarkan data renter
     * @param id id dari user
     * @param name nama dari user
     * @param address alamat dari user
     * @param phoneNumber nomor telepon dari user
     */
    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id,
                                @Query("name") String name,
                                @Query("address") String address,
                                @Query("phoneNumber") String phoneNumber);

    /**
     * request untuk isi ulang dana
     * @param id id dari user
     * @param balance saldo dari user
     */
    @POST("account/{id}/topUp")
    Call<Account> topUp(@Path("id") int id, @Query("balance") int balance);

    /**
     * get room berdasarkan id
     * @param id id dari user
     */
    @GET("room/{id}")
    Call<Room> getRoom (@Path("id") int id);

    /**
     * get room berdasarkan renter
     * @param id id dari user
     */
    @GET("room/{id}/renter")
    Call<List<Room>> getRoomByRenter (@Path("id") int id,
                                      @Query("page") int page,
                                      @Query("pageSize") int pageSize);

    /**
     * membuat room baru
     * @param accountId id dari user
     * @param name nama dari room
     * @param size ukuran dari room
     * @param price harga dari room
     * @param facility fasilitas dari room
     * @param city kota dari room
     * @param address alamat dari room
     * @param bedType tipe tempat tidur dari room
     */
    @POST("room/create")
    Call<Room> createRoom (@Query("accountId") int accountId,
                           @Query("name") String name,
                           @Query("size") int size,
                           @Query("price") double price,
                           @Query("facility") ArrayList<Facility> facility,
                           @Query("City") City city,
                           @Query("address") String address,
                           @Query("bedType") BedType bedType);

    /**
     * mengambil semua room
     * @param page halaman yang akan diambil
     * @param pageSize jumlah data yang akan diambil
     */

    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom (@Query("page") int page,
                                 @Query("pageSize") int pageSize);


    /**
     * membuat payment baru
     * @param buyerId id dari user
     * @param renterId id dari renter
     * @param roomId id dari room
     * @param from tanggal awal
     * @param to tanggal akhir
     * @return payment
     */
    @POST("payment/create")
    Call<Payment> createPayment (@Query("buyerId") int buyerId,
                          @Query("renterId") int renterId,
                          @Query("roomId") int roomId,
                          @Query("from") String from,
                          @Query("to") String to);

    /**
     * megambil data payment berdasarkan id
     * @param buyerId id dari user
     * @param renterId id dari renter
     * @param roomId id dari room
     * @return payment
     */
    @GET("payment/get")
    Call<Payment> getPayment(@Query("buyerId") int buyerId,
                             @Query("renterId") int renterId,
                             @Query("roomId") int roomId);

    /**
     * accept payment
     * @param id id dari payment
     * @return payment
     */
    @POST("payment/{id}/confirm")
    Call<Boolean> confirmPayment (@Path("id") int id);

    /**
     * cancel payment
     * @param id id dari payment
     * @return boolean
     */
    @POST("payment/{id}/cancel")
    Call<Boolean> cancelPayment (@Path("id") int id);
}

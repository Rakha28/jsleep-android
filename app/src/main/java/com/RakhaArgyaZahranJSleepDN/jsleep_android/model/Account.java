package com.RakhaArgyaZahranJSleepDN.jsleep_android.model;

/**
 * @author Rakha Argya .
 * @version 1.0.0
 * tempat untuk menyimpan data account yang akan diambil dari database
 */


public class Account extends Serializable{
    public String password;
    public String email;
    public String name;
    public Renter renter;
    public double balance;

    /**
     * toString disini untuk mengubah object menjadi string
     */
    @Override
    public String toString(){
        return "Account{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", renter='" + renter + '\'' +
                ", balance=" + balance +
                '}';
    }


}


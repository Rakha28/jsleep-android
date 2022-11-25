package com.RakhaArgyaZahranJSleepDN.jsleep_android.model;

public class Account extends Serializable{
    public String password;
    public String email;
    public String name;
    public Renter renter;
    public double balance;

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


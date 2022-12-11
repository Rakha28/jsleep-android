package com.RakhaArgyaZahranJSleepDN.jsleep_android.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Rakha Argya
 * @version 1.0.0
 * tempat untuk menyimpan data Room yang akan diambil dari database
 */

public class Room extends Serializable{
    public int accountId;
    public String name;
    public ArrayList<Date> booked;
    public String address;
    public Price price;
    public City city;
    public int size;
    public BedType bedType;
    public ArrayList<Facility> facility;

    public String toString() {
        return name;
    }

}

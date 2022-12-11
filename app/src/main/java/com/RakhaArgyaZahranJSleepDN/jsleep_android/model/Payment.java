package com.RakhaArgyaZahranJSleepDN.jsleep_android.model;

import java.util.Date;

/**
 * @author Rakha Argya
 * @version 1.0.0
 * tempat untuk menyimpan data payment yang akan diambil dari database
 */
public class Payment extends Invoice {
    public Date to;
    public Date from;
    private int roomId;
}

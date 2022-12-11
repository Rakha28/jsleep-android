package com.RakhaArgyaZahranJSleepDN.jsleep_android.model;

/**
 * @author Rakha Argya
 * @version 1.0.0
 * tempat untuk menyimpan data invoice yang akan diambil dari database
 */
public class Invoice extends Serializable {
    public int buyerId;
    public int renterId;
    public PaymentStatus status;
    public RoomRating rating;

    public enum RoomRating {
        NONE, BAD, NEUTRAL, GOOD
    }

    public enum PaymentStatus {
        FAILED, WAITING, SUCCESS
    }
}

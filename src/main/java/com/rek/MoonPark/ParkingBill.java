package com.rek.MoonPark;

public interface ParkingBill {

    long getTotalMinParked();

    int getParkingFeePrHour();

    void setParkingFeePrHour(int parkingFeePrHour);

    int getSum();

}

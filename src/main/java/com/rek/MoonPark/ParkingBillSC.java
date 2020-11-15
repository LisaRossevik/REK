package com.rek.MoonPark;

public abstract class ParkingBillSC implements ParkingBill {

    private long totalMinParked;
    private int parkingFeePrHour;
    private int sum;

    public ParkingBillSC() {
    }

    public ParkingBillSC(long totalMinParked) {
        this.totalMinParked = totalMinParked;
    }

    public long getTotalMinParked() {
        return totalMinParked;
    }

    public void setTotalMinParked(long totalMinParked) {
        this.totalMinParked = totalMinParked;
    }

    public int getParkingFeePrHour() {
        return parkingFeePrHour;
    }

    public void setParkingFeePrHour(int parkingFeePrHour) {
        this.parkingFeePrHour = parkingFeePrHour;
    }

    public int getSum() {
        return 0;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}

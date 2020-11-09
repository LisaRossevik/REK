package com.rek.MoonPark;

public abstract class ParkingBillSC implements ParkingBill {

    private long totalMinParked;
    private int parkingFeePrHour;
    private String weekDay;
    private int sum;

    public ParkingBillSC() {
    }

    public ParkingBillSC(long totalMinParked) {
        this.totalMinParked = totalMinParked;
    }

    public ParkingBillSC(int totalMinParked, String weekDay) {
        this.totalMinParked = totalMinParked;
        this.weekDay = weekDay;
    }

    public ParkingBillSC(long totalMinParked, int parkingFeePrHour) {
        this.totalMinParked = totalMinParked;
        this.parkingFeePrHour = parkingFeePrHour;
    }

    public long getTotalMinParked() {
        return totalMinParked;
    }

    public void setTotalMinParked(int totalMinParked) {
        this.totalMinParked = totalMinParked;
    }

    public int getParkingFeePrHour() {
        return parkingFeePrHour;
    }

    public void setParkingFeePrHour(int parkingFeePrHour) {
        this.parkingFeePrHour = parkingFeePrHour;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public int getSum() {
        return 0;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}

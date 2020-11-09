package com.rek.MoonPark;

public class ParkingBillM1 extends ParkingBillSC implements ParkingBill {
    private int parkingFeePrHour = 60;

    public ParkingBillM1(long totalMinParked) {
        super(totalMinParked);
    }

    public void setParkingFeePrHour(int parkingFeePrHour) {
        this.parkingFeePrHour = parkingFeePrHour;
    }

    public int getParkingFeePrHour() {
        return parkingFeePrHour;
    }

    @Override
    public int getSum() {
        int sum;
        if (super.getTotalMinParked() % 60 == 0) {
            sum = (int)(super.getTotalMinParked() / 60) * parkingFeePrHour;
        } else {
            sum = (int)(super.getTotalMinParked() / 60) * parkingFeePrHour + parkingFeePrHour;
        }
        System.out.println("sum = " + sum);
        return sum;
    }

}

package com.rek.MoonPark;

public class ParkingBillM2 extends ParkingBillSC implements ParkingBill {
    private int parkingFeePrHour = 100;

    public ParkingBillM2(int totalMinParked, String weekDay) {
        super(totalMinParked, weekDay);
    }

    @Override
    public int getParkingFeePrHour() {
        if (getWeekDay().equals("saturday") || getWeekDay().equals("sunday")) {
            return parkingFeePrHour * 2;
        } else {
            return parkingFeePrHour;
        }
    }

    @Override
    public int getSum() {
        long s = getTotalMinParked() % 60 == 0 ? (getTotalMinParked() / 60) * getParkingFeePrHour() : (getTotalMinParked() / 60) * getParkingFeePrHour() + getParkingFeePrHour();
        int sum = (int) s;
        System.out.println("sum = " + sum);
        return sum;
    }

}

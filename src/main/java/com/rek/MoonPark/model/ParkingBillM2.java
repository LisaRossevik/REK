package com.rek.MoonPark.model;

import com.rek.MoonPark.ParkingBill;
import com.rek.MoonPark.ParkingBillSC;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ParkingBillM2 extends ParkingBillSC implements ParkingBill {

    @Id
    @GeneratedValue
    private long m2Id;
    private int parkingFeePrHour = 100;
    private String weekDay;
    private int totalMinParked;
    private int sum;

    public ParkingBillM2() {
    }

    public ParkingBillM2(int totalMinParked, String weekDay) {
        this.totalMinParked = totalMinParked;
        this.weekDay = weekDay;
    }

    public long getM2Id() {
        return m2Id;
    }

    public void setM2Id(long m2Id) {
        this.m2Id = m2Id;
    }

    public String getWeekDay() {
        return weekDay;
    }

    @Override
    public long getTotalMinParked() {
        return totalMinParked;
    }

    public void setParkingFeePrHour(int parkingFeePrHour) {
        this.parkingFeePrHour = parkingFeePrHour;
    }

    public int getParkingFeePrHour() {
        if (getWeekDay().equals("saturday") || getWeekDay().equals("sunday")) {
            return parkingFeePrHour * 2;
        } else {
            return parkingFeePrHour;
        }
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public int getSum() {
        long s = getTotalMinParked() % 60 == 0 ? (getTotalMinParked() / 60) * getParkingFeePrHour() : (getTotalMinParked() / 60) * getParkingFeePrHour() + getParkingFeePrHour();
        int sum = (int) s;
        System.out.println("sum = " + sum);
        return sum;
    }

    @Override
    public String toString() {
        return "ParkingBillM2{" +
                "parkingFeePrHour=" + parkingFeePrHour +
                ", weekDay='" + weekDay + '\'' +
                '}';
    }
}

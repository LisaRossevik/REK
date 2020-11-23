package com.rek.MoonPark.model;


import com.rek.MoonPark.ParkingBill;
import com.rek.MoonPark.ParkingBillSC;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ParkingBillM1 extends ParkingBillSC implements ParkingBill {

    @Id
    @GeneratedValue
    private long m1Id;
    private int parkingFeePrHour = 60;
    private long totalMinParked;
    private int sum;

    public ParkingBillM1(){}

    public ParkingBillM1(long totalMinParked) {
        this.totalMinParked = totalMinParked;
    }

    public long getM1Id() {
        return m1Id;
    }

    public void setM1Id(long m1Id) {
        this.m1Id = m1Id;
    }

    public void setParkingFeePrHour(int parkingFeePrHour) {
        this.parkingFeePrHour = parkingFeePrHour;
    }

    public int getParkingFeePrHour() {
        return parkingFeePrHour;
    }

    @Override
    public long getTotalMinParked() {
        return totalMinParked;
    }

    public void setTotalMinParked(long totalMinParked) {
        this.totalMinParked = totalMinParked;
    }

    @Override
    public int getSum() {
        int sum;
        if (getTotalMinParked() % 60 == 0) {
            sum = (int) (getTotalMinParked() / 60) * parkingFeePrHour;
        } else {
            sum = (int) (getTotalMinParked() / 60) * parkingFeePrHour + parkingFeePrHour;
        }
        System.out.println("sum = " + sum);
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "ParkingBillM1{" +
                "parkingFeePrHour=" + parkingFeePrHour +
                '}';
    }
}

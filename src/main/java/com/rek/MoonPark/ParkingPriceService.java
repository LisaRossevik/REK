package com.rek.MoonPark;

import java.time.LocalDateTime;

public class ParkingPriceService {
    /*
    /* Må implementeres og testes :
     * Mandag - lørdag
     * Korttid mellom 8 - 16
     * Korttid mellom 16 - 18
     * Korttid passerer 8
     * Korttid passerer 16
     *
     * Søndag
     * Korttid max 24 h
     * Starter før søndag, slutter på søndag
     * Starter søndag, slutter etter søndag
     *
     * * */

    public ParkingBillSC calculateParkingPrice(String zone, int totalMinParked, String weekDay, int hourOfDay, LocalDateTime start, LocalDateTime end) {

        switch (zone) {
            case "M1":
                ParkingBillM1 pbm1 = new ParkingBillM1(totalMinParked);
                pbm1.setParkingFeePrHour(pbm1.getParkingFeePrHour());
                pbm1.setSum(pbm1.getSum());
                return pbm1;
            case "M2":
                ParkingBillM2 pbm2 = new ParkingBillM2(totalMinParked, weekDay);
                pbm2.setSum(pbm2.getSum());
                return pbm2;
            case "M3":
                ParkingBillM3 pbm3 = new ParkingBillM3(start, end);
                return pbm3;
            default:
                System.out.println("Invalid zone was given. Must be M1, M2 or M3");
                return null;
        }
    }

}
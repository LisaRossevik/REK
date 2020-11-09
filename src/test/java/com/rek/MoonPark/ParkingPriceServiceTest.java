package com.rek.MoonPark;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingPriceServiceTest {

    private ParkingPriceService pps = new ParkingPriceService();

    @Test
    public void calculateParkingPriceM1Test() {

        ParkingBillSC bill1 = pps.calculateParkingPrice("M1", 61, null, 7, null, null );
        assertEquals(120, bill1.getSum());

        ParkingBillSC bill2 = pps.calculateParkingPrice("M1", 120, null, 2, null, null);
        assertEquals(120, bill2.getSum());

        ParkingBillSC bill3 = pps.calculateParkingPrice("M1", 181, null, 11, null, null);
        assertEquals(240, bill3.getSum());
    }

    @Test
    public void calculateParkingPriceM2WeekTest() {
        ParkingBillSC bill1 = pps.calculateParkingPrice("M2", 180, "monday", 3, null, null);
        assertEquals(300, bill1.getSum());

        ParkingBillSC bill2 = pps.calculateParkingPrice("M2", 121, "tuesday", 3, null, null);
        assertEquals(300, bill2.getSum());

        ParkingBillSC bill3 = pps.calculateParkingPrice("M2", 181, "wednesday", 3, null, null);
        assertEquals(400, bill3.getSum());
    }

    @Test
    public void calculateParkingPriceM2WeekEndTest() {

        ParkingBillSC bill1 = pps.calculateParkingPrice("M2", 119, "saturday", 12, null, null);
        assertEquals(400, bill1.getSum());

        ParkingBillSC bill2 = pps.calculateParkingPrice("M2", 120, "sunday", 12, null, null);
        assertEquals(400, bill2.getSum());

        ParkingBillSC bill3 = pps.calculateParkingPrice("M2", 121, "saturday", 12, null, null);
        assertEquals(600, bill3.getSum());

    }

    @Test
    public void calculateParkingPriceM3SaturdayTest() {
        LocalDateTime startTime1 = LocalDateTime.of(2020, 11, 14, 11, 10, 00);
        LocalDateTime endTime1 = LocalDateTime.of(2020, 11, 14, 12, 20, 00);
        ParkingBillSC bill1 = pps.calculateParkingPrice("M3", 119, "saturday", 12, startTime1, endTime1);
        assertEquals(20, bill1.getSum());
    }

    @Test
    public void calculateParkingPriceM3SundayTest() {
        LocalDateTime startTime2 = LocalDateTime.of(2020, 11, 15, 11, 00, 00);
        LocalDateTime endTime2 = LocalDateTime.of(2020, 11, 15, 14, 50, 00);
        ParkingBillSC bill2 = pps.calculateParkingPrice("M3", 120, "sunday", 12, startTime2, endTime2);
        assertEquals(0, bill2.getSum());
    }

    @Test
    public void calculateParkingPriceM3MondayTest() {
        LocalDateTime startTime3 = LocalDateTime.of(2020, 11, 16, 18, 00, 00);
        LocalDateTime endTime3 = LocalDateTime.of(2020, 11, 16, 19, 05, 00);
        ParkingBillSC bill3 = pps.calculateParkingPrice("M3", 121, "monday", 12, startTime3, endTime3);
        assertEquals(195, bill3.getSum());

    }


}

package com.rek.MoonPark;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingPriceServiceTest {

    private ParkingPriceService pps = new ParkingPriceService();

    @Test
    public void calculateParkingPriceM1Test() {

        ParkingBillM1 bill1 = pps.calculateParkingPriceM1(61);
        assertEquals(120, bill1.getSum());

        ParkingBillM1 bill2 = pps.calculateParkingPriceM1( 120);
        assertEquals(120, bill2.getSum());

        ParkingBillM1 bill3 = pps.calculateParkingPriceM1(181);
        assertEquals(240, bill3.getSum());
    }

    @Test
    public void calculateParkingPriceM2WeekTest() {
        ParkingBillM2 bill1 = pps.calculateParkingPriceM2(180, "monday");
        assertEquals(300, bill1.getSum());

        ParkingBillM2 bill2 = pps.calculateParkingPriceM2( 121, "tuesday");
        assertEquals(300, bill2.getSum());

        ParkingBillM2 bill3 = pps.calculateParkingPriceM2(181, "wednesday");
        assertEquals(400, bill3.getSum());
    }

    @Test
    public void calculateParkingPriceM2WeekEndTest() {

        ParkingBillM2 bill1 = pps.calculateParkingPriceM2(119, "saturday");
        assertEquals(400, bill1.getSum());

        ParkingBillM2 bill2 = pps.calculateParkingPriceM2(120, "sunday");
        assertEquals(400, bill2.getSum());

        ParkingBillM2 bill3 = pps.calculateParkingPriceM2(121, "saturday");
        assertEquals(600, bill3.getSum());

    }

    @Test
    public void calculateParkingPriceM3PassingMidnightTest() {
        LocalDateTime start = LocalDateTime.of(2020, 11, 1, 0, 0, 00);
        LocalDateTime end = LocalDateTime.of(2020, 11, 16, 0, 5, 00);
        int diff = (int) Duration.between(start, end).toMinutes();
        assertEquals(21605, diff);
        assertEquals(5, pps.calculateMinutesAfterFullWeekDaysRemoved(start, end, diff));
        assertEquals(49920, pps.findPriceForFullDaysAndWeeks(21605 - 5));

        LocalDateTime newEnd = LocalDateTime.of(2020, 11, 1, 0, 5, 00);
        assertEquals(newEnd, start.plusMinutes(5));

        LocalDateTime start1 = LocalDateTime.of(2020, 11, 1, 22, 0, 00);
        LocalDateTime newEnd1 = LocalDateTime.of(2020, 11, 2, 2, 5, 00);
        assertEquals(245, Duration.between(start1, newEnd1).toMinutes());
    }

    @Test
    public void calculateParkingPriceM3WeekDayToWeekDayTest() {
        LocalDateTime start = LocalDateTime.of(2020, 11, 11, 22, 00, 00);
        LocalDateTime end = LocalDateTime.of(2020, 11, 12, 2, 00, 00);
        ParkingBillSC price = pps.calculateParkingPriceM3(start, end);
        assertEquals(720, price.getSum());
    }

    @Test
    public void calculateParkingPriceM3SaturdayToSundayTest() {
        LocalDateTime start = LocalDateTime.of(2020, 11, 14, 22, 00, 00);
        LocalDateTime end = LocalDateTime.of(2020, 11, 15, 2, 00, 00);
        ParkingBillSC price = pps.calculateParkingPriceM3(start, end);
        assertEquals(360, price.getSum());
    }

    @Test
    public void calculateParkingPriceM3SundayToMondayTest() {
        LocalDateTime start = LocalDateTime.of(2020, 11, 15, 22, 00, 00);
        LocalDateTime end = LocalDateTime.of(2020, 11, 16, 2, 00, 00);
        ParkingBillSC price = pps.calculateParkingPriceM3(start, end);
        assertEquals(360, price.getSum());
    }

    @Test
    public void calculateParkingMoreThan24HoursTest() {
        LocalDateTime start = LocalDateTime.of(2020, 11, 11, 0, 00, 00);
        LocalDateTime end = LocalDateTime.of(2020, 11, 12, 23, 59, 00);
        ParkingBillSC price = pps.calculateParkingPriceM3(start, end);
        assertEquals(7677, price.getSum());
    }

    @Test
    public void calculateParkingMoreThan1WeekTest() {
        LocalDateTime start = LocalDateTime.of(2020, 11, 1, 0, 00, 00);
        LocalDateTime end = LocalDateTime.of(2020, 11, 18, 10, 5, 00);
        ParkingBillSC price = pps.calculateParkingPriceM3(start, end);
        long totalMinutesParked = Duration.between(start, end).toMinutes();

        assertEquals(1440, pps.findMinutesInFullWeekDay());
        assertEquals(10080, pps.findMinutesInFullWeek());
        assertEquals(3840, pps.findPriceInFullWeekDay());
        assertEquals(23040, pps.findPriceInFullWeek());
        assertEquals(25085, totalMinutesParked);
        assertEquals(605, price.getTotalMinParked());
        assertEquals(24480, totalMinutesParked-price.getTotalMinParked());

        assertEquals(57600, pps.findPriceForFullDaysAndWeeks(24480));
        assertEquals(57600, price.getSum());
    }

    @Test
    public void calculateParkingFixTest() {
        LocalDateTime start = LocalDateTime.of(2020, 11, 11, 0, 00, 00);
        LocalDateTime end = LocalDateTime.of(2020, 11, 12, 23, 55, 00);
        ParkingBillSC price = pps.calculateParkingPriceM3(start, end);
        ParkingBillM3 t = new ParkingBillM3(start, end);
        long totalMinutesParked = Duration.between(start, end).toMinutes();
        assertEquals(end, t.getEndTime());
    }
}
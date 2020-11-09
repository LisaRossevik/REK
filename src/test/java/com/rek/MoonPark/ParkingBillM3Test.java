package com.rek.MoonPark;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingBillM3Test {

    @Test
    public void dayOfWeekTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 10, 7, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 10, 15, 00, 00);

        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(true, pbw.isMorningTime(startTime));
        assertEquals(false, pbw.isSunday(startTime));
        assertEquals(false, pbw.isDayTime(startTime));
        assertEquals(false, pbw.isEveningTime(startTime));

        assertEquals(false, pbw.isMorningTime(endTime));
        assertEquals(false, pbw.isSunday(endTime));
        assertEquals(true, pbw.isDayTime(endTime));
        assertEquals(false, pbw.isEveningTime(endTime));

        assertEquals("2020-11-10T00:00", pbw.getMidnight().toString());
        assertEquals("2020-11-10T08:00", pbw.getDayStart().toString());
        assertEquals("2020-11-10T16:00", pbw.getEveningStart().toString());

        assertEquals(60, pbw.calculateMinutesParkedMorning());
        assertEquals(420, pbw.calculateMinutesParkedDay());
        assertEquals(0, pbw.calculateMinutesParkedEvening());
        assertEquals(1020, pbw.getSum());
    }

    @Test
    public void fullSundayTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 15, 6, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 15, 18, 00, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);

        assertEquals(true, pbw.isSunday(startTime));
        assertEquals(0, pbw.getSum());
    }

    @Test
    public void sundayToMondayTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 15, 22, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 16, 2, 00, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(360, pbw.getSum());
    }

    @Test
    public void saturdayToSundayTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 14, 22, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 15, 02, 00, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(360, pbw.getSum());
    }

    @Test
    public void fridayEveningToSaturdayMorningTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 13, 22, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 14, 02, 00, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(360*2, pbw.getSum());
    }

  /*  @Test
    public void fridayEveningToSaturdayDayTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 13, 23, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 14, 9, 00, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(1740, pbw.getSum());
    }*/

 /*   @Test
    public void fridayEveningToSaturdayEveningTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 13, 23, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 14, 17, 00, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(1800+960, pbw.getSum());
    }*/

    @Test
    public void fridayEveningTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 13, 18, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 13, 20, 00, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(360, pbw.getSum());
    }

    @Test
    public void fridayDayTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 13, 12, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 13, 14, 00, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(120, pbw.getSum());
    }

    @Test
    public void sundayTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 15, 13, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 15, 15, 00, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(0, pbw.getSum());
    }

    @Test
    public void fullDayTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 11, 8, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 11, 16, 00, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(840, pbw.getSum());
    }

    @Test
    public void full24HTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 11, 0, 01, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 11, 23, 59, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(3834, pbw.getSum());
    }

 /*   @Test
    public void longParkingTest() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 5, 0, 01, 00);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 15, 23, 59, 00);
        ParkingBillM3 pbw = new ParkingBillM3(startTime, endTime);
        assertEquals(3834, pbw.getSum());
    }*/

}

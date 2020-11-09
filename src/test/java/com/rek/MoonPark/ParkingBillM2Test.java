package com.rek.MoonPark;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingBillM2Test {

    @Test
    public void parkingPriceM2FullHoursTest() {
        ParkingBillM2 pbw = new ParkingBillM2(3 * 60, "friday");
        assertEquals(300, pbw.getSum());

        ParkingBillM2 pbwe = new ParkingBillM2(3 * 60, "saturday");
        assertEquals(600, pbwe.getSum());
    }

    @Test
    public void parkingPriceM2LessThanFullHoursTest() {
        ParkingBillM2 pbw = new ParkingBillM2((3 * 60) - 1, "monday");
        assertEquals(300, pbw.getSum());

        ParkingBillM2 pbwe = new ParkingBillM2((3 * 60) - 1, "sunday");
        assertEquals(600, pbwe.getSum());
    }

    @Test
    public void parkingPriceM2MoreThanFullHoursTest() {
        ParkingBillM2 pbw = new ParkingBillM2((3 * 60) + 1, "tuesday");
        assertEquals(400, pbw.getSum());

        ParkingBillM2 pbwe = new ParkingBillM2((3 * 60) + 1, "sunday");
        assertEquals(800, pbwe.getSum());
    }

    @Test
    public void parkingPriceM2ZeroHoursTest() {
        ParkingBillM2 pbw = new ParkingBillM2(0, "sunday");
        assertEquals(0, pbw.getSum());

        ParkingBillM2 pbwe = new ParkingBillM2(0, "wednesday");
        assertEquals(0, pbwe.getSum());
    }
}

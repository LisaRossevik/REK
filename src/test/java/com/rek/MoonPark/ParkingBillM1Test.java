package com.rek.MoonPark;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingBillM1Test {

    @Test
    public void parkingPriceM1FullHourTest() {
        ParkingBillM1 pb = new ParkingBillM1(3 * 60);
        assertEquals(180, pb.getSum());
    }

    @Test
    public void parkingPriceM1LittleMoreThanFullHourTest() {
        ParkingBillM1 pb = new ParkingBillM1((3 * 60) + 1);
        assertEquals(240, pb.getSum());
    }

    @Test
    public void parkingPriceM1LittleLessThanFullHourTest() {
        ParkingBillM1 pb = new ParkingBillM1((3 * 60) - 1);
        assertEquals(180, pb.getSum());
    }

    @Test
    public void parkingPriceM1InputZero() {
        ParkingBillM1 pb = new ParkingBillM1(0);
        assertEquals(0, pb.getSum());
    }

}

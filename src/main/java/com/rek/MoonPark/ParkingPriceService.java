package com.rek.MoonPark;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class ParkingPriceService {
    public int oneWeekInMin = 60 * 24 * 7;
    public int oneDayInMin = 60 * 24;
    /*
    /* Må implementeres og testes :
     - Teste søndags timene
     - Teste parkering over flere uker/dager
     * * */

    public ParkingBillM1 calculateParkingPriceM1(int totalMinParked) {
        ParkingBillM1 pbm1 = new ParkingBillM1(totalMinParked);
        pbm1.setParkingFeePrHour(pbm1.getParkingFeePrHour());
        pbm1.setSum(pbm1.getSum());
        return pbm1;
    }

    public ParkingBillM2 calculateParkingPriceM2(int totalMinParked, String weekDay) {
        ParkingBillM2 pbm2 = new ParkingBillM2(totalMinParked, weekDay);
        pbm2.setSum(pbm2.getSum());
        return pbm2;
    }

    public ParkingBillM3 calculateParkingPriceM3(LocalDateTime start, LocalDateTime end) {

        long totalMinutesParked = Duration.between(start, end).toMinutes();
        System.out.println("totalMinutesParked = " + totalMinutesParked);
        long minAfterFullDaysWeeksRemoved = calculateMinutesAfterFullWeekDaysRemoved(start, end, totalMinutesParked);
        long price = findPriceForFullDaysAndWeeks(totalMinutesParked - minAfterFullDaysWeeksRemoved);
        LocalDateTime newEndDate = start.plusMinutes(minAfterFullDaysWeeksRemoved);

        if (start.getDayOfWeek() == newEndDate.getDayOfWeek()) {
            ParkingBillM3 pbm3 = new ParkingBillM3(start, newEndDate);
            pbm3.setTotalMinParked(totalMinutesParked);
            pbm3.setSum(pbm3.getSum() + (int) price);
            return pbm3;
        } else {
            // Find total duration and remove full weeks and days
            LocalDateTime midnight = start.with(ChronoField.HOUR_OF_DAY, 0).plusDays(1);
            // calculate before midninght
            ParkingBillM3 pbBeforeMidnigth = new ParkingBillM3(start, midnight);
            int sum1 = pbBeforeMidnigth.getSum();

            // Calculate after midnight
            ParkingBillM3 pbAfterMidnight = new ParkingBillM3(midnight, newEndDate);
            int sum2 = pbAfterMidnight.getSum();

            // Create object containing values from the two pervious
            ParkingBillM3 pbTotal = new ParkingBillM3(start, newEndDate);
            pbTotal.setEndTime(end);
            pbTotal.setTotalMinParked(pbBeforeMidnigth.getTotalMinParked() + pbAfterMidnight.getTotalMinParked());
            pbTotal.setMinSunday(pbBeforeMidnigth.getMinSunday() + pbAfterMidnight.getMinSunday());
            pbTotal.setMinWeekMorning(pbBeforeMidnigth.getMinWeekMorning() + pbAfterMidnight.getMinWeekMorning());
            pbTotal.setMinWeekDay(pbBeforeMidnigth.getMinWeekDay() + pbAfterMidnight.getMinWeekDay());
            pbTotal.setMinWeekEvening(pbBeforeMidnigth.getMinWeekEvening() + pbAfterMidnight.getMinWeekEvening());
            pbTotal.setSum(pbBeforeMidnigth.getSum() + pbAfterMidnight.getSum() + (int) price);
            return pbTotal;
        }
    }

    public long calculateMinutesAfterFullWeekDaysRemoved(LocalDateTime start, LocalDateTime end, long totalMinutesParked) {
        //(60*24*7*2) +(60*24*3) + (60*10) + 5         //int totalMinParked = 25085;
        //System.out.println("##totalMinutesParked = " + totalMinutesParked);
        long weeks = totalMinutesParked / oneWeekInMin;
        //System.out.println("##weeks = " + weeks);
        long restMinutes = totalMinutesParked % oneWeekInMin;
        //System.out.println("##restMinutes = " + restMinutes);
        long days = restMinutes / oneDayInMin;
        //System.out.println("##days = " + days);

        long minutesAfterFullDaysWeeksRemoved = totalMinutesParked - (weeks * oneWeekInMin + days * oneDayInMin);
        return minutesAfterFullDaysWeeksRemoved;
    }

    public long findPriceForFullDaysAndWeeks(long fullDaysAndWeeksPartInMinutes) {
        long weeks = (fullDaysAndWeeksPartInMinutes / oneWeekInMin);
        long weekPrice = weeks * findPriceInFullWeek();  //23040 *2weeks = 46080
        long restMinutes = (fullDaysAndWeeksPartInMinutes % oneWeekInMin);
        long days = (restMinutes / oneDayInMin);
        long dayPrice = days * findPriceInFullWeekDay(); // 3840*1day = 3840
        System.out.println("fullDaysAndWeeksPartInMinutes =" + fullDaysAndWeeksPartInMinutes);
        System.out.println("weeks =" + weeks);
        System.out.println("weekPrice =" + weekPrice);
        System.out.println("restMinutes =" + restMinutes);
        System.out.println("days =" + days);
        System.out.println("dayPrice =" + dayPrice);
        // 1 day = 3840 min 1, week = 23040
        return (weekPrice + dayPrice); //46080 + 3840 = 49920
    }

    public long findMinutesInFullWeekDay() {
        return 24 * 60;
    }

    public long findMinutesInFullWeek() {
        return (8 * 60 * 7) + (16 * 60 * 7);
    }

    public long findPriceInFullWeek() {
        return (8 * 60 * 6 * 2) + (16 * 60 * 6 * 3);
    }

    public long findPriceInFullWeekDay() {
        return (8 * 60 * 2) + (16 * 60 * 3);
    }
}
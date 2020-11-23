package com.rek.MoonPark;

import com.rek.MoonPark.model.ParkingBillM1;
import com.rek.MoonPark.model.ParkingBillM2;
import com.rek.MoonPark.model.ParkingBillM3;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

@Service
public class ParkingPriceService {
    private int oneWeekInMin = 60 * 24 * 7;
    private int oneDayInMin = 60 * 24;
    private long fullWeeks;
    private long full24Hours;
    private long minutesAfterFullDaysWeeksRemoved;

    public int getOneWeekInMin() {
        return oneWeekInMin;
    }

    public int getOneDayInMin() {
        return oneDayInMin;
    }

    public long getFullWeeks() {
        return fullWeeks;
    }

    public long getFull24Hours() {
        return full24Hours;
    }

    public long getMinutesAfterFullDaysWeeksRemoved() {
        return minutesAfterFullDaysWeeksRemoved;
    }

    public void setFullWeeks(long fullWeeks) {
        this.fullWeeks = fullWeeks;
    }

    public void setFull24Hours(long full24Hours) {
        this.full24Hours = full24Hours;
    }

    public void setMinutesAfterFullDaysWeeksRemoved(long minutesAfterFullDaysWeeksRemoved) {
        this.minutesAfterFullDaysWeeksRemoved = minutesAfterFullDaysWeeksRemoved;
    }

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
        calculateMinutesAfterFullWeekDaysRemoved(start, end, totalMinutesParked);
        long price = findPriceForFullDaysAndWeeks(totalMinutesParked - getMinutesAfterFullDaysWeeksRemoved(), start, end);
        LocalDateTime newEndDate = start.plusMinutes(getMinutesAfterFullDaysWeeksRemoved());

        if (start.getDayOfWeek() == newEndDate.getDayOfWeek()) {
            ParkingBillM3 pbm3 = new ParkingBillM3(start, newEndDate);
            pbm3.setTotalMinParked(totalMinutesParked);
            pbm3.setSum(pbm3.getSum() + (int) price);
            pbm3.setFullWeeks(getFullWeeks());
            pbm3.setFull24Hours(getFull24Hours());
            return pbm3;
        } else {
            // Passes midnight
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
            pbTotal.setFullWeeks(getFullWeeks());
            pbTotal.setFull24Hours(getFull24Hours());
            pbTotal.setSum(pbBeforeMidnigth.getSum() + pbAfterMidnight.getSum() + (int) price);
            return pbTotal;
        }
    }

    public void calculateMinutesAfterFullWeekDaysRemoved(LocalDateTime start, LocalDateTime end, long totalMinutesParked) {
        //(60*24*7*2) +(60*24*3) + (60*10) + 5         //int totalMinParked = 25085;
        //System.out.println("##totalMinutesParked = " + totalMinutesParked);
        long weeks = totalMinutesParked / oneWeekInMin;
        setFullWeeks(weeks);
        System.out.println("##weeks = " + weeks + " fullWeeks = "+fullWeeks);
        long restMinutes = totalMinutesParked % oneWeekInMin;
        //System.out.println("##restMinutes = " + restMinutes);
        long days = restMinutes / oneDayInMin;
        setFull24Hours(days);
        System.out.println("##days = " + days + " full24Hours = "+full24Hours);
        setMinutesAfterFullDaysWeeksRemoved(totalMinutesParked - (weeks * oneWeekInMin + days * oneDayInMin));
    }

    public long findPriceForFullDaysAndWeeks(long fullDaysAndWeeksPartInMinutes, LocalDateTime start, LocalDateTime end) {
        long weeks = (fullDaysAndWeeksPartInMinutes / oneWeekInMin);
        long weekPrice = weeks * findPriceInFullWeek();  //23040 *2weeks = 46080
        long restMinutes = (fullDaysAndWeeksPartInMinutes % oneWeekInMin);
        long days = (restMinutes / oneDayInMin);
        long daysWithoutSundays = removeSundays(start, days);
        long dayPrice = daysWithoutSundays * findPriceInFullWeekDay(); // 3840*1day = 3840
        System.out.println("fullDaysAndWeeksPartInMinutes =" + fullDaysAndWeeksPartInMinutes);
        System.out.println("weeks =" + weeks);
        System.out.println("weekPrice =" + weekPrice);
        System.out.println("restMinutes =" + restMinutes);
        System.out.println("days =" + days);
        System.out.println("daysWithoutSundays =" + daysWithoutSundays);
        System.out.println("dayPrice =" + dayPrice);
        // 1 day = 3840 min 1, week = 23040
        return (weekPrice + dayPrice); //46080 + 3840 = 49920
    }

    public long removeSundays(LocalDateTime start, long days) {
        LocalDateTime end = start.plusDays(days);
        long newDays = days;
        if (days >= 2 && (start.getDayOfWeek() == DayOfWeek.SUNDAY || start.plusDays(1).getDayOfWeek() == DayOfWeek.SUNDAY || start.plusDays(2).getDayOfWeek() == DayOfWeek.SUNDAY ||
                start.plusDays(3).getDayOfWeek() == DayOfWeek.SUNDAY || start.plusDays(4).getDayOfWeek() == DayOfWeek.SUNDAY ||
                start.plusDays(5).getDayOfWeek() == DayOfWeek.SUNDAY || start.plusDays(6).getDayOfWeek() == DayOfWeek.SUNDAY)) {
            newDays = days - 1;
        }
        return newDays;
    }

    public long findMinutesInFullWeekDay() {
        return 24 * 60;
    }

    public long findMinutesInFullWeek() {
        return (8 * 60 * 6) + (16 * 60 * 6);
    }

    public long findPriceInFullWeek() {
        return (8 * 60 * 6 * 2) + (16 * 60 * 6 * 3);
    }

    public long findPriceInFullWeekDay() {
        return (8 * 60 * 2) + (16 * 60 * 3);
    }
}
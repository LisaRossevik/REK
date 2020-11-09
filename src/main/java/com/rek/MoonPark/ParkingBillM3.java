package com.rek.MoonPark;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class ParkingBillM3 extends ParkingBillSC {

    private int weekDayFee = 2;
    private int weekEveningFee = 3;
    private long totalMinParked;
    private String weekDay;
    private long minWeekEvening;
    private long minWeekDay;
    private long minWeekMorning;
    private long minSunday;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime midnight;
    private LocalDateTime dayStart;
    private LocalDateTime eveningStart;
    private int sum;

    public ParkingBillM3(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalMinParked = Duration.between(startTime, endTime).toMinutes();
        this.weekDay = startTime.getDayOfWeek().toString().toLowerCase();
        this.midnight = startTime.with(ChronoField.HOUR_OF_DAY, 0);
        this.dayStart = startTime.with(ChronoField.HOUR_OF_DAY, 8);
        this.eveningStart = startTime.with(ChronoField.HOUR_OF_DAY, 16);
        this.minWeekMorning = calculateMinutesParkedMorning();
        this.minWeekDay = calculateMinutesParkedDay();
        this.minWeekEvening = calculateMinutesParkedEvening();
        this.minSunday = calculateMinutesParkedSunday();
        this.sum = claculateTotalPrice();
    }

    public long getTotalMinParked() {
        return totalMinParked;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getMidnight() {
        return midnight;
    }

    public LocalDateTime getDayStart() {
        return dayStart;
    }

    public LocalDateTime getEveningStart() {
        return eveningStart;
    }

    public long getMinWeekEvening() {
        return minWeekEvening;
    }

    public void setMinWeekEvening(long minWeekEvening) {
        this.minWeekEvening = minWeekEvening;
    }

    public long getMinWeekDay() {
        return minWeekDay;
    }

    public void setMinWeekDay(long minWeekDay) {
        this.minWeekDay = minWeekDay;
    }

    public long getMinWeekMorning() {
        return minWeekMorning;
    }

    public void setMinWeekMorning(long minWeekMorning) {
        this.minWeekMorning = minWeekMorning;
    }

    public long getMinSunday() {
        return minSunday;
    }

    public void setMinSunday(long minSunday) {
        this.minSunday = minSunday;
    }

    public boolean isSunday(LocalDateTime startOrEnd) {
        return startOrEnd.getDayOfWeek() == DayOfWeek.SUNDAY ? true : false;
    }

    public boolean isMorningTime(LocalDateTime startOrEnd) {
        return startOrEnd.getHour() >= 0 && startOrEnd.getHour() <= 8 ? true : false;
    }

    public Boolean isDayTime(LocalDateTime startOrEnd) {
        return startOrEnd.getHour() >= 8 && startOrEnd.getHour() <= 16 ? true : false;
    }

    public boolean isEveningTime(LocalDateTime startOrEnd) {
        return startOrEnd.getHour() >= 16 && startOrEnd.getHour() <= 24 ? true : false;
    }

    public long calculateMinutesAfterFullWeekDaysRemoved() {
        //(60*24*7*2) +(60*24*3) + (60*10) + 5         //int totalMinParked = 25085;
        int oneWeekInMin = 60 * 24 * 7;
        int oneDayInMin = 60 * 24;
        //System.out.println("##totalTotalMinParked = " + getTotalMinParked());
        long weeks = getTotalMinParked() / oneWeekInMin;
        //System.out.println("##weeks = " + weeks);
        long restMinutes = getTotalMinParked() % oneWeekInMin;
        //System.out.println("##restMinutes = " + restMinutes);
        long days = restMinutes / oneDayInMin;
        //System.out.println("##days = " + days);

        long minutesAfterFullDaysWeeksRemoved = getTotalMinParked() - (weeks * oneWeekInMin + days * oneDayInMin);
        return minutesAfterFullDaysWeeksRemoved;
    }

    public long calculateMinutesParkedMorning() {
        long morningMin = 0;
        long period = Duration.between(getStartTime(), getEndTime()).toMinutes();

        if (getDayStart().getDayOfWeek() != DayOfWeek.SUNDAY) {
            if (isMorningTime(getStartTime()) && (getEndTime().isAfter(getDayStart()) || getEndTime().isEqual(getDayStart()))) {
                morningMin = Duration.between(getStartTime(), getDayStart()).toMinutes();
            }
            if (isMorningTime(getEndTime()) && (getStartTime().isBefore(getMidnight().plusDays(1)) || getStartTime().isEqual(getMidnight().plusDays(1)))) {
                morningMin = Duration.between(getMidnight().plusDays(1), getEndTime()).toMinutes();
            }
            if ((getStartTime().isBefore(getMidnight()) || getStartTime().isEqual(getMidnight())) && (getEndTime().isAfter(getDayStart()) || getEndTime().isEqual(getDayStart()))) {
                morningMin = Duration.between(getMidnight(), getDayStart()).toMinutes();
            }
            if (isMorningTime(getStartTime()) && isMorningTime(getEndTime()) && period < 480) {
                morningMin = Duration.between(getStartTime(), getEndTime()).toMinutes();
            }
            if (isMorningTime(getStartTime()) && isMorningTime(getEndTime()) && period > 480) {
                morningMin = Duration.between(getMidnight(), getEndTime()).toMinutes();
            }
        } else if (getEndTime().isAfter(getMidnight().plusDays(1))) {
            if (isMorningTime(getEndTime())) {
                morningMin = Duration.between(getMidnight().plusDays(1), getEndTime()).toMinutes();
            }
            if (getEndTime().isAfter(getDayStart().plusDays(1))) {
                morningMin = Duration.between(getMidnight().plusDays(1), getDayStart().plusDays(1)).toMinutes();
            }
        } else {
            morningMin = 0;
        }
        return morningMin;
    }

    public long calculateMinutesParkedDay() {
        long dayMin = 0;
        long period = Duration.between(getStartTime(), getEndTime()).toMinutes();

        if (getDayStart().getDayOfWeek() != DayOfWeek.SUNDAY) {
            if ((getStartTime().isBefore(getDayStart()) || getStartTime().isEqual(getDayStart())) && isDayTime(getEndTime())) {
                dayMin = Duration.between(getDayStart(), getEndTime()).toMinutes();
            }
            if ((getStartTime().isBefore(getDayStart()) || getStartTime().isEqual(getDayStart())) && (getEndTime().isAfter(getEveningStart()) || getEndTime().isEqual(getEveningStart()))) {
                dayMin = Duration.between(getDayStart(), getEveningStart()).toMinutes();
            }
            if (isDayTime(getStartTime()) && isDayTime(getEndTime()) && period < 480) {
                long dayMinBeforeFreeHour = Duration.between(getStartTime(), getEndTime()).toMinutes();
                if (dayMinBeforeFreeHour >= 60) {
                    dayMin = dayMinBeforeFreeHour - 60;
                } else {
                    dayMin = 0;
                }
            }
            if (isDayTime(getStartTime()) && isDayTime(getEndTime()) && period > 480) {
                dayMin = Duration.between(getDayStart(), getEndTime()).toMinutes();
            }
            if (isDayTime(getStartTime()) && (getEndTime().isAfter(getEveningStart()) || getEndTime().isEqual(getEveningStart()))) {
                long dayMinBeforeFreeHour = Duration.between(getDayStart(), getEveningStart()).toMinutes();
                if (dayMinBeforeFreeHour >= 60) {
                    dayMin = dayMinBeforeFreeHour - 60;
                } else {
                    dayMin = 0;
                }
            }
        } else if (getEndTime().isAfter(getDayStart().plusDays(1))) {
            if (isDayTime(getEndTime())) {
                dayMin = Duration.between(getDayStart().plusDays(1), getEndTime()).toMinutes();
            }
            if (getEndTime().isAfter(getEveningStart().plusDays(1))) {
                dayMin = Duration.between(getDayStart().plusDays(1), getEveningStart().plusDays(1)).toMinutes();
            }
        } else {
            dayMin = 0;
        }
        return dayMin;
       /* }
        //System.out.println("dayMin = " + getMinWeekDay());
        if (getDayStart().getDayOfWeek() == DayOfWeek.SUNDAY) {
            return 0;
        } else {
            return dayMin;
        }
        //return getDayStart().getDayOfWeek() == DayOfWeek.SUNDAY ? 0 : dayMin;*/
    }

    public long calculateMinutesParkedEvening() {
        long eveningMin = 0;
        long period = Duration.between(getStartTime(), getEndTime()).toMinutes();

        if (isEveningTime(getStartTime()) && (getEndTime().isAfter(getEveningStart().plusHours(8)) || getEndTime().isEqual(getEveningStart().plusHours(8)))) {
            eveningMin = Duration.between(getStartTime(), getEveningStart().plusHours(8)).toMinutes();
        }
        if ((getStartTime().isBefore(getEveningStart()) || getStartTime().isEqual((getEveningStart()))) && isEveningTime(getEndTime())) {
            eveningMin = Duration.between(getEveningStart(), getEndTime()).toMinutes();
        }
        if ((getStartTime().isBefore(getEveningStart()) || getStartTime().isEqual(getEveningStart())) && (getEndTime().isAfter(getEveningStart().plusHours(8)) || getEndTime().isEqual(getEveningStart().plusHours(8)))) {
            eveningMin = Duration.between(getEveningStart(), getEveningStart().plusHours(8)).toMinutes();
        }
        if (isEveningTime(getStartTime()) && isEveningTime(getEndTime()) && period < 480) {
            eveningMin = Duration.between(getStartTime(), getEndTime()).toMinutes();
        }
        if (isEveningTime(getStartTime()) && isEveningTime(getEndTime()) && period > 480) {
            eveningMin = Duration.between(getEveningStart().plusDays(1), getEndTime()).toMinutes();
        }
        if (isEveningTime(getStartTime()) && (getEndTime().isAfter(getMidnight().plusDays(1)) || getEndTime().isEqual((getMidnight().plusDays(1))))) {
            eveningMin = Duration.between(getStartTime(), getMidnight().plusDays(1)).toMinutes();
        }


        //setMinWeekMorning(eveningMin);
        //System.out.println("eveningMin = " + getMinWeekEvening());
        //System.out.println("getEveningStart().getDayOfWeek() = " + getEveningStart().getDayOfWeek());
        if (getEveningStart().getDayOfWeek() == DayOfWeek.SUNDAY) {
            return 0;
        } else {
            return eveningMin;
        }
    }

    public long calculateMinutesParkedSunday() {
        long sundayMin = 0;
        //long period = Duration.between(getStartTime(), getEndTime()).toMinutes();

        if (isSunday(getStartTime()) && !isSunday(getEndTime())) {
            sundayMin = Duration.between(getStartTime(), getMidnight()).toMinutes();
        }
        if (!isSunday(getStartTime()) && isSunday(getEndTime())) {
            sundayMin = Duration.between(getMidnight(), getEndTime()).toMinutes() - (24 * 60);
        }
        if (isSunday(getStartTime()) && isSunday(getEndTime())) {
            sundayMin = Duration.between(getStartTime(), getEndTime()).toMinutes();
        }
        return sundayMin;
    }

    @Override
    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }

    public int claculateTotalPrice() {
        long minutesLessThan24Hours = calculateMinutesAfterFullWeekDaysRemoved();
        LocalDateTime newEndTime = getStartTime().plusMinutes(minutesLessThan24Hours);
        setEndTime(newEndTime);
        // long period = Duration.between(getStartTime(), getEndTime()).toMinutes();
        if (isSunday(getStartTime()) && isSunday(getEndTime())) {
            sum = 0;
        } else if (!isSunday(getStartTime()) && isSunday(getEndTime())) {
            setEndTime(getEndTime().minusMinutes(getMinSunday()));
            System.out.println("#### getEndTime ()" + getEndTime());
            long m = calculateMinutesParkedMorning();
            long d = calculateMinutesParkedDay();
            long e = calculateMinutesParkedEvening();
            sum = (int) ((m + e) * weekEveningFee + d * weekDayFee);
            //  sum = (int) ((getMinWeekMorning() + getMinWeekEvening()) * weekEveningFee + getMinWeekDay() * weekDayFee);
        } else if (isSunday(getStartTime()) && !isSunday(getEndTime())) {
            setStartTime(getStartTime().plusMinutes(getMinSunday()));
            calculateMinutesParkedMorning();
            calculateMinutesParkedDay();
            calculateMinutesParkedEvening();
            sum = (int) ((getMinWeekMorning() + getMinWeekEvening()) * weekEveningFee + getMinWeekDay() * weekDayFee);
        } else if (!isSunday(getStartTime()) && !isSunday(getEndTime())) {
            sum = (int) ((getMinWeekMorning() + getMinWeekEvening()) * weekEveningFee + getMinWeekDay() * weekDayFee);
        }
        return sum;
    }
}

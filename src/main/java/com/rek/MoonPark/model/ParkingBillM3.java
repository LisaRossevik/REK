package com.rek.MoonPark.model;

import com.rek.MoonPark.ParkingBillSC;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

@Entity
public class ParkingBillM3 extends ParkingBillSC {

    @Id
    @GeneratedValue
    private long m3Id;
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
    private long fullWeeks;
    private long full24Hours;

    public ParkingBillM3(){}

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

    public void setTotalMinParked(long totalMinParked) {
        this.totalMinParked = totalMinParked;
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

    public long getFullWeeks() {
        return fullWeeks;
    }

    public void setFullWeeks(long fullWeeks) {
        this.fullWeeks = fullWeeks;
    }

    public long getFull24Hours() {
        return full24Hours;
    }

    public void setFull24Hours(long full24Hours) {
        this.full24Hours = full24Hours;
    }

    public long getM3Id() {
        return m3Id;
    }

    public void setM3Id(long m3Id) {
        this.m3Id = m3Id;
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

    public long calculateMinutesParkedMorning() {
        long morningMin = 0;
        long period = Duration.between(getStartTime(), getEndTime()).toMinutes();

        if (getDayStart().getDayOfWeek() != DayOfWeek.SUNDAY) {
            if (isMorningTime(getStartTime()) && (getEndTime().isAfter(getDayStart()) || getEndTime().isEqual(getDayStart()))) {
                morningMin = Duration.between(getStartTime(), getDayStart()).toMinutes();
            }
            if (isMorningTime(getEndTime()) && getStartTime().isEqual(getMidnight().plusDays(1))) {
                morningMin = Duration.between(getMidnight().plusDays(1), getEndTime()).toMinutes();
            }
            if (getStartTime().isEqual(getMidnight()) && (getEndTime().isAfter(getDayStart()) || getEndTime().isEqual(getDayStart()))) {
                morningMin = Duration.between(getMidnight(), getDayStart()).toMinutes();
            }
            if (isMorningTime(getStartTime()) && isMorningTime(getEndTime()) && period < 480) {
                morningMin = Duration.between(getStartTime(), getEndTime()).toMinutes();
            }
            return morningMin;
        } else {
            return 0;
        }
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
            if (isDayTime(getStartTime()) && (getEndTime().isAfter(getEveningStart()) || getEndTime().isEqual(getEveningStart()))) {
                long dayMinBeforeFreeHour = Duration.between(getStartTime(), getEveningStart()).toMinutes();
                if (dayMinBeforeFreeHour >= 60) {
                    dayMin = dayMinBeforeFreeHour - 60;
                } else {
                    dayMin = 0;
                }
            }
            return dayMin;
        } else {
            return 0;
        }
    }

    public long calculateMinutesParkedEvening() {
        long eveningMin = 0;
        long period = Duration.between(getStartTime(), getEndTime()).toMinutes();

        if (getDayStart().getDayOfWeek() != DayOfWeek.SUNDAY) {
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
            return eveningMin;
        } else {
            return 0;
        }
    }

    public long calculateMinutesParkedSunday() {
        long sundayMin = 0;

        if (isSunday(getStartTime()) || getEndTime().isEqual(getEveningStart().plusHours(8))) {
            sundayMin = Duration.between(getStartTime(), getEveningStart().plusHours(8)).toMinutes();
        }
        if (!isSunday(getStartTime()) && isSunday(getEndTime())) {
            sundayMin = Duration.between(getStartTime(), getEndTime()).toMinutes();
        }
        if (isSunday(getStartTime()) && isSunday(getEndTime())) {
            sundayMin = Duration.between(getStartTime(), getEndTime()).toMinutes();
        }
        return sundayMin;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }

    public int claculateTotalPrice() {
        if ((isSunday(getStartTime()) && getEndTime().isEqual(getEndTime())) || ((isSunday(getEndTime()) && getStartTime().isEqual(getMidnight())))) {
            sum = 0;
        } else {
            sum = (int) (((getMinWeekMorning() + getMinWeekEvening()) * weekEveningFee) + (getMinWeekDay() * weekDayFee));
        }
        return sum;
    }

    @Override
    public String toString() {
        return "ParkingBillM3{" +
                "weekDayFee=" + weekDayFee +
                ", weekEveningFee=" + weekEveningFee +
                ", totalMinParked=" + totalMinParked +
                ", weekDay='" + weekDay + '\'' +
                ", minWeekEvening=" + minWeekEvening +
                ", minWeekDay=" + minWeekDay +
                ", minWeekMorning=" + minWeekMorning +
                ", minSunday=" + minSunday +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", midnight=" + midnight +
                ", dayStart=" + dayStart +
                ", eveningStart=" + eveningStart +
                ", sum=" + sum +
                ", fullWeeks=" + fullWeeks +
                ", full24Hours=" + full24Hours +
                '}';
    }
}

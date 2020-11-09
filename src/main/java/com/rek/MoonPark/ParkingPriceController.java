package com.rek.MoonPark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ParkingPriceController {

    private ParkingPriceService pps = new ParkingPriceService();

    @GetMapping("/takst")
    public ParkingBillSC findParkingPrice(@RequestParam(value = "zone", defaultValue = "M1", required = true) String zone,
                                          @RequestParam(value = "totalMinParked", defaultValue = "0", required = true) int totalMinParked,
                                          @RequestParam(value = "weekDay", defaultValue = "monday", required = false) String weekDay,
                                          @RequestParam(value = "hourOfDay", defaultValue = "5", required = false) int hourOfDay,
                                          @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                          @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        ParkingBillSC pB = pps.calculateParkingPrice(zone, totalMinParked, weekDay.toLowerCase(), hourOfDay, start, end);
        return pB;
    }
}


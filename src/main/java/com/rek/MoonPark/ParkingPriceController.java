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
        ParkingBillM1 pbm1 = null;
        ParkingBillM2 pbm2 = null;
        ParkingBillM3 pbm3 = null;

        switch(zone) {
            case "M1":
                pbm1 = pps.calculateParkingPriceM1(totalMinParked);
                break;
            case "M2":
                pbm2 = pps.calculateParkingPriceM2(totalMinParked, weekDay);
                break;
            case "M3":
                pbm3 = pps.calculateParkingPriceM3(start, end);
                break;
            default:
                System.out.println("Invalid zone given! Must be M1, M2 or M3! ");
        }
        return pbm1 != null ? pbm1: (pbm2 != null ? pbm2: pbm3);
    }
}


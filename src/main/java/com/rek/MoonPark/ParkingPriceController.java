package com.rek.MoonPark;

import com.rek.MoonPark.dao.ParkingBillM1Repo;
import com.rek.MoonPark.dao.ParkingBillM2Repo;
import com.rek.MoonPark.dao.ParkingBillM3Repo;
import com.rek.MoonPark.dao.ParkingBillRepo;
import com.rek.MoonPark.model.ParkingBill;
import com.rek.MoonPark.model.ParkingBillM1;
import com.rek.MoonPark.model.ParkingBillM2;
import com.rek.MoonPark.model.ParkingBillM3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
//@RequestMapping("/takst")
public class ParkingPriceController {

    @Autowired
    ParkingBillM1Repo m1Repo;

    @Autowired
    ParkingBillM2Repo m2Repo;

    @Autowired
    ParkingBillM3Repo m3Repo;

    @Autowired
    ParkingBillRepo allRepo;

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
        ParkingBill pb = null;

        switch (zone) {
            case "M1":
                pbm1 = pps.calculateParkingPriceM1(totalMinParked);
                m1Repo.save(pbm1);
                pb = new ParkingBill(pbm1.getM1Id(), "M1");
                allRepo.save(pb);
                break;
            case "M2":
                pbm2 = pps.calculateParkingPriceM2(totalMinParked, weekDay);
                m2Repo.save(pbm2);
                pb = new ParkingBill(pbm2.getM2Id(), "M2");
                allRepo.save(pb);
                break;
            case "M3":
                pbm3 = pps.calculateParkingPriceM3(start, end);
                m3Repo.save(pbm3);
                pb = new ParkingBill(pbm3.getM3Id(), "M3");
                allRepo.save(pb);
                break;
            default:
                System.out.println("Invalid zone given! Must be M1, M2 or M3! ");
        }
        return pbm1 != null ? pbm1 : (pbm2 != null ? pbm2 : pbm3);
    }

    @GetMapping("/M1")
    public ResponseEntity<List<ParkingBillM1>> getAllParkingBillM1s () {
        try {
            List<ParkingBillM1> m1s = new ArrayList<ParkingBillM1>();
            m1Repo.findAll().forEach(m1s::add);

            if (m1s.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(m1s, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/M1/{id}")
    public ResponseEntity<ParkingBillM1> getM1ById(@PathVariable("id") long id) {
        Optional<ParkingBillM1> ParkingBillM1Data = m1Repo.findById(id);

        if (ParkingBillM1Data.isPresent()) {
            return new ResponseEntity<>(ParkingBillM1Data.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/M2")
    public ResponseEntity<List<ParkingBillM2>> getAllParkingBillM2s () {
        try {
            List<ParkingBillM2> m2s = new ArrayList<ParkingBillM2>();
            m2Repo.findAll().forEach(m2s::add);

            if (m2s.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(m2s, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/M2/{id}")
    public ResponseEntity<ParkingBillM2> getM2ById(@PathVariable("id") long id) {
        Optional<ParkingBillM2> ParkingBillM2Data = m2Repo.findById(id);

        if (ParkingBillM2Data.isPresent()) {
            return new ResponseEntity<>(ParkingBillM2Data.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/M3")
    public ResponseEntity<List<ParkingBillM3>> getAllParkingBillM3s () {
        try {
            List<ParkingBillM3> m3s = new ArrayList<ParkingBillM3>();
            m3Repo.findAll().forEach(m3s::add);

            if (m3s.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(m3s, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/M3/{id}")
    public ResponseEntity<ParkingBillM3> findM3ById(@PathVariable("id") long id) {
        Optional<ParkingBillM3> ParkingBillM3Data = m3Repo.findById(id);

        if (ParkingBillM3Data.isPresent()) {
            return new ResponseEntity<>(ParkingBillM3Data.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allZones")
    public ResponseEntity<List<ParkingBill>> getAllParkingBillSCs () {
        try {
            List<ParkingBill> allBills = new ArrayList<ParkingBill>();
            allRepo.findAll().forEach(allBills::add);

            if (allBills.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(allBills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allZones/{id}")
    public ResponseEntity<ParkingBill> findById(@PathVariable("id") long id) {
        Optional<com.rek.MoonPark.model.ParkingBill> ParkingBillData = allRepo.findById(id);

        if (ParkingBillData.isPresent()) {
            return new ResponseEntity<>(ParkingBillData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}

package com.rek.MoonPark.dao;


import com.rek.MoonPark.model.ParkingBillM1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingBillM1Repo  extends JpaRepository<ParkingBillM1, Long> {
    Optional<ParkingBillM1> findById(long id);
}
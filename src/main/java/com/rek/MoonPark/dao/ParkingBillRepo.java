package com.rek.MoonPark.dao;

import com.rek.MoonPark.model.ParkingBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingBillRepo extends JpaRepository<ParkingBill, Long> {
    Optional<ParkingBill> findById(long id);
}

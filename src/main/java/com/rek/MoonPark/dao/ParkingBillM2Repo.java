package com.rek.MoonPark.dao;

import com.rek.MoonPark.model.ParkingBillM2;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParkingBillM2Repo  extends JpaRepository<ParkingBillM2, Long> {
    Optional<ParkingBillM2> findById(long id);
}
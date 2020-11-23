package com.rek.MoonPark.dao;

import com.rek.MoonPark.model.ParkingBillM3;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParkingBillM3Repo extends JpaRepository<ParkingBillM3, Long> {
    Optional<ParkingBillM3> findById(long id);
}

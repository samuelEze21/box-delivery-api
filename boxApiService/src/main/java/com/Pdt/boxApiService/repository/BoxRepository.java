package com.Pdt.boxApiService.repository;

import com.Pdt.boxApiService.model.entity.Box;
import com.Pdt.boxApiService.model.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {
    List<Box> findByStateAndBatteryCapacityGreaterThanEqual(State state, int batteryCapacity);
}
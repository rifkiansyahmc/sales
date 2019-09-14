package com.rifkiansyah.sales.repository;

import com.rifkiansyah.sales.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer> {
    List<Sales> findByUsername(String username);

    List<Sales> findByItemname(String itemname);

}

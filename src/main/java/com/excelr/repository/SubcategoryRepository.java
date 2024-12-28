package com.excelr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelr.model.Subcategory;
@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

}

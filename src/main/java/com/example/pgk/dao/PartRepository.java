package com.example.pgk.dao;

import com.example.pgk.model.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Long > {
    @Transactional
    @Modifying
    @Query("select p from Part p where YEAR(p.createdAt)=:year")
    List<Part> findAllByYear(int year);

}

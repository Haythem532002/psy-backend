package com.psy.repositories;

import com.psy.models.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Page<Doctor> findAll(Pageable pageable);

    @Query("SELECT d FROM Doctor d WHERE " +
            "(:name IS NULL OR d.firstname = :name OR d.lastname = :name) AND " +
            "(:price IS NULL OR d.price <= :price) AND " +
            "(:gender IS NULL OR :gender = 'All' OR d.gender = :gender)")

    Page<Doctor> searchDoctors(
            @Param("name") String name,
            @Param("price") Integer price,
            @Param("gender") String gender,
            Pageable pageable
    );

}
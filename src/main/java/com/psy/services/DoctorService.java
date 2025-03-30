package com.psy.services;

import com.psy.models.Doctor;
import com.psy.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public Page<Doctor> getAllDoctors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorRepository.findAll(pageable);
    }

    public Integer getDoctorCount() {
        return Math.toIntExact(doctorRepository.count());
    }

    public Doctor getDoctorById(Integer id) {
        return doctorRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Doctor not found")
        );
    }
}

package com.psy.services;

import com.psy.models.Doctor;
import com.psy.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Page<Doctor> getDoctors(int page,int size,String name, int price,String gender) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorRepository.searchDoctors(name, price, gender, pageable);
//        List<Doctor> doctors = doctorRepository.findAll();
//        if (name != null && !name.isEmpty()) {
//            doctors = doctors.stream()
//                    .filter(doctor -> (doctor.getFirstname().equalsIgnoreCase(name) ||
//                            doctor.getLastname().equalsIgnoreCase(name))
//                    )
//                    .toList();
//
//        }
//        if (price > 50) {
//            doctors = doctors.stream()
//                    .filter(doctor -> doctor.getPrice() <= price)
//                    .toList();
//        }
//        if(gender!=null && !gender.equals("All")) {
//            doctors = doctors.stream()
//                    .filter(doctor -> doctor.getGender().equalsIgnoreCase(gender))
//                    .toList();
//        }
//        return (Page<Doctor>) doctors;
    }

}
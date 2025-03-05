package com.psy.controllers;

import com.psy.models.Doctor;
import com.psy.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/all")
    Page<Doctor> getAllDoctors(@RequestParam int page, @RequestParam int size) {
        return doctorService.getAllDoctors(page,size);
    }

    @GetMapping("/count")
    ResponseEntity<Integer> getDoctorCount() {
        return ResponseEntity.ok(doctorService.getDoctorCount());
    }
}

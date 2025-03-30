package com.psy.controllers;

import com.psy.models.Doctor;
import com.psy.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/all")
    Page<Doctor> getAllDoctors(@RequestParam int page, @RequestParam int size) {
        return doctorService.getAllDoctors(page,size);
    }

    @GetMapping("/{id}")
    ResponseEntity<Doctor> getDoctorById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping("/count")
    ResponseEntity<Integer> getDoctorCount() {
        return ResponseEntity.ok(doctorService.getDoctorCount());
    }
}

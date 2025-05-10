package com.psy.controllers;

import com.psy.models.Doctor;
import com.psy.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/search")
    public ResponseEntity<Page<Doctor>> getDoctors(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "gender", required = false) String gender
    ) {

        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
        System.out.println("Gender: " + gender);
        // Treat empty strings as null
        price = price == 50 ? null : price;
        name = (name != null && name.trim().isEmpty()) ? null : name;
        gender = (gender != null && gender.trim().isEmpty()) ? null : gender;

        return ResponseEntity.ok(doctorService.getDoctors(page, size, name, price, gender));
    }
}

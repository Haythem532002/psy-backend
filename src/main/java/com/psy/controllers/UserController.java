package com.psy.controllers;

import com.psy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/email")
    public ResponseEntity<Integer> getUserIdByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserIdByEmail(email));
    }
}

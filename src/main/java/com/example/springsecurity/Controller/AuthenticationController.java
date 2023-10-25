package com.example.springsecurity.Controller;


import com.example.springsecurity.DTO.LoginDTO;
import com.example.springsecurity.DTO.RegisterDTO;
import com.example.springsecurity.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/create/")
    public ResponseEntity<?> create(@RequestBody RegisterDTO registerDTO){
        return new ResponseEntity<>(authenticationService.signup(registerDTO), HttpStatus.OK);
    }

//    @GetMapping("/view/")
//    public ResponseEntity<?> view(){
//        return new ResponseEntity<>(authenticationService.viewAll(), HttpStatus.OK);
//    }

    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(authenticationService.login(loginDTO), HttpStatus.OK);
    }

}

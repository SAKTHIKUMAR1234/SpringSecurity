package com.example.springsecurity.Services;

import com.example.springsecurity.DTO.AuthenticationTokenDTO;
import com.example.springsecurity.DTO.LoginDTO;
import com.example.springsecurity.DTO.RegisterDTO;
import com.example.springsecurity.Entity.Role;
import com.example.springsecurity.Entity.Users;
import com.example.springsecurity.Repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtServices jwtServices;

    public Users signup(RegisterDTO registerDTO){


        Users users = Users.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.USERS)
                .build();

        return usersRepository.save(users);
    }

//    public List<Users> viewAll(){
//        return  usersRepository.findAll();
//    }

    public AuthenticationTokenDTO login(LoginDTO loginDTO){
        Users user = usersRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new UsernameNotFoundException("The Given Email is not Exist"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
        AuthenticationTokenDTO authenticationTokenDTO = AuthenticationTokenDTO.builder()
                .token(jwtServices.getJwtToken(user))
                .build();

        return authenticationTokenDTO;
    }
}

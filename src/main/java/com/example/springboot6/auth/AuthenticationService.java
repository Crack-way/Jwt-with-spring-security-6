package com.example.springboot6.auth;


import com.example.springboot6.enums.Role;
import com.example.springboot6.jwt.JwtService;
import com.example.springboot6.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.springboot6.entity.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .username(request.getUsername()).
                password(passwordEncoder.encode(request.getPassword())).role(Role.USER)
                .build();
        userRepo.save(user);

        var jwToken = jwtService.generateToken(user);


        return AuthenticationResponse.builder().token(jwToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user=userRepo.findByUsername(request.getUsername()).orElseThrow();
        var jwToken = jwtService.generateToken(user);


        return AuthenticationResponse.builder().token(jwToken)
                .build();
    }
}

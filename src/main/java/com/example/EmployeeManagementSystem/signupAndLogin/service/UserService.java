package com.example.EmployeeManagementSystem.signupAndLogin.service;

import com.example.EmployeeManagementSystem.signupAndLogin.dto.response.TokenResponse;
import com.example.EmployeeManagementSystem.signupAndLogin.configuration.JwtService;
import com.example.EmployeeManagementSystem.signupAndLogin.dto.mapper.UserMapper;
import com.example.EmployeeManagementSystem.signupAndLogin.dto.request.UserRequest;
import com.example.EmployeeManagementSystem.signupAndLogin.dto.response.UserResponse;
import com.example.EmployeeManagementSystem.signupAndLogin.entity.User;
import com.example.EmployeeManagementSystem.signupAndLogin.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.regex.Pattern;

@Service
public class UserService implements IUserService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtHelper;


    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtHelper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }



    @Override
    public UserResponse registerUser(UserRequest userRequest) {

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }
        if (!EMAIL_PATTERN.matcher(userRequest.getEmail()).matches()) {
            throw new ValidationException("Email format is incorrect");
        }
        if(userRequest.getEmail() == null || userRequest.getEmail().isEmpty())
            throw new ValidationException("Email cannot be empty");




        User user = UserMapper.toUserEntity(userRequest);
        String token = jwtHelper.generateToken(userRequest.getEmail());
        user.setToken(token);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userRepository.save(user);
        return null;
        //return UserMapper.toResponse(registeredUser);
    }
    @Override
    public String generateToken(String email) {
        return jwtHelper.generateToken(email);
    }


    @Override
    public UserResponse loginUser(UserRequest userRequest) throws AuthenticationException {
        if(userRequest.getEmail() == null || userRequest.getEmail().isEmpty())
            throw new ValidationException("Email cannot be empty");

        if (!EMAIL_PATTERN.matcher(userRequest.getEmail()).matches()) {
            throw new ValidationException("Email format is incorrect");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String token = jwtHelper.generateToken(userRequest.getEmail());
                String refreshToken = jwtHelper.generateRefreshToken(userRequest.getEmail());
                User user = userRepository.findByEmail(userRequest.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                user.setToken(token);
                user.setRefreshToken(refreshToken);
                userRepository.save(user);
                return UserMapper.toResponse(user);
            } else {
                throw new RuntimeException("Invalid email or password");
            }
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Invalid email or password");
        }
    }
    public TokenResponse refreshToken(String refreshToken) {
        String email = jwtHelper.extractEmail(refreshToken);

        if (jwtHelper.validateToken(refreshToken, email)) {
            String newAccessToken = jwtHelper.generateToken(email);

            String newRefreshToken = jwtHelper.generateRefreshToken(email);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setRefreshToken(newRefreshToken);
            userRepository.save(user);

            return new TokenResponse(newAccessToken, newRefreshToken);
        }

        throw new RuntimeException("Invalid refresh token");
    }
















}

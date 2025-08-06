package com.authservice.service;

import com.authservice.model.User;
import com.authservice.repository.UserRepository;
//import com.authservice.dto.UpdateCredentialsRequest;
import com.authservice.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user, "userId", "password");
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.getRole() == null || userDTO.getRole().isEmpty()) {
            user.setRole("user");
        } else {
            user.setRole(userDTO.getRole());
        }
        return convertToDto(userRepository.save(user));
    }
    
// // service/AuthUserService.java
//    @Transactional
//    public UserDTO updateCredentials(UpdateCredentialsRequest req) {
//        User user = userRepository.findByUsername(req.getUsername())
//            .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        if (req.getNewUsername() != null && !req.getNewUsername().isBlank()) {
//            if (userRepository.existsByUsername(req.getNewUsername())) {
//                throw new IllegalArgumentException("New username already taken");
//            }
//            user.setUsername(req.getNewUsername());
//        }
//        if (req.getNewPassword() != null && !req.getNewPassword().isBlank()) {
//            user.setPasswordHash(passwordEncoder.encode(req.getNewPassword()));
//        }
//        userRepository.save(user);
//        return convertToDto(user);
//    }


    public UserDTO convertToDto(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto, "passwordHash");
        dto.setPassword(null);
        return dto;
    }
}


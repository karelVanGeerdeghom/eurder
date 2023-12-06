package com.example.eurder.service;

import com.example.eurder.domain.Admin;
import com.example.eurder.exception.UnknownAdminEmailException;
import com.example.eurder.exception.WrongPasswordException;
import com.example.eurder.repository.AdminRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin authenticate(String email, String password) throws UnknownAdminEmailException, WrongPasswordException {
        return validatePassword(getByEmail(email), password);
    }

    private Admin validatePassword(Admin admin, String password) throws WrongPasswordException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(password, admin.getPassword())) {
            throw new WrongPasswordException();
        }

        return admin;
    }

    public Admin getByEmail(String email) {
        return adminRepository.getByEmail(email);
    }
}

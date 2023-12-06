package com.example.eurder.service;

import com.example.eurder.domain.Admin;
import com.example.eurder.exception.UnknownAdminEmailException;
import com.example.eurder.exception.WrongPasswordException;
import com.example.eurder.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AdminServiceTest {
    private AdminService adminService;

    @BeforeEach
    public void setup() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        AdminRepository adminRepository = new AdminRepository();
        adminRepository.truncate();
        Admin admin = new Admin("admin@eurder.com", bCryptPasswordEncoder.encode("admin"));
        adminRepository.create(admin);

        adminService = new AdminService(adminRepository);
    }

    @Test
    void givenRightEmailAndRightPassword_whenAuthenticate_thenGetAuthenticatedAdmin() {
        // GIVEN
        String email = "admin@eurder.com";
        String password = "admin";

        // WHEN
        Admin actual = adminService.authenticate(email, password);

        // THEN
        assertThat(actual).isInstanceOf(Admin.class);
    }

    @Test
    void givenRightEmailAndWrongPassword_whenAuthenticate_thenThrowWrongPasswordException() {
        // GIVEN
        String email = "admin@eurder.com";
        String password = "password";

        // WHEN + THEN
        assertThatThrownBy(() -> adminService.authenticate(email, password)).isInstanceOf(WrongPasswordException.class);
    }

    @Test
    void givenWrongEmailAndRightPassword_whenAuthenticate_thenThrowUnknownAdminEmailException() {
        // GIVEN
        String email = "firstName.lastName@mail.com";
        String password = "admin";

        // WHEN + THEN
        assertThatThrownBy(() -> adminService.authenticate(email, password)).isInstanceOf(UnknownAdminEmailException.class);
    }

    @Test
    void givenWrongEmailAndWrongPassword_whenAuthenticate_thenThrowUnknownAdminEmailException() {
        // GIVEN
        String email = "firstName.lastName@mail.com";
        String password = "password";

        // WHEN + THEN
        assertThatThrownBy(() -> adminService.authenticate(email, password)).isInstanceOf(UnknownAdminEmailException.class);
    }
}
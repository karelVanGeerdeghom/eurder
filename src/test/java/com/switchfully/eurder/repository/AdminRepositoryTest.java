package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Admin;
import com.switchfully.eurder.exception.UnknownAdminEmailException;
import com.switchfully.eurder.exception.UnknownAdminIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AdminRepositoryTest {
    private AdminRepository adminRepository = new AdminRepository();

    @BeforeEach
    void truncate() {
        adminRepository.truncate();
    }

    @Test
    void givenAdmin_whenCreateAdmin_thenGetCreatedAdminWithIdOne() {
        // GIVEN
        String email = "firstName.lastName@mail.com";
        String password = "password";

        Admin admin = new Admin(email, password);

        // WHEN
        Admin actual = adminRepository.create(admin);

        // THEN
        assertThat(actual).isInstanceOf(Admin.class);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPassword()).isEqualTo(password);
    }

    @Test
    void givenExistingId_whenGetAdminById_thenGetAdminWithGivenId() {
        // GIVEN
        Integer id = 1;

        String email = "firstName.lastName@mail.com";
        String password = "password";

        adminRepository.create(new Admin(email, password));

        // WHEN
        Admin actual = adminRepository.getById(id);

        // THEN
        assertThat(actual).isInstanceOf(Admin.class);
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPassword()).isEqualTo(password);
    }

    @Test
    void givenExistingEmail_whenGetAdminByEmail_thenGetAdminWithGivenEmail() {
        // GIVEN
        String email = "firstName.lastName@mail.com";
        String password = "password";

        adminRepository.create(new Admin(email, password));

        // WHEN
        Admin actual = adminRepository.getByEmail(email);

        // THEN
        assertThat(actual).isInstanceOf(Admin.class);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getPassword()).isEqualTo(password);
    }

    @Test
    void givenUnknownId_whenGetAdminById_thenThrowUnknownAdminIdException() {
        // GIVEN
        Integer id = 1;

        // WHEN + THEN
        assertThatThrownBy(() -> adminRepository.getById(id)).isInstanceOf(UnknownAdminIdException.class);
    }

    @Test
    void givenUnknownEmail_whenGetAdminByEmail_thenThrowUnknownAdminEmailException() {
        // GIVEN
        String email = "firstName.lastName@eurder.com";

        // WHEN + THEN
        assertThatThrownBy(() -> adminRepository.getByEmail(email)).isInstanceOf(UnknownAdminEmailException.class);
    }
}
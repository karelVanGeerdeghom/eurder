package com.example.eurder.repository;

import com.example.eurder.domain.Admin;
import com.example.eurder.exception.UnknownAdminEmailException;
import com.example.eurder.exception.UnknownAdminIdException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AdminRepository {
    private static Integer autoIncrementId = 0;
    private Map<Integer, Admin> admins = new HashMap<>();

    public AdminRepository() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        admins.put(0, new Admin("admin@eurder.com", bCryptPasswordEncoder.encode("admin")));
    }

    public Admin create(Admin admin) {
        autoIncrementId++;
        admin.setId(autoIncrementId);

        return save(admin);
    }

    private Admin save(Admin admin) {
        admins.put(admin.getId(), admin);

        return admin;
    }

    public void truncate() {
        autoIncrementId = 0;
        admins = new HashMap<>();
    }

    public Admin getById(Integer id) throws UnknownAdminIdException {
        return admins.values().stream().filter(admin -> admin.getId().equals(id)).findFirst().orElseThrow(UnknownAdminIdException::new);
    }

    public Admin getByEmail(String email) throws UnknownAdminEmailException {
        return admins.values().stream().filter(admin -> admin.getEmail().equals(email)).findFirst().orElseThrow(UnknownAdminEmailException::new);
    }
}

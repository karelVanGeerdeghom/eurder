package com.example.eurder.repository;

import com.example.eurder.domain.Admin;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AdminRepository {
    private Map<String, Admin> admins = new HashMap<>();

    public AdminRepository() {

    }
}

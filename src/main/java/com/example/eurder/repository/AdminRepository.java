package com.example.eurder.repository;

import com.example.eurder.domain.Admin;
import com.example.eurder.exception.UnknownAdminUsernameException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class AdminRepository {
    private Map<Long, Admin> admins = new HashMap<>();

    public AdminRepository() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Collection<GrantedAuthority> authorities = mapRolesToAuthorities(new String[] { "ROLE_ADMIN" });
        Admin admin = new Admin("admin", bCryptPasswordEncoder.encode("admin"), authorities, "admin@eurder.com");
        admins.put(admin.getId(), admin);
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(String[] roles) {
        return Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Admin findUserByUsername(String username) {
        return admins.values().stream().filter(admin -> admin.getUsername().equals(username)).findFirst().orElseThrow(UnknownAdminUsernameException::new);
    }
}

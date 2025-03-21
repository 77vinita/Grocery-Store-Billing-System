package com.project.billingSoftware.service;

import com.project.billingSoftware.model.Admin;
import com.project.billingSoftware.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin validateAdmin(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        return (admin != null && admin.getPassword().equals(password)) ? admin : null;
    }
}

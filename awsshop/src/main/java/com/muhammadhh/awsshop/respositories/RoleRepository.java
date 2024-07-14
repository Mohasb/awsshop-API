package com.muhammadhh.awsshop.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muhammadhh.awsshop.models.Role;
import com.muhammadhh.awsshop.models.Role.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
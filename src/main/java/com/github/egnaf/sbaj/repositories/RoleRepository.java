package com.github.egnaf.sbaj.repositories;

import com.github.egnaf.sbaj.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

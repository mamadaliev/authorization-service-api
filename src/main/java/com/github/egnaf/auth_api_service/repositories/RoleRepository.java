package com.github.egnaf.auth_api_service.repositories;

import com.github.egnaf.auth_api_service.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
}

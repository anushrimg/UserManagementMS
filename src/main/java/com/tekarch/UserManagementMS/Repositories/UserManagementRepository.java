package com.tekarch.UserManagementMS.Repositories;

import com.tekarch.UserManagementMS.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagementRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);


}

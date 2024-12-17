package com.tekarch.UserManagementMS.Services.Interfaces;

import com.tekarch.UserManagementMS.Models.User;

import java.util.List;
import java.util.Optional;

public interface UserManagementService {
    List<User> getAllUsers();
    User addUser(User user);
    Optional<User> findById(long userId);
    User update(Long userId,User updatedUser);
    void deleteById(long userId);
}

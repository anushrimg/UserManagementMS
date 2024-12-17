package com.tekarch.UserManagementMS.Services;

import com.tekarch.UserManagementMS.Models.User;
import com.tekarch.UserManagementMS.Repositories.UserManagementRepository;
import com.tekarch.UserManagementMS.Services.Interfaces.UserManagementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserManagementRepository userManagementRepository;
    private static final Logger logger = LogManager.getLogger(UserManagementServiceImpl.class);

    public UserManagementServiceImpl(UserManagementRepository userManagementRepository) {
        this.userManagementRepository = userManagementRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userManagementRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        return userManagementRepository.save(user);
    }

    @Override
    public Optional<User> findById(long userId) {

        return Optional.ofNullable(userManagementRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));


    }

    @Override
    public User update(Long userId,User updatedUser) {
        User existingUser = userManagementRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check for duplicate email
        User userWithEmail = userManagementRepository.findByEmail(updatedUser.getEmail());
        if (userWithEmail != null && !userWithEmail.getUserId().equals(userId)) {
            throw new RuntimeException("Email '" + updatedUser.getEmail() + "' is already in use.");
        }


        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setPasswordHash(updatedUser.getPasswordHash());
        existingUser.setTwoFactorEnabled(updatedUser.getTwoFactorEnabled());
        existingUser.setKycStatus(updatedUser.getKycStatus());


        return userManagementRepository.save(existingUser);
    }

    @Override
    public void deleteById(long userId) {
       // userManagementRepository.deleteById(userId);
        User existingUser = userManagementRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete the user from the database
        userManagementRepository.delete(existingUser);

    }


}

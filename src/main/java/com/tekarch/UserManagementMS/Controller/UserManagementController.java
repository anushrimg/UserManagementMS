package com.tekarch.UserManagementMS.Controller;

import com.tekarch.UserManagementMS.DTO.UserProfile;
import com.tekarch.UserManagementMS.Models.User;
import com.tekarch.UserManagementMS.Services.Interfaces.UserManagementService;
import com.tekarch.UserManagementMS.Services.UserManagementServiceImpl;
import jakarta.websocket.server.PathParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserManagementController {
    private final UserManagementService userManagementService;
    private static final Logger logger = LogManager.getLogger(UserManagementController.class);

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }


    @GetMapping()
    public ResponseEntity<List<User>> getUser(){
      return new ResponseEntity<>(userManagementService.getAllUsers(), HttpStatus.OK) ;
    }
    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User user){
        logger.info("creating new user with name {} ",user.getUserName());
        return  new ResponseEntity<>(userManagementService.addUser(user),HttpStatus.CREATED);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Optional<User>> findById(@PathVariable long userId){
        return  ResponseEntity.ok(userManagementService.findById(userId));
    }
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {

        User user = userManagementService.update(userId, updatedUser);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {

        userManagementService.deleteById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
    // Update Personal Info
    @PutMapping("/{userId}/personal-info")
    public ResponseEntity<String> addPersonalInfo(@PathVariable Long userId, @RequestBody User updatedInfo) {

        userManagementService.updatePersonalInfo(userId, updatedInfo);

        return ResponseEntity.ok("User profile updated successfully");
    }
    // Retrieve Personal Info
    @GetMapping("/{userId}/personal-info")
    public ResponseEntity<Map<String, String>> getPersonalInfo(@PathVariable Long userId) {
        User user = userManagementService.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
        Map<String, String> personalInfo = new HashMap<>();
        personalInfo.put("address", user.getAddress());
        personalInfo.put("dob", user.getDob());
        personalInfo.put("gender", user.getGender());
        return ResponseEntity.ok(personalInfo);
    }
//    @GetMapping("/{userId}/personal-info")
//   public ResponseEntity<UserProfile> getPersonalInfo(@PathVariable Long userId) {
//        return ResponseEntity.ok(UserProfile);
//    }
// Submit KYC
    @PostMapping("/{userId}/kyc")
    public ResponseEntity<String> submitKYC(@PathVariable Long userId) {
        userManagementService.submitKYC(userId);
        return ResponseEntity.ok("KYC submitted successfully for User ID: " + userId);
    }

    // Retrieve KYC Status
    @GetMapping("/{userId}/kyc")
    public ResponseEntity<String> getKYCStatus(@PathVariable Long userId) {

        String status = userManagementService.getKYCStatus(userId);
        return ResponseEntity.ok(status);
    }

    // Update KYC Status
    @PutMapping("/{userId}/kyc")
    public ResponseEntity<String> updateKYC(@PathVariable Long userId, @RequestBody String kycStatus) {

        String response = userManagementService.updateKYC(userId, kycStatus);
        return ResponseEntity.ok(response);
    }

    // Delete KYC
    @DeleteMapping("/{userId}/kyc")
    public ResponseEntity<Void> deleteKYC(@PathVariable Long userId) {

        userManagementService.deleteKYC(userId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> repondWithError(Exception e){
        // logger.error("Exception occured Details :{}",e.getMessage());
        return new ResponseEntity<>("Exception Occured.More Info : "+e.getMessage(), HttpStatus.BAD_REQUEST);

    }




}


package com.tekarch.UserManagementMS.Controller;

import com.tekarch.UserManagementMS.Models.User;
import com.tekarch.UserManagementMS.Services.Interfaces.UserManagementService;
import com.tekarch.UserManagementMS.Services.UserManagementServiceImpl;
import jakarta.websocket.server.PathParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @ExceptionHandler
    public ResponseEntity<String> repondWithError(Exception e){
        // logger.error("Exception occured Details :{}",e.getMessage());
        return new ResponseEntity<>("Exception Occured.More Info : "+e.getMessage(), HttpStatus.BAD_REQUEST);

    }




}


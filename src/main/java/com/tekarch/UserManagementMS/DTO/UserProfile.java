package com.tekarch.UserManagementMS.DTO;

import lombok.Data;

@Data
public class UserProfile {
    private Long userId;
    private String address;
    private String dob;
    private String gender;
}

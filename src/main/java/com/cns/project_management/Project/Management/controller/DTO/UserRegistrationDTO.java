package com.cns.project_management.Project.Management.controller.DTO;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    private  String username;
    private  String password;
    private  String email;
}

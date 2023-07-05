package com.cns.project_management.Project.Management.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long id;

    @Column(name = "username", length = 200, nullable = false)
    private String username;

    @Column(name = "password", length = 300)
    private String password;

    @Column(name = "email", length = 300)
    private String email;
}



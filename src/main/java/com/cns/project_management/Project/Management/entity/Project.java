package com.cns.project_management.Project.Management.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    //TODO: add project entity
    //name, intro, owner, status (pre = 0,start = 1,end = 3), members, StartDate, EndDate
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "intro", length = 300)
    private String intro;

    @Column(name = "status" , length = 1)
    private int status;

    @Column(name = "StartDate", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "project_members",
                joinColumns = @JoinColumn(name = "project_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
        private List<User> members;

    public int calculateStatus() {
        LocalDate today = LocalDate.now();
        if (today.isBefore(startDate)) {
            return 0;
        } else if (today.isAfter(endDate)) {
            return 3;
        } else {
            return 1;
        }
    }





}

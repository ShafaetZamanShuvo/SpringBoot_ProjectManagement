package net.javaguides.springboot.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Task {

    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}

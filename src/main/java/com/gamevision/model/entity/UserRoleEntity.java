package com.gamevision.model.entity;

import com.gamevision.model.enums.UserRoleEnum;


import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoleEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum name;

    public UserRoleEntity() {
    }


    public UserRoleEnum getName() {
        return name;
    }

    public UserRoleEntity setName(UserRoleEnum name) {
        this.name = name;
        return this;
    }


    @Override
    public String toString() {
        return "UserRoleEntity{" +
                "id=" + getId() +
                ", userRole=" + name +
                '}';
    }

}

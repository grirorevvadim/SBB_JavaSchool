package com.tsystems.javaschool.projects.SBB.domain.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity(name = "roles")
public class Role extends AbstractEntity implements GrantedAuthority {
    private String name;

    @ManyToMany(mappedBy = "rolesList")
    private List<User> users;

    @Override
    public String getAuthority() {
        return name;
    }
}

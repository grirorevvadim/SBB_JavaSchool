package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity(name = "roots")
public class Root extends AbstractEntity implements Serializable {

    @Column(nullable = false)
    private String rootId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private List<Path> pathsList;
}
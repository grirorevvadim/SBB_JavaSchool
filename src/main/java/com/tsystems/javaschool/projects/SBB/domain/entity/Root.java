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

    @Column(nullable = false, name = "root_id")
    private String rootId;

//    @OneToMany(mappedBy = "root" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Path> pathsList;

    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable(name = "roots_paths", joinColumns = @JoinColumn(name = "root_id"), inverseJoinColumns = @JoinColumn(name = "path_id"))
    private List<Path> linkedPaths;
}


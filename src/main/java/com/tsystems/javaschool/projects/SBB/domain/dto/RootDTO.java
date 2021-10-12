package com.tsystems.javaschool.projects.SBB.domain.dto;

import com.tsystems.javaschool.projects.SBB.domain.entity.Path;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class RootDTO implements Serializable {
    private static final long serialVersionID = 37425487353252L;
    private Long id;
    private String rootId;
    private List<Path> pathsList;
}

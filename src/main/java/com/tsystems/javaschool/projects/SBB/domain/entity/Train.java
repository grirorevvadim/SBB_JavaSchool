package com.tsystems.javaschool.projects.SBB.domain.entity;

import com.tsystems.javaschool.projects.SBB.service.util.TrainType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity(name = "trains")
public class Train extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 53553531L;

    @Column(nullable = false, name = "all_seats_number")
    private Integer allSeatsNumber;

    @Column(name = "train_type")
    private TrainType trainType;

    @Column(name = "train_number")
    private String trainNumber;

    @Column(name = "section_price")
    private Integer sectionPrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "train")
    private List<Schedule> scheduleList;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "root_id", referencedColumnName = "id")
    private Root rootId;


    @Override
    public String toString() {
        return "Train{" +
                "allSeatsNumber=" + allSeatsNumber +
                ", trainType=" + trainType +
                ", trainNumber='" + trainNumber + '\'' +
                '}';
    }
}

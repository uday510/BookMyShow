package com.lld.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Picture extends BaseModel {
    // 1 : 1
    // M : 1
    @ManyToOne
    private Movie movie;
    private Date startTime;
    private Date endTime;
    @ManyToOne
    private Screen screen;
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> features;

}
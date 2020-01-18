package ru.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Duration;
import java.util.Date;

@Entity
@Table(name = "activities")
public class Activity {
    private String appName;
    private Date startDate;
    private Duration duration;
}

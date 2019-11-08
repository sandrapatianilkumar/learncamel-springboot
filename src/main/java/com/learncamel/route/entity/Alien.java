package com.learncamel.route.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Entity
@Table(name = "aliens")
public class Alien {
    @Id
    private  Integer aid;

    @Column(name = "alienname")
    private String aname;

    private String color;
}

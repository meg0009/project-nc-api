package com.chivapchichi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "record_id", nullable = false)
    private int recordId;

    @Column(name = "paid", nullable = false)
    private boolean paid;

    @Column(name = "return", nullable = false)
    private boolean ret;
}

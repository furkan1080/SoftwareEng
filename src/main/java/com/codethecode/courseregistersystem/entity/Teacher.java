package com.codethecode.courseregistersystem.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="teacher")
public class Teacher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    //@Email
    //@Column(name="email", unique = true)
    //private String email;

    //@NotNull
    //@Size(max = 128)
    //@Column(name="password")
    //private String password;

    @Column(name="courses") // courses teacher teaches
    private ArrayList<String> courses;

    @Column(name="surname")
    private String surname;

    @Column(name="gender")
    private String gender;

    @Column(name="busy_days")
    private ArrayList<String> busyDays;

    @Column(name="branch")
    private String branch;

    @Column(name="hourly_cost")
    private Integer cost;

    @Column(name="balance")
    private Integer balance;

    @OneToMany(mappedBy = "teacher")
    private List<Request> request;
}
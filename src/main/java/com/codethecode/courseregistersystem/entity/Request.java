package com.codethecode.courseregistersystem.entity;

import com.codethecode.courseregistersystem.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
/*
    @Column(name="course") // courses teacher teaches
    private Course course;

    @Column(name="teacher") // courses teacher teaches
    private Teacher teacher;

    @Column(name="student") // courses teacher teaches
    private Student student;
*/
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "studentid")
    private Student student;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "courseid")
    private Course course;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "teacherid")
    private Teacher teacher;

    private RequestStatus requestStatus;

}


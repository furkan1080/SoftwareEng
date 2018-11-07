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

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "id")
    private Student student;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "id")
    private Course course;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "id")
    private Teacher teacher;

    private RequestStatus requestStatus;

}


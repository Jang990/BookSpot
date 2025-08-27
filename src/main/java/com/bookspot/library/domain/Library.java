package com.bookspot.library.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "POINT SRID 4326")
    private Point location;

    private String address;
    private String contactNumber;
    private String homePage;
    private String closedInfo;
    private String operatingInfo;
    
    private LocalDate updatedAt;
    private LocalDate stockUpdatedAt;

    private String libraryCode;
}

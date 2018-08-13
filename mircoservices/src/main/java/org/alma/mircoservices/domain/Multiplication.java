package org.alma.mircoservices.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Cette class représente une multiplication dans notre application
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class Multiplication {
    @Id
    @GeneratedValue
    @Column(name="MULTIPLICATION_ID")
    private Long id;
    // Both factors
    private final int factorA;
    private final int factorB;


    public Multiplication() {
        this(0,0);
    }

    
}
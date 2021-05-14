package org.covid.vaccineman.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "district_info")
@Entity
public class DistrictEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    private Long id;

    @Column(name = "district_name", updatable = false)
    private String districtName;

    @Column(name="district_id", updatable = false)
    private Integer districtId;
}

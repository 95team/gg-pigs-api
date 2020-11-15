package com.pangoapi.advertisementType.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class AdvertisementType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADVERTISEMENT_TYPE_ID")
    private Long id;

    private String type;
    private String width;
    private String height;
}

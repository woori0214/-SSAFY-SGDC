package com.ssafy.sgdc.badge.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BadgeListDto {

    private int badgeId;
    private String badgeName;
    private String badgeDetail;
    private String badgeImg;

}

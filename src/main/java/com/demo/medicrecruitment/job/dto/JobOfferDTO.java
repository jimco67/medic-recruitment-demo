package com.demo.medicrecruitment.job.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobOfferDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String speciality;
    private Long recruiterId;

    public JobOfferDTO() {
    }

    public JobOfferDTO(Long id, String title, String description, String location, String speciality, Long recruiterId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.speciality = speciality;
        this.recruiterId = recruiterId;
    }
}

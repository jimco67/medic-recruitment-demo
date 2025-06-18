package com.demo.medicrecruitment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class JobOffer {

    @Id
    @GeneratedValue(generator = "joboffer_id_gen")
    @Access(AccessType.PROPERTY)
    private Long id;

    @Schema(description = "Title of the job offer")
    private String title;

    @Schema(description = "Description of the job offer")
    private String description;

    @Schema(description = "Location of the job offer")
    private String location;

    @Schema(description = "Speciality required for the job offer")
    private String speciality;

    @Schema(description = "Creation datetime")
    @CreatedDate
    private LocalDateTime creationDatetime;

    @Schema(description = "ID of the recruiter who created this job offer")
    private Long recruiterId;
}

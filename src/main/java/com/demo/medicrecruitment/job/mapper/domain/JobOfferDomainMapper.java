package com.demo.medicrecruitment.job.mapper.domain;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.job.dto.JobOfferDTO;
import com.demo.medicrecruitment.model.JobOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobOfferDomainMapper {

    /**
     * Maps a JobOfferDomain object to a JobOffer model object.
     *
     * @param jobOfferDomain the JobOfferDomain to map
     * @return the mapped JobOffer model object, or null if the input is null
     */

    public JobOffer fromDomain(JobOfferDomain jobOfferDomain) {
        if (jobOfferDomain == null) {
            return null;
        }
        return JobOffer.builder()
                .id(jobOfferDomain.getId())
                .title(jobOfferDomain.getTitle())
                .description(jobOfferDomain.getDescription())
                .location(jobOfferDomain.getLocation())
                .speciality(jobOfferDomain.getSpeciality())
                .creationDatetime(jobOfferDomain.getCreatedAt())
                .recruiterId(jobOfferDomain.getRecruiterId())
                .build();
    }

    public JobOfferDTO toDTO(JobOfferDomain jobOfferDomain) {
        if (jobOfferDomain == null) {
            return null;
        }
        return new JobOfferDTO(
                jobOfferDomain.getId(),
                jobOfferDomain.getTitle(),
                jobOfferDomain.getDescription(),
                jobOfferDomain.getLocation(),
                jobOfferDomain.getSpeciality(),
                jobOfferDomain.getRecruiterId()
        );
    }
}

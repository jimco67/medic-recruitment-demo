package com.demo.medicrecruitment.job.mapper.domain;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.model.JobOffer;
import org.springframework.stereotype.Service;

@Service
public class JobOfferDomainMapper {

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
}

package com.demo.medicrecruitment.job.infrastructure;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.model.JobOffer;

public interface JobOfferDomainRepository {
    JobOffer save(JobOfferDomain jobOfferDomain);
}

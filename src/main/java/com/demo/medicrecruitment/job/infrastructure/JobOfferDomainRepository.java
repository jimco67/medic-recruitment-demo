package com.demo.medicrecruitment.job.infrastructure;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;

public interface JobOfferDomainRepository {
    void save(JobOfferDomain jobOfferDomain);
}

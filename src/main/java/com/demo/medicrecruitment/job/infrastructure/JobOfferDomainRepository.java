package com.demo.medicrecruitment.job.infrastructure;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.model.JobOffer;

import java.util.List;

public interface JobOfferDomainRepository {
    JobOffer save(JobOfferDomain jobOfferDomain);

    List<JobOffer> getAll();
}

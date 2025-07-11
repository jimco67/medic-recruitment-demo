package com.demo.medicrecruitment.job.providers;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.job.infrastructure.JobOfferDomainRepository;
import com.demo.medicrecruitment.job.mapper.domain.JobOfferDomainMapper;
import com.demo.medicrecruitment.model.JobOffer;
import com.demo.medicrecruitment.repository.JobOfferRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
class JpaJobOfferRepository implements JobOfferDomainRepository {

    private final JobOfferRepository jobOfferRepository;
    private final JobOfferDomainMapper jobOfferDomainMapper;

    public JpaJobOfferRepository(JobOfferRepository jobOfferRepository, JobOfferDomainMapper jobOfferDomainMapper) {
        this.jobOfferRepository = jobOfferRepository;
        this.jobOfferDomainMapper = jobOfferDomainMapper;
    }

    @Override
    public JobOffer save(JobOfferDomain jobOfferDomain) {
        JobOffer jobOffer = jobOfferDomainMapper.fromDomain(jobOfferDomain);
        jobOffer.setCreationDatetime(LocalDateTime.now());
        return jobOfferRepository.save(jobOffer);
    }

    @Override
    public List<JobOffer> getAll() {
        return jobOfferRepository.findAll();
    }
}

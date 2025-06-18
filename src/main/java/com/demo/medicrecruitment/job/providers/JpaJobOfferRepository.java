package com.demo.medicrecruitment.job.providers;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.job.infrastructure.JobOfferDomainRepository;
import com.demo.medicrecruitment.job.mapper.domain.JobOfferDomainMapper;
import com.demo.medicrecruitment.model.JobOffer;
import com.demo.medicrecruitment.repository.JobOfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class JpaJobOfferRepository implements JobOfferDomainRepository {

    private final JobOfferRepository jobOfferRepository;
    private final JobOfferDomainMapper jobOfferDomainMapper;

    @Override
    public void save(JobOfferDomain jobOfferDomain) {
        JobOffer jobOffer = jobOfferDomainMapper.fromDomain(jobOfferDomain);
        jobOffer.setCreationDatetime(LocalDateTime.now());
        jobOfferRepository.save(jobOffer);
    }
}

package com.demo.medicrecruitment.job.usecase;

import com.demo.medicrecruitment.job.infrastructure.JobOfferDomainRepository;
import com.demo.medicrecruitment.model.JobOffer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSpecificJobOfferUseCase {

    private final JobOfferDomainRepository repository;

    public GetSpecificJobOfferUseCase(JobOfferDomainRepository repository) {
        this.repository = repository;
    }

    public JobOffer handle(Long jobId) {
        return repository.getJob(jobId);
    }
}

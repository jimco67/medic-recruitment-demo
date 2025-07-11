package com.demo.medicrecruitment.job.usecase;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.job.infrastructure.JobOfferDomainRepository;
import com.demo.medicrecruitment.model.JobOffer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllJobOfferUseCase {

    private final JobOfferDomainRepository repository;

    public GetAllJobOfferUseCase(JobOfferDomainRepository repository) {
        this.repository = repository;
    }

    public List<JobOffer> handle() {
        return repository.getAll();
    }
}

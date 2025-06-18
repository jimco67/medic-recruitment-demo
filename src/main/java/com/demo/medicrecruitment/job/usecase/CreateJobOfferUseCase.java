package com.demo.medicrecruitment.job.usecase;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.job.infrastructure.JobOfferDomainRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateJobOfferUseCase {
    private final JobOfferDomainRepository repository;

    public CreateJobOfferUseCase(JobOfferDomainRepository repository) {
        this.repository = repository;
    }

    public void handle(CreateJobOfferCommand createJobOfferCommand) {
        JobOfferDomain jobOfferDomain = JobOfferDomain.builder()
                .withId(createJobOfferCommand.id())
                .withTitle(createJobOfferCommand.title())
                .withDescription(createJobOfferCommand.description())
                .withLocation(createJobOfferCommand.location())
                .withSpeciality(createJobOfferCommand.speciality())
                .withRecruiterId(createJobOfferCommand.recruiterId())
                .build();

        repository.save(jobOfferDomain);
    }

    record CreateJobOfferCommand(
            Long id,
            String title,
            String description,
            String location,
            String speciality,
            Long recruiterId
    ) {}
}



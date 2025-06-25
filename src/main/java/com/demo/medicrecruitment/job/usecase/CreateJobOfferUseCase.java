package com.demo.medicrecruitment.job.usecase;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.job.exception.JobOfferMissingInformationException;
import com.demo.medicrecruitment.job.infrastructure.JobOfferDomainRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CreateJobOfferUseCase {
    private final JobOfferDomainRepository repository;

    public CreateJobOfferUseCase(JobOfferDomainRepository repository) {
        this.repository = repository;
    }

    /**
     * Handles the creation of a new job offer.
     *
     * @param createJobOfferCommand the command containing job offer details
     * @return the created JobOfferDomain object
     */
    public JobOfferDomain handle(CreateJobOfferCommand createJobOfferCommand) throws JobOfferMissingInformationException {
        if (isNotValid(createJobOfferCommand)) {
            throw new JobOfferMissingInformationException();
        }
        JobOfferDomain jobOfferDomain = JobOfferDomain.builder()
                .withTitle(createJobOfferCommand.title())
                .withDescription(createJobOfferCommand.description())
                .withLocation(createJobOfferCommand.location())
                .withSpeciality(createJobOfferCommand.speciality())
                .withRecruiterId(createJobOfferCommand.recruiterId())
                .build();

        repository.save(jobOfferDomain);
        return jobOfferDomain;
    }

    private static boolean isNotValid(CreateJobOfferCommand createJobOfferCommand) {
        return StringUtils.isEmpty(createJobOfferCommand.title()) ||
                StringUtils.isEmpty(createJobOfferCommand.location())|| StringUtils.isEmpty(createJobOfferCommand.speciality()) ||
                createJobOfferCommand.recruiterId() == null;
    }

    public record CreateJobOfferCommand(
            String title,
            String description,
            String location,
            String speciality,
            Long recruiterId
    ) {}
}



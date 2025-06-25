package com.demo.medicrecruitment.job;

import com.demo.medicrecruitment.event.CreateJobEventProducer;
import com.demo.medicrecruitment.job.dto.JobOfferDTO;
import com.demo.medicrecruitment.job.exception.JobOfferMissingInformationException;
import com.demo.medicrecruitment.job.mapper.domain.JobOfferDomainMapper;
import com.demo.medicrecruitment.job.usecase.CreateJobOfferUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/job-offers")
@AllArgsConstructor
public class JobOfferController {

    private final CreateJobOfferUseCase createJobOfferUseCase;
    private final JobOfferDomainMapper jobOfferDomainMapper;
    private final CreateJobEventProducer createJobEventProducer;

    /**
     * Creates a new job offer.
     *
     * @param title       the title of the job offer
     * @param description the description of the job offer
     * @param location    the location of the job offer
     * @param specialty   the specialty required for the job offer
     * @param recruiterId the ID of the recruiter creating the job offer
     * @return a ResponseEntity containing the created JobOffer
     */
    @RequestMapping("/create")
    public ResponseEntity<JobOfferDTO> createJobOffer(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "specialty", required = false) String specialty,
            @RequestParam(value = "recruiterId", required = false) Long recruiterId
    ) throws JobOfferMissingInformationException {
        var createJobOfferCommand = new CreateJobOfferUseCase.CreateJobOfferCommand(
                title,
                description,
                location,
                specialty,
                recruiterId
        );
        var jobOfferDomain = createJobOfferUseCase.handle(createJobOfferCommand, createJobEventProducer);
        return status(HttpStatus.CREATED).body(jobOfferDomainMapper.toDTO(jobOfferDomain));
    }

//    @RequestMapping("/{jobOfferId}")
//    public ResponseEntity<JobOfferDTO> getJobOffer(@PathVariable("jobOfferId") Long id) {
//
//        var jobOfferDomain = createJobOfferUseCase.handle(createJobOfferCommand);
//        return status(HttpStatus.CREATED).body(jobOfferDomainMapper.toDTO(jobOfferDomain));
//    }
}

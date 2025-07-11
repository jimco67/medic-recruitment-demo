package com.demo.medicrecruitment.job;

import com.demo.medicrecruitment.event.CreateJobEventProducer;
import com.demo.medicrecruitment.job.dto.JobOfferDTO;
import com.demo.medicrecruitment.job.exception.JobOfferMissingInformationException;
import com.demo.medicrecruitment.job.mapper.domain.JobOfferDomainMapper;
import com.demo.medicrecruitment.job.mapper.domain.JobOfferMapper;
import com.demo.medicrecruitment.job.usecase.CreateJobOfferUseCase;
import com.demo.medicrecruitment.job.usecase.GetAllJobOfferUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/job-offers")
@AllArgsConstructor
public class JobOfferController {

    private final CreateJobOfferUseCase createJobOfferUseCase;
    private final GetAllJobOfferUseCase getAllJobOfferUseCase;
    private final JobOfferDomainMapper jobOfferDomainMapper;
    private final JobOfferMapper jobOfferMapper;
    private final CreateJobEventProducer createJobEventProducer;

    /**
     * Creates a new job offer.
     *
     * @param title       the title of the job offer
     * @param description the description of the job offer
     * @param location    the location of the job offer
     * @param speciality   the specialty required for the job offer
     * @param recruiterId the ID of the recruiter creating the job offer
     * @return a ResponseEntity containing the created JobOffer
     */
    @RequestMapping("/create")
    public ResponseEntity<JobOfferDTO> createJobOffer(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "speciality", required = false) String speciality,
            @RequestParam(value = "recruiterId", required = false) Long recruiterId
    ) throws JobOfferMissingInformationException {
        var createJobOfferCommand = new CreateJobOfferUseCase.CreateJobOfferCommand(
                title,
                description,
                location,
                speciality,
                recruiterId
        );
        var jobOfferDomain = createJobOfferUseCase.handle(createJobOfferCommand, createJobEventProducer);
        return status(HttpStatus.CREATED).body(jobOfferDomainMapper.toDTO(jobOfferDomain));
    }


    /**
     * Retrieves a list of all job offers.
     *
     * @return a ResponseEntity containing a list of JobOfferDTO objects and an HTTP status of OK (200)
     */
    @RequestMapping("")
    public ResponseEntity<List<JobOfferDTO>> getAllJobOffers() {
        var jobOffersList = getAllJobOfferUseCase.handle();
        return status(HttpStatus.OK).body(jobOfferMapper.toDTOList(jobOffersList));
    }

//    @RequestMapping("/{jobOfferId}")
//    public ResponseEntity<JobOfferDTO> getJobOffer(@PathVariable("jobOfferId") Long id) {
//
//        var jobOfferDomain = createJobOfferUseCase.handle(createJobOfferCommand);
//        return status(HttpStatus.CREATED).body(jobOfferDomainMapper.toDTO(jobOfferDomain));
//    }
}

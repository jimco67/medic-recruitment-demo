package com.demo.medicrecruitment.job.mapper.domain;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.job.dto.JobOfferDTO;
import com.demo.medicrecruitment.model.JobOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobOfferMapper {

    public List<JobOfferDTO> toDTOList(List<JobOffer> jobOffers) {
        return jobOffers.stream().map(this::toDTO).toList();
    }

    public JobOfferDTO toDTO(JobOffer jobOffer) {
        if (jobOffer == null) {
            return null;
        }
        return new JobOfferDTO(
                jobOffer.getId(),
                jobOffer.getTitle(),
                jobOffer.getDescription(),
                jobOffer.getLocation(),
                jobOffer.getSpeciality(),
                jobOffer.getRecruiterId()
        );
    }
}

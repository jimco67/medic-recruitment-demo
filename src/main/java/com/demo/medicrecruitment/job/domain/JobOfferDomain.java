package com.demo.medicrecruitment.job.domain;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
public final class JobOfferDomain {
    private final Long id;
    @NonNull
    private final String title;
    private final String description;
    @NonNull
    private final String location;
    @NonNull
    private final String speciality;
    private final LocalDateTime createdAt;
    @NonNull
    private final Long recruiterId;

    private JobOfferDomain(Long id, @NonNull String title, String description, @NonNull String location, @NonNull String speciality, @NonNull Long recruiterId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.speciality = speciality;
        this.createdAt = LocalDateTime.now();
        this.recruiterId = recruiterId;
    }

    public static JobOfferBuilder builder() {
        return new JobOfferBuilder();
    }

    public static class JobOfferBuilder {
        private Long id;
        private String title;
        private String description;
        private String location;
        private String speciality;
        private LocalDateTime createdAt;
        private Long recruiterId;

        public JobOfferBuilder() {}

        public JobOfferBuilder(JobOfferDomain jobOfferDomain) {
            this.id = jobOfferDomain.getId();
            this.title = jobOfferDomain.getTitle();
            this.description = jobOfferDomain.getDescription();
            this.location = jobOfferDomain.getLocation();
            this.speciality = jobOfferDomain.getSpeciality();
            this.createdAt = jobOfferDomain.getCreatedAt();
            this.recruiterId = jobOfferDomain.getRecruiterId();
        }

        public JobOfferBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public JobOfferBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public JobOfferBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public JobOfferBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public JobOfferBuilder withSpeciality(String speciality) {
            this.speciality = speciality;
            return this;
        }

        public JobOfferBuilder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public JobOfferBuilder withRecruiterId(Long recruiterId) {
            this.recruiterId = recruiterId;
            return this;
        }

        public JobOfferDomain build() {
            return new JobOfferDomain(id, title, description, location, speciality, recruiterId);
        }
    }
}
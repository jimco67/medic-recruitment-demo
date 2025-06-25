package com.demo.medicrecruitment.cucumber.usecases;

import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.job.exception.JobOfferMissingInformationException;
import com.demo.medicrecruitment.job.usecase.CreateJobOfferUseCase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateJobOfferStepDefinitions {

    @Autowired
    private CreateJobOfferUseCase createJobOfferUseCase;

    private long recruiterId;
    private CreateJobOfferUseCase.CreateJobOfferCommand command;
    private JobOfferDomain result;

    @Given("a recruiter with ID {long}")
    public void a_recruiter_with_id(long id) {
        this.recruiterId = id;
    }

    @When("they submit a job offer titled {string} located in {string} requiring {string}")
    public void they_submit_a_job_offer(String title, String location, String speciality) {
        this.command = new CreateJobOfferUseCase.CreateJobOfferCommand(
                title,
                "Example description",
                location,
                speciality,
                recruiterId
        );
    }

    @Then("the job offer should be created and returned with the same title")
    public void the_job_offer_should_be_created() throws JobOfferMissingInformationException {
        result = createJobOfferUseCase.handle(command);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo(command.title());
    }

    @Then("the job offer shouldn't be created and returned {string} exception")
    public void theJobOfferShouldnTBeCreatedAndReturnedException(String exceptionName) {
        switch(exceptionName) {
            case "missing information" -> Assertions.assertThatThrownBy(() -> createJobOfferUseCase.handle(command))
                    .isInstanceOf(JobOfferMissingInformationException.class);
            default -> throw new IllegalArgumentException("Unknown exception: " + exceptionName);
        }

    }
}

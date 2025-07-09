package com.demo.medicrecruitment.cucumber.usecases;

import com.demo.medicrecruitment.KafkaMock;
import com.demo.medicrecruitment.event.CreateJobEventProducer;
import com.demo.medicrecruitment.job.domain.JobOfferDomain;
import com.demo.medicrecruitment.job.exception.JobOfferMissingInformationException;
import com.demo.medicrecruitment.job.usecase.CreateJobOfferUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

import java.util.List;

public class CreateJobOfferStepDefinitions {

    @Autowired
    private CreateJobOfferUseCase createJobOfferUseCase;

    @Autowired
    private CreateJobEventProducer createJobEventProducer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    protected ObjectMapper mapper;

    private KafkaMock kafkaMock;

    private long recruiterId;
    private CreateJobOfferUseCase.CreateJobOfferCommand command;
    private JobOfferDomain result;

    @Before
    public void setup() {
        kafkaMock = new KafkaMock(mapper, embeddedKafkaBroker);
        kafkaMock.setUpKafka(
                List.of("create_job_event")
        );
    }

    @After
    public void tearDown() {
        kafkaMock.tearDown();
    }

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
    public void the_job_offer_should_be_created() throws JobOfferMissingInformationException, InterruptedException {
        result = createJobOfferUseCase.handle(command, createJobEventProducer);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo(command.title());
        kafkaMock.poll().expectTopicToHaveReceivedMessageOnlyOnce("create_job_event");
    }

    @Then("the job offer shouldn't be created and returned {string} exception")
    public void theJobOfferShouldnTBeCreatedAndReturnedException(String exceptionName) {
        switch(exceptionName) {
            case "missing information" -> Assertions.assertThatThrownBy(() -> createJobOfferUseCase.handle(command, createJobEventProducer))
                    .isInstanceOf(JobOfferMissingInformationException.class);
            default -> throw new IllegalArgumentException("Unknown exception: " + exceptionName);
        }

    }
}

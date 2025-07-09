package com.demo.medicrecruitment.job.exception;

public class JobOfferMissingInformationException extends Exception {

    public JobOfferMissingInformationException() {
        super("Couldn't create job offer due to missing information");
    }

    public JobOfferMissingInformationException(String param, Throwable cause) {
        super(String.format("Couldn't create job offer due to missing information, param missing : %s",param), cause);
    }
}

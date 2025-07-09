package com.demo.medicrecruitment.user.domain;

import lombok.Getter;

@Getter
public final class UserDomain {
    private final Long id;
    private final String firstName;
    private final String lastName;

    private UserDomain(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static UserDomain.UserBuilder builder() {
        return new UserDomain.UserBuilder();
    }

    public static class UserBuilder {

        private Long id;
        private String firstName;
        private String lastName;

        public UserBuilder() {}

        public UserBuilder(UserDomain userDomain) {
            this.id = userDomain.getId();
            this.firstName = userDomain.getFirstName();
            this.lastName = userDomain.getLastName();
        }

        public UserBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDomain build() {
            return new UserDomain(id, firstName, lastName);
        }
    }
}

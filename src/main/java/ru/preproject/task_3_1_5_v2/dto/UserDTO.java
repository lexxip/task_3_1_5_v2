package ru.preproject.task_3_1_5_v2.dto;

import ru.preproject.task_3_1_5_v2.model.Role;

import java.util.Set;

public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Byte age;
    private String email;
    private String password;
    private Set<Long> roles;

    public UserDTO() {
    }

    private UserDTO(Long id, String firstName, String lastName, Byte age, String email, String password, Set<Long> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public static class UserDTOBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private Byte age;
        private String email;
        private String password;
        private Set<Long> roles;

        public UserDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDTOBuilder age(Byte age) {
            this.age = age;
            return this;
        }

        public UserDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDTOBuilder password(String password) {
            this.password = "";
            return this;
        }

        public UserDTOBuilder roles(Set<Long> roles) {
            this.roles = roles;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(id, firstName, lastName, age, email, password, roles);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getRoles() {
        return roles;
    }

    public void setRoles(Set<Long> roles) {
        this.roles = roles;
    }
}

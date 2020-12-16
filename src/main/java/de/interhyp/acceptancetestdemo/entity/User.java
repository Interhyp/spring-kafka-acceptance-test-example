package de.interhyp.acceptancetestdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Accessors(fluent = true)
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;

    @Builder
    public User(final String id, final String name) {
        final String[] split = name.split(" ");
        this.id = id;
        this.firstName = split[0];
        this.lastName = split[1];
    }

    public String name() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
    }
}

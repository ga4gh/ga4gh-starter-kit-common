package org.ga4gh.starterkit.common.testutil;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;

@Entity
@Table(name = "student")
public class Student implements HibernateEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "enrolled")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime enrolled;

    /* Constructors */

    public Student() {

    }

    /* Setters and Getters */

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEnrolled(LocalDateTime enrolled) {
        this.enrolled = enrolled;
    }

    public LocalDateTime getEnrolled() {
        return enrolled;
    }

    @Override
    public void loadRelations() {

    }
}

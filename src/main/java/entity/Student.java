package entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = true, length = 15)
    private String name;

    @Column(name = "middle", nullable = true, length = 20)
    private String middle;

    @Column(name = "surname", nullable = true, length = 45)
    private String surname;

    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @Column(name = "phone", nullable = true, length = 15)
    private String phone;

    @Column(name = "adress", nullable = true, length = 150)
    private String adress;

    @Column(name = "description", nullable = true, length = 200)
    private String description;

    @OneToMany(mappedBy = "student")
    private List<RecordOnCourse> studgroups = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RecordOnCourse> getStudgroups() {
        return studgroups;
    }

    public void setStudgroups(List<RecordOnCourse> studgroups) {
        this.studgroups = studgroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        return new EqualsBuilder()
                .append(id, student.id)
                .append(name, student.name)
                .append(middle, student.middle)
                .append(surname, student.surname)
                .append(email, student.email)
                .append(phone, student.phone)
                .append(adress, student.adress)
                .append(description, student.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(middle)
                .append(surname)
                .append(email)
                .append(phone)
                .append(adress)
                .append(description)
                .toHashCode();
    }
}

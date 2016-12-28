package entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Lecturer {
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

    @Column(name = "description", nullable = true, length = 200)
    private String description;

    @OneToMany(mappedBy = "lecturerId")
    private List<Course> courses;

    @Transient
    private String abbreviation;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getAbbreviation() {
        setAbbreviation();
        return abbreviation;
    }

    public void setAbbreviation() {
        this.abbreviation = surname + " " + name.charAt(0) + ". " + middle.charAt(0) +  ". " ;;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Lecturer)) return false;

        Lecturer lecturer = (Lecturer) o;

        return new org.apache.commons.lang.builder.EqualsBuilder()
                .append(id, lecturer.id)
                .append(name, lecturer.name)
                .append(middle, lecturer.middle)
                .append(surname, lecturer.surname)
                .append(email, lecturer.email)
                .append(phone, lecturer.phone)
                .append(description, lecturer.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang.builder.HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(middle)
                .append(surname)
                .append(email)
                .append(phone)
                .append(description)
                .toHashCode();
    }

    @Override
    public String toString() {
        return surname + " " + name.charAt(0) + ". " + middle.charAt(0) +  ". " ;
    }
}

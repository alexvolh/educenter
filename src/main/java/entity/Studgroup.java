package entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Studgroup implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @ManyToOne
    private Course course;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy = "studgroup")
    private List<RecordOnCourse> students = new ArrayList<>();

    public Studgroup() {
    }

    public Studgroup(String name, Course courseId, String description) {
        this.name = name;
        this.course = courseId;
        this.description = description;
    }

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course courseId) {
        this.course = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RecordOnCourse> getStudents() {
        return students;
    }

    public void setStudents(List<RecordOnCourse> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Studgroup studgroup = (Studgroup) o;

        return new EqualsBuilder()
                .append(id, studgroup.id)
                .append(name, studgroup.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Studgroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", course=" + course +
                ", description='" + description + '\'' +
                '}';
    }
}

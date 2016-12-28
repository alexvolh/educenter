package entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import service.TimeService;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Course implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    private Lecturer lecturerId;

    @Column(name = "title", nullable = true, length = 150)
    private String title;

    @Column(name = "start", nullable = true)
    private Date start;

    @Column(name = "end", nullable = true)
    private Date end;

    @Column(name = "duration", nullable = true)
    private String duration;

    @Column(name = "description", nullable = true, length = 200)
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Transient
    private String formattedStartTime;

    @Transient
    private String formattedEndTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lecturer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Lecturer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormattedStartTime() {
        this.setFormattedStartTime();
        return formattedStartTime;
    }

    private void setFormattedStartTime() {
        this.formattedStartTime = "c " + TimeService.formattedDate(this.start);
    }

    public String getFormattedEndTime() {
        this.setFormattedEndTime();
        return formattedEndTime;
    }

    private void setFormattedEndTime() {
        this.formattedEndTime = "по " + TimeService.formattedDate(this.end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        return new EqualsBuilder()
                .append(id, course.id)
                .append(lecturerId, course.lecturerId)
                .append(title, course.title)
                .append(start, course.start)
                .append(end, course.end)
                .append(duration, course.duration)
                .append(description, course.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(lecturerId)
                .append(title)
                .append(start)
                .append(end)
                .append(duration)
                .append(description)
                .toHashCode();
    }

}

package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Alexander on 26.02.2016.
 */
@Entity
public class Schedule {
    private int id;
    private int studgroupId;
    private Date dateTime;
    private Short lectureHall;
    private String description;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "studgroup_id", nullable = false)
    public int getStudgroupId() {
        return studgroupId;
    }

    public void setStudgroupId(int studgroupId) {
        this.studgroupId = studgroupId;
    }

    @Basic
    @Column(name = "date_time", nullable = true)
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Basic
    @Column(name = "lecture_hall", nullable = true)
    public Short getLectureHall() {
        return lectureHall;
    }

    public void setLectureHall(Short lectureHall) {
        this.lectureHall = lectureHall;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (id != schedule.id) return false;
        if (studgroupId != schedule.studgroupId) return false;
        if (dateTime != null ? !dateTime.equals(schedule.dateTime) : schedule.dateTime != null) return false;
        if (lectureHall != null ? !lectureHall.equals(schedule.lectureHall) : schedule.lectureHall != null)
            return false;
        if (description != null ? !description.equals(schedule.description) : schedule.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + studgroupId;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (lectureHall != null ? lectureHall.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}

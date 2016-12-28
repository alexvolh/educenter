package entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecordOnCourseId implements Serializable {

    @Column(name = "student_id")
    protected Long studentId;

    @Column(name = "studgroup_id")
    protected Long studgroupId;

    public RecordOnCourseId(){}

    public RecordOnCourseId(Long studentId, Long studgroupId) {
        this.studentId = studentId;
        this.studgroupId = studgroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordOnCourseId that = (RecordOnCourseId) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(studgroupId, that.studgroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studgroupId);
    }

    /*    private long studgroupId;

    private long studentId;

    public long getStudgroupId() {
        return studgroupId;
    }

    public void setStudgroupId(long studgroupId) {
        this.studgroupId = studgroupId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int hashCode() {
        return (int)(studentId + studgroupId);
    }

    public boolean equals(Object object) {
        if (object instanceof RecordOnCourseId) {
            RecordOnCourseId otherId = (RecordOnCourseId) object;
            return (otherId.studentId == this.studentId) && (otherId.studgroupId == this.studgroupId);
        }
        return false;
    }*/
}

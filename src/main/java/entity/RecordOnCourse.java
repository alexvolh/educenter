package entity;

import javax.persistence.*;

@Entity
@Table(name = "RECORD_ON_COURSE")
public class RecordOnCourse {
    @EmbeddedId
    private RecordOnCourseId id;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "studgroup_id", insertable = false, updatable = false)
    private Studgroup studgroup;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    public RecordOnCourse() {}

    public RecordOnCourse(Student student, Studgroup studgroup, Invoice invoice) {
        this.id = new RecordOnCourseId(student.getId(), studgroup.getId());

        this.student = student;
        this.studgroup = studgroup;
        this.invoice = invoice;

        student.getStudgroups().add(this);
        studgroup.getStudents().add(this);
    }


    public RecordOnCourseId getId() {
        return id;
    }

    public void setId(RecordOnCourseId id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Studgroup getStudgroup() {
        return studgroup;
    }

    public void setStudgroup(Studgroup studgroup) {
        this.studgroup = studgroup;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoiceId) {
        this.invoice = invoiceId;
    }
}

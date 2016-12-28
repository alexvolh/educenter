package bean;

import dao.CourseDao;
import dao.LecturerDao;
import entity.Course;
import entity.Lecturer;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Named
@javax.enterprise.context.SessionScoped
public class CourseBean implements Serializable {
    @EJB
    LecturerDao lecturerDao;
    @EJB
    CourseDao courseDao;

    @Inject
    StudgroupBean studgroupBean;

    private String title;
    private Date startCourse;
    private Date endCourse;
    private String duration;
    private BigDecimal price;
    private String description;

    private Course course;
    private Course selectedCourse;
    private Course[] selectedCourses;
    private List<Course> courses;
    private List<Course> filteredCourses;
    private List<Lecturer> lecturers;
    private HashMap<String,Lecturer> menuLecturers;
    private Lecturer selectedLecturer;


    @PostConstruct
    public void init() {
        courses = courseDao.getCourseList();
        lecturers = new ArrayList<>(lecturerDao.getLecturerList());
        menuLecturers = new HashMap<>();
        for (Lecturer lecturer : lecturers) {
            menuLecturers.put(lecturer.getAbbreviation(),
                    lecturer);
        }
    }

    public void addCourse() {
        course = new Course();
        course.setTitle(title);
        course.setStart(startCourse);
        course.setEnd(endCourse);
        course.setPrice(price);
        course.setDuration(duration);
        course.setLecturerId(selectedLecturer);
        course.setDescription(description);

        courseDao.saveCourse(course);
        courses.add(course);
        studgroupBean.updateCourseMap(courses);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        NavigationBean navigationBean = (NavigationBean) elContext.getELResolver().getValue(elContext,null,"navigationBean");
        navigationBean.setNaviPage("course_info");
    }

    public void resetInput() {
        title = null;
        startCourse = null;
        endCourse = null;
        duration = null;
        description = null;
        RequestContext.getCurrentInstance().reset(":Include:courseForm:coursePanel");

    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartCourse() {
        return startCourse;
    }

    public void setStartCourse(Date startCourse) {
        this.startCourse = startCourse;
    }

    public Date getEndCourse() {
        return endCourse;
    }

    public void setEndCourse(Date endCourse) {
        this.endCourse = endCourse;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public List<Course> getFilteredCourses() {
        return filteredCourses;
    }

    public void setFilteredCourses(List<Course> filteredCourses) {
        this.filteredCourses = filteredCourses;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public Lecturer getSelectedLecturer() {
        return selectedLecturer;
    }

    public void setSelectedLecturer(Lecturer selectedLecturer) {
        this.selectedLecturer = selectedLecturer;
    }

    public HashMap<String, Lecturer> getMenuLecturers() {
        return menuLecturers;
    }

    public void setMenuLecturers(HashMap<String, Lecturer> menuLecturers) {
        this.menuLecturers = menuLecturers;
    }

    public void clearSelectedCourses() {
        selectedCourses = null;
    }

    public Course[] getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(Course[] selectedCourses) {
        this.selectedCourses = selectedCourses;
    }

    public void updateMenuLecturers() {
        setLecturers(lecturerDao.getLecturerList());
        menuLecturers.clear();
        for (Lecturer lecturer : lecturers) {
            menuLecturers.put(lecturer.getAbbreviation(),
                    lecturer);
        }
    }

}

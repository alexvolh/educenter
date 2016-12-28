package bean;

import dao.LecturerDao;
import entity.Course;
import entity.Lecturer;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class LecturerBean implements Serializable {
    @EJB
    private LecturerDao lecturerDao;
    private Lecturer lecturer;
    private Lecturer selectedLecturer;
    private List<Lecturer> lecturers;
    private List<Lecturer> filteredLecturers;
    private List<Course> currentCourses;

    private String name;
    private String surname;
    private String middle;
    private String email;
    private String phone;
    private String description;

    @Inject
    CourseBean courseBean;

    @PostConstruct
    public void init() {
        try {
            lecturers = new ArrayList<>(lecturerDao.getLecturerList());
        } catch (Exception exc) {
            System.err.println("Error load lecturer list:  " + exc);
        }
        currentCourses = new ArrayList<>();
//        selectedLecturer = new Lecturer();
    }

    public void addLecturer() {
        lecturer = new Lecturer();
        lecturer.setName(name);
        lecturer.setSurname(surname);
        lecturer.setMiddle(middle);
        lecturer.setEmail(email);
        lecturer.setPhone(phone);
        lecturer.setDescription(description);
        try {
            lecturerDao.saveLecturer(lecturer);
        } catch (Exception exc) {
            System.err.println("Error add new lecturer: " + exc);
        }
        lecturers.add(lecturer);
        resetTableFilters();
        courseBean.updateMenuLecturers();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
  /*      CourseBean courseBean = (CourseBean) elContext.getELResolver().getValue(elContext,null,"courseBean");
        courseBean.updateMenuLecturers();*/
        NavigationBean navigationBean = (NavigationBean) elContext.getELResolver().getValue(elContext,null,"navigationBean");
        navigationBean.setNaviPage("lecturer_info");

    }

    public void updateLecturer() {
       try {
            lecturers.remove(selectedLecturer);
            lecturers.add(selectedLecturer);
            lecturerDao.updateLecturer(selectedLecturer);
        } catch (Exception exc) {
            System.err.println("Error update lecturer: " + exc);
        }

        resetTableFilters();
    }

    public  void deleteLecturer() {
        try {
            lecturerDao.deleteLecturer(selectedLecturer);
            lecturers.remove(selectedLecturer);
            courseBean.updateMenuLecturers();
        } catch (Exception exc) {
            System.err.println("Error delete lecturer: " + exc);
        }
        resetTableFilters();
    }

    private void resetTableFilters() {
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":Include:FormLecture:TableLecturer");
        if (dataTable != null) {
            dataTable.resetValue();
            dataTable.reset();
            dataTable.setFilters(null);
        }
    }

    public void resetInput() {
        name = null;
        surname = null;
        middle = null;
        email = null;
        phone = null;
        description = null;
        RequestContext.getCurrentInstance().reset(":Include:InputForm:InputPanel");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
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

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Lecturer getSelectedLecturer() {
        return selectedLecturer;
    }

    public void setSelectedLecturer(Lecturer selectedLecturer) {
        this.selectedLecturer = selectedLecturer;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public List<Lecturer> getFilteredLecturers() {
        return filteredLecturers;
    }

    public void setFilteredLecturers(List<Lecturer> filteredLecturers) {
        this.filteredLecturers = filteredLecturers;
    }

    public List<Course> getCurrentCourses() {
        return currentCourses;
    }

    public void setCurrentCourses() {
        currentCourses = courseBean.getCourses().stream().filter(l ->
                l.getLecturerId().getSurname().equals(selectedLecturer.getSurname())).collect(Collectors.toList());
    }
}

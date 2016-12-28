package bean;

import dao.CourseDao;
import dao.StudgroupDao;
import entity.Course;
import entity.RecordOnCourse;
import entity.Student;
import entity.Studgroup;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class StudgroupBean implements Serializable {
    @EJB
    CourseDao courseDao;
    @EJB
    StudgroupDao studgroupDao;

    @Inject
    CourseBean courseBean;

    private String name;
    private String selectedName;
    private String oldSelectedName;
    private String selectedDescription;
    private String currentDescription;
    private String description;
    private String courseTitle;

    private DualListModel<Course> courseDualListModel;
    private Map<String,Course> courseMap;
    private Map<String, List<Studgroup>> studgroupMap;

    private List<Course> courses;
    private List<Course> selectedCourses;
    private Studgroup selectedCourse;

    private List<String> nameGroup;
    private List<Studgroup> studgroups;
    private List<Studgroup> selectedStudgroups;
    private List<Studgroup> currentCourses;
    private List<RecordOnCourse> recordOnCourseStudents;
    private List<Student> currentStudents;

    @PostConstruct
    public void init() {
        courses = new ArrayList<>(courseDao.getCourseList());
        selectedCourses = new ArrayList<>();

        courseMap = new HashMap<>();
        for (Course course : courses) {
            courseMap.put(course.getTitle(), course);
        }
        currentStudents = new ArrayList<>();
        courseDualListModel = new DualListModel<>(courses,selectedCourses);

        studgroups = studgroupDao.getStudgroupList();
        if(! studgroups.isEmpty()) {
            studgroupMap = new HashMap<>(studgroups.stream().collect(Collectors.groupingBy(Studgroup::getName)));
            nameGroup = new ArrayList<>(studgroupMap.keySet());
            setCurrentCourses(nameGroup.get(0));
            selectedDescription = currentCourses.get(0).getDescription();
        } else if (studgroups.isEmpty()){
            studgroupMap = new HashMap<>();
            nameGroup = new ArrayList<>();
        }
    }

    public void gotoListStudgroups() {
        if (studgroupMap.isEmpty()) {
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Внимание!", "Отсутствуют группы...."));

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            NavigationBean navigationBean = (NavigationBean) elContext.getELResolver().getValue(elContext, null, "navigationBean");
            navigationBean.setNaviPage("list_studgroups");
        } else {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            NavigationBean navigationBean = (NavigationBean) elContext.getELResolver().getValue(elContext, null, "navigationBean");
            navigationBean.setNaviPage("list_studgroups");
        }
    }

    public void addStudgroup() {
        List<Studgroup> studgroups = new ArrayList<>();
        Studgroup loopStudgroup;
        // add studgroup to DB in "for each" loop
        for(Course course : selectedCourses) {
            loopStudgroup = new Studgroup(name, course, description);
            studgroups.add(loopStudgroup);
            studgroupDao.saveStudgroup(loopStudgroup);
        }
        // list of name's student group
        studgroupMap.put(this.name, studgroups);
        nameGroup.add(this.name);

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        NavigationBean navigationBean = (NavigationBean) elContext.getELResolver().getValue(elContext,null,"navigationBean");
        navigationBean.setNaviPage("studgroup_info");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Course> getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(List<Course> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }

    public DualListModel<Course> getCourseDualListModel() {
        return courseDualListModel;
    }

    public void setCourseDualListModel(DualListModel<Course> courseDualListModel) {
        this.courseDualListModel = courseDualListModel;
    }

    public Map<String, Course> getCourseMap() {
        return courseMap;
    }

    public void setCourseMap(Map<String, Course> courseMap) {
        this.courseMap = courseMap;
    }

    void updateCourseMap(List<Course> courses) {
        courseMap.clear();
        for (Course course : courses) {
            courseMap.put(course.getTitle(), course);
        }
        courseDualListModel.setSource(courses);
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

//=======   Link courses with studgroup on tranfer evnt
    public void onTransfer(TransferEvent transferEvent) {
        selectedCourses.clear();
        for (Course course : courseDualListModel.getTarget()) {
            selectedCourses.add(course);
        }
    }
    public void resetInput() {
        this.name = null;
        this.description = null;

        selectedCourses.clear();
        courseDualListModel.setSource(courses);
        courseDualListModel.setTarget(selectedCourses);
    }

/*    public Map<String, List<Studgroup>> getStudgroupMap() {
        return studgroupMap;
    }*/

 /*   public void setStudgroupMap(Map<String, List<Studgroup>> studgroupMap) {
        this.studgroupMap = studgroupMap;
    }*/

    public List<String> getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(List<String> nameGroup) {
        this.nameGroup = nameGroup;
    }

    //  *   map all courses of current group on select event   *   //
    public void onSelect(SelectEvent  event) {
        String key = event.getObject().toString();
        setCurrentCourses(key);
        selectedDescription = currentCourses.get(0).getDescription();
    }

    public void setCurrentCourses(String key) {
        currentCourses = studgroupMap.get(key);
    }

    public List<Studgroup> getCurrentCourses() {
        return currentCourses;
    }

    //  *   get current name of selected studgroup   *    //
    public void updSelectedStudgroupName() {
        selectedName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("studgroupName");
        oldSelectedName = selectedName;
        selectedDescription = currentCourses.get(0).getDescription();
    }

    public void addSelectedStudgroupName() {
        selectedName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("studgroupName");
        courseBean.clearSelectedCourses();
    }

    public void delSelectedStudgroupName() {
        selectedName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("studgroupName");
    }
    //====================================================================================================================//

    public void deleteSelectedStudgroup() {
        studgroupDao.deleteStudgroupByName(selectedName);
        updateViewPageGroups();
    }

    public void deleteCurrentCourseFromGroup(Studgroup studgroup) {
        studgroupDao.deleteStudgroup(studgroup);
        currentCourses.remove(studgroup);
        studgroupMap = studgroupDao.getStudgroupList().stream().collect(Collectors.groupingBy(Studgroup::getName));
        nameGroup = new ArrayList<>(studgroupMap.keySet());
        setCurrentCourses(studgroup.getName());
        RequestContext.getCurrentInstance().update("Include");
    }
    //  *   update selected studgroup  *    //
    public void updateSelectedStudgroup() {
            studgroupDao.updateListStudgroupByName(oldSelectedName, selectedName, selectedDescription);
            updateViewPageGroups();
    }

    //  *   add courses to current studgroup  *    //
    public void addCoursesToGroup() {
        Course course;
        boolean isNotMatch = true;

        if(courseBean.getSelectedCourses().length == 0) {
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Внимание!", "Не выбран не один из курсов"));
            isNotMatch = false;
        } else if (courseBean.getSelectedCourses().length != 0) {
            for (int i = 0; i < courseBean.getSelectedCourses().length; i++) {
                course = courseBean.getSelectedCourses()[i];
                for (Studgroup currentCourse : currentCourses) {
                    if (currentCourse.getCourse().equals(course)) {
                        FacesContext.getCurrentInstance().
                                addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "Ошибка!", "Выбранные курсы уже есть в списке: " + course.getTitle()));
                        RequestContext.getCurrentInstance().execute("PF('selectDatatable').unselectAllRows()");
                        isNotMatch = false;
                        break;
                    }
                }
            }
        }

        if (isNotMatch) {
            for (int i = 0; i < courseBean.getSelectedCourses().length; i++) {
                studgroupDao.saveStudgroup(new Studgroup(currentCourses.get(0).getName(), courseBean.getSelectedCourses()[i], currentCourses.get(0).getDescription()));
            }
            updateViewPageGroups();
            RequestContext.getCurrentInstance().execute("PF('addCourseDialog').hide()");
        }
    }

    //  *   update views components on studgroup page  *    //
    private void updateViewPageGroups() {
        studgroupMap = studgroupDao.getStudgroupList().stream().collect(Collectors.groupingBy(Studgroup::getName));
        nameGroup = new ArrayList<>(studgroupMap.keySet());
        setCurrentCourses(selectedName);
        RequestContext.getCurrentInstance().update("Include");
    }

    public Studgroup getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Studgroup selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public String getSelectedName() {
        return selectedName;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    public String getOldSelectedName() {
        return oldSelectedName;
    }

    public void setOldSelectedName(String oldSelectedName) {
        this.oldSelectedName = oldSelectedName;
    }

    public String getSelectedDescription() {
        return selectedDescription;
    }

    public void setSelectedDescription(String selectedDescription) {
        this.selectedDescription = selectedDescription;
    }

    public String getCurrentDescription() {
        return currentDescription;
    }

    public void setCurrentDescription(String currentDescription) {
        this.currentDescription = currentDescription;
    }

    public List<Studgroup> getStudgroups() {
        return studgroups;
    }

    public void setStudgroups(List<Studgroup> studgroups) {
        this.studgroups = studgroups;
    }

    public List<Student> getCurrentStudents() {
        return currentStudents;
    }

    public void setCurrentStudents(List<Student> currentStudents) {
        this.currentStudents = currentStudents;
    }

    public List<RecordOnCourse> getRecordOnCourseStudents() {
        return recordOnCourseStudents;
    }

    public void setRecordOnCourseStudents(List<RecordOnCourse> recordOnCourseStudents) {
        this.recordOnCourseStudents = recordOnCourseStudents;
    }

    public List<Studgroup> getSelectedStudgroups() {
        return selectedStudgroups;
    }

    public void setSelectedStudgroups(List<Studgroup> selectedStudgroups) {
        this.selectedStudgroups = selectedStudgroups;
    }

    public void getStudentsOfGroup() {
        String nameStudgroup =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("studgroupName");

        if(studgroups.stream().anyMatch(studgroup -> studgroup.getName().contains(nameStudgroup))) {
            selectedStudgroups = studgroups.stream().filter(studgroup -> studgroup.getName().contains(nameStudgroup)).collect(Collectors.toList());
        }

        List<Student> listWithDublicateStudents = new ArrayList<>();


        for (Studgroup selectedStudgroup : selectedStudgroups) {
            if (selectedStudgroup.getStudents().size() > 0) {
                for (int j = 0; j < selectedStudgroup.getStudents().size(); j++) {
                    listWithDublicateStudents.add(selectedStudgroup.getStudents().get(j).getStudent());
                }
            }
        }

        if (listWithDublicateStudents.size() > 0) {
           currentStudents = listWithDublicateStudents.parallelStream().distinct().collect(Collectors.toList());
        }




/*        for (Student printStudent : currentStudents) {
            System.out.println(printStudent.getName() + " " +printStudent.getMiddle() + " " + printStudent.getSurname() );
        }*/
    }
}
package bean;

import dao.*;
import entity.*;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class StudentBean implements Serializable {
    @EJB
    private StudentDao studentDao;
    @EJB
    private RecordCourseDao recordCourseDao;
    @EJB
    private StudgroupDao studgroupDao;
    @EJB
    private InvoiceDao invoiceDao;
    @EJB
    private CourseDao courseDao;

    @Inject
    CourseBean courseBean;
    @Inject
    StudgroupBean studgroupBean;
    @Inject
    NavigationBean navigationBean;


    private Student student;
    private Student selectedStudent;
    private Course selectedCourse;
    private Studgroup selectedStudgroup;
    private Invoice invoice;
    private String printNameStudgroup;
    private String color;
    private String rendered;

    private List<Student> students;
    private List<Student> filteredStudents;
    private String name;
    private String middle;
    private String surname;
    private String email;
    private String phone;
    private String adress;
    private String description;
    private String recordDescription;

    private BigDecimal allSum;
    private BigDecimal paydSum;
    private int procent;
    private double partProcent;
    private boolean displayPaydSum;
    private boolean displayButton;
    private boolean displayCheckBox;
    private boolean prevent;
    private String infoPay;

    private String photoName;
    private List<RecordOnCourse> studgroups;
    private List<Course> courseList;
    private List<Studgroup> studgroupList;
    String pathToCamFolder;

    @PostConstruct
    public void init() {
        try {
            students = new ArrayList<>(studentDao.getStudentList());
        } catch (Exception exc) {
            System.err.println("Error load student list:  " + exc);
        }
        studgroups = new ArrayList<>();
        courseList = new ArrayList<>();
        studgroupList = new ArrayList<>();

        allSum = new BigDecimal(1200);
        paydSum = new BigDecimal(0);
        procent = 50;

        this.setInfoPay();

        displayPaydSum = true;
        displayButton = true;
        displayCheckBox = false;
        rendered = "true";
        prevent = false;
        pathToCamFolder = "C:" + File.separator + "photo_webcam" + File.separator;
    }

    public void addStudent() {
        student = new Student();
        student.setName(name);
        student.setMiddle(middle);
        student.setSurname(surname);
        student.setEmail(email);
        student.setPhone(phone);
        student.setAdress(adress);
        student.setDescription(description);

        try {
            studentDao.saveStudent(student);
        } catch (Exception exc) {
            System.err.println("Error add new student: " + exc);
        }

        students.add(student);
        resetTableFilters();

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        NavigationBean navigationBean = (NavigationBean) elContext.getELResolver().getValue(elContext,null,"navigationBean");
        navigationBean.setNaviPage("student_info");

    }

    public void updateStudent() {
        try {
            students.remove(selectedStudent);
            students.add(selectedStudent);
            studentDao.updateLecturer(selectedStudent);
        } catch (Exception exc) {
            System.err.println("Error update student: " + exc);
        }

        resetTableFilters();
    }

    public  void deleteStudent() {
        try {
            studentDao.deleteStudent(selectedStudent);
            students.remove(selectedStudent);
        } catch (Exception exc) {
            System.err.println("Error delete student: " + exc);
        }

        resetTableFilters();
    }

 // Record student on Course
    public void recordStudentCourse() {
       if (recordCourseDao.isContain(selectedStudent, selectedStudgroup)) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                        "Запись существует",  "Студент уже записан на данный курс: "+ selectedCourse.getTitle()));
        } else {
           invoice = new Invoice();
           invoice.setAllSum(allSum);
           invoice.setFactSum(paydSum);
           invoice.setDescription(recordDescription);
           if (paydSum.intValue() == allSum.intValue()) {
               invoice.setPaid(true);
           } else {
               invoice.setPaid(false);
           }

           invoiceDao.saveInvoice(invoice);
           RecordOnCourse recordOnCourse = new RecordOnCourse(selectedStudent, selectedStudgroup, invoice);
           recordCourseDao.saveRecCourse(recordOnCourse);
           navigationBean.setNaviPage("record_on_course_info");
        }
    }

    private void resetTableFilters() {
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":Include:FormStudent:TableStudent");
        if (dataTable != null) {
            dataTable.resetValue();
            dataTable.reset();
            dataTable.setFilters(null);
        }
    }

    public void oncapture(CaptureEvent captureEvent) {
        photoName = getRandomPhotoName();
        byte[] data = captureEvent.getData();

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        String newFileName = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "photocam" + File.separator + photoName + ".jpeg";
        String tmpPath = pathToCamFolder;
        pathToCamFolder += photoName + ".jpeg";
        tmpPath += photoName + ".jpeg";

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(tmpPath));
            imageOutput.write(data, 0, data.length);
            System.out.println(pathToCamFolder);
            imageOutput.close();
        }
        catch(IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }

    }

    public void resetInput() {
        name = null;
        surname = null;
        middle = null;
        email = null;
        phone = null;
        adress = null;
        description = null;
        RequestContext.getCurrentInstance().reset(":Include:InputForm:InputPanel");
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public String getRecordDescription() {
        return recordDescription;
    }

    public void setRecordDescription(String recordDescription) {
        this.recordDescription = recordDescription;
    }

    public BigDecimal getAllSum() {
        return allSum;
    }

    public void setAllSum(BigDecimal allSum) {
        this.allSum = allSum;
    }

    public BigDecimal getPaydSum() {
        return paydSum;
    }

    public void setPaydSum(BigDecimal paydSum) {
        this.paydSum = paydSum;
    }

    public int getProcent() {
        return procent;
    }

    public void setProcent(int procent) {
        this.procent = procent;
    }

    public boolean isPrevent() {
        return prevent;
    }

    public void setPrevent(boolean prevent) {
        this.prevent = prevent;
    }

    public boolean getReversePrevent() {
        return prevent;
    }

    public void preventToCourse() {
        if (prevent) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Введите описание",  "Не меньше 10 символов "));
            rendered = "true";
        } else {
            recordDescription = "";
            rendered = "false";
            displayButton = true;
        }
    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    public boolean isDisplayButton() {
        return displayButton;
    }

    public void setDisplayButton(boolean displayButton) {
        this.displayButton = displayButton;
    }

    public boolean isDisplayCheckBox() {
        return displayCheckBox;
    }

    public void setDisplayCheckBox(boolean displayCheckBox) {
        this.displayCheckBox = displayCheckBox;
    }

    public String getInfoPay() {
        this.setInfoPay();
        return infoPay;
    }

    private void setInfoPay() {
        if (! prevent) {
            if (paydSum != null) {
                if (!(paydSum.doubleValue() > allSum.doubleValue())) {
                    if (paydSum.doubleValue() > 0) {
                        partProcent = (100 * paydSum.doubleValue()) / allSum.doubleValue();
                        if (paydSum.doubleValue() == allSum.doubleValue()) {
                            infoPay = "ВНЕСЕНА ВСЯ СУММА";
                            color = "green";
                            displayCheckBox = true;
                            if (!getStudgroupList().isEmpty()) {
                                displayButton = false;
                            }
                        } else if (partProcent == procent || partProcent > procent) {
                            infoPay = "Проплата составляет " + String.format("%.0f", partProcent) + " %. Внесена половина или большая часть от общей суммы (студент допускается до курса)";
                            color = "green";
                            displayCheckBox = true;
                            if (!getStudgroupList().isEmpty())
                                displayButton = false;
                        } else {
                            infoPay = "Проплата составляет " + String.format("%.0f", partProcent) + " % от общей суммы (студент не допускается до курса)";
                            color = "red";
                            displayButton = true;
                            displayCheckBox = false;
                        }
                    } else if (paydSum.doubleValue() == 0) {
                        displayButton = true;
                        infoPay = "Первоначальный взнос = 0";
                        displayCheckBox = false;
                        color = "red";
                    }
                } else if (paydSum.doubleValue() > allSum.doubleValue()) {
                    infoPay = "Проплата больше чем стоимость курса !!!";
                    color = "red";
                    displayButton = true;
                    displayCheckBox = true;
                }
            } else {
                infoPay = "Не введено значение !!!";
                displayButton = true;
                displayCheckBox = true;
                color = "red";
            }
        }
    }

    public void setPaydSumToNull() {
        if (displayPaydSum) {
            this.paydSum = BigDecimal.valueOf(0);
            this.partProcent = 0;
        }

        if(prevent) {
            recordDescription = "";
        }
    }

    public boolean isDisplayPaydSum() {
        return displayPaydSum;
    }

    public void setDisplayPaydSum(boolean displayPaydSum) {
        this.displayPaydSum = displayPaydSum;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public List<Student> getFilteredStudents() {
        return filteredStudents;
    }

    public void setFilteredStudents(List<Student> filteredStudents) {
        this.filteredStudents = filteredStudents;
    }

    public String getPhotoName() {
        return photoName;
    }

    private String getRandomPhotoName() {
        int i = (int) (Math.random() * 10000000);

        return String.valueOf(i);
    }

    public String getPathToCamFolder() {
        return pathToCamFolder;
    }

    public void setPathToCamFolder(String pathToCamFolder) {
        this.pathToCamFolder = pathToCamFolder;
    }

    public List<RecordOnCourse> getStudgroups() {
        return studgroups;
    }

    public void setStudgroupsPersist() {
        this.studgroups = selectedStudent.getStudgroups();
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public Studgroup getSelectedStudgroup() {
        return selectedStudgroup;
    }

    public void setSelectedStudgroup(Studgroup selectedStudgroup) {
        this.selectedStudgroup = selectedStudgroup;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public List<Studgroup> getStudgroupList() {
            return studgroupList.stream().filter(p -> p.getCourse().equals(selectedCourse)).collect(Collectors.toList());
    }

    public void setStudgroupList() {
        this.studgroupList = studgroupDao.getStudgroupList();
    }

    public String getPrintNameStudgroup() {
        return printNameStudgroup;
    }

    public void setPrintNameStudgroup() {
        this.printNameStudgroup = selectedStudgroup.getName();
    }

    public void gotoRecord() {
        if(selectedStudent != null) {
            courseList = courseBean.getCourses();
            this.setStudgroupList();
            if (! courseList.isEmpty()) {
                selectedCourse = courseList.get(0);
                if (! getStudgroupList().isEmpty()) {
                    selectedStudgroup = getStudgroupList().get(0);
                    printNameStudgroup = selectedStudgroup.getName();
                }
            }
            navigationBean.setNaviPage("record_on_course");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Не выбран студент",null));
        }
    }

    public void checkLinkedStudgroup() {
        allSum = selectedCourse.getPrice();

        if (! getStudgroupList().isEmpty()) {
            selectedStudgroup = getStudgroupList().get(0);
            printNameStudgroup = selectedStudgroup.getName();
            displayButton = false;
        }else if (getStudgroupList().isEmpty()) {
            displayButton = true;
            printNameStudgroup = "Отсутствует связанная группа";
        } else if (paydSum.doubleValue() == allSum.doubleValue() || (partProcent == procent || partProcent > procent)) {
                displayButton = false;
        }
    }

    public void checkDescription() {
        if (recordDescription.length() >= 10 & (! getStudgroupList().isEmpty())) {
                displayButton = false;
        } else if (recordDescription.length() < 10){
            displayButton = true;
        }
    }

}


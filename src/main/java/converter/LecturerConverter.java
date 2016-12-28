package converter;

import entity.Lecturer;
import bean.CourseBean;


import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;


@FacesConverter(value = "converter.LecturerConverter")
public class LecturerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            CourseBean course = (CourseBean) elContext.getELResolver().getValue(elContext, null, "courseBean");
            return course.getMenuLecturers().get(value);
        } catch (Exception exc) {
            throw new ConverterException((new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid lecturer.")));
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        } else {
            return String.valueOf(((Lecturer) value).getAbbreviation());
        }
    }
}

package converter;

import entity.Course;
import bean.StudgroupBean;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converter.CourseConverter")
public class CourseConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            StudgroupBean studgroupBean = (StudgroupBean) elContext.getELResolver().getValue(elContext, null, "studgroupBean");
            return studgroupBean.getCourseMap().get(value);
        } catch (Exception exc) {
            throw new ConverterException((new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid studgroup.")));
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")){
            return "";
        } else {
           return String.valueOf(((Course) value).getTitle());
        }
    }
}

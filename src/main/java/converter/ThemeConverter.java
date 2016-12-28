package converter;

import bean.UserSettingsBean;
import service.Theme;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ThemeConverter implements Serializable, Converter {
    @Inject
    UserSettingsBean userSettingsBean;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Theme) value).getName();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Theme> themes = userSettingsBean.getThemes();
        for (Theme theme : themes) {
            if (theme.getName().equals(value)) {
                return theme;
            }
        }
        return null;
    }
}

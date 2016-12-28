package bean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class NavigationBean {
    private String naviPage;

    NavigationBean() {
        naviPage = "add_student";
    }

    public String getNaviPage() {
        return naviPage;
    }

    public void setNaviPage(String naviPage) {
        this.naviPage = naviPage;
    }

}

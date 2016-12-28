package bean;

import service.Theme;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class UserSettingsBean implements Serializable {
    private List<Theme> themes;
    private Theme theme;

    @PostConstruct
    public void init() {
        themes = new ArrayList<>();
        themes.add(new Theme("Afterdark", "afterdark"));
        themes.add(new Theme("Afternoon", "afternoon"));
        themes.add(new Theme("Afterwork", "afterwork"));
        themes.add(new Theme("Aristo", "aristo"));
        themes.add(new Theme("Black-Tie", "black-tie"));
        themes.add(new Theme("Blitzer", "blitzer"));
        themes.add(new Theme("Bluesky", "bluesky"));
        themes.add(new Theme("Bootstrap", "bootstrap"));
        themes.add(new Theme("Casablanca", "casablanca"));
        themes.add(new Theme("Cruze", "cruze"));
        themes.add(new Theme("Cupertino", "cupertino"));
        themes.add(new Theme("Dark-Hive", "dark-hive"));
        themes.add(new Theme("Delta", "delta"));
        themes.add(new Theme("Dot-Luv", "dot-luv"));
        themes.add(new Theme("Eggplant", "eggplant"));
        themes.add(new Theme("Excite-Bike", "excite-bike"));
        themes.add(new Theme("Flick", "flick"));
        themes.add(new Theme("Glass-X", "glass-x"));
        themes.add(new Theme("Home", "home"));
        themes.add(new Theme("Hot-Sneaks", "hot-sneaks"));
        themes.add(new Theme("Humanity", "humanity"));
        themes.add(new Theme("Le-Frog", "le-frog"));
        themes.add(new Theme("Midnight", "midnight"));
        themes.add(new Theme("Mint-Choc", "mint-choc"));
        themes.add(new Theme("Overcast", "overcast"));
        themes.add(new Theme("Pepper-Grinder", "pepper-grinder"));
        themes.add(new Theme("Redmond", "redmond"));
        themes.add(new Theme("Rocket", "rocket"));
        themes.add(new Theme("Sam", "sam"));
        themes.add(new Theme("Smoothness", "smoothness"));
        themes.add(new Theme("South-Street", "south-street"));
        themes.add(new Theme("Start", "start"));
        themes.add(new Theme("Sunny", "sunny"));
        themes.add(new Theme("Swanky-Purse", "swanky-purse"));
        themes.add(new Theme("Trontastic", "trontastic"));
        themes.add(new Theme("UI-Darkness", "ui-darkness"));
        themes.add(new Theme("UI-Lightness", "ui-lightness"));
        themes.add(new Theme("Vader", "vader"));

        theme = themes.get(36);
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}


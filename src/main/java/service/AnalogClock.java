package service;

import org.primefaces.extensions.component.analogclock.model.AnalogClockColorModel;
import org.primefaces.extensions.component.analogclock.model.DefaultAnalogClockColorModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.awt.*;
import java.io.Serializable;
import java.util.Date;

@Named
@RequestScoped
public class AnalogClock implements Serializable{

    private static final long serialVersionUID = -5427668306657486626L;
    private AnalogClockColorModel customTheme = new DefaultAnalogClockColorModel();

    public AnalogClock() {

        customTheme.setBorder(Color.RED);
        customTheme.setFace(Color.DARK_GRAY);
        customTheme.setHourHand(Color.WHITE);
        customTheme.setHourSigns(Color.WHITE);
        customTheme.setMinuteHand(Color.WHITE);
        customTheme.setPin(Color.RED);
        customTheme.setSecondHand(Color.WHITE);
    }

    public AnalogClockColorModel getCustomTheme() {
        return customTheme;
    }

    private final Date now = new Date();

    public Date getNow() {
        return now;
    }

}

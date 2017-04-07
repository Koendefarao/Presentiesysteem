package model.persoon;

import java.util.Calendar;

/**
 * Created by EgorDm on 07-Apr-2017.
 */
public class Les {
    private String naam;
    private Calendar datumStart;
    private Calendar datumEind;
    private WeekelijkseLes weekelijkseLes;

    public Les(Calendar datumStart, Calendar datumEind, WeekelijkseLes weekelijkseLes) {
        this.naam = weekelijkseLes.getNaam();
        this.datumStart = datumStart;
        this.datumEind = datumEind;
        this.weekelijkseLes = weekelijkseLes;
    }

    public String getNaam() {
        return naam;
    }

    public Calendar getDatumStart() {
        return datumStart;
    }

    public Calendar getDatumEind() {
        return datumEind;
    }

    public WeekelijkseLes getWeekelijkseLes() {
        return weekelijkseLes;
    }
}

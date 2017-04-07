package model.persoon;

import general.MyUtils;
import model.klas.Klas;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by EgorDm on 07-Apr-2017.
 */
public class WeekelijkseLes {
    private String naam;
    private Calendar datumStart;
    private Calendar datumEind;
    private Klas klas;

    public WeekelijkseLes(String naam, Calendar datumStart, Calendar datumEind, Klas klas) {
        this.naam = naam;
        this.datumStart = datumStart;
        this.datumEind = datumEind;
        this.klas = klas;
    }

    public Calendar getNextStart() {
        return MyUtils.getNextWeekFrom(datumStart, Calendar.getInstance());
    }

    public Calendar getNextStartFrom(Calendar from) {
        return MyUtils.getNextWeekFrom(datumStart, from);
    }

    public Calendar getNextEind() {
        return MyUtils.getNextWeekFrom(datumEind, Calendar.getInstance());
    }

    public Calendar getNextEindFrom(Calendar from) {
        return MyUtils.getNextWeekFrom(datumEind, from);
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

    public boolean hasStudent(String username) {
        for(Student student : klas.getStudenten()) {
            if(student.getGebruikersnaam().equals(username)) return true;
        }
        return false;
    }

    public ArrayList<Student> getStudenten() {
        return klas.getStudenten();
    }
}

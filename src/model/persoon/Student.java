//checked
package model.persoon;

import model.PrIS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class Student extends Persoon {

    private int studentNummer;
    private String groepId;
    protected ArrayList<AbsentieOpname> absenties = new ArrayList<>();

    public Student(
            String pVoornaam,
            String pTussenvoegsel,
            String pAchternaam,
            String pWachtwoord,
            String pGebruikersnaam,
            int pStudentNummer) {
        super(
                pVoornaam,
                pTussenvoegsel,
                pAchternaam,
                pWachtwoord,
                pGebruikersnaam);
        this.setStudentNummer(pStudentNummer);
        this.setGroepId("");
    }


    public String getGroepId() {
        return this.groepId;
    }

    public void setGroepId(String pGroepId) {
        this.groepId = pGroepId;
    }

    public int getStudentNummer() {
        return this.studentNummer;
    }

    private void setStudentNummer(int pStudentNummer) {
        this.studentNummer = pStudentNummer;
    }

    public AbsentieOpname getCurrentAbsentie() {
        Date currentDate = new Date();
        return getAbsentieByDate(currentDate);
    }

    private AbsentieOpname getAbsentieByDate(Date date) {
        for (AbsentieOpname absentie : absenties) {
            if (absentie.isDateWithin(date)) return absentie;
        }
        return null;
    }

    public boolean isAbsent() {
        return getCurrentAbsentie() != null;
    }

    public void setAbsent(AbsentieOpname absentie) throws Exception {
        for (AbsentieOpname abs : absenties) {
            if (abs.isDateWithin(absentie.getStartDatum())
                    || abs.isDateWithin(absentie.getEindDatum())
                    || absentie.isDateWithin(abs.getStartDatum())
                    || absentie.isDateWithin(abs.getEindDatum())) {
                abs.merge(absentie);
                return;
            }
        }
        absenties.add(absentie);
    }

    public void setPresent(Date date) throws Exception {
        AbsentieOpname current = getAbsentieByDate(date);
        if (current == null) throw new Exception("Student is dan al present!");
        current.setEindDatum(date);
    }

    public ArrayList<AbsentieOpname> getAbsenties() {
        Collections.sort(absenties);
        return absenties;
    }

    public ArrayList<Les> getGemisteLessen(PrIS database, Date from, Date till) {
        ArrayList<Les> ret = new ArrayList<>();
        ArrayList<AbsentieOpname> toenAbsenties = new ArrayList<>();
        System.out.println(from.getTime());
        for(AbsentieOpname opname : absenties) {
            if((opname.getEindDatum() == null || opname.getEindDatum().after(from)) && opname.getStartDatum().before(till))
                toenAbsenties.add(opname);
        }
        if(toenAbsenties.isEmpty()) return ret;

        //Voor overzicht he
        ArrayList<WeekelijkseLes> lessen = database.getMyLessen(getGebruikersnaam());
        for(AbsentieOpname opname : toenAbsenties) {
            Calendar startAbs = Calendar.getInstance();
            startAbs.setTime(opname.getStartDatum());

            Calendar eindAbs = Calendar.getInstance();
            if(opname.getEindDatum() == null)
                eindAbs.setTime(new Date());
            else
                eindAbs.setTime(opname.getEindDatum());

            for(WeekelijkseLes les : lessen) {
                Calendar startAbsTemp = (Calendar) startAbs.clone();
                while (les.getNextStartFrom(startAbsTemp).before(eindAbs)) {
                    ret.add(new Les(les.getNextStartFrom(startAbsTemp), les.getNextEindFrom(startAbsTemp), les));
                    startAbsTemp = (Calendar) startAbsTemp.clone();
                    startAbsTemp.add(Calendar.DAY_OF_WEEK, 7);
                }
            }
        }
        return ret;
    }
}

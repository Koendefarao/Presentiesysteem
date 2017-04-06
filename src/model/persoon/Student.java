//checked
package model.persoon;

import java.util.ArrayList;
import java.util.Date;

public class Student extends Persoon {

    private int studentNummer;
    private String groepId;
    protected ArrayList<AbsentieOpname> absenties = new ArrayList<>();
    protected AbsentieOpname huidigeAbsentie;

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

    public boolean isAbsent() {
        if(huidigeAbsentie == null) return false;
        if(huidigeAbsentie.getEindDatum().before(new Date())) {
            huidigeAbsentie = null;
            return false;
        }
        return true;
    }

    public void setAbsent(AbsentieOpname absentie) throws Exception {
        if(huidigeAbsentie != null) throw new Exception("Student is al absent!");
        huidigeAbsentie = absentie;
        absenties.add(absentie);
    }

    public ArrayList<AbsentieOpname> getAbsenties() {
        return absenties;
    }
}

package model.klas;

import java.util.ArrayList;
import java.util.Date;

import model.PrIS;
import model.persoon.Les;
import model.persoon.Student;

public class Klas {

	private String klasCode;
	private String naam;
	private ArrayList<Student> deStudenten = new ArrayList<Student>();

	public Klas(String klasCode, String naam) {
		this.klasCode = klasCode;
		this.naam = naam;
	}
	
	public String getKlasCode() {
		return klasCode;
	}
	
	public String getNaam() {
		return naam;
	}

	public ArrayList<Student> getStudenten() {
		return this.deStudenten;
	}
	
	public boolean bevatStudent(Student pStudent) {
		for (Student lStudent : this.getStudenten()) {
			if (lStudent==pStudent) {
				return true;
			}
		}
		return false;
	}

	public void voegStudentToe(Student pStudent) {
		if (!this.getStudenten().contains(pStudent)) {
			this.getStudenten().add(pStudent);
		}
	}

	public ArrayList<Les> getGemisteLessen(PrIS informatieSysteem, Date from, Date till) {
		ArrayList<Les> ret = new ArrayList<>();
		for(Student student : deStudenten) {
			ret.addAll(student.getGemisteLessen(informatieSysteem, from, till));
		}
		return ret;
	}
}

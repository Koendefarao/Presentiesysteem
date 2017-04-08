package controller;

import general.ChartUtils;
import model.PrIS;
import model.persoon.AbsentieOpname;
import model.persoon.Les;
import model.persoon.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by EgorDm on 07-Apr-2017.
 */
public class UnitTests {
    public static void main(String[] args) throws Exception {
        PrIS database = new PrIS();
        Student student = database.getStudent("robin.aalten@student.hu.nl");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String absS = "2017-03-07 00:00";
        String absE = "2017-03-20 16:00";
        String till = "2017-04-20 00:00";

        student.setAbsent(new AbsentieOpname(format.parse(absS), format.parse(absE)));
        ArrayList<Les> lessen = student.getGemisteLessen(database, format.parse(absS), format.parse(till));

        System.out.println(ChartUtils.chartAbsentiesByMonth(lessen).build().toString());
        /*for(Les les : lessen) {
            System.out.println(les.getNaam());
            System.out.println(format.format(new Date(les.getDatumStart().getTimeInMillis())));
            System.out.println(format.format(new Date(les.getDatumEind().getTimeInMillis())));
        }*/
    }
}

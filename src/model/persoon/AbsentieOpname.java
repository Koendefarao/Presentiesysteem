package model.persoon;

import java.util.Date;

/**
 * Created by EgorDm on 06-Apr-2017.
 */
public class AbsentieOpname {

    private Date startDatum;
    private Date eindDatum;


    /**
     * Voor onbepaalde tijd
     *
     * @param startDatum
     */
    public AbsentieOpname(Date startDatum) {
        this.startDatum = startDatum;
        this.eindDatum = null;
    }

    /**
     * Voor bepaalde tijd
     *
     * @param startDatum
     * @param eindDatum
     */
    public AbsentieOpname(Date startDatum, Date eindDatum) {
        this.startDatum = startDatum;
        this.eindDatum = eindDatum;
    }

    public Date getStartDatum() {
        return startDatum;
    }

    public Date getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(Date eindDatum) {
        this.eindDatum = eindDatum;
    }

    public boolean isOnbepaald() {
        return eindDatum == null;
    }

    public boolean isDateWithin(Date date) {
        return date != null && (eindDatum == null || eindDatum.after(date)) && startDatum.before(date);
    }

    public void merge(AbsentieOpname absentieOpname) {
        if (absentieOpname.getStartDatum().before(startDatum)) startDatum = absentieOpname.getStartDatum();
        if (absentieOpname.getEindDatum() == null || absentieOpname.getEindDatum().after(eindDatum))
            eindDatum = absentieOpname.getEindDatum();
    }


}

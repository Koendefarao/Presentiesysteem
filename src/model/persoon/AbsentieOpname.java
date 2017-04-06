package model.persoon;

import java.util.Date;

/**
 * Created by EgorDm on 06-Apr-2017.
 */
public class AbsentieOpname {

    protected Date startDatum;
    protected Date eindDatum;


    /**
     * Voor onbepaalde tijd
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
}

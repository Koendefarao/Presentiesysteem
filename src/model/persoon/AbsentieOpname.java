package model.persoon;

import general.MyUtils;
import model.BasisModel;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.util.Date;

/**
 * Created by EgorDm on 06-Apr-2017.
 */
public class AbsentieOpname extends BasisModel implements Comparable<AbsentieOpname> {

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
        return date != null && (eindDatum == null || eindDatum.after(date) || eindDatum.equals(date)) && (startDatum.before(date) || startDatum.equals(date));
    }

    public void merge(AbsentieOpname absentieOpname) {
        if (absentieOpname.getStartDatum().before(startDatum) || absentieOpname.getStartDatum().equals(startDatum)) startDatum = absentieOpname.getStartDatum();
        if (absentieOpname.getEindDatum() == null || eindDatum == null || absentieOpname.getEindDatum().after(eindDatum) || absentieOpname.getEindDatum().equals(eindDatum))
            eindDatum = absentieOpname.getEindDatum();
    }

    @Override
    public int compareTo(AbsentieOpname o) {
        return startDatum.before(o.getStartDatum()) ? 1 : -1;
    }

    @Override
    public JsonObjectBuilder serialize() {
        JsonObjectBuilder ret = Json.createObjectBuilder()
                .add("start_datum", MyUtils.getStringToMillis(startDatum));
        if(eindDatum != null)
            ret.add("eind_datum", MyUtils.getStringToMillis(eindDatum));
        else
            ret.add("eind_datum", JsonValue.NULL);
        return ret;
    }
}

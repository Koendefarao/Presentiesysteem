package model;

import javax.json.JsonObjectBuilder;

/**
 * Created by EgorDm on 07-Apr-2017.
 */
public abstract class BasisModel {
    public abstract JsonObjectBuilder serialize();
}
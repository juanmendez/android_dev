package com.example;

import java.io.File;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class DaoGenerator {

    public static final String SRC = "app/src-gen";

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "info.juanmendez.android.db.mycontentprovider");



        Entity country = schema.addEntity( "Country" );
        country.setTableName("COUNTRY");
        country.addIdProperty();
        country.addStringProperty( "name" );

        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, SRC);
    }
}

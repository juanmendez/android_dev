package de.greenrobot.daoexample;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class MyDaoGenerator {

    public static final String SRC = "C:\\Users\\Public\\development\\practice\\android\\my_demos\\DroidSpace\\09.database_connection\\00.greendao_demo\\app\\src-gen";
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(4, "de.greenrobot.daoexample");
        Entity noteEntity = schema.addEntity("Note");
        noteEntity.addIdProperty();
        noteEntity.addStringProperty("text");
        noteEntity.addStringProperty("comment");
        noteEntity.addDateProperty("date");


        Entity country = schema.addEntity( "Country" );
        country.setTableName("COUNTRY");
        country.addIdProperty();
        country.addStringProperty( "country" );
        country.addStringProperty( "flag" );


        Entity link = schema.addEntity( "Link" );
        link.setTableName("LINKS");
        link.addIdProperty();
        link.addStringProperty("url");
        Property countryId = link.addLongProperty( "countryId").notNull().getProperty();
        link.addToOne( country, countryId );

        ToMany linkToCountry = country.addToMany(link, countryId);
        linkToCountry.setName("links");
        new DaoGenerator().generateAll(schema, SRC);
    }
}

package com.example.cityguideapp.services.database;

/**
 * Created by Baal on 3/27/2018.
 */

public class DBConstants {
    public static String DATABASE_NAME = "cityguideapp";

    public static String TABLE_USERLIST = "userList";
    public static String COLUMN_USERLIST_ID = "id";
    public static String COLUMN_USERLIST_NAME = "name";
    public static String COLUMN_USERLIST_USERID= "uid";


    public static String TABLE_GOOGLEPLACE = "googlePlace";
    public static String COLUMN_GOOGLEPLACE_ID = "id";
    public static String COLUMN_GOOGLEPLACE_PLACEID = "placeID";
    public static String COLUMN_GOOGLEPLACE_NAME= "name";
    public static String COLUMN_GOOGLEPLACE_FK_USERLIST= "fkUserList";


}

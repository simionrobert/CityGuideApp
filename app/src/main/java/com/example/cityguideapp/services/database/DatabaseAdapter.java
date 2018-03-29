package com.example.cityguideapp.services.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cityguideapp.models.GooglePlace;
import com.example.cityguideapp.models.UserList;

import java.util.ArrayList;

/**
 * Created by Baal on 3/27/2018.
 */

public class DatabaseAdapter {


    private static final String TAG = "DatabaseAdapter";

    private SQLiteDatabase db;
    private SQLiteOpenHelper helper ;

    public DatabaseAdapter(Context context, int vs) {
        this.helper = new DatabaseHelper(context,DBConstants.DATABASE_NAME,null,vs);
    }

    public void startConnection(){
        db = helper.getWritableDatabase();
    }

    public void closeConnection(){
        db.close();
        helper.close();
    }



    public ArrayList<UserList> selectUserListByUID(String uid){
        ArrayList<UserList> lists = new ArrayList<>();

        Cursor cursor = db.query(DBConstants.TABLE_USERLIST,
                new String[]{DBConstants.COLUMN_USERLIST_ID, DBConstants.COLUMN_USERLIST_NAME,DBConstants.COLUMN_USERLIST_USERID},
                DBConstants.COLUMN_USERLIST_USERID+"=?",new String[]{uid},null,null,null);

        if(cursor!= null && cursor.moveToFirst()){
            do{
                UserList list = new UserList(cursor.getInt(0),cursor.getString(2),cursor.getString(1));
                ArrayList<GooglePlace> listsGooglePlace = selectGooglePlacesByUserList(list.getId());
                list.setPlaces(listsGooglePlace);

                lists.add(list);
            } while(cursor.moveToNext());
        }

        return  lists;
    }

    public UserList selectUserListByID(String userListID){
        UserList list = null;

        Cursor cursor = db.query(DBConstants.TABLE_USERLIST,
                new String[]{DBConstants.COLUMN_USERLIST_ID, DBConstants.COLUMN_USERLIST_NAME,DBConstants.COLUMN_USERLIST_USERID},
                DBConstants.COLUMN_GOOGLEPLACE_ID+"=?",new String[]{userListID},null,null,null);

        if(cursor!= null && cursor.moveToFirst()){
            list = new UserList(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
            ArrayList<GooglePlace> listsGooglePlace = selectGooglePlacesByUserList(list.getId());
            list.setPlaces(listsGooglePlace);
        }

        return list;
    }

    public void insertUserList(UserList list,String UID){
        ContentValues cv = new ContentValues();

        cv.put(DBConstants.COLUMN_USERLIST_NAME,list.getName());
        cv.put(DBConstants.COLUMN_USERLIST_USERID,UID);

        db.insert(DBConstants.TABLE_USERLIST,null,cv);

        if(list.getPlaces()!=null){
            for (GooglePlace place:list.getPlaces()) {
                insertGooglePlace(place,list.getId());
            }
        }
    }

    public void deleteUserList(UserList list,String UID){
        if(list.getUID().compareTo(UID) == 0){
            db.delete(DBConstants.TABLE_USERLIST,DBConstants.COLUMN_USERLIST_ID+"=?",new String[]{Integer.toString(list.getId())});

            for (GooglePlace place:list.getPlaces()) {
                deleteGooglePlace(place);
            }
        } else
            Log.e(TAG,"List UID doesn't match logged user UID");

    }

    public ArrayList<GooglePlace> selectGooglePlacesByUserList(int fkUserListID) {
        ArrayList<GooglePlace> lists = new ArrayList<>();

        Cursor cursor = db.query(DBConstants.TABLE_GOOGLEPLACE,
                new String[]{DBConstants.COLUMN_GOOGLEPLACE_ID,DBConstants.COLUMN_GOOGLEPLACE_PLACEID,DBConstants.COLUMN_GOOGLEPLACE_NAME},
                DBConstants.COLUMN_GOOGLEPLACE_FK_USERLIST+"=?",new String[]{Integer.toString(fkUserListID)},null,null,null);

        if(cursor!=null&&cursor.moveToFirst()){
            do{
                GooglePlace place = new GooglePlace(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
                lists.add(place);
            } while(cursor.moveToNext());
        }

        return lists;
    }

    public void insertGooglePlace(GooglePlace place,int fkUserListID){
        ContentValues cv = new ContentValues();

        cv.put(DBConstants.COLUMN_GOOGLEPLACE_PLACEID,place.getPlaceid());
        cv.put(DBConstants.COLUMN_GOOGLEPLACE_NAME,place.getName());
        cv.put(DBConstants.COLUMN_GOOGLEPLACE_FK_USERLIST,fkUserListID);

        db.insert(DBConstants.TABLE_GOOGLEPLACE,null,cv);
    }

    public void deleteGooglePlace(GooglePlace place) {
        db.delete(DBConstants.TABLE_GOOGLEPLACE, DBConstants.COLUMN_GOOGLEPLACE_ID + "=?", new String[]{Integer.toString(place.getId())});
    }

}

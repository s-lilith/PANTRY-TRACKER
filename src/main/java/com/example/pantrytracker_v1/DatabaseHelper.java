package com.example.pantrytracker_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME=  "UserData.db";
    private static final int DATABASE_VERSION= 1;

    private static final String TABLE_NAME= "Data";
    private static final String COLUMN_ID= "id";
    private static final String COLUMN_NAME= "name";
    private static final String COLUMN_DESCRIPTION= "description";
    private static final String COLUMN_INSERTDATE= "insertDate";
    private static final String COLUMN_USER= "user";
    private static final String COLUMN_BARCODE= "barcode";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query= "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_INSERTDATE + " TEXT, " +
                COLUMN_BARCODE + " TEXT, " +
                COLUMN_USER + " TEXT);";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    Boolean insertData(String name, String description, String insertDate, String barcode,String user){

        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values= new ContentValues();

        String id= null;
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_INSERTDATE, insertDate);
        values.put(COLUMN_BARCODE, barcode);
        values.put(COLUMN_USER, user);

        long result = database.insert(TABLE_NAME, null, values);
        
        if (result == -1) return false;
            else return true;

    }

    public Cursor getData(String id){

        String query= "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database= this.getWritableDatabase();

        Cursor cursor= null;
        if(database != null){
            cursor= database.rawQuery(query, null);
        }
        return cursor;
    }

    public Boolean deleteData(String id){

        SQLiteDatabase database= this.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select * from " + TABLE_NAME + " where id = ?", new String[]{id});

        if(cursor.getCount() > 0){
            long result= database.delete(TABLE_NAME, "id=?", new String[]{id});
            if (result== -1) return false;
            else return false;
        }
        else return true;
    }
}

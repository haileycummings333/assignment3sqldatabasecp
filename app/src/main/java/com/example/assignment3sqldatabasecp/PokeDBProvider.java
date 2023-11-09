package com.example.assignment3sqldatabasecp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class PokeDBProvider extends ContentProvider {
    public static final String DBNAME = "POKEDB";
    public static final String TABLE_NAME = "PokeData";
    public static final String COLUMN1_NAME = "National Number";
    public static final String COLUMN2_NAME = "Name";
    public static final String COLUMN3_NAME = "Species";
    public static final String COLUMN4_NAME = "Gender";
    public static final String COLUMN5_NAME = "Height";
    public static final String COLUMN6_NAME = "Weight";
    public static final String COLUMN7_NAME = "Level";
    public static final String COLUMN8_NAME = "HP";
    public static final String COLUMN9_NAME = "Attack";
    public static final String COLUMN10_NAME = "Defense";
    public static final String AUTHORITY = "com.example.assignment3sqldatabasecp";
    public static final Uri contentURI = Uri.parse( AUTHORITY + "/" + DBNAME);
    private MainDatabaseHelper SQLHelper;
    private static final String CREATE_DB_QUERY = "CREATE TABLE " + TABLE_NAME + //SQL query
            "(_ID INTEGER PRIMARY KEY," +
            COLUMN1_NAME + "TEXT,"
            + COLUMN2_NAME + "TEXT,"
            +COLUMN3_NAME + "TEXT,"
            +COLUMN4_NAME + "TEXT,"
            +COLUMN5_NAME + "TEXT,"
            +COLUMN6_NAME + "TEXT,"
            +COLUMN7_NAME + "TEXT,"
            +COLUMN8_NAME + "TEXT,"
            +COLUMN9_NAME + "TEXT,"
            + COLUMN10_NAME + "TEXT)";
    protected  static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_DB_QUERY);
        }
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){

        }
    };
    public PokeDBProvider() {
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return SQLHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = SQLHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        return Uri.withAppendedPath(contentURI, "" + id);
    }
    @Override
    public boolean onCreate() {
        SQLHelper = new MainDatabaseHelper(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return SQLHelper.getReadableDatabase().query(TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
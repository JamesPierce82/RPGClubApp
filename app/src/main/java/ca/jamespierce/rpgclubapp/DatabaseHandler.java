package ca.jamespierce.rpgclubapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by web on 2017-02-14.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    /**
     * Keep track of the database version
     */

    private static final int DATABASE_VERSION = 1;

    /**
     * Create the name of the database
     */

    private static final String DATABASE_NAME = "rpgclub";

    /**
     * Create the names of all the tables
     */

    private static final String TABLE_MESSAGES = "message";
    private static final String TABLE_AUTHORS = "author";
    private static final String TABLE_IMAGES = "image";

    /**
     * Common column names
     */

    private static final String KEY_ID = "id";

    /**
     * Message Table column names
     */

    private static final String KEY_TIME = "timesent";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_AUTHOR_KEY = "author_key";
    private static final String KEY_IMAGE_KEY = "image_key";

    /**
     * Author Table column names
     */

    private static final String KEY_NAME = "name";

    /**
     * Avatar Table column names
     */

    private static final String KEY_IMAGE = "image";

    /**
     * Create statments for all the tables
     */

    private static final String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES
                        + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_AUTHOR_KEY
                        + " INTEGER REFERENCES " + TABLE_AUTHORS + "(" + KEY_ID + "),"
                        + KEY_TIME + " DATETIME NOT NULL," + KEY_CONTENT + " TEXT,"
                        + KEY_IMAGE_KEY
                        + " INTEGER REFERENCES " + TABLE_IMAGES + "(" + KEY_ID + "),";

    private static final String CREATE_AUTHORS_TABLE = "CREATE TABLE " + TABLE_AUTHORS
                        + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";

    private static final String CREATE_IMAGES_TABLE = "CREATE TABLE " + TABLE_IMAGES
                        + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IMAGE + " BLOB)";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
 
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

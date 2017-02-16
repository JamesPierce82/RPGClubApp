package ca.jamespierce.rpgclubapp;

import android.content.ContentValues;
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
    private static final String TABLE_USERS = "users";
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
    private static final String KEY_USER_KEY = "user_key";

    /**
     * User Table column names
     */

    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";

    /**
     * Create statments for all the tables
     */

    private static final String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES
                        + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_KEY
                        + " INTEGER REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
                        + KEY_TIME + " DATETIME NOT NULL," + KEY_CONTENT + " TEXT)";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS
                        + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT"
                        + KEY_IMAGE + " BLOB)";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MESSAGES_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
    }

    // This will drop all existing tables and create them from scratch
    // This is useful if we make any major changes to the database and need to reset everything
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(db);
    }


    /**
     * CRUD OPERATIONS FOR THE DATABASE AND TABLES
     * Create
     * Read
     * Update
     * Delete
     */

    // I will need to create author and image classes, then implement them in the app
    // correctly. Once that is done the database will be easier to implement correctly
    public void addMessage(Message message, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, message.getTimeSent());
        values.put(KEY_CONTENT, message.getContent());
        values.put(KEY_USER_KEY, user.getId());
        db.insert(TABLE_MESSAGES, null, values);
    }


    /**
     * Closing the database connection
     */

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()) {
            db.close();
        }
    }




}

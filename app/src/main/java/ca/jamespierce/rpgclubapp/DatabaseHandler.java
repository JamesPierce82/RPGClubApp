package ca.jamespierce.rpgclubapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
    private static final String TABLE_PICTURES = "picture";

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
     *Picture Table Column Names
     */
    private static final String COLUMN_RESOURCE = "resource";

    /**
     * User Table column names
     */

    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "avatar";

    /**
     * Create statments for all the tables
     */

    private static final String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES
                        + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_KEY
                        + " INTEGER REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
                        + KEY_TIME + " DATETIME NOT NULL," + KEY_CONTENT + " TEXT)";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS
                        + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT, "
                        + KEY_IMAGE + " INTEGER)";

    private static final String CREATE_PICTURES_TABLE = "CREATE TABLE " + TABLE_PICTURES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_RESOURCE + " TEXT" + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_MESSAGES_TABLE);
        db.execSQL(CREATE_PICTURES_TABLE);

    }

    // This will drop all existing tables and create them from scratch
    // This is useful if we make any major changes to the database and need to reset everything
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PICTURES);
        onCreate(db);
    }


    /**
     * CRUD OPERATIONS FOR THE DATABASE AND TABLES
     * Create
     * Read
     * Update
     * Delete
     */


    public void addMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_KEY, message.getUser_id());
        values.put(KEY_TIME, message.getTimeSent());
        values.put(KEY_CONTENT, message.getContent());
        db.insert(TABLE_MESSAGES, null, values);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_IMAGE, user.getAvatar());
        db.insert(TABLE_USERS, null, values);
    }

    //We modified addPicture to return the rowNumber it was added into
    public int addPicture(Picture picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE, picture.getResource());
        db.insert(TABLE_PICTURES, null, values);
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if(cursor.moveToFirst()) {
            int location = Integer.parseInt(cursor.getString(0));
            System.out.println("Record ID " + location);
            db.close();
            return location;
        }
        return -1;
    }

    /**
     * READ OPERATIONS
     */

    // get individual user
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        /**
         * Create a cursor
         * (Which is able to move through and access database records)
         * Have it store all the records retrieved from the db.query()
         * cursor starts by pointing at record 0
         * Databases do not have a record 0
         * we use cursor.moveToFirst() to have it at the first record returned
         */
        User user = null;
        Cursor firstCursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID, KEY_NAME, KEY_IMAGE},  KEY_ID + " = " + String.valueOf(id), null, null, null, null, null);
        if(cursor.moveToFirst()){
            user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        }


        /**
         * We crate a User object usin the cursor record
         */

//        User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        return user;
    }

    // get individual Message
    public Message getMessage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        /**
         * Create a cursor
         * (Which is able to move through and access database records)
         * Have it store all the records retrieved from the db.query()
         * cursor starts by pointing at record 0
         * Databases do not have a record 0
         * we use cursor.moveToFirst() to have it at the first record returned
         */
        Cursor cursor = db.query(TABLE_MESSAGES, new String[] {KEY_ID, KEY_USER_KEY, KEY_TIME, KEY_CONTENT}, "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

        /**
         * We create a user object using the cursor record
         */
        Message message = new Message(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
        return message;
    }

    // get all messages
    public ArrayList<Message> getAllMessages() {
        ArrayList<Message> messageList = new ArrayList<Message>();
        String selectQuery = "SELECT * FROM " + TABLE_MESSAGES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setId(Integer.parseInt(cursor.getString(0)));
                message.setUser_id(Integer.parseInt(cursor.getString(1)));
                message.setTimeSent(cursor.getString(2));
                message.setContent(cursor.getString(3));
                messageList.add(message);
            }while(cursor.moveToNext());
        }

        return messageList;
    }

    public Picture getPicture(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // This is a test, increase id by one for accessing database
        id++;

        Cursor cursor = db.query(TABLE_PICTURES, new String[] {KEY_ID, COLUMN_RESOURCE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Picture picture = new Picture(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));

        return picture;
    }

    public ArrayList<Picture> getAllPictures() {
        ArrayList<Picture> pictureList = new ArrayList<Picture>();
        String selectQuery = "SELECT * FROM " + TABLE_PICTURES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            System.out.println("Moved to first");
            do {
                Picture picture = new Picture();
                picture.setId(Integer.parseInt(cursor.getString(0)));
                picture.setResource(cursor.getString(1));
                pictureList.add(picture);
            } while (cursor.moveToNext());
        }
        return pictureList;
    }

    /**
     * UPDATE OPERATIONS
     */

    // Update the Message
    public int updateMessage(Message message) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_KEY, message.getUser_id());
        values.put(KEY_TIME, message.getTimeSent());
        values.put(KEY_CONTENT, message.getContent());
        return db.update(TABLE_MESSAGES, values, KEY_ID + " = ?",
                new String[] {String.valueOf(message.getId())});
    }

    // Update the User
    public int updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_IMAGE, user.getAvatar());
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[] {String.valueOf(user.getId())});
    }

    public int updatePicture(Picture picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE, picture.getResource());
        return db.update(TABLE_PICTURES, values, KEY_ID + " = ?", new String[] { String.valueOf(picture.getId()) });
    }

    /**
     * DELETE OPERATIONS
     */

    // Delete a Message
    public void deleteMessage(long message_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_MESSAGES, KEY_ID + " = ?",
                new String[] {String.valueOf(message_id)});
    }

    // Delete a User
    public void deleteUser(long user_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[] {String.valueOf(user_id)});
    }

    public void deletePicture(long picture_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PICTURES, KEY_ID + " = ?",
                new String[] { String.valueOf(picture_id) });
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

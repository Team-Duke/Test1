package com.example.sajja_000.test1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sajja_000 on 27/03/2015.
 */
public class databaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CollegeCompanionDatabase",
            TABLE_STUDENT = "student",
            KEY_ID = "Id",
            KEY_FIRSTNAME = "firstName",
            KEY_LASTNAME = "lastName",
            KEY_ADDRESS = "address";

    public databaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE_TABLE " + TABLE_STUDENT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIRSTNAME + " TEXT," + KEY_LASTNAME + " TEXT," + KEY_ADDRESS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    public void createStudent(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, student.getFirstName());
        values.put(KEY_ID, student.getId());
        values.put(KEY_LASTNAME, student.getLastName());
        values.put(KEY_ADDRESS, student.getAddress());
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }
    public void getContact(Student student)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query(TABLE_STUDENT,new String[]{KEY_ID,KEY_FIRSTNAME,KEY_LASTNAME,KEY_ADDRESS},KEY_ID+"=?",new String[]{String.valueOf(1)},null,null,null,null);

        if (cursor!= null)
            cursor.moveToFirst();
        Student student = new Student(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
    }
}
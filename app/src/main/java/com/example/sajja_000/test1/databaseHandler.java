package com.example.sajja_000.test1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.GestureDetector;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

        values.put(KEY_LASTNAME, student.getLastName());
        values.put(KEY_ADDRESS, student.getAddress());
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }
    public Student getStudent(int id)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query(TABLE_STUDENT,new String[]{KEY_ID,KEY_FIRSTNAME,KEY_LASTNAME,KEY_ADDRESS},KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);

        if (cursor!= null)
            cursor.moveToFirst();
        Student student = new Student(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
      db.close();
        cursor.close();
        return student;
    }
    public void deleteStudet(Student student)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_STUDENT,KEY_ID +"?",new String[]{String.valueOf(student.getId())});
        db.close();

    }
    public int GetStudentCount()
    {
      SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT* FROM " + TABLE_STUDENT, null);
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    public int UpdateStudnt(Student student)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_FIRSTNAME, student.getFirstName());
        values.put(KEY_LASTNAME, student.getLastName());
        values.put(KEY_ADDRESS, student.getAddress());

        int rowsAffected = db.update(TABLE_STUDENT, values, KEY_ID + "=?", new String[] { String.valueOf(student.getId()) });
        db.close();
        return rowsAffected;


    }
    public List<Student> getAllStudents()
    {
        List<Student> students =new ArrayList<Student>();
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " + TABLE_STUDENT,null);

        if (cursor.moveToFirst())
        {
            do
            {
                Student student = new Student(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));

            }
            while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return students;
    }
}
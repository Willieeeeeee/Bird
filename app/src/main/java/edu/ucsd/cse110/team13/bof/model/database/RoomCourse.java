package edu.ucsd.cse110.team13.bof.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.team13.bof.model.ClassSize;
import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.Quarter;

@Entity(tableName = "past_courses")
public class RoomCourse implements IClass {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="courseId")
    public int courseId;

    @ColumnInfo(name="uid")
    public String uid;

    @ColumnInfo(name="year")
    public int year;

    @ColumnInfo(name="quarter")
    @NonNull
    public Quarter quarter;

    @ColumnInfo(name="size")
    @NonNull
    public ClassSize size;

    @ColumnInfo(name="subject")
    @NonNull
    public String subject;

    @ColumnInfo(name="course_number")
    @NonNull
    public String course_number;

    public RoomCourse(String uid, int year, Quarter quarter, ClassSize size, String subject, String course_number) {
        this.uid      = uid;
        this.year     = year;
        this.size     = size;
        this.quarter  = quarter;
        this.subject  = subject;
        this.course_number = course_number;
    }

    public int       getYear()         { return year; }
    public Quarter   getQuarter()      { return quarter; }
    public ClassSize getSize()         { return size; }
    public String    getSubject()      { return subject; }
    public String    getCourseNumber() { return course_number; }

    /* uid & size is ignored in equals comparison */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        // type casting is safe because the class type was checked above
        IClass a = (IClass) obj;

        // check equivalence of each attribute
        if(!(this.getYear() == (a.getYear())))
            return false;
        if(!(this.getQuarter().equals(a.getQuarter())))
            return false;
        if(!(this.getSubject().equals(a.getSubject())))
            return false;
        if(!(this.getCourseNumber().equals(a.getCourseNumber())))
            return false;

        // if nothing is wrong return true
        return true;
    }
}
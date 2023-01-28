package edu.ucsd.cse110.team13.bof.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {student.class, RoomCourse.class}, version = 2)
@TypeConverters({QuarterConverter.class})
public abstract class ProfileDatabase extends RoomDatabase {
    private static ProfileDatabase singletonInstance;

    public static ProfileDatabase singleton(Context context){
        if(singletonInstance==null){
            singletonInstance= Room.databaseBuilder(context,ProfileDatabase.class,"profile.db")
                    .createFromAsset("profile.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return singletonInstance;
    }

    public abstract CourseDao courseDao();
}
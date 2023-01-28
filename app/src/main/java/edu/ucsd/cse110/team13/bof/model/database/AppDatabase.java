package edu.ucsd.cse110.team13.bof.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {RoomSession.class, RoomProfile.class, RoomCourse.class, SessionProfileCrossRef.class},
        version = 2,
        exportSchema = false)
@TypeConverters({QuarterConverter.class, ClassSizeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singletonInstance;

    public static AppDatabase singleton(Context context){
        if(singletonInstance == null){
            singletonInstance= Room.databaseBuilder(context, AppDatabase.class,"bof.db")
                    //.createFromAsset("bof.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return singletonInstance;
    }

    public static void useTestSingleton(Context context) {
        singletonInstance = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    public abstract SessionDao sessionDao();
    public abstract ProfileDao profileDao();
    public abstract CourseDao  courseDao();
}

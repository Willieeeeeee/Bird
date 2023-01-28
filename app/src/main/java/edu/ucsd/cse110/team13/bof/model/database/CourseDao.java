package edu.ucsd.cse110.team13.bof.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.ucsd.cse110.team13.bof.model.Quarter;

@Dao
public interface CourseDao {
    /* Getters */
    @Query("SELECT COUNT(*) from past_courses")
    int size();

    @Query("SELECT * FROM past_courses WHERE courseId=:courseId")
    RoomCourse get(int courseId);

    @Query("SELECT * FROM past_courses WHERE uid=:uid")
    List<RoomCourse> getCourses(String uid);

    @Query("SELECT COUNT(*) from past_courses " +
            "WHERE uid=:uid AND year=:year AND quarter=:quarter AND subject=:subject AND course_number=:course_number")
    int findCount(String uid, int year, Quarter quarter, String subject, String course_number);

    /* Modifiers */
    @Insert
    void insert(RoomCourse course);

    @Query("DELETE FROM past_courses WHERE uid=:uid")
    void deleteByUid(String uid);

    @Query("DELETE FROM past_courses")
    void deleteAll();
}
package edu.ucsd.cse110.team13.bof.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ProfileDao {
    /* Getters */
    @Query("SELECT COUNT(*) FROM profiles")
    int size();

    @Query("SELECT * FROM profiles WHERE uid=:UUID")
    RoomProfile get(String UUID);

    @Transaction
    @Query("SELECT * FROM profiles")
    List<RoomProfile> getAll();

    @Transaction
    @Query("SELECT * FROM profiles WHERE favorite=1")
    List<ProfileWithCourses> getFavoritesWithCourses();

    @Transaction
    @Query("SELECT * FROM profiles WHERE uid=:uid")
    ProfileWithCourses getProfileWithCourses(String uid);

    @Transaction
    @Query("SELECT * FROM profiles")
    List<ProfileWithCourses> getAllProfilesWithCourses();

    /* Modifiers */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomProfile profile);

    @Query("UPDATE profiles SET favorite=:favorite WHERE uid=:uid")
    void updateFavorite(String uid, boolean favorite);

    @Query("DELETE FROM profiles")
    void deleteAll();
}

package edu.ucsd.cse110.team13.bof.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface SessionDao {
    /* Getters */
    @Query("SELECT COUNT(*) FROM saved_sessions")
    int size();

    @Query("SELECT * FROM saved_sessions WHERE sessionId=:sessionId")
    RoomSession get(int sessionId);

    @Query("SELECT * FROM saved_sessions WHERE sessionName=:sessionName")
    RoomSession get(String sessionName);

    @Transaction
    @Query("SELECT * FROM saved_sessions")
    List<RoomSession> getAll();

    // TODO: add test case
    @Query("SELECT MAX(sessionId) from saved_sessions")
    int recentId();

    @Transaction
    @Query("SELECT * FROM saved_sessions WHERE sessionId=:sessionId")
    SessionWithProfiles getSessionWithProfiles(int sessionId);

    @Transaction
    @Query("SELECT * FROM saved_sessions")
    List<SessionWithProfiles> getAllSessionsWithProfiles();

    /* Modifiers */
    @Insert
    void insert(RoomSession session);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SessionProfileCrossRef ref);

    @Query("UPDATE saved_sessions SET sessionName=:name WHERE sessionId=:sessionId")
    void renameSession(int sessionId, String name);

    @Query("DELETE FROM saved_sessions")
    void deleteAll();
}

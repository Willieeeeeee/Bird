package edu.ucsd.cse110.team13.bof.model.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"sessionId", "uid"}, tableName = "session_profile_ref")
public class SessionProfileCrossRef {
    @NonNull
    public String uid;
    public int sessionId;

    public SessionProfileCrossRef(int sessionId, String uid) {
        this.sessionId = sessionId;
        this.uid = uid;
    }
}

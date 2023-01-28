package edu.ucsd.cse110.team13.bof.model.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_sessions")
public class RoomSession {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="sessionId")
    public int sessionId;

    @ColumnInfo(name="sessionName")
    public String sessionName;

    public RoomSession(String sessionName) {
        this.sessionName = sessionName;
    }
}

package edu.ucsd.cse110.team13.bof.model.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class SessionWithProfiles {
    @Embedded public RoomSession session;

    @Relation(
            parentColumn = "sessionId",
            entityColumn = "uid",
            associateBy = @Junction(SessionProfileCrossRef.class)
    )
    public List<RoomProfile> profiles;
}

package edu.ucsd.cse110.team13.bof.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "profiles")
public class RoomProfile {
    @Ignore
    private static final String NO_URL = "";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name="uid", index = true)
    public final String uid;

    @ColumnInfo(name="firstName")
    public String firstName;

    @ColumnInfo(name="url")
    public String url;

    @ColumnInfo(name="favorite")
    public boolean favorite;

    public RoomProfile(String uid, String firstName, String url) {
        this.uid       = uid;
        this.firstName = firstName;
        this.url       = url;
        this.favorite  = false;
    }
}

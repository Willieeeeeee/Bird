package edu.ucsd.cse110.team13.bof.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "student")
public class student {
    @PrimaryKey
    @ColumnInfo(name="name")
    @NonNull
    String studentName;

    @ColumnInfo(name="photoUrl")
    public String photoUrl;
}
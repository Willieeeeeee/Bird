package edu.ucsd.cse110.team13.bof.model.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.IProfile;
import edu.ucsd.cse110.team13.bof.model.Quarter;

public class ProfileWithCourses implements IProfile {
    @Embedded
    public RoomProfile profile;

    @Relation(
            parentColumn = "uid",
            entityColumn = "uid")
    public List<RoomCourse> courses;

    public ProfileWithCourses(RoomProfile profile, List<RoomCourse> courses) {
        this.profile = profile;
        this.courses = courses;
    }

    public String       getUid()         { return profile.uid;              }
    public String       getFirstName()   { return profile.firstName;        }
    public String       getHeadshotUrl() { return profile.url;              }
    public List<IClass> getClasses()     { return new ArrayList<>(courses); }
    public boolean      isFavorite()     { return profile.favorite;         }

    public boolean hasCommonClasses(IProfile profile) {
        if(profile == null) { return false; }

        ArrayList<IClass> myClasses = new ArrayList<>(courses);
        for(IClass c : profile.getClasses()) {
            if(myClasses.contains(c)) { return true; }
        }
        return false;
    }

    public List<IClass> getCommonClasses(IProfile profile) {
        if(profile == null) { return new ArrayList<>(); }

        ArrayList<IClass> result    = new ArrayList<>();
        ArrayList<IClass> myClasses = new ArrayList<>(courses);
        for(IClass c : profile.getClasses()) {
            if(myClasses.contains(c)) { result.add(c); }
        }
        return result;
    }
    public int getMostRecentScore(IProfile profile){
        if(profile == null) { return 0; }

        int result=0;
        List<IClass> myClasses    = this.getCommonClasses(profile);
        for(int i=0;i<myClasses.size();i++) {
            if(myClasses.get(i).getQuarter()==Quarter.FA&&myClasses.get(i).getYear()==2021) { result=result+5; }
            else if(myClasses.get(i).getQuarter()==Quarter.SS2&&myClasses.get(i).getYear()==2021) { result=result+4; }
            else if(myClasses.get(i).getQuarter()==Quarter.SS1&&myClasses.get(i).getYear()==2021) { result=result+4; }
            else if(myClasses.get(i).getQuarter()==Quarter.SSS&&myClasses.get(i).getYear()==2021) { result=result+4; }
            else if(myClasses.get(i).getQuarter()==Quarter.SP&&myClasses.get(i).getYear()==2021) { result=result+3; }
            else if(myClasses.get(i).getQuarter()==Quarter.WI&&myClasses.get(i).getYear()==2021) { result=result+2; }
            else  { result=result+1; }
        }
        return result;
    }
}

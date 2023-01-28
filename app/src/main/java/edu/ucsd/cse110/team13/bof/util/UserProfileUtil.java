package edu.ucsd.cse110.team13.bof.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import edu.ucsd.cse110.team13.bof.R;
import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.IProfile;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.database.AppDatabase;
import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;
import edu.ucsd.cse110.team13.bof.model.database.RoomCourse;
import edu.ucsd.cse110.team13.bof.model.database.RoomProfile;
import edu.ucsd.cse110.team13.bof.model.mProfile;

public class UserProfileUtil {
    public  static final String EMPTY_URL = "";
    private static final String USR_ID_KEY       = "USR_ID",
                                FIRST_NAME_KEY   = "USR_FIRST_NAME",
                                HEADSHOT_URL_KEY = "USR_HS_URL",
                                NEED_CLASSES_KEY = "N_USR_CLASSES";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private AppDatabase db;

    public UserProfileUtil(Context context) {
        preferences = context.getSharedPreferences("BOF", Context.MODE_PRIVATE);
        editor      = preferences.edit();
        db          = AppDatabase.singleton(context);
    }

    /* Getters */
    public boolean needUid()         { return !preferences.contains(USR_ID_KEY);       }
    public boolean needFirstName()   { return !preferences.contains(FIRST_NAME_KEY);   }
    public boolean needHeadshotUrl() { return !preferences.contains(HEADSHOT_URL_KEY); }
    public boolean needClasses()     { return preferences.getBoolean(NEED_CLASSES_KEY, true); }

    public String   getUid()         { return preferences.getString(USR_ID_KEY,null); }
    public ProfileWithCourses getUserProfile() {
        return db.profileDao().getProfileWithCourses(getUid());
    }

    private String getFirstName()     { return preferences.getString(FIRST_NAME_KEY,   ""); }
    private String getHeadshotUrl()   { return preferences.getString(HEADSHOT_URL_KEY, ""); }
    private List<IClass> getClasses() { return new ArrayList<>(db.courseDao().getCourses(getUid())); }

    /* Setters */
    public void setUid() {
        // uid can only be set once
        if(needUid()) {
            editor.putString(USR_ID_KEY, UUID.randomUUID().toString());
            editor.apply();
        }
    }

    public void setFirstName(String firstName) {
        if(firstName == null || firstName.trim().length() == 0) { return; }
        editor.putString(FIRST_NAME_KEY, firstName);
        editor.apply();
    }

    public void setHeadshotUrlAsEmpty() {
        setHeadshotUrl(EMPTY_URL);
        editor.apply();
    }

    public void setHeadshotUrl(String url) {
        if(url == null) { return; }
        editor.putString(HEADSHOT_URL_KEY, url);
        editor.apply();
    }

    public boolean addClass(IClass c) {
        if(needUid() || c == null) { return false; }

        // If duplicate course record found, then insertion is abandoned
        //   note that class size is ignored in the comparison
        if(db.courseDao().findCount(getUid(),
                                    c.getYear(),
                                    c.getQuarter(),
                                    c.getSubject(),
                                    c.getCourseNumber())
                > 0) { return false; }

        RoomCourse rc = new RoomCourse(getUid(),
                                       c.getYear(),
                                       c.getQuarter(),
                                       c.getSize(),
                                       c.getSubject(),
                                       c.getCourseNumber());
        db.courseDao().insert(rc);
        return true;
    }

    public boolean setClassesAsFinished() {
        // cannot set to finish when no class is inserted
        if(db.courseDao().getCourses(getUid()).size() == 0) { return false; }

        editor.putBoolean(NEED_CLASSES_KEY, false);
        editor.apply();
        db.profileDao().insert(new RoomProfile(getUid(),getFirstName(),getHeadshotUrl()));
        return true;
    }

    public void resetProfile() {
        String uid = getUid();
        db.courseDao().deleteByUid(getUid());
        editor.clear();
        editor.putString(USR_ID_KEY,uid);
        editor.apply();
    }

    public void resetDatabase() {
        db.courseDao() .deleteAll();
        db.profileDao().deleteAll();
        db.sessionDao().deleteAll();
        editor.clear();
    }

    public List<String> getCurrentClassNames(Date date){
        ArrayList<String> classes = new ArrayList<>();
        for(IClass c : db.courseDao().getCourses(getUid())) {
            if(c.getQuarter() == Quarter.WI && c.getYear() == 2022 )
            classes.add(c.getSubject()+" "+c.getCourseNumber());
        }

        return classes;
    }
}

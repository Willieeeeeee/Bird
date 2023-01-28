package edu.ucsd.cse110.team13.bof.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.database.AppDatabase;
import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;
import edu.ucsd.cse110.team13.bof.model.database.RoomCourse;
import edu.ucsd.cse110.team13.bof.model.database.RoomProfile;
import edu.ucsd.cse110.team13.bof.model.database.RoomSession;
import edu.ucsd.cse110.team13.bof.model.database.SessionProfileCrossRef;
import edu.ucsd.cse110.team13.bof.model.database.SessionWithProfiles;

public class AppDatabaseUtil {
    private AppDatabase db;

    public AppDatabaseUtil(Context context) {
        db = AppDatabase.singleton(context);
    }

    /* Getters */
    /* - Profile & Course*/
    public boolean  hasProfile(String uid) { return (db.profileDao().get(uid) != null);          }
    public ProfileWithCourses getProfile(String uid) { return  db.profileDao().getProfileWithCourses(uid); }
    public List<ProfileWithCourses> getFavorites() { return new ArrayList<>(db.profileDao().getFavoritesWithCourses()); }
    /* - Session */
    public boolean  hasSession(int sessionId) { return (db.sessionDao().get(sessionId) != null); }
    public int      getSessionId(String name) { return (db.sessionDao().get(name) == null) ? -1 : db.sessionDao().get(name).sessionId; }
    public int      getRecentId()             { return db.sessionDao().recentId(); }
    public List<RoomSession> getAllSessions() { return db.sessionDao().getAll();   }

    //TODO: use ProfileWithCourses instead because uid is needed
    public List<ProfileWithCourses> getProfilesInSession(int sessionId) {
        if(!hasSession(sessionId)) { return null; }
        SessionWithProfiles sessionWithProfiles = db.sessionDao().getSessionWithProfiles(sessionId);
        ArrayList<ProfileWithCourses> result = new ArrayList<>();
        for(RoomProfile roomProfile : sessionWithProfiles.profiles) {
            result.add(getProfile(roomProfile.uid));
        }
        return result;
    }

    /* Modifiers */
    /**
     * Update a profile's favorite status
     * Input:  profileId - should match an existing profile
     *         favorite
     * Output: false - if the given profileId does not match a profile, nothing happens
     *         true  - modification is made
     */
    public boolean setFavorite(String uid, boolean favorite) {
        if(!hasProfile(uid)) { return false; }
        db.profileDao().updateFavorite(uid,favorite);
        return true;
    }
    /* - Profile & Course */
    /**
     * Add/update a profile and its past classes in the database, and link it to an existing session
     * Input:  sessionId - should match an existing session
     *         profile   - should not be null
     * Output: false - if the input requirement is not satisfied, nothing happens
     *         true  - profile and its course added/updated, and it now links to the given session
     *                 if profile is updated, favorite remains and only new classes will be updated
     *                 note that class size will not be checked, since we refer to the user's ones
     */
    public boolean addProfileToSession(int sessionId, ProfileWithCourses profile) {
        if(!hasSession(sessionId) || profile == null) { return false; }
        // Add/update profile to database
        // - Insert profile, duplicate uid is handled by replacing old ones except favorite info
        if(hasProfile(profile.profile.uid) && getProfile(profile.profile.uid).isFavorite()) {
            db.profileDao().insert(profile.profile);
            setFavorite(profile.profile.uid,true);
        } else {
            db.profileDao().insert(profile.profile);
        }
        // - Insert courses, only insert new ones, old ones will not be removed
        // -  old courses has the same uid, year, quarter, subject and course number
        for(IClass c : profile.courses) {
            if(db.courseDao().findCount(
                    profile.getUid(),
                    c.getYear(),
                    c.getQuarter(),
                    c.getSubject(),
                    c.getCourseNumber()) <= 0) {
                RoomCourse roomCourse = new RoomCourse(
                        profile.profile.uid,
                        c.getYear(),
                        c.getQuarter(),
                        c.getSize(),
                        c.getSubject(),
                        c.getCourseNumber());
                db.courseDao().insert(roomCourse);
            }
        }
        // Link profile with session
        SessionProfileCrossRef ref = new SessionProfileCrossRef(sessionId, profile.getUid());
        db.sessionDao().insert(ref);
        return true;
    }
    /* - Session */
    /**
     * Create a new session in the database with the given name
     * Input:  name - name of the session created | should not match any existing session names
     * Output: -1        - if the given session match an existing session, nothing happens
     *         sessionId - sessionId of the new session
     */
    public int createSession() {
        String autoName = makeSessionName(Calendar.getInstance().getTime());
        db.sessionDao().insert(new RoomSession(autoName));
        return db.sessionDao().get(autoName).sessionId;
    }

    public int createSession(String sessionName) {
        if(db.sessionDao().get(sessionName) != null) { return -1; }
        db.sessionDao().insert(new RoomSession(sessionName));
        return db.sessionDao().get(sessionName).sessionId;
    }
    /**
     * Rename an existing session in the database with the given sessionId
     * Input:  sessionId - should match an existing session
     * Output: false - if the given sessionId does not match any session, nothing happens
     *         true  - the session corresponds to the given session id is renamed
     */
    public boolean renameSession(int sessionId, String name) {
        if(!hasSession(sessionId)) { return false; }
        db.sessionDao().renameSession(sessionId,name);
        return true;
    }

    /* Others */
    /** TODO
     * A helper method to convert given time to a session name described in MS2 requirement
     * Input:  date - creation date of the session | should not be null or this method will crash
     * Output: sessionName - a string in the format ""
     */
    private static final String FORMAT_AUTO_SESSION_NAME = "M/d/yy h:mma";
    public static String makeSessionName(Date date) {
        return new SimpleDateFormat(FORMAT_AUTO_SESSION_NAME, Locale.US).format(date);
    }
}

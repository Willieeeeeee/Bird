package edu.ucsd.cse110.team13.bof;

import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.encodeMessage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;

import edu.ucsd.cse110.team13.bof.mock.DebugPage;
import edu.ucsd.cse110.team13.bof.mock.MockNearbyActivity;
import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;
import edu.ucsd.cse110.team13.bof.util.AppDatabaseUtil;
import edu.ucsd.cse110.team13.bof.util.NearbyListener;
import edu.ucsd.cse110.team13.bof.util.NearbyUtil;
import edu.ucsd.cse110.team13.bof.util.NearbyWaveListener;
import edu.ucsd.cse110.team13.bof.util.UserProfileUtil;

public class MainActivity extends AppCompatActivity
        implements PopupMenu.OnMenuItemClickListener {
    public static final int CODE_MOCK_REQUEST   = 404,
                            CODE_RESUME_SESSION = 1,
                            CODE_SAVE_SESSION   = 2,
                            MOCK_WAVE_REQUEST_CODE = 69,
                            CODE_FAVORITE_UPDATE = 414;

    private String   userUid;
    private ProfileWithCourses userProfile;

    // recycler view objects used to manipulate
    protected RecyclerView studentsRecyclerView;
    protected StudentsViewAdapter studentsViewAdapter;

    private UserProfileUtil userProfileUtil;
    private AppDatabaseUtil appDatabaseUtil;

    // create message listener for receiving similar "Student(s)"
    private boolean hasActiveSession;
    private boolean needRenameSession;
    private int activeSessionId;
    private NearbyListener msgListener;
    private NearbyWaveListener msgListenerWave;
    private Message userMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userProfileUtil = new UserProfileUtil(this);
        appDatabaseUtil = new AppDatabaseUtil(this);
        NearbyUtil.setMainActivity(this);
        msgListener = new NearbyListener(this::onProfileReceived, this::onWaveUidsReceived);

        /* Setup ActionBar (menu bar) using toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button scanBtn = findViewById(R.id.scan_btn);
        scanBtn.setBackgroundColor(getColor(R.color.teal_700));

        /* Initialize recycler view */
        studentsRecyclerView = findViewById(R.id.discover_students_view);
        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        updateActiveSession(false,false,-1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* determine if the first time profile editing is completed */
        if(!launchUnfinishedEditActivity()) { return; }
        updateUserProfile();
        if(hasActiveSession) {
            Nearby.getMessagesClient(this).subscribe(msgListener);
        }
        studentsViewAdapter.refresh();
    }

    public void onProfileReceived(ProfileWithCourses profile) {
        updateUserProfile();
        if(userProfile.hasCommonClasses(profile)) {
            // TODO: check if profile with the given uid exists in the database
            if(appDatabaseUtil.hasProfile(profile.getUid())) {
                // matched profile is on screen and exists in the database,
                //   but its info and classes might differ so update ui and database accordingly
                /* 1. Database - update student to the database */
                appDatabaseUtil.addProfileToSession(activeSessionId,profile);
                /* 2. UI       - update profile on the screen */
                studentsViewAdapter.refresh();
            } else {
                // otherwise, matched profile should be added
                /* 1. Database - add student to the database */
                appDatabaseUtil.addProfileToSession(activeSessionId,profile);
                /* 2. UI       - add profile to the screen */
                studentsViewAdapter.refresh();
            }
        }
    }

    public void onWaveUidsReceived(String uid, List<String> uids) {
        /* Check if user's uid is listed in the list */
        if(uids.contains(userUid)) {
            /* Notify StudentsViewAdapter with the given uid */
            studentsViewAdapter.receiveWaveFrom(uid);
        }
    }

    public void onWaveToReceived(String uid) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        /* Pause listener to avoid null ptr exception */
        if(hasActiveSession) {
            Nearby.getMessagesClient(this).unsubscribe(msgListener);
        }
    }

    @Override
    protected void onDestroy() {
        updateActiveSession(false,false,-1);
        super.onDestroy();
    }

    public void onScanBtnClicked(View view) {
        if(hasActiveSession) {
            /* Stop the active session */
            if(!needRenameSession) {
                /* -- If there's no need to rename session */
                updateActiveSession(false,false,-1);
            } else {
                /* -- If user need to the rename session */
                List<String> currentClassNames= userProfileUtil.getCurrentClassNames(Calendar.getInstance().getTime());
                if(currentClassNames.isEmpty()){
                    /* -- user does not have enrolled classes this semester */
                    EditText sessionNameInput = new EditText(this);
                    sessionNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                    dialogBuilder
                            .setTitle("Save this session")
                            .setView(sessionNameInput)
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String input = sessionNameInput.getText().toString().trim();
                                    if(input.isEmpty()) {
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setTitle("Empty Session Name")
                                                .setMessage("Please enter a non-empty session name")
                                                .setNeutralButton("OK", (dialogInterface1, i1) -> { })
                                                .show();
                                        return;
                                    } else if(appDatabaseUtil.getSessionId(input) != -1) {
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setTitle("Duplicate Session Name")
                                                .setMessage("There is already a session named " + input + ". Please try a different name.")
                                                .setNeutralButton("OK", (dialogInterface1, i1) -> { })
                                                .show();
                                        return;
                                    }
                                    appDatabaseUtil.renameSession(
                                            appDatabaseUtil.getRecentId(),
                                            sessionNameInput.getText().toString());
                                    updateActiveSession(false,false,-1);
                                }
                            });
                    dialogBuilder.show();
                } else {
                    /* -- user has enrolled classes this semester */
                    Intent intent = new Intent(this, SaveSession.class);
                    startActivityForResult(intent,CODE_SAVE_SESSION);
                }
            }
        } else {
            /* Start a session */
            if(appDatabaseUtil.getAllSessions().isEmpty()) {
                /* -- If there's no saved session, start a new session without any prompt */
                activeSessionId = appDatabaseUtil.createSession();
                updateActiveSession(true,true,activeSessionId);
            } else {
                /* -- If there is saved session, start a prompt to let user decide */
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder
                        .setTitle("Starting discovery")
                        .setMessage("Would you like to start a new session or resume a previous one?")
                        .setPositiveButton("Resume a previous session", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(),ResumeSessionActivity.class);
                                startActivityForResult(intent,CODE_RESUME_SESSION);
                            }
                        })
                        .setNegativeButton("Start a new session", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                activeSessionId = appDatabaseUtil.createSession();
                                updateActiveSession(true,true,activeSessionId);
                            }
                        })
                        .setCancelable(true);
                alertBuilder.create().show();
            }
        }
    }

    public void onMenuBtnClicked(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main_activity);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_sort_filter:
                if(!hasActiveSession) {
                    // show a toast saying an active session required
                    Toast.makeText(
                            this,
                            "Please start a discovery session first",
                            Toast.LENGTH_LONG).show();
                } else {
                    // launch select sort/filter menu
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder .setTitle("Select sort/filter method:")
                            .setItems(
                                    new String[]{"Sort by common class",
                                            "Sort by recent",
                                            "Sort by class size"},
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            switch(i) {
                                                case 0:
                                                    studentsViewAdapter.setSortStrategy(StudentsViewAdapter.SORT_BY_COMMON);
                                                    return;
                                                case 1:
                                                    studentsViewAdapter.setSortStrategy(StudentsViewAdapter.SORT_BY_RECENT);
                                                    return;
                                                case 2:
                                                    studentsViewAdapter.setSortStrategy(StudentsViewAdapter.SORT_BY_SIZE);
                                                    return;
                                                default:
                                                    return;
                                            }
                                        }
                                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return true;
            case R.id.menu_add_classes:
                Intent intentClass = new Intent(this,PastClassUI.class);
                startActivity(intentClass);
                return true;
            case R.id.menu_view_favorites:
                Intent intentFavorite = new Intent(this,ViewSessionActivity.class);
                intentFavorite.putExtra(ViewSessionActivity.CODE_FAVORITE,true);
                startActivity(intentFavorite);
                return true;
            case R.id.menu_view_session:
                if(hasActiveSession) {
                    // show a toast saying an active session is running
                    Toast.makeText(
                            this,
                            "Please end your current session first",
                            Toast.LENGTH_LONG).show();
                    return true;
                }
                Intent intentSession = new Intent(this,ViewSessionListActivity.class);
                startActivity(intentSession);
                return true;
            case R.id.menu_mock:
                if(!hasActiveSession) {
                    Toast.makeText(
                            this,
                            "Note: Nothing will happen without starting a session first",
                            Toast.LENGTH_LONG).show();
                }
                Intent intentMock = new Intent(this,MockNearbyActivity.class);
                startActivityForResult(intentMock, CODE_MOCK_REQUEST);
                return true;
            case R.id.menu_debug:
                Intent intentDebug = new Intent(this, DebugPage.class);
                startActivity(intentDebug);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("BOF_Main_Activity", requestCode + ":" + resultCode + ":" + data);

        if(hasActiveSession && requestCode == CODE_MOCK_REQUEST && data != null) {
            String profileEncoded = data.getStringExtra(String.valueOf(CODE_MOCK_REQUEST));
            Message m = new Message(profileEncoded.getBytes(StandardCharsets.UTF_8));
            msgListener.onFound(m);
            return;
        }

        if(requestCode == CODE_RESUME_SESSION && data != null) {
            updateActiveSession(
                    true,
                    false,
                    data.getIntExtra(String.valueOf(CODE_RESUME_SESSION),-1));
            return;
        }

        if(requestCode == CODE_SAVE_SESSION && data != null) {
            if(data.getBooleanExtra(String.valueOf(CODE_SAVE_SESSION),false)) {
                updateActiveSession(false, false, -1);
            }
            return;
        }

        if(hasActiveSession && requestCode == MOCK_WAVE_REQUEST_CODE && data != null) {
            String studentIdentifier = data.getStringExtra("student_id");
            Message m = new Message(studentIdentifier.getBytes(StandardCharsets.UTF_8));
            msgListenerWave.onFound(m);
        }

        if(requestCode == CODE_FAVORITE_UPDATE && data != null){
            String student_id = data.getStringExtra("student_id");

            Log.d("student_dump", "student in onActivityResult(): \n"
                    + "setting status too:" + "\n"
                    + "favorite bool: " + "TODO" + "\n"
                    + "waved bool: " + "TODO" + "\n"
                    + "student id: " + student_id);

//            studentsViewAdapter.sentWaveTo(student_id, waveSent);
//            studentsViewAdapter.favoriteStatus(student_id, favorite);
        }
    }

    public boolean launchUnfinishedEditActivity() {
        UserProfileUtil userProfileUtil = new UserProfileUtil(this);
        if(userProfileUtil.needUid()) {
            userProfileUtil.setUid();
        }

        if(userProfileUtil.needFirstName()) {
            Intent intentName = new Intent(this, SignInProfile.class);
            startActivity(intentName);
            return false;
        } else if(userProfileUtil.needHeadshotUrl()) {
            Intent intentUrl = new Intent(this, AddPhotoActivity.class);
            startActivity(intentUrl);
            return false;
        } else if(userProfileUtil.needClasses()) {
            Intent intentClass = new Intent(this, PastClassUI.class);
            startActivity(intentClass);
            return false;
        }

        return true;
    }

    public void updateUserProfile() {
        userUid     = userProfileUtil.getUid();
        userProfile = userProfileUtil.getUserProfile();
        updateNearbyMessage();
    }

    private void updateActiveSession(boolean isActive, boolean needRename, int sessionId) {
        Log.i("BOF_Main_Activity", "Active Session Status: " + (isActive ? "active " + sessionId : "inactive"));
        /* 1. Update nearby service */
        if(hasActiveSession != isActive) { hasActiveSession = isActive; toggleNearby(); }

        hasActiveSession = isActive;
        needRenameSession = (isActive) ? needRename : false;
        activeSessionId = (isActive) ? sessionId : -1;

        /* 2. Update main screen UI */
        Button scanBtn = findViewById(R.id.scan_btn);
        if(isActive) {
            studentsViewAdapter = new StudentsViewAdapter(userProfileUtil,appDatabaseUtil,activeSessionId,true,false);
            scanBtn.setText(getString(R.string.scan_btn_stop));
            scanBtn.setBackgroundColor(getColor(R.color.purple_200));
        } else {
            studentsViewAdapter = new StudentsViewAdapter(userProfileUtil,appDatabaseUtil,activeSessionId,false,false);
            scanBtn.setText(getString(R.string.scan_btn_start));
            scanBtn.setBackgroundColor(getColor(R.color.teal_700));
        }
        studentsRecyclerView.setAdapter(studentsViewAdapter);
    }

    /* Update the user profile and waving uids of the Nearby message */
    private void updateNearbyMessage() {
        if(hasActiveSession) {
            Nearby.getMessagesClient(this).unpublish(userMessage);
            userMessage = new Message(encodeMessage(userProfileUtil.getUserProfile(), NearbyUtil.getWavedUids()).getBytes(StandardCharsets.UTF_8));
            Nearby.getMessagesClient(this).publish(userMessage);
            Log.i("BOF_Nearby", "Broadcast update: " + encodeMessage(userProfileUtil.getUserProfile(), NearbyUtil.getWavedUids()));
        }
    }

    /* Must only be called when hasActiveSession is toggled */
    private void toggleNearby() {
        if(!hasActiveSession) {
            NearbyUtil.clearWavedUids();
            Nearby.getMessagesClient(this).unpublish(userMessage);
            Nearby.getMessagesClient(this).unsubscribe(msgListener);
            Log.i("BOF_Nearby", "Broadcast end");
        } else {
            userMessage = new Message(encodeMessage(userProfileUtil.getUserProfile(), NearbyUtil.getWavedUids()).getBytes(StandardCharsets.UTF_8));
            Nearby.getMessagesClient(this)
                    .publish(userMessage)
                    .addOnFailureListener(e -> Log.i("BOF_Nearby", "publish failed: " + e.toString()))
                    .addOnSuccessListener(unused -> Log.i("BOF_Nearby", "publish success: "));
            Nearby.getMessagesClient(this)
                    .subscribe(msgListener)
                    .addOnFailureListener(e -> Log.i("BOF_Nearby", "subscribe failed: " + e.toString()))
                    .addOnSuccessListener(unused -> Log.i("BOF_Nearby", "subscribe success: "));
            Log.i("BOF_Nearby", "Broadcasting: " + encodeMessage(userProfileUtil.getUserProfile(), NearbyUtil.getWavedUids()));
        }
    }
}
package edu.ucsd.cse110.team13.bof;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.team13.bof.model.database.RoomSession;
import edu.ucsd.cse110.team13.bof.util.AppDatabaseUtil;

public class ResumeSessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_session);
        AppDatabaseUtil appDatabaseUtil = new AppDatabaseUtil(this);

        List<String> sessionNames = new ArrayList<>();
        List<RoomSession> roomSessions = appDatabaseUtil.getAllSessions();
        for(int i = 0; i < roomSessions.size(); i++){
            sessionNames.add(roomSessions.get(i).sessionName);
        }

        ListView sessionList = findViewById(R.id.resume_list);
        // TODO: define a session row layout file for full-width clickable row
        ArrayAdapter<String> sessionListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sessionNames);
        sessionList.setAdapter(sessionListAdapter);
        sessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String sessionName = sessionNames.get(i);
                Intent intent = new Intent();
                intent.putExtra(String.valueOf(MainActivity.CODE_RESUME_SESSION),appDatabaseUtil.getSessionId(sessionName));
                setResult(MainActivity.CODE_RESUME_SESSION, intent);
                finish();
            }
        });
    }
}
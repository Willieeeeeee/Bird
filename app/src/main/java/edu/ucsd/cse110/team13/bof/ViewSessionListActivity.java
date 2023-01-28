package edu.ucsd.cse110.team13.bof;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import edu.ucsd.cse110.team13.bof.util.AppDatabaseUtil;

public class ViewSessionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_session_list);

        RecyclerView sessionsRecyclerView = findViewById(R.id.sessions_view);
        sessionsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        sessionsRecyclerView.setAdapter(new SessionViewAdapter(new AppDatabaseUtil(this)));
    }
}
package edu.ucsd.cse110.team13.bof;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.team13.bof.model.database.RoomSession;
import edu.ucsd.cse110.team13.bof.util.AppDatabaseUtil;

public class RenameSession extends AppCompatActivity {
    List<String> CurrentSession = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_session);
        AppDatabaseUtil appDatabaseUtil = new AppDatabaseUtil(this);

        List<RoomSession> allRoomSession=appDatabaseUtil.getAllSessions();
        for(int i=0;i<allRoomSession.size();i++){
            CurrentSession.add(allRoomSession.get(i).sessionName);
        }
        ListView sessionList = findViewById(R.id.rename_chooseList);
        // TODO: use a better layout for full-width click support
        ArrayAdapter<String> currentClassAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CurrentSession);
        sessionList.setAdapter(currentClassAdapter);

        sessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String classes=CurrentSession.get(i);
                TextView name=findViewById(R.id.textView5);
                name.setText(classes);
            }
        });
    }

    public void rename(View view) {
        AppDatabaseUtil appDatabaseUtil = new AppDatabaseUtil(this);
        TextView name=findViewById(R.id.textView5);
        EditText ChangeName=findViewById(R.id.editTextTextPersonName2);
        appDatabaseUtil.renameSession(appDatabaseUtil.getSessionId(name.getText().toString()),ChangeName.getText().toString());
        List<RoomSession> allRoomSession=appDatabaseUtil.getAllSessions();
        List<String> CurrentSession = new ArrayList<>();
        for(int i=0;i<allRoomSession.size();i++){
            CurrentSession.add(allRoomSession.get(i).sessionName);
        }
        ListView sessionList = findViewById(R.id.rename_chooseList);
        ArrayAdapter<String> currentclassAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CurrentSession);
        sessionList.setAdapter(currentclassAdapter);

    }
}
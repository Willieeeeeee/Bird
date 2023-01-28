package edu.ucsd.cse110.team13.bof;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import edu.ucsd.cse110.team13.bof.util.AppDatabaseUtil;
import edu.ucsd.cse110.team13.bof.util.UserProfileUtil;

public class SaveSession extends AppCompatActivity{
    ListView sessionList;
    List<String> CurrentClass;
    UserProfileUtil userProfileUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_session);
        userProfileUtil = new UserProfileUtil(this);
        CurrentClass= userProfileUtil.getCurrentClassNames(Calendar.getInstance().getTime());
        sessionList = findViewById(R.id.list1);
        ArrayAdapter<String> currentclassAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CurrentClass);
        sessionList.setAdapter(currentclassAdapter);
        sessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String classes=CurrentClass.get(i);
                TextView name=findViewById(R.id.editTextTextPersonName4);
                name.setText(classes);
            }
        });
    }

    public void Save(View view) {
        AppDatabaseUtil appDatabaseUtil = new AppDatabaseUtil(this);
        TextView name = findViewById(R.id.editTextTextPersonName4);
        appDatabaseUtil.renameSession(appDatabaseUtil.getRecentId(),name.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(String.valueOf(MainActivity.CODE_SAVE_SESSION),true);
        setResult(MainActivity.CODE_SAVE_SESSION, intent);
        finish();
    }
}
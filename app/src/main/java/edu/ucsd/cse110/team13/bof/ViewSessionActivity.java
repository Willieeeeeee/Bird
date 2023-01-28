package edu.ucsd.cse110.team13.bof;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.ucsd.cse110.team13.bof.util.AppDatabaseUtil;
import edu.ucsd.cse110.team13.bof.util.UserProfileUtil;

public class ViewSessionActivity extends AppCompatActivity {
    public static final String  CODE_SESSION_ID   = "sessionId",
                                CODE_SESSION_NAME = "sessionName",
                                CODE_FAVORITE     = "favorite";

    StudentsViewAdapter studentsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_session);

        Intent intent = getIntent();
        int sessionId = intent.getIntExtra(CODE_SESSION_ID,-1);
        boolean isViewingFavorite = intent.getBooleanExtra(CODE_FAVORITE, false);

        /* Setup ActionBar (menu bar) using toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(
                isViewingFavorite ?
                "Favorite" :
                intent.getStringExtra(CODE_SESSION_NAME));

        RecyclerView studentsRecyclerView = findViewById(R.id.session_students_view);
        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        studentsViewAdapter = new StudentsViewAdapter(
                new UserProfileUtil(this),
                new AppDatabaseUtil(this),
                sessionId,
                false,
                isViewingFavorite);
        studentsRecyclerView.setAdapter(studentsViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        studentsViewAdapter.refresh();
    }

    public void onSortBtnClicked(View view) {
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
}
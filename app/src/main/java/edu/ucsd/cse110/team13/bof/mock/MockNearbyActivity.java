package edu.ucsd.cse110.team13.bof.mock;

import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.decodeMessage;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.ucsd.cse110.team13.bof.MainActivity;
import edu.ucsd.cse110.team13.bof.R;
import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;
import edu.ucsd.cse110.team13.bof.util.CSVUtil;
import edu.ucsd.cse110.team13.bof.util.NearbyUtil;
import edu.ucsd.cse110.team13.bof.util.UserProfileUtil;

public class MockNearbyActivity extends AppCompatActivity {
    private EditText recordTextview;
    private Button newMsgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_nearby);

        newMsgBtn = findViewById(R.id.new_msg_btn);
        recordTextview = findViewById(R.id.record_textview);

        /* Setup ActionBar (menu bar) using toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Add listener to let newMsgBtn text changes dynamically (empty - go back | input - enter) */
        recordTextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                newMsgBtn.setText(
                        recordTextview.getText().length() == 0 ? "Go Back" : "Enter"
                );
            }
        });
    }

    public void onNewMsgBtnClickedDev(View view) {
        /* Exit - the button says go back */
        if(recordTextview.getText().toString().trim().length() == 0) { finish(); return; }

        /* Attempt to parse & mock broadcast - the button says enter */
        // Parse CSV data
        String input = recordTextview.getText().toString().trim();
        Optional<Pair<ProfileWithCourses, List<String>>> parseOp = decodeMessage(input);

//        ArrayList<String> urlRow = new ArrayList<>();
//        UserProfileUtil userProfileUtil = new UserProfileUtil(this);
//        urlRow.add(userProfileUtil.getUid());
//        urlRow.add(NearbyUtil.WAVE_PREFIX);
//        urlRow.add("");
//        urlRow.add("");
//        urlRow.add("");
//        input += "\n" + CSVUtil.toCSVSimple(new ArrayList<ArrayList<String>>(){{ add(urlRow); }});

        if(!parseOp.isPresent()) {
            // Display error message if input is invalid
            showAlert("CSV Parse Error", "Input string cannot be parsed.");
            recordTextview.setText("");
        } else {
            // Display parsed result
            // NO INPUT CHECK HERE, assuming csv follows the order - name, url, class
//            StringBuilder logBuilder = new StringBuilder();
//
//            String firstName = parseOp.get().get(0).get(0);
//            String url       = parseOp.get().get(1).get(0);
//
//            logBuilder.append(firstName).append('\n')
//                      .append(url).append('\n');
//
//            for(int i = 2; i < parseOp.get().size(); i++) {
//                logBuilder.append(parseOp.get().get(i).get(0)).append(' ')
//                          .append(parseOp.get().get(i).get(1)).append(' ')
//                          .append(parseOp.get().get(i).get(2)).append(' ')
//                          .append(parseOp.get().get(i).get(3)).append('\n');
//            }
//
//            mProfile profile = new mProfile(parseOp.get().get(0).get(0), parseOp.get().get(1).get(0));
//
//            for(int i = 2; i < parseOp.get().size(); i++) {
//                mClass c = new mClass(Integer.parseInt(parseOp.get().get(i).get(0)),
//                        QuarterConverter.stringToQuarter(parseOp.get().get(i).get(1)),
//                        ClassSizeConverter.stringToSize(parseOp.get().get(i).get(2)),
//                        parseOp.get().get(i).get(3),
//                        parseOp.get().get(i).get(4));
//                profile.addClass(c);
//            }

            Intent intent = new Intent();
            intent.putExtra(String.valueOf(MainActivity.CODE_MOCK_REQUEST), input);
            setResult(MainActivity.CODE_MOCK_REQUEST, intent);
            finish();
        }
    }

    private void showAlert(String title, String msg) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", (dialog, id) -> { dialog.cancel(); })
                .setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public void onDevBtnClicked(View view) {
        Intent intent = new Intent(this, DebugPage.class);
        startActivity(intent);
    }
}
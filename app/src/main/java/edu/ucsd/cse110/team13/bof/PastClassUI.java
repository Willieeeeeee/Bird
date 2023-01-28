package edu.ucsd.cse110.team13.bof;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.Calendar;

import edu.ucsd.cse110.team13.bof.model.ClassSize;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.mClass;
import edu.ucsd.cse110.team13.bof.util.AlertDialogUtil;
import edu.ucsd.cse110.team13.bof.util.UserProfileUtil;


public class PastClassUI extends AppCompatActivity {
    private static final int START_YEAR = 2010,
                             E_END_YEAR = 2020;  // the end year to use if java gives year <= 2010
    UserProfileUtil userProfileUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_class_ui);
        /* Set up year spinner (drop down menu) */
        // set up years array (descending) used for year spinner
        int endYear = Calendar.getInstance().get(Calendar.YEAR);
        if(endYear < START_YEAR) { endYear = E_END_YEAR; }
        int yearsSize = endYear - START_YEAR + 1;
        Integer[] years = new Integer[yearsSize];
        for(int i = 0; i < yearsSize; i++) { years[i] = endYear - i; }
        // inflate year spinner with years array
        Spinner yearSpinner = findViewById(R.id.edit_class_year_spinner);
        yearSpinner.setAdapter(new ArrayAdapter<Integer>(this,
                android.R.layout.simple_dropdown_item_1line,
                years));

        /* Set up quarter spinner (drop down menu) */
        Spinner quarterSpinner = findViewById(R.id.edit_class_quarter_spinner);
        quarterSpinner.setAdapter(new ArrayAdapter<Quarter>(this,
                android.R.layout.simple_dropdown_item_1line,
                Quarter.values()));

        /* Set up class size spinner (drop down menu) */
        Spinner sizeSpinner = findViewById(R.id.edit_class_size_spinner);
        sizeSpinner.setAdapter(new ArrayAdapter<ClassSize>(this,
                android.R.layout.simple_dropdown_item_1line,
                ClassSize.values()));

        /* Get an instance of user profile database */
        userProfileUtil = new UserProfileUtil(this);
    }



    public void onLaunchProfileClicked(View view) {
        if(!userProfileUtil.setClassesAsFinished()) {
            AlertDialogUtil.showAlert(
                    this,
                    "Cannot Proceed",
                    "You must have at least one class in your profile");
            return;
        }

        finish();
    }

    public void enter(View view) {
        Spinner yearSpinner    = findViewById(R.id.edit_class_year_spinner);
        Spinner quarterSpinner = findViewById(R.id.edit_class_quarter_spinner);
        Spinner sizeSpinner    = findViewById(R.id.edit_class_size_spinner);
        TextView subject       = findViewById(R.id.edit_class_subject_textview);
        TextView courseID      = findViewById(R.id.edit_class_course_number_textview);

        int     year    = (int)        yearSpinner.getSelectedItem();
        Quarter quarter = (Quarter) quarterSpinner.getSelectedItem();
        ClassSize size  = (ClassSize)  sizeSpinner.getSelectedItem();
        String subject_text=subject.getText().toString().trim().toUpperCase();
        String courseID_text=courseID.getText().toString().trim().toUpperCase();

        if(subject_text.length() == 0 || courseID_text.length() == 0) {
            AlertDialogUtil.showAlert(
                    this,
                    "Missing Fields",
                    "Please fill all class info before submitting");
            return;
        }

        mClass newClass = new mClass(year,quarter,size,subject_text,courseID_text);
        if(userProfileUtil.addClass(newClass)) {
            AlertDialogUtil.showAlert(
                    this,
                    "Success",
                    "You have successfully entered your class");
        } else {
            AlertDialogUtil.showAlert(
                    this,
                    "Duplicate Classes in Profile",
                    "Looks like you've already entered this class");
        }
    }
}
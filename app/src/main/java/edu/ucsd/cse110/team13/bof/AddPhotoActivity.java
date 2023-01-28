package edu.ucsd.cse110.team13.bof;

import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import edu.ucsd.cse110.team13.bof.util.AlertDialogUtil;
import edu.ucsd.cse110.team13.bof.util.UserProfileUtil;

public class AddPhotoActivity extends AppCompatActivity {
    UserProfileUtil addUrl;
    String url;
    boolean validUrl = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        addUrl = new UserProfileUtil(this);
    }

    public void onSubmitClicked(View view){
        ImageView z=(ImageView) findViewById(R.id.imageView);
        EditText input=(EditText) findViewById(R.id.url_text);

        url = input.getText().toString().trim();
        if(url.length()==0){
            validUrl = false;
            AlertDialogUtil.showAlert(
                    this,
                    "Empty URL",
                    "You must enter a URL before submitting");
        } else if(!URLUtil.isValidUrl(url)){
            validUrl = false;
            AlertDialogUtil.showAlert(
                    this,
                    "Invalid URL",
                    "The URL you entered is invalid");
        } else if(!url.contains("image") && !url.contains("png") && !url.contains("jpg") && !url.contains("jpeg")){
            validUrl = false;
            AlertDialogUtil.showAlert(
                    this,
                    "Invalid",
                    "The URL you entered does not contain an image");
        } else { validUrl = true; }

        // TODO: fix empty url bug
        if(!validUrl) {
            ((Button)findViewById(R.id.back_button)).setText("Skip");
            z.setVisibility(View.INVISIBLE);
        } else {
            ((Button)findViewById(R.id.back_button)).setText("Enter");
            Picasso.get().load(url).into(z);
            z.setVisibility(View.VISIBLE);
        }
    }


    public void onGoBackClicked(View view) {
        if(!validUrl) { addUrl.setHeadshotUrlAsEmpty(); }
        else { addUrl.setHeadshotUrl(url); }
        finish();
    }
}
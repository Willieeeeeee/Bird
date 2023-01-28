package edu.ucsd.cse110.team13.bof;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.team13.bof.util.AlertDialogUtil;
import edu.ucsd.cse110.team13.bof.util.UserProfileUtil;

public class SignInProfile extends AppCompatActivity {
    UserProfileUtil userProfileUtil;

    //private ActivityGoogleSignInBinding binding;
    //private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_profile);

        userProfileUtil = new UserProfileUtil(this);
        // Google sign-in logic
        // binding=ActivityGoogleSignInBinding.inflate(getLayoutInflater());
        // firebaseAuth=FirebaseAuth.getInstance();
        //GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        //String personGivenName = acct.getGivenName();
        //TextView GoogleName=findViewById(R.id.nameTV);
        //GoogleName.setText(personGivenName);
    }

    public void onEnterClicked(View view) {
        TextView nameTextview = (TextView) findViewById(R.id.edit_name_textview);
        String name = nameTextview.getText().toString().trim();

        /* input check */
        if(name.isEmpty()){
            AlertDialogUtil.showAlert(
                    this,
                    "Empty First Name",
                    "Your first name cannot be empty");
            return;
        }

        /* update backend */
        userProfileUtil.setFirstName(name);
        finish();
    }
}
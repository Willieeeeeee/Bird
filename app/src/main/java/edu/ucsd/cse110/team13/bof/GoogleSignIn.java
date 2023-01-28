package edu.ucsd.cse110.team13.bof;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import edu.ucsd.cse110.team13.bof.databinding.ActivityGoogleSignInBinding;

public class GoogleSignIn extends AppCompatActivity {

        private ActivityGoogleSignInBinding binding;
        private static final int Sign = 100;
        private GoogleSignInClient googleSignInClient;

        //private FirebaseAuth firebaseAuth;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityGoogleSignInBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            //configure the Google Signin
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            googleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, googleSignInOptions);

            //Init firebase
            //firebaseAuth = FirebaseAuth.getInstance();

            binding.googleSignInBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //google sign in
                    Intent intent = googleSignInClient.getSignInIntent();
                    startActivityForResult(intent, Sign);
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            if (requestCode == Sign) {
                Task<GoogleSignInAccount> task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }

        private void handleSignInResult(Task<GoogleSignInAccount> task) {
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // Signed in successfully, show authenticated UI.
                updateUI(account);
            } catch (ApiException e) {
                Log.w("Google Login", "signInResult:failed code=" + e.getStatusCode());
                updateUI(null);
            }
        }

        private void updateUI(GoogleSignInAccount account) {
            if (account != null) {
                Intent intent = new Intent(GoogleSignIn.this, SignInProfile.class);
                startActivity(intent);
            }
        }
}
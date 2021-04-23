package com.example.survey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.example.survey.utils.SharedPrefs;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {

    private Button emailBtn;
    private Button googleBtn;
    private myListener listener;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private com.facebook.login.widget.LoginButton loginButton;
    private static final String EMAIL = "email";
    private CallbackManager mCallbackManager;
    private CallbackManager mFacebookCallbackManager;
    private RequestQueue requestQueue;
    private String mVerificationId;
    private SharedPrefs sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookCallbackManager=CallbackManager.Factory.create();
        googleBtn=(Button)findViewById(R.id.btnGoogle);
        emailBtn=(Button)findViewById(R.id.buttonEmail);
        loginButton = (LoginButton) findViewById(R.id.btnFacebook);
        loginButton.setPermissions("email","public_profile","user_friends");
        sharedPrefs=new SharedPrefs(getApplicationContext());


         //loginButton.setLoginText(R.string.faceBookBtn);
        loginButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });
        listener=new myListener();
        emailBtn.setOnClickListener(listener);
        googleBtn.setOnClickListener(listener);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }



    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            Gson gson=new Gson();
                            String sUser=gson.toJson(user);
                            sharedPrefs.putUser(sUser);
                            Intent i = new Intent(MainActivity.this, CustomDashboard.class);
                            startActivity(i);
                            finish();
                        } else {
                            Log.d("Debug","----mdsg-------");
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
        else{
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Gson gson=new Gson();
                        String sUser=gson.toJson(user);
                        sharedPrefs.putUser(sUser);
                        Intent i = new Intent(MainActivity.this, CustomDashboard.class);
                        startActivity(i);
                        finish();
                    }

                });
    }

    private class myListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonEmail:
                    Intent i = new Intent(getApplicationContext(), EmailSendingActivity.class);
                    startActivity(i);
                    break;
                case R.id.btnGoogle:
                    signIn();
                    break;
            }
        }
    }


}

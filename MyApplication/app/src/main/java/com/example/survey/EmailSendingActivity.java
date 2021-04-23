package com.example.survey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.survey.R;
import com.example.survey.utils.CheckInternetConnection;
import com.example.survey.utils.SharedPrefs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

public class EmailSendingActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button btnEmailLogin;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private SharedPrefs sharedPrefs;
    public EmailSendingActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sending);
        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPassword);
        btnEmailLogin = (Button) findViewById(R.id.btnLoginInWithEmail);
        mAuth = FirebaseAuth.getInstance();
        sharedPrefs = new SharedPrefs(getApplicationContext());

        btnEmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternetConnection.isConnected(EmailSendingActivity.this)) {
                    if (username.getText().toString().isEmpty() && username.getText().toString().isEmpty()) {
                        username.setError("Required Field");
                    } else {
                        signIn();
                    }
                } else {
                    Snackbar.make(findViewById(R.id.emailLoginLayout), "Check Internet Connection", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                FirebaseUser firebaseUser =FirebaseAuth.getInstance().getCurrentUser();
                Gson gson=new Gson();
                String sUser=gson.toJson(firebaseUser);
                sharedPrefs.putUser(sUser);
                Intent i = new Intent(EmailSendingActivity.this, CustomDashboard.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Save verification ID and resending token so we can use them later
                // mVerificationId = verificationId;
                mResendToken = token;
                Intent i = new Intent(EmailSendingActivity.this, VerifyCodeActivity.class);
                i.putExtra("auth", mAuth.getCurrentUser());
                i.putExtra("verificationId", verificationId);

                startActivity(i);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };
    }


    private void signIn() {
        mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(EmailSendingActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d("hello", "Reached here");
                    linkPhoneAuthentication(mAuth);


                } else {
                    Log.d("-----------", task.getException().getLocalizedMessage());
                    Snackbar.make(findViewById(R.id.emailLoginLayout), "Authentication Failed", Snackbar.LENGTH_LONG).show();
                }

                // ...
            }
        });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }


    private void linkPhoneAuthentication(FirebaseAuth mAuth) {
        Log.d("Number","========");
//        TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        @SuppressLint("MissingPermission") String mPhoneNumber = tMgr.getLine1Number();
//        Log.d("Number",mPhoneNumber);
        startPhoneNumberVerification("+44 7803363973");

    }


}

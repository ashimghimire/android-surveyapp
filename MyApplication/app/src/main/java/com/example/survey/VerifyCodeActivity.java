package com.example.survey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyCodeActivity extends AppCompatActivity {

    private EditText txtCode;
    private Button btnCode;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        txtCode = (EditText) findViewById(R.id.txtCode);
        btnCode=(Button)findViewById(R.id.btnCode);
        currentUser = (FirebaseUser) getIntent().getParcelableExtra("auth");
        verificationId = getIntent().getStringExtra("verificationId");
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhoneNumberWithCode(txtCode.getText().toString());
            }
        });


    }
    private PhoneAuthCredential verifyPhoneNumberWithCode (String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        currentUser.linkWithCredential(credential)
                .addOnCompleteListener(VerifyCodeActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "linkWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            updateUI();
                        } else {
                            Log.w("TAG", "linkWithCredential:failure", task.getException());
                            Toast.makeText(VerifyCodeActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return credential;
    }

    private void updateUI(){
        Intent i = new Intent(VerifyCodeActivity.this, CustomDashboard.class);
        i.putExtra("auth", currentUser);
        startActivity(i);
    }

}
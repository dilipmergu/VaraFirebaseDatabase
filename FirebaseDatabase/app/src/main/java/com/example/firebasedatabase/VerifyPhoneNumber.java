package com.example.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumber extends AppCompatActivity {
    String savingverificationcodesentbysystem;
    Button verify;
    EditText enterotp;
    ProgressBar progressBar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        verify = findViewById(R.id.verify_btn);
        enterotp = findViewById(R.id.otp);
        progressBar = findViewById(R.id.progressbar);

        String usergivenphonenumber = getIntent().getStringExtra("phonenumber");
        Log.i("onCreate method","calling");
	Log.i("onCreate method","Check again");
        SendVerificationCodetoUser(usergivenphonenumber);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codeverify= enterotp.getText().toString();

                if (codeverify.isEmpty()&& codeverify.length()<6){
                    enterotp.setError("Please enter correct OTP");
                }
                progressBar.setVisibility(View.VISIBLE);
                VerifyCode(codeverify);
            }
        });

    }

    private void SendVerificationCodetoUser(String usergivenphonenumber) {
        Log.i("code sent call","code");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
               "+1"+usergivenphonenumber,    // Phone number to verify
                60,                         // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.i("sent code","sms"+s);
            savingverificationcodesentbysystem=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String userenteredcode=phoneAuthCredential.getSmsCode();

            if (userenteredcode!=null){
                progressBar.setVisibility(View.VISIBLE);
                VerifyCode(userenteredcode);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneNumber.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };

    private void VerifyCode(String usercode){

        PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(savingverificationcodesentbysystem,usercode);
        Signinwithavailablecredentials(phoneAuthCredential);
            
        }

    private void Signinwithavailablecredentials(PhoneAuthCredential phoneAuthCredential) {

        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(VerifyPhoneNumber.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Intent intent=new Intent(getApplicationContext(),HomePage.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("Verification",savingverificationcodesentbysystem);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(VerifyPhoneNumber.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}



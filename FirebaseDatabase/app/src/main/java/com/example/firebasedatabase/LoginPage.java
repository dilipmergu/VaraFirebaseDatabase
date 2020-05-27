package com.example.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    EditText email,password;
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        email=findViewById(R.id.loginemail);
        password=findViewById(R.id.loginpass);
        loginbtn=findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser();
            }
        });
    }

    private void isUser() {
        final String userenteredname=email.getEditableText().toString();
        final String userenteredpass=password.getEditableText().toString();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Registration");

        Query checkuser=reference.orderByChild("username").equalTo(userenteredname);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String passwordfromdb= dataSnapshot.child(userenteredname).child("password").getValue(String.class);
                    if (passwordfromdb.equals(userenteredpass)){

                        Intent intent=new Intent(getApplicationContext(),HomePage.class);
                        startActivity(intent);
                    }
                    else {
                        password.setError("Incorrect password");
                    }
                }
                else {
                    email.setError("No Such User");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

package com.example.firebasedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText signupname,signupphone,signupemail,signuppass;
    Button button,button1;
    FirebaseDatabase rootnode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signupemail=findViewById(R.id.email);
        signupname=findViewById(R.id.name);
        signupphone=findViewById(R.id.phone);
        signuppass=findViewById(R.id.pass);
        button=findViewById(R.id.signup);
        button1=findViewById(R.id.asignup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                rootnode=FirebaseDatabase.getInstance();
                reference=rootnode.getReference("Registration");

                String testemail=signupemail.getText().toString();
                String username=signupname.getText().toString();
                String testphone=signupphone.getText().toString();
                String testpass=signuppass.getText().toString();
                JavaHelperClass javaHelperClass=new JavaHelperClass(username,testemail,testphone,testpass);
                reference.child(username).setValue(javaHelperClass);

                Intent intent=new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent1);
            }
        });


    }
}

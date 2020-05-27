package com.example.firebasedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText signupname,signupphone,signupemail,signuppass;
    Button button,button1;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    Spinner spinner;
    int selpos;
    String textselect;

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
        spinner=findViewById(R.id.registration_spinner);

        List<String> Registerationpurpose= new ArrayList<>();

        Registerationpurpose.add(0,"Select Purpose");
        Registerationpurpose.add(1,"Register As Nanny");
        Registerationpurpose.add(2,"Register For Nanny");
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item,Registerationpurpose);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textselect= parent.getItemAtPosition(position).toString();
                selpos = position;
                System.out.println("printing..."+selpos);
                Toast.makeText(MainActivity.this,"Selected" + textselect,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent.putExtra("phonenumber",testphone);

                if (selpos==1){

                    RigisterasNanny();
                }
                else if (selpos==2) {

                    Rigisterfornanny();
                }
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
    private void RigisterasNanny() {
        rootnode=FirebaseDatabase.getInstance();
        reference=rootnode.getReference("Registr As Nanny");
        String testemail=signupemail.getText().toString();
        String username=signupname.getText().toString();
        String testphone=signupphone.getText().toString();
        String testpass=signuppass.getText().toString();
        JavaHelperClass javaHelperClass=new JavaHelperClass(username,testemail,testphone,testpass);
        reference.child(username).setValue(javaHelperClass);
    }

    private void Rigisterfornanny() {
        rootnode=FirebaseDatabase.getInstance();
        reference=rootnode.getReference("Registr For Nanny");
        String testemail=signupemail.getText().toString();
        String username=signupname.getText().toString();
        String testphone=signupphone.getText().toString();
        String testpass=signuppass.getText().toString();
        JavaHelperClass javaHelperClass=new JavaHelperClass(username,testemail,testphone,testpass);
        reference.child(username).setValue(javaHelperClass);
    }
}

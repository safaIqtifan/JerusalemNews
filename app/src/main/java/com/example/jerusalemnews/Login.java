package com.example.jerusalemnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText email,password;
    private FirebaseAuth mAuth;
    private ProgressBar pB ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pass);

        pB = (ProgressBar) findViewById(R.id.PBar);
        pB.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

    }

    public void login(View view){
        pB.setVisibility(View.INVISIBLE);

        String Email = email.getText().toString().trim();
        String Pass = password.getText().toString().trim();

        if (Email.isEmpty()){
            Toast.makeText(this, "Please Add Email.", Toast.LENGTH_SHORT).show();
        }
        else if (Pass.isEmpty()){
            Toast.makeText(this, "Please Add Password.", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(Email,Pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    Intent intent = new Intent(Login.this , MainActivity.class);
                    startActivity(intent);
                    pB.setVisibility(View.VISIBLE);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(Login.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                    pB.setVisibility(View.VISIBLE);

                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Toast.makeText(Login.this, "Task has Cancled From User , Please Try Agen",
                            Toast.LENGTH_LONG).show();
                    pB.setVisibility(View.VISIBLE);

                }
            });

        }

    }


    public void SignUp(View view) {
        Intent intent = new Intent(Login.this , CreateAccount.class);
        startActivity(intent);
    }
}

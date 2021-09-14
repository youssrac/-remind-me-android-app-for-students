package com.example.pavilion.remind2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetpassword extends AppCompatActivity {
    private EditText email;
    private Button reset;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        reset=(Button)findViewById(R.id.reset);
        email=(EditText)findViewById(R.id.user);
        auth=FirebaseAuth.getInstance();
    }

    public void reset(View view) {

        String usermail=email.getText().toString().trim();
        if (usermail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(usermail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(resetpassword.this,"task successful,Please check your Email",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(resetpassword.this,welcome.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(resetpassword.this,"Error,failed to send",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

package com.example.pavilion.remind2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText ID, email, fullname, pass,group,sirname, pass2,code;
    private FirebaseFirestore db;
    String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sid = "FMROfZpN1bZtzcWmXrmR";
        db = FirebaseFirestore.getInstance();
    }


    public void register(View view) {


            ID = (EditText) findViewById(R.id.phone);
        code = (EditText) findViewById(R.id.idn);
        sirname = (EditText) findViewById(R.id.sirname);
            email = (EditText) findViewById(R.id.email);
       group = (EditText) findViewById(R.id.group);
            fullname = (EditText) findViewById(R.id.fullname);
        pass2 = (EditText) findViewById(R.id.pass2);
        pass = (EditText) findViewById(R.id.pass);
            String id = ID.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String fname = fullname.getText().toString().trim();
        String coden = code.getText().toString().trim();
        String sname = sirname.getText().toString().trim();
        String groupe = group.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String password2 = pass2.getText().toString().trim();
        String message= "email: "+  mail +
        "\n id: "+coden +
        "\n phone: "+id +
        "\n name: "+ fname +"\n surname: "+ sname+  "\n group: "+ groupe ;


        if (mail.isEmpty()) {
                email.setError("Email is required");
                email.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                email.setError("Please enter a valid email");
                email.requestFocus();
                return;
            }


            if (fname.isEmpty()) {
                fullname.setError("Full name is required");
                fullname.requestFocus();
                return;
            }

        if (coden.isEmpty()) {
           code.setError("Student Id number is required");
           code.requestFocus();
            return;
        }

            if (id.isEmpty()) {
                ID.setError("Student phone number is required");
                ID.requestFocus();
                return;
            }
        if (groupe.isEmpty()) {
            group.setError("group is required");
            group.requestFocus();
            return;
        }

       if(!groupe.equals("02")&&!groupe.equals("01")&&!groupe.equals("1")&&!groupe.equals("2")){
            group.setText("");
        group.setError("group invalid,choose 01 or 02");
        group.requestFocus();
        return;
    }


        if (password.isEmpty()) {
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if (sname.isEmpty()) {
           sirname.setError("surname is required");
           sirname.requestFocus();
            return;
        }
        if (password.length()<6){
            pass.setError("Password is required to have at least 6 characters");
            pass.requestFocus();
            return;
        }


        if (password2.isEmpty()) {
            pass2.setError("Please confirm your password");
            pass2.requestFocus();
            return;
        }

if(!password.equals(password2)){
    pass.setError("Passwords are not equal");
    pass.requestFocus();
    return;
}


        Map<String,Object> notificationmessage=new HashMap<>();

        notificationmessage.put("phone",id);
        notificationmessage.put("code",coden);
        notificationmessage.put("name",fname);
        notificationmessage.put("surname",sname);
        notificationmessage.put("email",mail);
        notificationmessage.put("group",groupe);
        notificationmessage.put("password",password);

        notificationmessage.put("message",message);
        db.collection("student/"+sid + "/notifications").add(notificationmessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(register.this,"request sent",Toast.LENGTH_LONG).show();
                //pg.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(register.this,"Failed to send",Toast.LENGTH_LONG).show();
                // Toast.makeText(sendactivity.this,e.getMessage().toString(),Toast.LENGTH_LONG);
                //  pg.setVisibility(View.INVISIBLE);
            }
        });


    }
}

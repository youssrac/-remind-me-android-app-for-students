package com.example.pavilion.remind2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout DL;
    private ActionBarDrawerToggle btoggle;
    private TextView notificationslable,userslable,otherslable;
    private ViewPager pager;
    String current,docid;
    FirebaseFirestore db;
    String sid;
    private PagerViewAdapter adapter;
    private LinearLayout tabsview;
    private FirebaseAuth mauth;
    private FirebaseAuth firebaseAuth1;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i=getIntent();
        DL=(DrawerLayout)findViewById(R.id.drawer);
        mauth = FirebaseAuth.getInstance();
        sid = "FMROfZpN1bZtzcWmXrmR";
        btoggle=new ActionBarDrawerToggle(this,DL,R.string.Open,R.string.Close);
        db = FirebaseFirestore.getInstance();
        DL.addDrawerListener(btoggle);
        btoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      firebaseAuth1= FirebaseAuth.getInstance();


        tabsview=(LinearLayout)findViewById(R.id.tabs_layout);
        notificationslable=(TextView)findViewById(R.id.notification_txt);
        userslable=(TextView)findViewById(R.id.userlist_txt);
        otherslable=(TextView)findViewById(R.id.other_txt);
        pager=(ViewPager)findViewById(R.id.main_pager);
        adapter=new PagerViewAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        notificationslable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(0);
                notificationslable.setTextSize(20);
                userslable.setTextSize(15);
                otherslable.setTextSize(15);
            }
        });
        userslable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(1);
                notificationslable.setTextSize(15);
                userslable.setTextSize(20);
                otherslable.setTextSize(15);

            }
        });
        otherslable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(2);
                notificationslable.setTextSize(15);
                userslable.setTextSize(15);
                otherslable.setTextSize(20);
            }
        });
        navigationView=(NavigationView)findViewById(R.id.vview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {case R.id.logout :
///////////////////////
                    DatabaseReference data= FirebaseDatabase.getInstance().getReference().child("teacher").child(mauth.getCurrentUser().getUid());
data.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists())
        {mauth.signOut();
          finish();
            startActivity(new Intent(getApplicationContext(),login.class));
        }
        else{

            current = mauth.getCurrentUser().getEmail();


            if (current.equals("example@gmail.com")) {


                Map<String,Object> tokenmapremove=new HashMap<>();
                tokenmapremove.put("tokenid", FieldValue.delete());
                db.collection("student").document(sid).update(tokenmapremove).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mauth.signOut();
                        db.collection("student").document();
                        finish();
                        Intent in=new Intent(MainActivity.this,login.class);
                        startActivity(in);
                        return;
                    }
                });

                //  Toast.makeText(login.this,""+user,Toast.LENGTH_LONG).show();
            }

       else {
                docid = mauth.getCurrentUser().getUid();


                Map<String, Object> tokenmapremove = new HashMap<>();
                tokenmapremove.put("tokenid", FieldValue.delete());

                db.collection("student").document(docid).update(tokenmapremove).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mauth.signOut();
                        db.collection("student").document();
                        finish();
                        Intent in = new Intent(MainActivity.this, login.class);
                        startActivity(in);
                        return;

                    }
                });

            }

        }



    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});



                   // firebaseAuth1.signOut();
                    //startActivity(new Intent(getApplicationContext(),login.class));
                   // finish();

                    break;
                    case R.id.moreinfo :
                        startActivity(new Intent(MainActivity.this,Profile.class));
                     break;
                    case R.id.myactivities :
                        startActivity(new Intent(MainActivity.this,Declare.class));
                       break;

                    case R.id.browse :
                        startActivity(new Intent(MainActivity.this,Rateusers.class));
                        break;
                    case R.id.settings :
                        if(firebaseAuth1.getCurrentUser().getEmail().equals("example@gmail.com"))
                        {
                            startActivity(new Intent(MainActivity.this,adminconsole.class));

                        }
                        else{
                            Toast.makeText(MainActivity.this,"Permission denied",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return true;
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(btoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth1.getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this,login.class));
            finish();}
    }
}

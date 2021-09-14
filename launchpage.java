package com.example.pavilion.remind2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class launchpage extends AppCompatActivity {
    ImageView log;
    TextView name;
    Animation fromtop;
    Animation frombottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launchpage);

        log=(ImageView)findViewById(R.id.imageView);
        name=(TextView)findViewById(R.id.textView3);
        fromtop= AnimationUtils.loadAnimation(this,R.anim.fromtop);
        frombottom=AnimationUtils.loadAnimation(this,R.anim.frombottom);
        name.setAnimation(fromtop);
        log.setAnimation(frombottom);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent=new Intent(launchpage.this,welcome.class);
                startActivity(homeintent);
                finish();
            }
        },3000);


    }
}


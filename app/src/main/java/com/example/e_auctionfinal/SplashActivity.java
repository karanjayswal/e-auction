package com.example.e_auctionfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_auctionfinal.Authentication.LoginActivitiy;

public class SplashActivity extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView imageView;
    TextView tvlogo,tvDesc;
    private static int SPLASH_SCREEN = 2300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        tvlogo=findViewById(R.id.tvLogo);
        tvDesc=findViewById(R.id.tvDesc);
        imageView=findViewById(R.id.imageView2);
        imageView.setAnimation(topAnim);
        tvDesc.setAnimation(bottomAnim);
        tvlogo.setAnimation(bottomAnim);
        bottomAnim.setDuration(2000);
        topAnim.setDuration(2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivitiy.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}
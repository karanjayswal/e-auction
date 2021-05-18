package com.example.e_auctionfinal.BottomNavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.e_auctionfinal.R;

public class Buyer extends MainActivity2 {


    @Override
    int getContentViewId() {
        return R.layout.activity_buyer;
    }

    @Override
    int getNavigationMenuItemId() {
        admins = getIntent().getStringExtra("username");
        return R.id.BOTTOM_Buyer;
    }

}
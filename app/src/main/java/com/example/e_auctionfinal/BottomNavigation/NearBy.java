package com.example.e_auctionfinal.BottomNavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.e_auctionfinal.R;

public class NearBy extends MainActivity2 {

    @Override
    int getContentViewId() {
        return R.layout.activity_near_by;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.BOTTOM_NearBy;
    }

}
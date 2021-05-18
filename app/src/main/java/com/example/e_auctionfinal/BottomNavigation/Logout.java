package com.example.e_auctionfinal.BottomNavigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.e_auctionfinal.Authentication.LoginActivitiy;
import com.example.e_auctionfinal.R;

public class Logout extends MainActivity2 {
    @Override
    int getContentViewId() { AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lgout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Logout.this, LoginActivitiy.class));
                finishAffinity();

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        builder.show();
        return R.layout.activity_logout;

    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.BOTTOM_Logout;
    }
}
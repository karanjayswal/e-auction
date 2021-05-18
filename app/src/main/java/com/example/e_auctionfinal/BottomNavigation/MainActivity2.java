package com.example.e_auctionfinal.BottomNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_auctionfinal.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class MainActivity2 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    protected BottomNavigationView navigationView;
    static String admins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        navigationView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int itemId = item.getItemId();
                switch (itemId)
                {
                    case R.id.BOTTOM_About:
                        Toast.makeText(MainActivity2.this, "ABout", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity2.this,About.class);
                        intent.putExtra("username",admins);
                        startActivity(intent);
                        break;
                    case R.id.BOTTOM_Buyer:
                        Toast.makeText(MainActivity2.this, "Create", Toast.LENGTH_SHORT).show();
                        Intent create= new Intent(MainActivity2.this,Buyer.class);
                        create.putExtra("username",admins);
                        startActivity(create);
                        break;
                    case R.id.BOTTOM_NearBy:
                        Toast.makeText(MainActivity2.this, "Groups", Toast.LENGTH_SHORT).show();
                        Intent groups = new Intent(MainActivity2.this,NearBy.class);
                        groups.putExtra("username",admins);
                        startActivity(groups);
                        break;
                    case R.id.BOTTOM_Seller:
                        Toast.makeText(MainActivity2.this, "Join", Toast.LENGTH_SHORT).show();
                        Intent join = new Intent(MainActivity2.this,Seller.class);
                        join.putExtra("username",admins);
                        startActivity(join);
                        break;
                    case R.id.BOTTOM_Logout:
                        Toast.makeText(MainActivity2.this, "Logout", Toast.LENGTH_SHORT).show();
                        Intent logout = new Intent(MainActivity2.this,Logout.class);
                        startActivity(logout);
                        logout.putExtra("username",admins);
                        break;
                }

            }
        }, 300);
        return true;
    }
    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();
}
